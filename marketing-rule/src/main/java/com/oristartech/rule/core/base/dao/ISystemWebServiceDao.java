package com.oristartech.rule.core.base.dao;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.base.model.SystemWebService;

public interface ISystemWebServiceDao extends IRuleBaseDao<SystemWebService, Integer>{
	/**
	 * 根据名称获取webservice
	 * @param name
	 * @return
	 */
	SystemWebService getByName(String name);
}
