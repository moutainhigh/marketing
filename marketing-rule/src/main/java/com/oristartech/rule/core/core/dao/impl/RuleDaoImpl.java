package com.oristartech.rule.core.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.core.dao.IRuleDao;
import com.oristartech.rule.core.core.model.Rule;
import com.oristartech.rule.core.core.model.RuleType;
import com.oristartech.rule.vos.core.vo.RuleVO;

@Repository
public class RuleDaoImpl extends RuleBaseDaoImpl<Rule, Integer> implements IRuleDao {
	
	public List<Rule> getRulesByGroup(Integer groupId) {
		String hql = "select r from Rule r where r.ruleGroup.id = ?";
	    return (List<Rule>) super.findByNamedParam(hql, groupId);
	}
	
	public List<Rule> getSimpleRulesByGroup(Integer groupId) {
		String hql = "select r.id AS id, r.name AS name, r.priority AS priority from Rule r where r.ruleGroup.id = ?";
		return (List<Rule>) super.findByNamedParam(hql, groupId, Rule.class);
	}
	
	public List<RuleVO> getSimpleRulesByGroupIds(List<Integer> groupIds) {
		String hql = "select r.id AS id, r.name AS name, r.priority AS priority, r.ruleGroup.id AS ruleGroupId from Rule r where r.ruleGroup.id in( :ids )";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", groupIds);
		return (List<RuleVO>) super.findByNamedParam(hql, param, RuleVO.class);
	}
	
	public List<Integer> getRuleIdsByGroup(Integer id) {
		String hql = "select r.id from Rule r where r.ruleGroup.id = ?";
	    List<Integer> ids = (List<Integer>)super.findByNamedParam(hql, id);
	    return ids;
	}
	
	public Rule getRuleInGroup(Integer groupId, Integer ruleId) {
		String hql = "select r from Rule r where r.ruleGroup.id = ? and r.id=?";
	    return (Rule)super.uniqueResult(hql, new Integer[]{groupId, ruleId});
	}
	
	public String getRuleTypeName(Integer ruleId) {
		String hql = "SELECT r.ruleType from Rule r where r.id = ? ";
	    return (String)super.uniqueResult(hql, ruleId);
	}
	
	public RuleType getRuleType(Integer id) {
		String hql = "SELECT rt from RuleType rt, Rule r where rt.name = r.ruleType and r.id = ? ";
	    return (RuleType)super.uniqueResult(hql, id);
	}
	
	public boolean existRuleInGroup(Integer groupId, Integer ruleId) {
		String hql = "SELECT count(r.id) from Rule r where  r.ruleGroup.id=? and r.id = ? ";
		Long amount = (Long)super.uniqueResult(hql, new Object[]{groupId, ruleId});
	    return amount != null && amount > 0;
	}
	
	public List<Rule> getRulesByIds(List<Integer> ids) {
		String hql = "select r from Rule r where r.id in ( :ids )";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		return (List<Rule>) super.findByNamedParam(hql, param);
	}
}
