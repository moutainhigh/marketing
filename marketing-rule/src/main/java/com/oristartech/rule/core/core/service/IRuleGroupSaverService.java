package com.oristartech.rule.core.core.service;

import java.util.List;

import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * rulegroup 保存service ,主要是提供给ruleManagerService调用. ruleManagerService是提供给外部的接口.
 * @author chenjunfei
 *
 */
public interface IRuleGroupSaverService {
	/**
	 * 保存规则组
	 * @param groupVoStr
	 * @param deleteRuleStrs
	 * @param bizCode
	 * @return
	 */
	public Integer saveRuleGroup(String groupVoStr, String deleteRuleStrs, String bizCode);
	
	/**
	 * 保存规则组
	 * @param groupVO
	 * @param deleteRules
	 * @return
	 */
	public Integer saveRuleGroup(RuleGroupVO groupVO, List<Integer> deleteRules);
	
	/**
	 * 为了保存准备数据
	 * @param groupVO
	 * @param deleteRules
	 * @param recover
	 * @return
	 */
	public RuleGroup prepareSave(RuleGroupVO groupVO, List<Integer> deleteRules, boolean recover);
}
