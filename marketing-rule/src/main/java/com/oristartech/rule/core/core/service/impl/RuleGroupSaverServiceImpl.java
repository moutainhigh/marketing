package com.oristartech.rule.core.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.core.base.dao.IActionFunctionDao;
import com.oristartech.rule.core.base.dao.IModelCategoryDao;
import com.oristartech.rule.core.base.dao.IModelFieldDao;
import com.oristartech.rule.core.base.dao.IOperatorDao;
import com.oristartech.rule.core.base.model.ActionFunction;
import com.oristartech.rule.core.base.model.ModelCategory;
import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.base.model.Operator;
import com.oristartech.rule.core.base.model.RuleActionFunctionParameter;
import com.oristartech.rule.core.core.dao.IRuleDao;
import com.oristartech.rule.core.core.dao.IRuleGroupDao;
import com.oristartech.rule.core.core.dao.IRuleSectionDao;
import com.oristartech.rule.core.core.dao.IRuleTypeDao;
import com.oristartech.rule.core.core.model.Rule;
import com.oristartech.rule.core.core.model.RuleAction;
import com.oristartech.rule.core.core.model.RuleActionParameter;
import com.oristartech.rule.core.core.model.RuleCondition;
import com.oristartech.rule.core.core.model.RuleConditionElement;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.core.model.RuleSection;
import com.oristartech.rule.core.core.service.IRuleGroupSaverService;
import com.oristartech.rule.vos.core.enums.RuleExecuteMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

@Component
@Transactional
public class RuleGroupSaverServiceImpl extends RuleBaseServiceImpl implements IRuleGroupSaverService {
	
	protected static final Logger log = LoggerFactory.getLogger(RuleManagerServiceImpl.class);
	@Autowired
	private IOperatorDao ruleOperatorDao;
	@Autowired
	private IActionFunctionDao ruleActionFunctionDao;
	@Autowired
	private IRuleTypeDao ruleTypeDao;
	@Autowired
	private IRuleDao ruleDao;
	@Autowired
	private IRuleSectionDao ruleSectionDao;
	@Autowired
	private IModelCategoryDao ruleModelCategoryDao;
	@Autowired
	private IModelFieldDao ruleModelFieldDao;
	@Autowired
	private IRuleGroupDao ruleGroupDao;
	
	public Integer saveRuleGroup(String groupVoStr, String deleteRuleStrs, String bizCode) {
		if(!BlankUtil.isBlank(groupVoStr) ) {
			RuleGroupVO groupVO = assembleGroupVO(groupVoStr, null, bizCode);
			List<Integer> deleteRules = JsonUtil.jsonArrayToList(deleteRuleStrs, Integer.class);
			return saveRuleGroup(groupVO, deleteRules);
		}
	    return null;
	}
	
	public Integer saveRuleGroup(RuleGroupVO groupVO, List<Integer> deleteRules) {
		if(groupVO.getExecuteMode() == null) {
			groupVO.setExecuteMode(RuleExecuteMode.OTHER);
		}
		RuleGroup ruleGroup = prepareSave(groupVO, deleteRules, false);
		return ruleGroupDao.save(ruleGroup);
	}
	
	/**
	 * groupvo json字符串变为对象
	 * @param groupVo
	 * @param exeMode
	 * @param bizCode
	 * @return
	 */
	private RuleGroupVO assembleGroupVO(String groupVo,  RuleExecuteMode exeMode, String bizCode) {
		if(!BlankUtil.isBlank(groupVo) ) {
			RuleGroupVO groupVO = JsonUtil.jsonToObject(groupVo, RuleGroupVO.class);
			//新建才需要设置bizcode
			if(groupVO.getId() == null) {
				groupVO.setBizOrderCode(bizCode);
			}
			if(exeMode != null) {
				groupVO.setExecuteMode(exeMode);
			} else if(groupVO.getExecuteMode() == null) {
				groupVO.setExecuteMode(RuleExecuteMode.OTHER);
			}
			return groupVO;
		}
		return null;
	}
	
	/**
	 * 保存前先验证和绑定相关关联属性.
	 * @param groupVO
	 * @param deleteRules
	 * @return
	 */
	public RuleGroup prepareSave(RuleGroupVO groupVO, List<Integer> deleteRules, boolean recover) {
		validateGroupVO(groupVO, groupVO.getRules(), deleteRules);
		RuleGroup ruleGroup = null;
		
		RuleGroup newRuleGroup = getMapper().map(groupVO, RuleGroup.class);
		if(newRuleGroup.getRuleSection() != null) {
			newRuleGroup.getRuleSection().setIsSerial(true);
		}
		if(groupVO.getId() == null) {
			ruleGroup = createRuleGroup(newRuleGroup, recover);
		} else {
			ruleGroup = updateRuleGroup(newRuleGroup, deleteRules, recover);
		}
		return ruleGroup;
	}
	
	/**
	 * 创建新的规则组
	 * @param groupVO
	 * @param editRuleVOs
	 * @return
	 */
	private RuleGroup createRuleGroup(RuleGroup newRuleGroup, boolean recover) {
		prepareGroup(newRuleGroup, recover);
		newRuleGroup.setCreateDate(new Date());
		newRuleGroup.setStatus(RuleStatus.NEW);
		
		bindGroupAndRule(newRuleGroup, newRuleGroup.getRules());
		bindGroupAndSection(newRuleGroup, newRuleGroup.getRuleSection());
		return newRuleGroup;
	}
	
	private void prepareGroup(RuleGroup ruleGroup,  boolean recover) {
		if(ruleGroup != null && ruleGroup.getRuleSection() != null && !BlankUtil.isBlank(ruleGroup.getRuleSection().getRuleConditions())) {
			prepareRuleSection(ruleGroup.getRuleSection(), recover);
		}
		List<Rule> rules = ruleGroup.getRules();
		prepareRules(rules, recover);
	}
	
	private void prepareRules(List<Rule> rules,  boolean recover) {
		if(!BlankUtil.isBlank(rules)) {
			for(Rule rule : rules) {
				prepareRule(rule, recover);
			}
		}
	}
	
	private void prepareRule(Rule rule,  boolean recover) {
		if(rule != null) {
			List<RuleSection> sections = rule.getRuleSections();
			if(!BlankUtil.isBlank(sections)) {
				for(RuleSection section : sections) {
					prepareRuleSection(section, recover);
				}
			}
		}
	}
	private void prepareRuleSection(RuleSection section, boolean recover) {
		if(section != null) {
			prepareModelCategory(section.getRuleConditions(), recover);
			prepareAction(section.getRuleActions(), recover);
		}
	}
	
	private void prepareAction(List<RuleAction> actions , boolean recover) {
		if(!BlankUtil.isBlank(actions)) {
			for(RuleAction action : actions) {
				ActionFunction af = action.getActionFunction();
				if(af != null && af.getId() == null && !BlankUtil.isBlank(af.getUniqueName())) {
					String name = af.getUniqueName();
					af = ruleActionFunctionDao.getByUniqueName(name);
					if(af == null) {
						throw new ServiceRuntimeException("不存的操作: " + name);
					}
					action.setActionFunction(af);
				}
				prepareActionParameters(action, recover);
			}
		}
	}
	
	private void prepareActionParameters(RuleAction action, boolean recover) {
		if(action != null && !BlankUtil.isBlank(action.getParameters())) {
			for(RuleActionParameter param : action.getParameters()) {
				RuleActionFunctionParameter fnParameter = param.getFnParameter();
				if(fnParameter == null || (fnParameter.getId() == null && BlankUtil.isBlank(fnParameter.getName()))) {
					throw new ServiceRuntimeException("请提供方法参数， 方法名称：" + action.getActionFunction().getUniqueName());
				}
				if(fnParameter.getId() == null || recover) {
					ActionFunction af = action.getActionFunction();
					String paramName = fnParameter.getName();
					fnParameter = ruleActionFunctionDao.getParameterForAction(af.getUniqueName(), paramName);
					if(fnParameter == null) {
						throw new ServiceRuntimeException("请提供方法参数， 方法名称：" 
								                    + action.getActionFunction().getUniqueName() 
								                    + ",参数名称:" + paramName);
					}
					param.setFnParameter(fnParameter);
				}
			}
		}
	}
	
	private void prepareModelCategory(List<RuleCondition> conditions, boolean recover) {
		if(!BlankUtil.isBlank(conditions)) {
			for(RuleCondition con : conditions) {
				ModelCategory category = con.getModelCategory();
				if(category != null && !BlankUtil.isBlank(category.getName()) && (category.getId() == null || recover)) {
					String name = category.getName();
					category = ruleModelCategoryDao.getByName(name);
					if(category == null) {
						throw new ServiceRuntimeException("不存在业务分类: " + name);
					}
					con.setModelCategory(category);
				}
				prepareModelField(con.getConditionElements(), category.getId(), recover);
			}
		}
	}
	
	private void prepareModelField(List<RuleConditionElement> conditionEles, Integer catId, boolean recover) {
		List<ModelField> fields = ruleModelFieldDao.getByCategoryId(catId);
		if(!BlankUtil.isBlank(fields) && !BlankUtil.isBlank(conditionEles)) {
			for (RuleConditionElement ele : conditionEles) {
				ModelField eleField = ele.getModelField();
				String fieldname = eleField.getName(); 
				if(eleField != null && (eleField.getId() == null || recover )&& !BlankUtil.isBlank(eleField.getName()) ) {
					for(ModelField field : fields) {
						if(field.getName().equals(eleField.getName())) {
							ele.setModelField(field);
						}
					}
				} 
				eleField = ele.getModelField();
				if (eleField == null || eleField.getId() == null ){
					throw new ServiceRuntimeException("不存在业务属性, 分类id=" + catId + ", field Name=" + fieldname);
				}
				prepareOperator(ele, recover);
			}
		} else if(!BlankUtil.isBlank(conditionEles)) {
			throw new ServiceRuntimeException("条件属性集合不能为空, catId=" + catId);
		}
	}
	
	private void prepareOperator(RuleConditionElement ele, boolean recover) {
		Operator op = ele.getOperator();
		if(op != null && (op.getId() == null || recover == true) && !BlankUtil.isBlank(op.getUniqueName())) {
			String name = op.getUniqueName();
			op = ruleOperatorDao.getByUniqueName(op.getUniqueName());
			if(op == null) {
				throw new ServiceRuntimeException("不存的运算符: " + name);
			}
			if(op.getOpNum() != null && op.getOpNum() == 0) {
				ele.setOperand(op.getDefaultOperand());
			}
			ele.setOperator(op);
		}
	}
	
	/**
	 * 更新规则组
	 * @param newRuleGroup
	 * @param deleteRules
	 * @return
	 */
	private RuleGroup updateRuleGroup(RuleGroup newRuleGroup, List<Integer> deleteRules, boolean recover) {
		RuleGroup oldRuleGroup = ruleGroupDao.get(newRuleGroup.getId());
		prepareGroup(newRuleGroup, recover);
			
		updateGroupSection(oldRuleGroup, newRuleGroup);
		BeanUtils.copyProperties(newRuleGroup, oldRuleGroup, new String[]{"ruleSection", "rules", "createDate", "modifyDate", "status"});
		updateGroupRule(oldRuleGroup, newRuleGroup ,  deleteRules);
		
		oldRuleGroup.setModifyDate(new Date());
		
		return oldRuleGroup;
	}
	
	/**
	 * 更新组中规则
	 * @param oldGroup
	 * @param newRuleGroup
	 * @param editRuleVOs
	 * @param deleteRules
	 */
	private void updateGroupRule(RuleGroup oldGroup, RuleGroup newRuleGroup, List<Integer> deleteRules) {
		List<Rule> oldRules = oldGroup.getRules();
		if(!BlankUtil.isBlank(deleteRules)) {
			for(Integer ruleId : deleteRules) {
				Iterator<Rule> it = oldRules.iterator();
				while( it.hasNext()) {
					Rule r = it.next();
					if(r.getId().equals(ruleId)) {
						it.remove();
					}
				}
			}
			ruleDao.deleteByIds(deleteRules);
		}
		
		delRuleOldSections(oldRules);
		
		List<Rule> newRules = new ArrayList<Rule> ();
		if(!BlankUtil.isBlank(oldRules)) {
			for(int i=0; i < newRuleGroup.getRules().size(); i++ ) {
				Rule newRule = newRuleGroup.getRules().get(i);
				
				boolean exist = false;
				
				for(Rule oldRule : oldRules ) {
					if(oldRule.getId().equals(newRule.getId())) {
						exist = true;
						BeanUtils.copyProperties(newRule, oldRule, new String[]{"version", "createDate", "modifyDate", "status"});
						newRules.add(oldRule);
						break;
					}
				}
				if(!exist) {
					newRules.add(newRule);
				}
			}
		} else {
			newRules.addAll(newRuleGroup.getRules());
		}
		oldGroup.setRules(newRules);
		bindGroupAndRule(oldGroup, newRules);
	}
	
	/**
	 * 更新组中共有的section
	 * @param oldRuleGroup
	 * @param newRuleGroup
	 */
	private void updateGroupSection(RuleGroup oldRuleGroup, RuleGroup newRuleGroup) {
	  RuleSection oldSection = oldRuleGroup.getRuleSection();
	  RuleSection newSection = newRuleGroup.getRuleSection(); 
		if(oldSection != null ) {
			ruleSectionDao.deleteById(oldSection.getId());
		}
		
		if(newSection != null) {
			newSection.setId(null);
			newSection.setRuleGroup(oldRuleGroup);
			
			bindSectionAndAction(newSection, newSection.getRuleActions());
			bindSectionAndCondition(newSection, newSection.getRuleConditions());
		}
		oldRuleGroup.setRuleSection(newSection);
	}
	
	/**
	 * 删除规则中旧的section, 因为便于更新时, 编辑时先删除所有就section
	 * @param rules
	 */
	private void delRuleOldSections(List<Rule> rules) {
		if(!BlankUtil.isBlank(rules)) {
			List<Integer> ruleIds = new ArrayList<Integer>();
			for(Rule rule : rules) {
				if(rule.getId() != null) {
					ruleIds.add(rule.getId());
					rule.setRuleSections(null);
				}
			}
			if(!BlankUtil.isBlank(ruleIds)) {
				ruleSectionDao.deleteByRuleIds(ruleIds);
			}
			
		}
	}
	
	/**
	 * 验证组vo
	 * @param groupVO
	 * @return
	 */
	private void validateGroupVO(RuleGroupVO groupVO, List<RuleVO> editRuleVOs, List<Integer> deleteIds) {
		if(groupVO == null) {
			throw new ServiceRuntimeException("rule group不能为空");
		}
		
		if(BlankUtil.isBlank(groupVO.getRuleType())) {
			throw new ServiceRuntimeException("规则类型不能为空, 规则类型必须在RuleType表中存在!");
		}
		
		if(!ruleTypeDao.existRuleType(groupVO.getRuleType())) {
			throw new ServiceRuntimeException("规则类型必须在RuleType表中存在!");
		}
		if(groupVO.getId() == null && BlankUtil.isBlank(editRuleVOs )) {
			throw new ServiceRuntimeException("新建规则组时, 规则不能为空");
		} 
		if(groupVO.getId() != null && BlankUtil.isBlank(editRuleVOs )) {
			//检测规则是否为空
			List<Integer> existIds = ruleDao.getRuleIdsByGroup(groupVO.getId());
			if(!BlankUtil.isBlank(deleteIds) && !BlankUtil.isBlank(existIds)) {
				Iterator<Integer> it = existIds.iterator();
				while(it.hasNext()) {
					Integer existId = it.next();
					for(Integer id : deleteIds) {
						if(existId.equals(id)) {
							it.remove();
							break;
						}
					}
				}
			}
			if(BlankUtil.isBlank(existIds)){
				throw new ServiceRuntimeException("规则不能为空");
			}
		}
		
	}
	
	/**
	 * 出来组和规则关系
	 * @param group
	 * @param rules
	 */
	private void bindGroupAndRule(RuleGroup group, List<Rule> rules) {
		if(group != null && !BlankUtil.isBlank(rules)) {
			for(int i =0 ; i < rules.size(); i++ ) {
				Rule rule = rules.get(i);
				rule.setVersion(1);
				rule.setSeqNum(i);
				if(rule.getRuleType() == null) {
					rule.setRuleType(group.getRuleType());
				}
				rule.setRuleGroup(group);
				rule.setBusinessCode(group.getRelateBusinessCode());
				rule.setValidDateStart(group.getValidDateStart());
				rule.setValidDateEnd(group.getValidDateEnd());
				//若group有优先级才, 统一设为group的优先级. 因为有地方是直接设rule的优先级
				if(group.getPriority() != null) {
					rule.setPriority(group.getPriority());
				} else if(rule.getPriority() == null) {
					//设置默认优先级
					rule.setPriority(0);
				}
				rule.setExecuteMode(group.getExecuteMode());
				rule.setStatus(group.getStatus());
				if(rule.getId() == null) {
					rule.setCreateDate(new Date());
				} else {
					rule.setModifyDate(new Date());
				}
				
				bindRuleAndSection(rule, rule.getRuleSections());
			}
		}
	}
	
	/**
	 * 出来规则和section关系
	 * @param rule
	 * @param sections
	 */
	private void bindRuleAndSection(Rule rule, List<RuleSection> sections) {
		if(rule != null && !BlankUtil.isBlank(sections)) {
			for(int i=0; i < sections.size(); i++) {
				RuleSection section = sections.get(i);
				section.setSeqNum(i);
				//因为每次更新都只是简单删除以前的和插入新的,所以不管有没有id,都认为是新的
				section.setId(null);
				section.setRule(rule);
				bindSectionAndAction(section, section.getRuleActions());
				bindSectionAndCondition(section, section.getRuleConditions());
			}
		}
	}
	
	/**
	 * 出来组和section关系
	 * @param group
	 * @param section
	 */
	private void bindGroupAndSection(RuleGroup group, RuleSection section) {
		if(section != null) {
			section.setRuleGroup(group);
			bindSectionAndCondition(section, section.getRuleConditions());
			bindSectionAndAction(section, section.getRuleActions());
		}
	}
	
	/**
	 * 出来section和action关系
	 * @param section
	 * @param actions
	 */
	private void bindSectionAndAction(RuleSection section, List<RuleAction> actions){
		if(section != null && !BlankUtil.isBlank(actions)) {
			for(int i=0; i < actions.size(); i++ ) {
				RuleAction action = actions.get(i);
				action.setSeqNum(i);
				//因为每次更新都只是简单删除以前的和插入新的,所以不管有没有id,都认为是新的
				action.setId(null);
				action.setRuleSection(section);
				ActionFunction af = action.getActionFunction();
				if(af == null || (af.getId() == null && BlankUtil.isBlank(af.getUniqueName()))) {
					throw new ServiceRuntimeException("请提供执行方法");
				}
				if(af.getId() == null) {
					af = ruleActionFunctionDao.getByUniqueName(af.getUniqueName());
					if(af == null) {
						throw new ServiceRuntimeException("请提供执行方法");
					}
					action.setActionFunction(af);
				}
				bindActionAndParameter(action, action.getParameters());
			}
		}
	}
	
	/**
	 * 绑定动作参数
	 * @param action
	 * @param parameters
	 */
	private void bindActionAndParameter(RuleAction action, List<RuleActionParameter> parameters) {
		if(action != null && !BlankUtil.isBlank(parameters)) {
			for(int i=0; i < parameters.size(); i++) {
				RuleActionParameter param = parameters.get(i);
				param.setId(null);
				param.setRuleAction(action);
				param.setSeqNum(i);
				RuleActionFunctionParameter fnParameter = param.getFnParameter();
				if(fnParameter == null || (fnParameter.getId() == null && BlankUtil.isBlank(fnParameter.getName()))) {
					throw new ServiceRuntimeException("请提供方法参数， 方法名称：" + action.getActionFunction().getUniqueName());
				}
				if(fnParameter.getId() == null) {
					ActionFunction af = action.getActionFunction();
					fnParameter = ruleActionFunctionDao.getParameterForAction(af.getUniqueName(), fnParameter.getName());
					if(fnParameter == null) {
						throw new ServiceRuntimeException("请提供方法参数， 方法名称：" + action.getActionFunction().getUniqueName());
					}
					param.setFnParameter(fnParameter);
				}
			}
		}
	}
	
	/**
	 * 出来section和condition关系
	 * @param section
	 * @param conditions
	 */
	private void bindSectionAndCondition(RuleSection section, List<RuleCondition> conditions){
		if(section != null && !BlankUtil.isBlank(conditions)) {
			for(int i=0; i < conditions.size(); i++ ) {
				RuleCondition con = conditions.get(i);
				con.setSeqNum(i);
				//因为每次更新都只是简单删除以前的和插入新的,所以不管有没有id,都认为是新的
				con.setId(null);
				con.setRuleSection(section);
				bindConditionAndEle(con, con.getConditionElements());
			}
		}
	}

	private void bindConditionAndEle(RuleCondition condition, List<RuleConditionElement> conditionEles){
		if(conditionEles != null && !BlankUtil.isBlank(conditionEles)) {
			for(int i=0; i < conditionEles.size(); i++ ) {
				RuleConditionElement con = conditionEles.get(i);
				con.setSeqNum(i);
				//因为每次更新都只是简单删除以前的和插入新的,所以不管有没有id,都认为是新的
				con.setId(null);
				con.setRuleCondition(condition);
				Operator op = con.getOperator();
				if(op == null || (op.getId() == null && BlankUtil.isBlank(op.getUniqueName()))) {
					throw new ServiceRuntimeException("请提供运算符号");
				}
				if(op.getId() == null) {
					op = ruleOperatorDao.getByUniqueName(op.getUniqueName());
					if(op == null) {
						throw new ServiceRuntimeException("请提供运算符号");
					}
					con.setOperator(op);
				}
			}
		}
	}
}
