package com.oristartech.rule.core.core.service;

import java.util.List;

import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * 专门用于规则执行的查找service
 * @author chenjunfei
 *
 */
public interface IRuleFinderService {
	
	public List<RuleGroupVO> assembleRuleGroupVO(List<RuleGroupVO> groupVOs);
	
	/**
	 * 查询用引擎执行的可用的规则
	 * @param searchCondition
	 * @return
	 */
	@Deprecated
	public Page<RuleGroupVO> searchEngineExeRules(RuleSearchCondition searchCondition);

	/**
	 * 加载指定规则组
	 * @param ruleGroupId
	 * @return
	 */
	public RuleGroupVO getRuleGroupForExe(Integer ruleGroupId);
}
