package com.oristartech.rule.core.core.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.core.model.RuleAction;

public interface IRuleActionDao extends IRuleBaseDao<RuleAction, Long> {
	/**
	 * 获取指定规则,指定动作id的动作
	 * @param ruleId
	 * @param actionIds
	 * @return
	 */
	List<RuleAction> findActionVOs(Integer ruleId, List<Long> actionIds);
	
	/**
	 * 获取id的动作列表
	 * @param actionIds
	 * @return
	 */
	public List<RuleAction> findActionVOs(List<Long> actionIds);
}
