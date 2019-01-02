package com.oristartech.rule.core.core.dao;

import java.util.List;
import java.util.Map;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.core.model.RuleCondition;

public interface IRuleConditionDao extends IRuleBaseDao<RuleCondition, Long>{

	/**
	 * 加载所有包含动态加载属性的规则条件分类. 只查询又规则引擎执行的,并且执行中的规则条件, 过滤相同的条件
	 * @param conditionCategory 条件中指定cateogry
	 */
	Map<String, List<RuleCondition>> searchAllDynamicConditions(String conditionCategory);

}
