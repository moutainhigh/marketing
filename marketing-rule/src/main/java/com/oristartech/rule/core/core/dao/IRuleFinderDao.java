package com.oristartech.rule.core.core.dao;

import java.util.List;

import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * 专门用于规则执行的查找dao
 * @author chenjunfei
 *
 */
public interface IRuleFinderDao {
	
	public List<RuleGroupVO> assembleRuleGroup(List<RuleGroupVO> groupVOs);
	
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
