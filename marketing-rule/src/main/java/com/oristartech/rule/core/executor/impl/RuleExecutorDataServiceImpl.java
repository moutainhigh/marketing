package com.oristartech.rule.core.executor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.base.service.IModelCategoryService;
import com.oristartech.rule.core.base.service.IModelFieldService;
import com.oristartech.rule.core.cache.IRuleDataCache;
import com.oristartech.rule.core.core.service.IRuleConditionService;
import com.oristartech.rule.core.executor.IRuleExecutorDataService;
import com.oristartech.rule.core.ws.client.service.IRuleExternDataService;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.base.vo.ModelFieldVO;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

/**
 * 规则匹配前的数据准备实现
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class RuleExecutorDataServiceImpl implements IRuleExecutorDataService {
	@Autowired
	private IModelCategoryService ruleModelCategoryService;
	@Autowired
	private IModelFieldService ruleModelFieldService;
	@Autowired
	private IRuleExternDataService ruleExternDataService;
	@Autowired
	private IRuleConditionService ruleConditionService;
	
	private static final String FIELD_SPLITER = ",";
	
//	public boolean hasDynamicField(String categoryName) {
//		return ruleModelCategoryService.hasDynamicField(categoryName);
//	}
	
//	public boolean isAutoLoadCategory(String categoryName) {
//		ModelCategoryVO category = getCategory(categoryName);
//		if(category != null && category.getIsAutoLoad() != null && category.getIsAutoLoad() == true) {
//			return true;
//		}
//		
//	    return false;
//	}
	
//	/**
//	 * 去业务系统加载category数据
//	 */
//	public Object loadCategoryData(Object categoryFact) {
//		if(categoryFact != null) {
//			Map<String, Object> map = BeanUtils.convertToMap(categoryFact);
//			String categoryName = String.valueOf(map.get(FactConstants.CATEGORY_TYPE_KEY));
//			ModelCategoryVO category = getCategory(categoryName);
//			if(category != null && category.getIsAutoLoad() != null && category.getIsAutoLoad() == true) {
//				Map<String, Object> params = new HashMap<String, Object>();
//				String queryFieldNames = category.getLoadKeyFieldNames();
//				
//				StringBuilder keySb = new StringBuilder();
//				keySb.append(categoryName + "_");
//				Object finalResult = null;
//				if(!BlankUtil.isBlank(queryFieldNames)) {
//					String[] names = queryFieldNames.split(FIELD_SPLITER);
//					for(int i=0; i < names.length; i++) {
//						keySb.append(names[i] + "=" + String.valueOf(map.get(names[i])));
//						params.put(names[i], map.get(names[i]));
//					}
//				}
//				
//				if(finalResult == null) {
//					RuleWSResultVO result = ruleExternDataService.findExternDataResultVO(category.getLoadServiceName(), params);
//					if(result.isOk()) {
//						Object data = result.getData();
//						if(data != null) {
//							if(data instanceof String) {
//								finalResult = JsonUtil.jsonToObject(data.toString(), Map.class);
//							} else {
//								finalResult = data;
//							}
//						}
//					}
//				}
//				if(finalResult != null) {
//					BeanUtils.copyProperties(map, finalResult);
//					finalResult = map;
//				}
//				return finalResult;
//			}
//		}
//	    return null;
//	}
	
	/**
	 * 加载factc对应的数据， 有些fact只是提供了基本key， 其他内容没设置，需要通过加载后接口设置， 会把原有的fact替换掉
	 */
	public void loadAndUpdateFactDatas(List<Object> factList){
		if(!BlankUtil.isBlank(factList)){
			Map<String, List<Object>> loadServiceMap = getFactLoadServiceMap(factList);
			
			for(String serviceMethod : loadServiceMap.keySet()){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("searchObjs", loadServiceMap.get(serviceMethod));
				RuleWSResultVO result = ruleExternDataService.findExternDataResultVO(serviceMethod, params);
				if(result == null || !result.isOk() || result.getData() == null) {
					continue;
				}
				
				Object data = result.getData();
				Object finalResult = null;
				if(data instanceof String) {
					finalResult = JsonUtil.jsonToCommonObject(data.toString(), Map.class);
				} else {
					finalResult = data;
				}
				
				if(finalResult != null && finalResult instanceof Collection) {
					factList.addAll((Collection) finalResult);
				} else if(finalResult != null) {
					factList.add(finalResult);
				}
			}
		}
	}
	
	public void loadAndUpdateFactFields(List<Object> factList) {
		Map<String, Map<String, Object>> fieldCacheValueMap = new HashMap<String, Map<String, Object>>();
	    for(Object fact : factList) {
	    	String categoryName = BeanUtils.getPropertyStr(fact,FactConstants.CATEGORY_TYPE_KEY);
	    	//当相同的category中相同的field而且查询参数值一样时，没必要重新去查，从缓存的map中查出来；
	    	List<ModelFieldVO> fieldVOs = ruleModelFieldService.getExternLoadFields(categoryName) ;
	    	if(BlankUtil.isBlank(fieldVOs)) {
	    		continue;
	    	}
	    	
    		for(ModelFieldVO field : fieldVOs) {
    			Map<String, Object> params = getLoadFieldParams(field, fact);
    			if(BlankUtil.isBlank(params)) {
    				continue;
    			}
    			String updateFieldName  = BlankUtil.isBlank(field.getAliasForField()) ? field.getName() : field.getAliasForField(); 
    			params.put(IRuleExecutorDataService.UPDATE_FIELD_NAME, updateFieldName);
    			String cacheKey = categoryName + "_" + updateFieldName + "_" + JsonUtil.objToJson(params);
    			Map<String, Object> cacheValue = fieldCacheValueMap.get(cacheKey) ;
    			if(!fieldCacheValueMap.containsKey(cacheKey)) {
    				cacheValue = loadExternFields(field, params);
    				fieldCacheValueMap.put(cacheKey, cacheValue);
    			}
    			if(cacheValue != null) {
    				for(String key : cacheValue.keySet()) {
    					BeanUtils.setProperty(fact, key, cacheValue.get(key));
    				}
    			}
    		}
	    }
	}
	
	/**
	 * 加载外部属性值
	 * @param field
	 * @param params
	 * @return
	 */
	private Map<String, Object> loadExternFields(ModelFieldVO field, Map<String, Object> params) {
		RuleWSResultVO result = ruleExternDataService.findExternDataResultVO(field.getLoadServiceName(), params);
		if(result== null || !result.isOk() || result.getData() == null) {
			return null;
		}
		
		Object data = result.getData();
		if(data instanceof String) {
			return (Map)JsonUtil.jsonToCommonObject(data.toString(), Map.class);
		} else {
			return (Map) data;
		}
	}
	
	private Map<String, Object> getLoadFieldParams(ModelFieldVO field, Object fact) {
		String queryKeyFieldValue = field.getQueryKeyFieldNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if(!BlankUtil.isBlank(queryKeyFieldValue)) {
			String [] queryKeyNames = queryKeyFieldValue.split(FIELD_SPLITER);
			//用treemap便于生成缓存key
			for(String keyName : queryKeyNames) {
				Object value = BeanUtils.getProperty(fact, keyName);
				if(value != null) {
					params.put(keyName, value);
				}
				
			}
		}
		return params;
	}
	
	/**
	 * 根据category loadServiceName 为key，list内容为事实
	 * @param categoryList
	 * @return map (key为loadServiceName)
	 */
	private Map<String, List<Object>> getFactLoadServiceMap(List<Object> facts){
		Map<String, ModelCategoryVO> categoryMap = new HashMap<String, ModelCategoryVO>();
		Map<String, List<Object>> serviceNamemap = new HashMap<String, List<Object>>();
		
//		会员开卡活动不会移出MemberInfo因子，因为此时并没有会员卡开通
		boolean removeMember = true;
		for(Object fact : facts){
			if(BeanUtils.getPropertyStr(fact, FactConstants.TRADE_TYPE) != null 
					&& BeanUtils.getPropertyStr(fact, FactConstants.TRADE_TYPE).equals("MEMBER_ACTIVE_CARD")){
				removeMember = false;
				break;
			}
		}
		
		Iterator<Object> it = facts.iterator();
		while(it.hasNext()){
			Object fact = it.next();
			String categoryName = BeanUtils.getPropertyStr(fact,FactConstants.CATEGORY_TYPE_KEY);
			ModelCategoryVO category = categoryMap.get(categoryName);
			if(category == null) {
				category = getCategory(categoryName);
				if(category == null) {
					continue;
				}
				categoryMap.put(categoryName, category);
			}
			String load = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_LOAD_KEY);
			if(!BlankUtil.isBlank(category.getLoadServiceName())
			   && (category.getIsAutoLoad() != null && category.getIsAutoLoad() == true || "true".equals(load))) {
				if(serviceNamemap.get(category.getLoadServiceName()) == null) {
					serviceNamemap.put(category.getLoadServiceName(), new ArrayList<Object>());
				}
				serviceNamemap.get(category.getLoadServiceName()).add(fact);
				
				if(removeMember)
					it.remove();
				else
//					会员开卡时候设置默认值为-1
					BeanUtils.setProperty(fact, "cardNum", "-1");
			}
		}
		
		return serviceNamemap;
	}
	
//	public Map<String, List<RuleConditionVO>> getAllDynamicConditions(String conditionCategory) {
//		if(!BlankUtil.isBlank(conditionCategory)) {
//			return ruleConditionService.searchAllDynamicConditions(conditionCategory);
//		}
//		return null;
//	}
	
//	public List<Object> loadDynamicConditionValues(Object category) {
//		String categoryName = BeanUtils.getPropertyStr(category, FactConstants.CATEGORY_TYPE_KEY);
//		Map<String, List<RuleConditionVO>> dynamicCons = getDynamicConditions(categoryName);
//		//key是serviceName , value是List<RuleConditionVO>
//		if(!BlankUtil.isBlank(dynamicCons)) {
//			List<Object> resultFacts = new ArrayList<Object>();
//			for(String key : dynamicCons.keySet()) {
//				List<RuleConditionVO> conditions = dynamicCons.get(key);
//				for(RuleConditionVO conditionVo : conditions) {
//					Map<String, Object> params = getParamInDynamicCondition(conditionVo, category);
//					RuleWSResultVO wsResult = ruleExternDataService.findExternDataResultVO(key, params);
//					if(wsResult != null && wsResult.isOk() && wsResult.getData() != null) {
//						Map<String, Object> conFact = new HashMap<String, Object>();
//						Map<String, Object> wsResultData = (wsResult.getData() instanceof Map) ? (Map)wsResult.getData() : BeanUtils.convertToMap(wsResult.getData());
//						conFact.putAll(wsResultData);
//						conFact.putAll(params);
//						conFact.put(FactConstants.CATEGORY_TYPE_KEY, conditionVo.getModelCategoryName());
//						resultFacts.add(conFact);
//					}
//				}
//			}
//			return resultFacts;
//		}
//		return null;
//		
//	}
	
	/**
	 * 获取动态条件的参数
	 * @param condition
	 * @param category
	 * @return
	 */
	private Map<String, Object> getParamInDynamicCondition(RuleConditionVO condition , Object category) {
		if(condition != null && !BlankUtil.isBlank(condition.getConditionElements())) {
			Map<String, Object> params = new HashMap<String, Object>();
			for(RuleConditionElementVO ele : condition.getConditionElements()) {
				if(ele.getModelField().getIsInDynamicCondition() != null && ele.getModelField().getIsInDynamicCondition() == true) {
					//动态值本身不是参数
					List<String> fieldNames = ModelField.splitQueryFieldNames(ele.getModelField().getQueryKeyFieldNames());
					//把指定的查询参数放进去
					if(!BlankUtil.isBlank(fieldNames)) {
						for(String fieldName : fieldNames) {
							params.put(fieldName, BeanUtils.getProperty(category, fieldName));
						}
					}
				} else {
					params.put(ele.getModelFieldName(), ele.getOperand());
				}
			}
			
			return params;
		}
		return null;
	}
	
//	private Map<String, List<RuleConditionVO>> getDynamicConditions(String categoryName ) {
//		
//		Boolean hasDynamic = null;
//		if(hasDynamic == null ) {
//			hasDynamic = this.hasDynamicField(categoryName);
//		}
//		if(hasDynamic == null || !hasDynamic ) {
//			return null;
//		}
//		Map<String, List<RuleConditionVO>> dynamicCons = ruleConditionService.searchAllDynamicConditions(categoryName);
//		return dynamicCons;
//	}
	
	private ModelCategoryVO getCategory(String categoryName) {
		if(!BlankUtil.isBlank(categoryName)) {
			return ruleModelCategoryService.getByName(categoryName);
		}
		return null;
	}
}
