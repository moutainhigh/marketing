package com.oristartech.rule.core.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.base.dao.ISystemWebServiceDao;
import com.oristartech.rule.core.base.model.SystemWebService;

@Repository
public class SystemWebServiceDaoImpl extends RuleBaseDaoImpl<SystemWebService, Integer> implements ISystemWebServiceDao {
	
	public SystemWebService getByName(String name) {
		super.setCacheQueries(true);
		String hql = "select ws from SystemWebService ws where ws.name = ?";
		SystemWebService ws = (SystemWebService)uniqueResult(hql, name);
		super.setCacheQueries(false);
		return ws;
	}
}
