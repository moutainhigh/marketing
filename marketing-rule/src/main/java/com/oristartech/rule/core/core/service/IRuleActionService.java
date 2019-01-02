package com.oristartech.rule.core.core.service;

import java.util.List;

import com.oristartech.rule.vos.core.vo.RuleActionVO;

/**
 * action service
 * @author chenjunfei
 *
 */
public interface IRuleActionService {
	/**
	 * 获取指定规则下的指定action id的动作vo, 会忽略section的初始化
	 * @param ruleId
	 * @param actionIds
	 * @return
	 */
	public List<RuleActionVO> getActionVOsForInvoke(Integer ruleId, List<Long> actionIds) ;
	
	/**
	 * 获取指定action id的动作vo, 会忽略section的初始化
	 * @param ruleId
	 * @param actionIds
	 * @return
	 */
	public List<RuleActionVO> getActionVOsForInvoke(List<Long> actionIds) ;

}
