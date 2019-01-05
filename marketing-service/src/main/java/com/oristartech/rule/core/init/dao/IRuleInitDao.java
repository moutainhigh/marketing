package com.oristartech.rule.core.init.dao;

import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.search.RuleSearchCondition;

public interface IRuleInitDao {

	/**
	 * 根据租户ID查询真正执行中的规则，初始化规则用
	 * 分页查询方法，结果是包含可以用engine 执行的Rule的page。
	 * @param searchCondition
	 * @return
	 */
	public Page findEngineExeRuleVOs(RuleSearchCondition searchCondition);
}
