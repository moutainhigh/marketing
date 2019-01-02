package com.oristartech.rule.core.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.core.dao.IRuleSectionDao;
import com.oristartech.rule.core.core.model.RuleSection;

@Repository
public class RuleSectionDaoImpl extends RuleBaseDaoImpl<RuleSection, Long> implements IRuleSectionDao {
	public void deleteActions(Long id) {
		String hql = "delete RuleAction ra where ra.ruleSection.id = ?";
		executeSaveOrUpdate(hql, id);
	}
	
	public void deleteConditions(Long id) {
		String hql = "delete RuleCondition rc where rc.ruleSection.id = ?";
		executeSaveOrUpdate(hql, id);
	}
	
	public void deleteByRuleIds(List<Integer> ruleIds) {
		String hql = "delete RuleSection rs where rs.rule.id in( :ids )";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ruleIds);
		executeSaveOrUpdate(hql, params);
	}
}
