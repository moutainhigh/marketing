package com.oristartech.rule.core.core.dao;

import java.util.List;
import java.util.Map;

import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * 专门用于规则执行的查找dao
 * @author chenjunfei
 *
 */
public interface IRuleFinderDao {
	
	/**
	 * 查询规则下的条件信息，包含rule-section-condition-conditionElement
	 * @param groupIds
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getRuleConditionInfo(List<String> groupIds) throws Exception;
	
	/**
	 * 查询规则组下的section信息，包含section-condition-conditionElement
	 * @param groupIds
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getGroupSectionInfo(List<String> groupIds) throws Exception;
	
	/**
	 * 查询规则下的action信息，包含section-action
	 * @param groupIds
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getRuleActionInfo(List<String> groupIds) throws Exception;
	
	@Deprecated
	public List<RuleGroupVO> assembleRuleGroupVO(List<RuleGroupVO> groupVOs);
	
	/**
	 * 分页查询方法，结果是包含可以用engine 执行的Rule的page。
	 * @param searchCondition
	 * @return
	 */
	Page<RuleGroupVO> findEngineExeRuleVOs(RuleSearchCondition searchCondition);

	/**
	 * 只加载规则执行相关信息
	 * @param ruleGroupId
	 * @return
	 */
	RuleGroupVO getRuleGroupForExe(Integer ruleGroupId);
}
