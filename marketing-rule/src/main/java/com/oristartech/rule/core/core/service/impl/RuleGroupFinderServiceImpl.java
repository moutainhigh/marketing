package com.oristartech.rule.core.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.BizOperatorConstants;
import com.oristartech.rule.core.base.dao.IModelFieldDataSourceDao;
import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.core.dao.IRuleGroupDao;
import com.oristartech.rule.core.core.model.Rule;
import com.oristartech.rule.core.core.model.RuleAction;
import com.oristartech.rule.core.core.model.RuleActionParameter;
import com.oristartech.rule.core.core.model.RuleCondition;
import com.oristartech.rule.core.core.model.RuleConditionElement;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.core.model.RuleSection;
import com.oristartech.rule.core.core.service.IRuleGroupFinderService;
import com.oristartech.rule.core.core.util.RuleGroupUtil;
import com.oristartech.rule.core.template.service.IRuleElementManagerService;
import com.oristartech.rule.core.ws.client.service.IRuleExternDataService;
import com.oristartech.rule.vos.base.vo.ModelFieldDataSourceVO;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleSectionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.template.vo.FieldGroupVO;
import com.oristartech.rule.vos.template.vo.FunctionGroupVO;

/**
 * 保存规则组的辅助类
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class RuleGroupFinderServiceImpl extends RuleBaseServiceImpl implements IRuleGroupFinderService {
	protected static final Logger log = LoggerFactory.getLogger(RuleManagerServiceImpl.class);
	
	@Autowired
	private IRuleExternDataService ruleExternDataService;
	@Autowired
	private IModelFieldDataSourceDao ruleModelFieldDataSourceDao;
	@Autowired
	private IRuleElementManagerService ruleElementManagerService;
	@Autowired
	private IRuleGroupDao ruleGroupDao;
	
	public RuleGroupVO getRuleGroupInitExternal(Integer id) {
		if(id != null) {
	    	RuleGroup ruleGroup = ruleGroupDao.get(id);
	    	if(ruleGroup != null) {
	    		initConditionOpLabel(ruleGroup);
	    		initExternDatas(ruleGroup);
	    		//这里通常是在定义规则处调用,使用其中一个会加载lazy属性的mapper
	    		RuleGroupVO ruleGroupVO = getMapper().map(ruleGroup, RuleGroupVO.class);
	    		
		    	return ruleGroupVO;
	    	}
	    }
	    return null;
	}
	
	public RuleGroupVO getRGWithDefinData(Integer id) {
		RuleGroupVO groupVO = this.getRuleGroupInitExternal(id);
		if(groupVO != null) {
			initGroupDefineData(groupVO);
		}
		
		return groupVO;
	}
	
	public RuleGroupVO getRuleGroupNoInitExternal(Integer id) {
		if(id != null) {
	    	RuleGroup ruleGroup = ruleGroupDao.get(id);
	    	if(ruleGroup != null) {
	    		//这里通常是在定义规则处调用,使用其中一个会加载lazy属性的mapper
	    		RuleGroupVO ruleGroupVO = getMapper().map(ruleGroup, RuleGroupVO.class);
	    		
		    	return ruleGroupVO;
	    	}
	    }
	    return null;
	}
	
	public RuleGroupVO copyRuleGroupById(Integer id) {
		RuleGroupVO groupVO = getRuleGroupInitExternal(id);
		if(groupVO != null) {
			RuleGroupVO result = getMapper().map(groupVO, RuleGroupVO.class); 
			result.removePropForNew();
			return result;
		}
	    return null;
	}
	
	public String copyRuleGroupJsonById(Integer id, Boolean escapeJs) {
	    RuleGroupVO vo = this.copyRuleGroupById(id);
	    if(vo != null) {
	    	 String result = JsonUtil.objToJsonIgnoreNull(vo);
	    	 if(escapeJs != null && escapeJs == true) {
	    		 result = StringEscapeUtils.escapeJavaScript(result);
	    	 }
	    	 return result;
	    }
	    return null;
	}
	
	public String copyRuleGroupJsonWithDefineData(Integer id, Boolean escapeJS) {
		RuleGroupVO vo = this.copyRuleGroupById(id);
		if(vo != null) {
			initGroupDefineData(vo);
			String result = JsonUtil.objToJsonIgnoreNull(vo);
			if(escapeJS != null && escapeJS == true) {
	    		 result = StringEscapeUtils.escapeJavaScript(result);
	    	 }
	    	 return result;
		}
		
	    return null;
	}
	
	private void initRuleExternDatas(Rule rule, String systemCode) {
		if(rule != null) {
			List<RuleSection> sections = rule.getRuleSections();
			if(!BlankUtil.isBlank(sections)) {
				for(RuleSection section : sections) {
					initSectionExternDatas(section, systemCode);
				}
			}
		}
	}
	
	/**
	 * 初始化外部数据的值显示信息, 因为外部数据一般只记录id或code
	 * @param ruleGroup
	 */
	private void initExternDatas(RuleGroup ruleGroup) {
		String systemCode = getSystemCode(ruleGroup);
		initSectionExternDatas(ruleGroup.getRuleSection(), systemCode);
		if(!BlankUtil.isBlank(ruleGroup.getRules())) {
			for(Rule rule : ruleGroup.getRules()) {
				initRuleExternDatas(rule, systemCode);
			}
		}
    }
	
	/**
	 * 获取系统编号
	 * @param ruleGroup
	 * @return
	 */
	private String getSystemCode(RuleGroup ruleGroup) {
		String type = ruleGroup.getRuleType();
		String systemCodeField = BizFactConstants.SI_BUSINESS_CODE;
		if(BizFactConstants.COUPON_RULE_TYPE.equals(type)) {
			systemCodeField = BizFactConstants.SI_CINEMA_CODE;
		}
		if(ruleGroup.getRuleSection() != null) {
			RuleSection section = ruleGroup.getRuleSection();
			if(!BlankUtil.isBlank(section.getRuleConditions())) {
				for(RuleCondition rc : section.getRuleConditions()) {
					if(BizFactConstants.SALE_INFO.equals(rc.getModelCategory().getName())
					  && !BlankUtil.isBlank(rc.getConditionElements())) {
						for(RuleConditionElement rce : rc.getConditionElements()) {
							if(systemCodeField.equals(rce.getModelField().getName()) 
							  && BizOperatorConstants.NORMAL_EQUAL_NAME.equals(rce.getOperator().getUniqueName())) {
								return rce.getOperand();
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private void initGroupDefineData(RuleGroupVO groupVO) {
		Map<Integer, Integer> fieldGroupIds = new HashMap<Integer,Integer>();
		Map<Integer, Integer> funcGroupIds = new HashMap<Integer,Integer>();
		addFieldAndFuncGroup(groupVO.getRuleSectionVO() ,fieldGroupIds ,funcGroupIds );
		List<RuleVO> rules = groupVO.getRules();
		if(!BlankUtil.isBlank(rules)) {
			for(RuleVO rule : rules) {
				for(RuleSectionVO sectionVO : rule.getRuleSections()) {
					addFieldAndFuncGroup(sectionVO ,fieldGroupIds ,funcGroupIds );
				}
				
			}
		}
		List<FieldGroupVO> fieldGroupVO = ruleElementManagerService.findConditionFieldGroups(new ArrayList<Integer>(fieldGroupIds.values()));
		List<FunctionGroupVO> funcGroupVO = ruleElementManagerService.findFuncGroup(new ArrayList<Integer>(funcGroupIds.values()));
		groupVO.setFieldGroups(fieldGroupVO);
		groupVO.setFuncGroups(funcGroupVO);
	}
	
	private void addFieldAndFuncGroup(RuleSectionVO sectionVO, Map<Integer, Integer>  fieldGroupIds, Map<Integer, Integer>  funcGroupIds) {
		if(sectionVO != null) {
			if(!BlankUtil.isBlank(sectionVO.getRuleConditions())) {
				for(RuleConditionVO conVO : sectionVO.getRuleConditions()) {
					if(conVO.getFieldGroupId() != null) {
						fieldGroupIds.put(conVO.getFieldGroupId(), conVO.getFieldGroupId());
					}
				}
			}
			
			if(!BlankUtil.isBlank(sectionVO.getRuleActions())) {
				for(RuleActionVO actionVO : sectionVO.getRuleActions()) {
					if(actionVO.getFuncGroupId() != null) {
						funcGroupIds.put(actionVO.getFuncGroupId(), actionVO.getFuncGroupId());
					}
				}
			}
		}
	}
	
	private void initConditionOpLabel(RuleGroup group) {
		if(group != null) {
			RuleSection commonSection = group.getRuleSection();
			if(commonSection != null && !BlankUtil.isBlank(commonSection.getRuleConditions())) {
				List<RuleCondition> ruleConditions = commonSection.getRuleConditions();
				for(RuleCondition con : ruleConditions) {
					List<RuleConditionElement> conEles = con.getConditionElements();
					if(!BlankUtil.isBlank(conEles)){
						for(RuleConditionElement conEle : conEles) {
							initConditionAndOpLabel(conEle);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 初始化操作符号和条件值是下拉列表的显示label
	 * @param conEle
	 */
	private void initConditionAndOpLabel(RuleConditionElement conEle) {
		Integer opNum = conEle.getOperator().getOpNum();
		if( conEle.getOperand() != null) {
			ModelField field = conEle.getModelField();
			String labelFieldName = field.getLabelFieldName();
			//若不是外部数据, 则看是否在datasource存在.
			if(!field.getIsExtern()) {
				List<ModelFieldDataSourceVO> fieldSource = ruleModelFieldDataSourceDao.getDatasByModelFieldId(field.getId());
				if(!BlankUtil.isBlank(fieldSource) ) {
					List<Object> fieldValues = new ArrayList<Object>();
					
					if(opNum == 1) {
						fieldValues.add(conEle.getOperand());
					} else if (opNum > 1 || opNum <= 0 ) {
						String operand = conEle.getOperand().trim();
						if(operand.startsWith("[")) {
							fieldValues = JsonUtil.jsonArrayToList(conEle.getOperand(), Object.class);
						} else {
							//只是简单用,号分隔
							String[] fs =  conEle.getOperand().split(RuleConditionElementVO.OPERAND_SPLIT);
							fieldValues = new ArrayList<Object>( Arrays.asList(fs));
						}
					} 
					if(opNum < -1 && !BlankUtil.isBlank(fieldSource)) {
						List<List<String>> labels = new ArrayList<List<String>>();
						for(Object obj : fieldSource) {
							//表示是数组中有数组
							if(obj instanceof List ) {
								List<String> aLabels = assembleListConLable((List<String>) obj, fieldSource);
								labels.add(aLabels);
							} 
						}
						conEle.setOperandLabel(JsonUtil.objToJson(labels));
					} else if(!BlankUtil.isBlank(fieldSource)) {
						List<String> aLabels = assembleListConLable(fieldValues , fieldSource);
						conEle.setOperandLabel(JsonUtil.objToJson(aLabels));
					}
				}
				
			}
		}
	}
	
	private List<String> assembleListConLable(List<? extends Object> fieldValues, List<ModelFieldDataSourceVO> fieldSource){
		List<String> labels = new ArrayList<String>();
		for(Object fieldValue : fieldValues) {
			for(ModelFieldDataSourceVO ds : fieldSource) {
				if(fieldValue != null) {
					if(fieldValue.toString().equals(ds.getValue())) {
						labels.add(ds.getLabel());
						break;
					}
				} 
			}
		}
		return labels;
	}
	
	//获取section 外部数据信息
	private void initSectionExternDatas(RuleSection section, String systemCode) {
		if(section != null) {
			List<RuleCondition> conditions = section.getRuleConditions();
			if(!BlankUtil.isBlank(conditions)) {
				for(RuleCondition con : conditions) {
					initRuleConEleExternDatas(con.getConditionElements(), systemCode);
				}
			}
			
			List<RuleAction> actions = section.getRuleActions();
			if(!BlankUtil.isBlank(actions)) {
				for(RuleAction action : actions) {
					initRuleActionsExternDatas(action, systemCode);
				}
			}
		}
	}
	
	//获取action 中的 外部数据信息
	private void initRuleActionsExternDatas(RuleAction action , String systemCode) {
		List<RuleActionParameter> params = action.getParameters();
		if(!BlankUtil.isBlank(params)) {
			for(RuleActionParameter param : params) {
				if(!BlankUtil.isBlank(param.getValue())) {
					ModelField field = param.getFnParameter().getModelField();
					if(RuleGroupUtil.isNeedLoadExternData(field)) {
						param.setLabel(loadExternFieldLabel(param.getValue(), field, systemCode));
					}
				}
			}
		}
	}
	
	//获取条件  外部数据信息
	private void initRuleConEleExternDatas(List<RuleConditionElement> conEles, String systemCode) {
		if(!BlankUtil.isBlank(conEles)) {
			for(RuleConditionElement conEle : conEles) {
				ModelField field = conEle.getModelField();
				if(!BlankUtil.isBlank(conEle.getOperand()) && RuleGroupUtil.isNeedLoadExternData(field)) {
					String externLabel = loadExternFieldLabel(conEle.getOperand(), field, systemCode);
					if(!BlankUtil.isBlank(externLabel)) {
						//若非空，用外部查到的值，否则用以保存的label。
						conEle.setOperandLabel(externLabel);
					} 
					
				}
			}
		}
	}
	
	/**
	 * 加载外部数据显示
	 * @param operand
	 * @param field
	 * @return
	 */
	private String loadExternFieldLabel(String operand, ModelField field, String systemCode) {
		return ruleExternDataService.loadExternFieldLabel(operand, field, systemCode);
	}
}
