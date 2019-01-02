package com.oristartech.rule.core.base.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.base.dao.IActionFunctionDao;
import com.oristartech.rule.core.base.model.ActionFunction;
import com.oristartech.rule.core.base.model.RuleActionFunctionParameter;

@Repository
public class ActionFunctionDaoImpl extends RuleBaseDaoImpl<ActionFunction, Integer> implements IActionFunctionDao {
	
	public ActionFunction getByUniqueName(String uniqueName) {
	    String hql = "select af from ActionFunction af where af.uniqueName = ?";
	    return (ActionFunction)uniqueResult(hql, uniqueName);
	}

	public RuleActionFunctionParameter getParameterForAction(String uniqueName, String name) {
		String hql = "select pm from RuleActionFunctionParameter pm where pm.actionFunction.uniqueName = ? AND pm.name=?";
		return (RuleActionFunctionParameter)uniqueResult(hql, new String[]{uniqueName, name});
	}
	
	public List<RuleActionFunctionParameter> getParametersById(Integer fnId) {
		String hql = "select pm from RuleActionFunctionParameter pm where pm.actionFunction.id = ? ";
		return (List<RuleActionFunctionParameter>)findByNamedParam(hql, fnId);
	}
}
