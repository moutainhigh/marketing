package com.oristartech.rule.core.result.filters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.marketing.core.ApplicationContextHelper;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.core.result.filters.impl.ListFactFilterChain;
import com.oristartech.rule.core.result.filters.impl.ListResultFilterChain;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;

public class FilterUtil {
	private static Logger log = LoggerFactory.getLogger(FilterUtil.class);
	
	/**
	 * 每次处理时需要新建 FactFilterChain；
	 * @return
	 */
	public static IFactFilterChain createFactFilterChain(RuleTypeVO ruleType) {
		long start = System.currentTimeMillis();
		
		List<String> filterNames = JsonUtil.jsonArrayToList(ruleType.getFactFilters(), String.class);
		IFactFilterChain filterChain = new ListFactFilterChain();
		if(!BlankUtil.isBlank(filterNames)) {
			List<IFactFilter> filters = new ArrayList<IFactFilter>();
			for(String filterName  : filterNames) {
				filters.add(ApplicationContextHelper.getContext().getBean(filterName, IFactFilter.class));
			}
			filterChain.setFilters(filters);
		}
		
		if(log.isDebugEnabled()) {
			log.debug("创建fact filterchain时间=" + (System.currentTimeMillis() - start));
		}
		
		return filterChain;
    }
	
	/**
	 * 每次处理时需要新建 RuleResultFilterChain；
	 * @return
	 */
	public static IResultFilterChain createRuleResultFilterChain(RuleTypeVO ruleType) {
		long start = System.currentTimeMillis();
		
		List<String> filterNames = JsonUtil.jsonArrayToList(ruleType.getResultFilters(), String.class);
		IResultFilterChain filterChain = new ListResultFilterChain();
		if(!BlankUtil.isBlank(filterNames)) {
			List<IResultFilter> filters = new ArrayList<IResultFilter>();
			for(String filterName  : filterNames) {
				filters.add(ApplicationContextHelper.getContext().getBean(filterName, IResultFilter.class));
			}
			filterChain.setRuleResultFilters(filters);
		}
		
		if(log.isDebugEnabled()) {
			log.debug("创建result filterchain时间=" + (System.currentTimeMillis() - start));
		}
		
		return filterChain;
    }
}
