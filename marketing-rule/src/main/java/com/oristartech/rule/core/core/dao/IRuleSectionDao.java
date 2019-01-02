package com.oristartech.rule.core.core.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.core.model.RuleSection;

public interface IRuleSectionDao  extends IRuleBaseDao<RuleSection, Long>{

	/**
	 * 删除section下的action
	 * @param id
	 */
	void deleteActions(Long id);

	/**
	 * 删除section下的condition
	 * @param id
	 */
	void deleteConditions(Long id);

	/**
	 * 根据rule id删除section
	 * @param ruleIds
	 */
	void deleteByRuleIds(List<Integer> ruleIds);
}
