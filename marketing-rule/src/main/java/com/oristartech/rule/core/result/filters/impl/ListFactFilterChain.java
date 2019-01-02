package com.oristartech.rule.core.result.filters.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.result.filters.IFactFilter;
import com.oristartech.rule.core.result.filters.IFactFilterChain;

/**
 * 匹配前过滤器链，必须每次过滤时重新创建本对象。
 * 基于list的过滤器链
 * @author chenjunfei
 *
 */
public class ListFactFilterChain implements IFactFilterChain {
	private List<IFactFilter> factFilters = new ArrayList<IFactFilter>();
	private Iterator<IFactFilter> filterIterator;

	/**
	 * @return the factFilters
	 */
	public List<IFactFilter> getFilters() {
		return factFilters;
	}

	/**
	 * @param factFilters the factFilters to set
	 */
	public void setFilters(List<IFactFilter> factFilters) {
		this.factFilters = factFilters;
		if(factFilters != null) {
			filterIterator = this.factFilters.iterator();
    	}
	}

	@Override
	public void doFilter(List<Object> facts) {
		if(filterIterator == null || !filterIterator.hasNext()) {
			return;
		}
		if(BlankUtil.isBlank(facts)) {
			return;
		}
		IFactFilter filter = filterIterator.next();
		filter.doFilter(facts, this);
	}

}
