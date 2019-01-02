package com.oristartech.rule.core.config.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.config.dao.ISystemConfigDao;
import com.oristartech.rule.core.config.model.SystemConfig;

/**
 * @className:SystemConfigDaoImpl
 * @desc:系统参数Dao实现类
 * @author:ch
 * @version:1.0
 * @createTime:2014年5月15日下午3:18:00
 */
@Repository
public class SystemConfigDaoImpl extends RuleBaseDaoImpl<SystemConfig, Long> implements ISystemConfigDao {
	
	//根据名称获取value
	public String getValueByName(String name){
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT value FROM SystemConfig WHERE name = ? ");
		Object value = this.uniqueResult(hql.toString(),new Object[]{name});
		return value != null ? value.toString() : null;
	}
}
