package com.oristartech.rule.core.config.dao;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.config.model.SystemConfig;

/**
 * @className:ISystemConfigDao
 * @desc:系统参数Dao
 * @author:123
 * @version:1.0
 * @createTime:2014年5月15日下午3:18:51
 */
public interface ISystemConfigDao extends IRuleBaseDao<SystemConfig, Long>{
	//根据名次获取value
	public String getValueByName(String name);
}
