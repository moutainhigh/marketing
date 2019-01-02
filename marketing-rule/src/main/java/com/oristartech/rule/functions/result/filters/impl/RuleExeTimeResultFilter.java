package com.oristartech.rule.functions.result.filters.impl;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.ApplicationContextHelper;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.core.service.IRuleExeStatisService;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.core.result.filters.IResultFilter;
import com.oristartech.rule.core.result.filters.IResultFilterChain;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.search.RuleExeStatisCondition;
import com.oristartech.rule.vos.result.RuleResult;

/**
 * 把执行次数超限的过滤掉
 * @author chenjunfei
 *
 */
@Component(value="ruleExeTimeResultFilter")
public class RuleExeTimeResultFilter implements IResultFilter {
	
	private static Logger log = LoggerFactory.getLogger(RuleExeTimeResultFilter.class);
	
	public void doFilter(RuleExecuteContext context, IResultFilterChain chain) {
		if(context == null) {
			return;
		}
		
		List<RuleResult> tempResults = context.getRuleResults();
		if(BlankUtil.isBlank(tempResults) ) {
			return;
		}
		removeLargeExeTimeRule(tempResults);
	    chain.doFilter(context);
	}
	
	private void removeLargeExeTimeRule(List<RuleResult> tempResults) {
		Iterator<RuleResult> it = tempResults.iterator();
		IRuleExeStatisService ruleExeStatisService = ApplicationContextHelper.getContext().getBean(IRuleExeStatisService.class);
		while(it.hasNext()) {
			RuleResult result = it.next();
			RuleExeStatisCondition condition = BizFunctionUtil.createStatCondition(result.getRuleVO());
			if(condition != null) {
			    boolean isLess = ruleExeStatisService.lessThanConstraintAmount(condition);
			    if(!isLess) {
			    	log.debug("规则匹配，但已经超过限制次数， ruleId=" + result.getRuleVO().getId() );
			    	it.remove();
			    }
			}
		}
	}

}
