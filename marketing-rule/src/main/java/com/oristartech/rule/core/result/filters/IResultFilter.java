package com.oristartech.rule.core.result.filters;

import com.oristartech.rule.drools.executor.context.RuleExecuteContext;

/**
 * 过滤规则结果接口, 若没必要,无须设置太多过滤器,影响性能
 * @author chenjunfei
 *
 */
public interface IResultFilter {
	public void doFilter(RuleExecuteContext context, IResultFilterChain chain);
}
