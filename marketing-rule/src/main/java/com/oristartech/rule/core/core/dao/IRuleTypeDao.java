package com.oristartech.rule.core.core.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.core.model.RuleType;
import com.oristartech.rule.vos.core.enums.RuleRunMode;

public interface IRuleTypeDao extends IRuleBaseDao<RuleType, Integer> {
	/**
	 * 是否存在名称为name的ruletype
	 * @param name
	 * @return
	 */
	boolean existRuleType(String name);

	/**
	 * 获取默认的type name
	 * @return
	 */
	String getDefaultTypeName();
	
	/**
	 * 获取默认的type
	 * @return
	 */
	RuleType getDefaultType();
	
	/**
	 * 根据名称获取RuleType
	 * @param name
	 * @return
	 */
	RuleType getByName(String name);
	
	/**
	 * 根据运行模式获取ruletype list
	 * @param runMode
	 * @return
	 */
	List<RuleType> getByRunMode(RuleRunMode runMode);
	
	/**
	 * 根据运行模式获取type name list
	 * @param runMode
	 * @return
	 */
	List<String> getNamesByRunMode(RuleRunMode runMode);
}
