package com.oristartech.rule.core.template.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.template.model.RuleElementTemplate;

public interface IRuleElementTemplateDao extends IRuleBaseDao<RuleElementTemplate, Integer>{

	/**
	 * 根据类型名称查找相关的模板
	 * @param type
	 * @return
	 */
	List<RuleElementTemplate> findByType(String type);
	
	/**
	 * 根据名称获取模板
	 * @param temName
	 * @return
	 */
	RuleElementTemplate getByName(String temName);
}
