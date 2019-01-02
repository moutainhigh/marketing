package com.oristartech.setting.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.GenericDao;
import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.setting.model.Property;


/**
 * 系统配置属性dao
 * @author chenguohui
 *
 */
public interface IPropertyDao extends IRuleBaseDao<Property, Long> {
	
	
	/**
	 * 按key 来查找 Property
	 * @param key
	 * @return
	 */
	 Property getPropertyByKey(String key);
	 
	 
		 
	 /**
	  * 按key更新它的值
	  * @param key
	  * @param val
	  * @return
	  */
	 public int updatePropertyByKey(String key,String val);
	 

	 /**
	  * 更新流水号的日期
	  * @param flowNumDateKey
	  * @return
	  */
	 public int updateFlowNumDate(String flowNumDateKey);
	 
	 /**
	  * 更新流水号
	  * @param flowNumSeq
	  * @return
	  */
	 public int updateFlowNumSeq(String flowNumSeq);
	 
	 
}
