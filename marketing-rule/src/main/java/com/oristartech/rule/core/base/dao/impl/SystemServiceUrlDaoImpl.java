package com.oristartech.rule.core.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.base.dao.ISystemServiceUrlDao;
import com.oristartech.rule.core.base.model.SystemServiceUrl;
import com.oristartech.rule.core.base.model.SystemWebService;

@Repository
public class SystemServiceUrlDaoImpl extends RuleBaseDaoImpl<SystemServiceUrl, Integer> implements ISystemServiceUrlDao {
	
	public SystemServiceUrl get(String systemName, String systemCode) {
		super.setCacheQueries(true);
		String hql = "select ws from SystemServiceUrl ws where ws.systemName = ? and ws.businessCode=?";
		SystemServiceUrl ws = (SystemServiceUrl)uniqueResult(hql, new String[]{systemName, systemCode});
		super.setCacheQueries(false);
		return ws;
	}
	
	public SystemWebService getByName(String name) {
		super.setCacheQueries(true);
		String hql = "select ws from SystemWebService ws where ws.name = ?";
		SystemWebService ws = (SystemWebService)uniqueResult(hql, name);
		super.setCacheQueries(false);
		return ws;
	}
}
