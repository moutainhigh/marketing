package com.oristartech.setting.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.setting.dao.IPropertyDao;
import com.oristartech.setting.model.Property;

/**
 * 系统配置属性dao
 * @author chenguohui
 *
 */
@Repository
public class PropertyDaoImpl extends RuleBaseDaoImpl<Property, Long> implements	IPropertyDao {

	
	/**
	 * 按key 来查找 Property
	 * @param key
	 * @return
	 */
	public Property getPropertyByKey(String key) {
		
		return (Property) uniqueResult("from Property where key = ? ", key);
	
	}

	
	 public int updatePropertyByKey(String key,String val){
		 List<String> params = new ArrayList<String>();
		 params.add(val);
		 params.add(key);
		 return executeSaveOrUpdate(" update Property set value =? where  key = ?  ", params );
	 }


	 public int updateFlowNumDate(String flowNumDateKey){
		  return executeSaveOrUpdate(" update  Property set value = DATE_FORMAT(CURRENT_DATE(),'%Y%m%d')  where key =? and value < DATE_FORMAT(CURRENT_DATE(),'%Y%m%d')", flowNumDateKey);
	 }
	 
	 public int updateFlowNumSeq(String flowNumSeq){		 
		 return executeSaveOrUpdate(" update  Property set value = value+1  where key =? ", flowNumSeq);
	 }

}
