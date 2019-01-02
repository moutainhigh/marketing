package com.oristartech.rule.core.base.dao;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.base.model.Operator;

public interface IOperatorDao extends IRuleBaseDao<Operator, Integer> {
	/**
	 * 由唯一名称获取Operator
	 * @param uniqueName
	 * @return
	 */
	Operator getByUniqueName(String uniqueName);
}
