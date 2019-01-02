package com.oristartech.rule.core.base.dao;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.base.model.SystemServiceUrl;

public interface ISystemServiceUrlDao extends IRuleBaseDao<SystemServiceUrl, Integer>{
	/**
	 * 根据名称获取webservice
	 * @param name
	 * @return
	 */
	SystemServiceUrl get(String systemName, String systemCode);
}
