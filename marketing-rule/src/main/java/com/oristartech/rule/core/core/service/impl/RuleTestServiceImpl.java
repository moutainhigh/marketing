package com.oristartech.rule.core.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.BizOperatorConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.base.dao.IActionFunctionDao;
import com.oristartech.rule.core.base.dao.IModelFieldDao;
import com.oristartech.rule.core.base.model.FunctionParameterDataSource;
import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.base.model.ModelFieldDataSource;
import com.oristartech.rule.core.base.model.RuleActionFunctionParameter;
import com.oristartech.rule.core.core.model.Rule;
import com.oristartech.rule.core.core.model.RuleAction;
import com.oristartech.rule.core.core.model.RuleCondition;
import com.oristartech.rule.core.core.model.RuleConditionElement;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.core.model.RuleSection;
import com.oristartech.rule.core.core.service.IRuleGroupSaverService;
import com.oristartech.rule.core.core.service.IRuleTestService;
import com.oristartech.rule.core.core.util.RuleGroupUtil;
import com.oristartech.rule.core.match.service.IRuleService;
import com.oristartech.rule.core.ws.client.service.IRuleExternDataService;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.RuleSectionResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

@Component
@Transactional
public class RuleTestServiceImpl extends RuleBaseServiceImpl implements IRuleTestService {
	
	private static final String RULE_ID_KEY = "ruleId";
	private static final String SECTION_ID_KEY = "sectionId";
	private static final String ACTION_ID_KEY = "actionId";
	private static final String CON_ID_KEY = "conditionId";
	private static final String CON_ELE_ID_KEY = "conditionEleId";
	
	protected static final Logger log = LoggerFactory.getLogger(RuleManagerServiceImpl.class);
	
	@Autowired
	private IRuleExternDataService ruleExternDataService;
	@Autowired
	private IActionFunctionDao ruleActionFunctionDao;
	@Autowired
	private IModelFieldDao ruleModelFieldDao;
	@Autowired
	private IRuleService ruleService;
	@Autowired
	private IRuleGroupSaverService ruleGroupSaverService;
	
	public void setRuleGroupSaverService(IRuleGroupSaverService ruleGroupSaverService) {
    	this.ruleGroupSaverService = ruleGroupSaverService;
    }

	public void setRuleService(IRuleService ruleService) {
    	this.ruleService = ruleService;
    }

	public void setRuleExternDataService(IRuleExternDataService ruleExternDataService) {
    	this.ruleExternDataService = ruleExternDataService;
    }
	
	public void setRuleModelFieldDao(IModelFieldDao ruleModelFieldDao) {
    	this.ruleModelFieldDao = ruleModelFieldDao;
    }
	
	public void setRuleActionFunctionDao(IActionFunctionDao ruleActionFunctionDao) {
    	this.ruleActionFunctionDao = ruleActionFunctionDao;
    }
	
	/**
	 * 设置虚拟id
	 * @param group
	 */
	public void setFakeIdForGroupInTest(RuleGroup group) {
		int groupId = -1;
		Map<String , Object> idMap = new HashMap<String, Object>();
		idMap.put(RULE_ID_KEY, -1);
		idMap.put(SECTION_ID_KEY, -1L);
		idMap.put(ACTION_ID_KEY, -1L);
		idMap.put(CON_ID_KEY, -1L);
		idMap.put(CON_ELE_ID_KEY, -1L);
		if(group.getId() == null) {
			group.setId(groupId);
		}
		
		if(group.getRuleSection() != null) {
			setFakeIdForSectionInTest(group.getRuleSection(), idMap);
		}
		if(!BlankUtil.isBlank(group.getRules())) {
			for(Rule rule : group.getRules()) {
				setFakeIdForRuleInTest(rule, idMap);
			}
		}
	}
		
	public String testRuleGroup(String ruleGroup, String facts) {
		return testRuleGroup(ruleGroup, JsonUtil.jsonArrayToList(facts, Object.class));
	}
	
	public String testRuleGroup(String ruleGroupStr, List<Object> facts) {
		try {
			RuleGroupVO groupVO = getTempGroupForTest(ruleGroupStr, true);
			String systemCode = groupVO.findFirstCommonConditionEleValue(BizFactConstants.SALE_INFO, BizFactConstants.SI_BUSINESS_CODE, BizOperatorConstants.NORMAL_EQUAL_NAME);
			
			List<RuleResult> results = ruleService.testRuleGroup(groupVO, facts);
			if(!BlankUtil.isBlank(results)) {
				initRuleResultLabels(results, systemCode);
				return JsonUtil.objToJson(results);
			} else {
				return "[]";
			}
		} catch (RuleExecuteRuntimeException e) {
			log.error("测算错误:", e);
			return getErrorMsg(e.getMessage());
		} catch (Exception e) {
			log.error("测算错误:", e);
			return "测算异常错误";
		}
		
	}
	
	public RuleGroupVO getTempGroupForTest(String ruleGroupStr, boolean needFakeId) {
		RuleGroupVO groupVO = JsonUtil.jsonToObject(ruleGroupStr, RuleGroupVO.class);
		//清空groupid, 避免hibernate 加载group, 导致生成保存sql
		groupVO.setId(null);
		RuleGroup group = ruleGroupSaverService.prepareSave(groupVO, null, true);
		
		if(needFakeId) {
			setFakeIdForGroupInTest(group);
		}
		
		return getMapper().map(group, RuleGroupVO.class);
	}
	
	private String getErrorMsg(String msg) {
		if(msg != null) {
			String[] fields = msg.split(":");
			return fields[fields.length - 1];
		}
		return "匹配错误";
	}
	
	/**
	 * 设置结果中的外部属性显示
	 * @param ruleResults
	 */
	private void initRuleResultLabels(List<RuleResult> ruleResults, String systemCode) {
		if(!BlankUtil.isBlank(ruleResults)) {
			for(RuleResult result : ruleResults) {
				Collection<RuleSectionResult> secResults = result.getSectionResults();
				if(!BlankUtil.isBlank(secResults)) {
					for(RuleSectionResult sectionResult : secResults) {
						initSectionLabels(sectionResult, systemCode);
					}
				}
			}
		}
	}
	
	private void initSectionLabels(RuleSectionResult sectionResult, String systemCode) {
		if(!BlankUtil.isBlank(sectionResult.getActionResults())) {
			for(RuleActionResult result : sectionResult.getActionResults()) {
				if(result.getIsParam() != null && result.getIsParam() == true) {
					initFnParamResultLabel(result, systemCode);
				} else {
					initNormalResultLabel(result, systemCode);
				}
			}
		}
	}
	
	/**
	 * 初始化方法里面修改的modelfield显示
	 * @param result
	 */
	private void initNormalResultLabel(RuleActionResult result, String systemCode) {
		if(!BlankUtil.isBlank(result.getFnResults())) {
			List<Map<String, Object>> fnResults = new ArrayList<Map<String, Object>>();
			for(Object fnRt : result.getFnResults()) {
				Map<String , Object> map = BeanUtils.convertToMap(fnRt);
				Map<String , Object> resultMap = BeanUtils.convertToMap(fnRt);
				
				Object categoryName = map.get(FactConstants.CATEGORY_TYPE_KEY);
				Iterator<String> keyIt = map.keySet().iterator();
				if(categoryName != null) {
					for(String key : map.keySet()) {
						ModelField field = ruleModelFieldDao.getByCatNameAndFieldName(categoryName.toString(), key);
						if(map.get(key) != null && field != null) {
							String label = getModelFieldValueLabel(field, String.valueOf(map.get(key)), systemCode);
							resultMap.put(getLabelKey(key), label);
						}
					}
				}
				fnResults.add(resultMap);
			}
			result.setFnResults(fnResults);
		}
	}
	
	private String getLabelKey(String key) {
		return key + "_Label";
	}
	
	/**
	 * 获取modelfield 的value
	 * @param field
	 * @param value
	 * @return
	 */
	private String getModelFieldValueLabel(ModelField field, String value, String systemCode) {
		if(field.getIsExtern() != null && field.getIsExtern() == true) {
			if(RuleGroupUtil.isNeedLoadExternData(field)) {
				return ruleExternDataService.loadExternFieldLabel(value, field, systemCode);
			}
		} else if(!BlankUtil.isBlank(field.getModelFieldDataSources())) {
			for(ModelFieldDataSource ds : field.getModelFieldDataSources()) {
				if(ds.getValue().equals(value)) {
					return ds.getLabel();
				}
			}
		}
		return  value;
	}
	
	/**
	 * 初始化方法里面直接返回方法参数的显示值
	 * @param result
	 */
	private void initFnParamResultLabel(RuleActionResult result, String systemCode) {
		if(!BlankUtil.isBlank(result.getFnResults())) {
			Integer fnId = result.getFnId();
			List<RuleActionFunctionParameter> params = ruleActionFunctionDao.getParametersById(fnId);
			List<Map<String, Object>> fnResults = new ArrayList<Map<String, Object>>();
			for(Object fnRt : result.getFnResults()) {
				Map<String , Object> map = BeanUtils.convertToMap(fnRt);
				Map<String , Object> resultMap = BeanUtils.convertToMap(fnRt);
				for(String key : map.keySet()) {
					Object value = map.get(key);
					if(value == null) {
						continue;
					}
					RuleActionFunctionParameter param = getFnParamByName(params, key);
					if(param == null) {
						continue;
					}
					if(param.getModelField() != null) {
						String label = getModelFieldValueLabel(param.getModelField(), value.toString(), systemCode);
						resultMap.put(getLabelKey(key), label);
					} else if(!BlankUtil.isBlank(param.getFuncParamDataSource())){
						for(FunctionParameterDataSource ds : param.getFuncParamDataSource()) {
							if(ds.getValue().equals(value)) {
								resultMap.put(getLabelKey(key), ds.getLabel());
								break;
							}
						}
						
					}
				}
				fnResults.add(resultMap);
			}
			result.setFnResults(fnResults);
		}
	}
	
	private RuleActionFunctionParameter getFnParamByName(List<RuleActionFunctionParameter> params, String name) {
		for(RuleActionFunctionParameter  param : params) {
			if(param.getName().equals(name)) {
				return param;
			}
		}
		return null;
	}
	
	private void setFakeIdForRuleInTest(Rule rule, Map<String , Object> idMap) {
		int ruleId = (Integer)idMap.get(RULE_ID_KEY);
		if(rule.getId() == null) {
			rule.setId(ruleId --);
		}
		
		idMap.put(RULE_ID_KEY, ruleId);
		if(!BlankUtil.isBlank(rule.getRuleSections())) {
			for(RuleSection sec : rule.getRuleSections()) {
				this.setFakeIdForSectionInTest(sec, idMap);
			}
		}
	}
	
	private void setFakeIdForSectionInTest(RuleSection sec, Map<String , Object> idMap) {
		long sectionId = (Long)idMap.get(SECTION_ID_KEY);
		if(sec.getId() == null) {
			sec.setId(sectionId --);
		}
		
		idMap.put(SECTION_ID_KEY, sectionId);
		if(!BlankUtil.isBlank(sec.getRuleActions())) {
			for(RuleAction ac : sec.getRuleActions()) {
				setFakeIdForActionInTest(ac, idMap);
			}
		}
		if(!BlankUtil.isBlank(sec.getRuleConditions())) {
			for(RuleCondition con : sec.getRuleConditions()) {
				setFakeIdForConInTest(con, idMap);
			}
		}
	}
	
	private void setFakeIdForActionInTest(RuleAction an, Map<String , Object> idMap) {
		long actionId = (Long)idMap.get(ACTION_ID_KEY);
		if(an.getId() == null) {
			an.setId(actionId --);
		}
		
		idMap.put(ACTION_ID_KEY, actionId);	
	}
	
	private void setFakeIdForConInTest(RuleCondition con, Map<String , Object> idMap) {
		long conditionId = (Long)idMap.get(CON_ID_KEY);
		if(con.getId() == null) {
			con.setId(conditionId --);
		}
		
		idMap.put(CON_ID_KEY, conditionId);
		if(!BlankUtil.isBlank(con.getConditionElements())) {
			for(RuleConditionElement conEle : con.getConditionElements()) {
				setFakeIdForConEleInTest(conEle, idMap);
			}
		}
	}
	
	private void setFakeIdForConEleInTest(RuleConditionElement conEle, Map<String , Object> idMap) {
		long conditionEleId = (Long)idMap.get(CON_ELE_ID_KEY);
		if(conEle.getId() == null) {
			conEle.setId(conditionEleId --);
		}
		
		idMap.put(CON_ELE_ID_KEY, conditionEleId);
	}
}
