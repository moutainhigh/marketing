package com.oristartech.rule.core.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.base.dao.IOperatorDao;
import com.oristartech.rule.core.base.model.Operator;

@Repository
public class OperatorDaoImpl extends RuleBaseDaoImpl<Operator, Integer> implements IOperatorDao {

	public Operator getByUniqueName(String uniqueName) {
		String hql = "select op from Operator op where op.uniqueName = ?";
	    return (Operator)uniqueResult(hql, uniqueName);
	}
}
