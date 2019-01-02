package com.oristartech.rule.core.result.filters;

import java.util.List;

/**
 * 规则匹配前的的处理链
 * @author chenjunfei
 *
 */
public interface IFactFilterChain {
	public void setFilters(List<IFactFilter> filters);
	
	public void doFilter(List<Object> facts);
}
