package com.oristartech.rule.core.result.filters;

import java.util.List;

import com.oristartech.rule.drools.executor.context.RuleExecuteContext;

/**
 * 结果过滤器链，必须每次过滤时重新创建本对象。
 * @author chenjunfei
 */
public interface IResultFilterChain {
	public void setRuleResultFilters(List<IResultFilter> ruleResultFilters);
	
	public void doFilter(RuleExecuteContext context);
}
