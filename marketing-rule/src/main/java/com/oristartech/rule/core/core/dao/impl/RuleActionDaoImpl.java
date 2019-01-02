package com.oristartech.rule.core.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.core.dao.IRuleActionDao;
import com.oristartech.rule.core.core.model.RuleAction;

@Repository
public class RuleActionDaoImpl extends RuleBaseDaoImpl<RuleAction, Long> implements IRuleActionDao {
	public List<RuleAction> findActionVOs(Integer ruleId, List<Long> actionIds) {
		String hql = " SELECT DISTINCT(ra) from RuleAction ra " +
				     " JOIN ra.ruleSection rs " +
				     " JOIN rs.rule r " +
				     " JOIN FETCH ra.parameters " +
				     " WHERE r.id = :ruleId AND ra.id in(:ids)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ruleId", ruleId);
		params.put("ids", actionIds);
	    List<RuleAction> actions = (List<RuleAction>)super.findByNamedParam(hql, params);
	    return actions;
	}
	
	public List<RuleAction> findActionVOs(List<Long> actionIds) {
		String hql = " SELECT DISTINCT(ra) from RuleAction ra JOIN FETCH ra.parameters " +
	     			 " WHERE ra.id in(:ids)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", actionIds);
		List<RuleAction> actions = (List<RuleAction>)super.findByNamedParam(hql, params);
	    return actions;
	}
}
