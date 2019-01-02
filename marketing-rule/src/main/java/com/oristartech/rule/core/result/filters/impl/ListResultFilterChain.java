package com.oristartech.rule.core.result.filters.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oristartech.rule.core.result.filters.IResultFilter;
import com.oristartech.rule.core.result.filters.IResultFilterChain;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;

/**
 * 结果过滤器链，必须每次过滤时重新创建本对象。
 * 基于list的过滤器链
 * @author chenjunfei
 *
 */
public class ListResultFilterChain implements IResultFilterChain {
	private List<IResultFilter> ruleResultFilters = new ArrayList<IResultFilter>();
	private Iterator<IResultFilter> filterIterator;
	

	public List<IResultFilter> getRuleResultFilters() {
    	return ruleResultFilters;
    }

	public void setRuleResultFilters(List<IResultFilter> ruleResultFilters) {
		this.ruleResultFilters = ruleResultFilters;
    	if(ruleResultFilters != null) {
    		filterIterator = this.ruleResultFilters.iterator();
    	}
    }

	public void doFilter(RuleExecuteContext context) {
		if(filterIterator == null || !filterIterator.hasNext()) {
			return;
		}
		IResultFilter filter = filterIterator.next();
		filter.doFilter(context, this);
	}

}
