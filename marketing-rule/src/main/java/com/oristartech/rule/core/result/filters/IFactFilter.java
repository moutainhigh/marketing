package com.oristartech.rule.core.result.filters;

import java.util.List;

/**
 * 规则匹配前的处理链
 * @author chenjunfei
 *
 */
public interface IFactFilter {
	public void doFilter(List<Object> facts, IFactFilterChain chain);
}
