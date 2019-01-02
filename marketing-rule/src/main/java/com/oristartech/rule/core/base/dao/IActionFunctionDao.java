package com.oristartech.rule.core.base.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.base.model.ActionFunction;
import com.oristartech.rule.core.base.model.RuleActionFunctionParameter;

public interface IActionFunctionDao extends IRuleBaseDao<ActionFunction, Integer> {
	/**
	 * 由唯一名称获取ActionFunction
	 * @param uniqueName
	 * @return
	 */
	ActionFunction getByUniqueName(String uniqueName);
	
	/**
	 * 获取指定函数的指定参数
	 * @param uniqueName
	 * @param name
	 * @return
	 */
	RuleActionFunctionParameter getParameterForAction(String uniqueName, String name);
	
	/**
	 * 获取指定函数的所有参数
	 * @param fnId
	 * @return
	 */
	List<RuleActionFunctionParameter> getParametersById(Integer fnId);
}
