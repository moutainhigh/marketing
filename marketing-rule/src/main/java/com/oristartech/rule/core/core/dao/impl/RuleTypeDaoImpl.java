package com.oristartech.rule.core.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.core.dao.IRuleTypeDao;
import com.oristartech.rule.core.core.model.RuleType;
import com.oristartech.rule.vos.core.enums.RuleRunMode;

@Repository
public class RuleTypeDaoImpl extends RuleBaseDaoImpl<RuleType, Integer> implements IRuleTypeDao {
	public boolean existRuleType(String name) {
		String hql = "SELECT rt FROM RuleType rt where rt.name = ?";
		Object ruleType = uniqueResult(hql, name);
		return ruleType != null;
	}
	
	public String getDefaultTypeName() {
		RuleType type = getDefaultType();
		if(type != null) {
			return type.getName();
		}
	    return null;
	}
	
	public RuleType getDefaultType() {
		String hql = "SELECT rt FROM RuleType rt where rt.isDefault = true";
		super.setCacheQueries(true);
		List<RuleType> result = (List<RuleType>)executeFindHql(hql, null);
		super.setCacheQueries(false);
		if(!BlankUtil.isBlank(result)) {
			return result.get(0);
		}
		return null;
	}
	
	public RuleType getByName(String name) {
		String hql = "SELECT rt FROM RuleType rt where rt.name = ?";
		return (RuleType)uniqueResult(hql, name);
	}
	
	public List<String> getNamesByRunMode(RuleRunMode runMode) {
		List<RuleType> ruleTypes = getByRunMode(runMode);
		if(!BlankUtil.isBlank(ruleTypes)) {
			List<String> types = new ArrayList<String>();
			for(RuleType type : ruleTypes) {
				types.add(type.getName());
			}
			return types;
		}
	    return null;
	}
	
	public List<RuleType> getByRunMode(RuleRunMode runMode) {
		String hql = "SELECT rt FROM RuleType rt where rt.runMode = ?";
		return (List)super.findByNamedParam(hql, runMode);
	}
}
