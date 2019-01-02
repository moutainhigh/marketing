package com.oristartech.rule.functions.result.filters.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.core.functions.sales.util.SaleFactsUtil;
import com.oristartech.rule.core.result.filters.IResultFilter;
import com.oristartech.rule.core.result.filters.IResultFilterChain;
import com.oristartech.rule.drools.executor.context.FactsContainer;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.enums.RuleExecuteMode;
import com.oristartech.rule.vos.result.RuleResult;

/**
 * 过滤掉低优先级的结果, 只返回同最高优先级的结果, 同时若指定了期望的规则的执行模式, 先过滤不需要的执行模式
 * @author chenjunfei
 *
 */
@Component(value="rulePriorityResultFilter")
public class RulePriorityResultFilter implements IResultFilter {

	public void doFilter(RuleExecuteContext context, IResultFilterChain chain) {
		if(context == null) {
			return;
		}
		
		List<RuleResult> tempResults = context.getRuleResults();
		
		if(BlankUtil.isBlank(tempResults)) {
			return;
		}
		
		if(tempResults.size() > 1) {
			tempResults = getMostPriorityResult(tempResults);
			context.setRuleResults(tempResults);
		}
		
		if(!BlankUtil.isBlank(tempResults)) {
			//设置规则消耗条件
			Map<String , Object> contextInfo = getRuleContextInfo(context);
			boolean isOutputConsumeFact = getIsOutputConsumeFacts(contextInfo);
			for(RuleResult result : tempResults) {
				setConsumeFacts(result, context, isOutputConsumeFact);
			}
		}
	    chain.doFilter(context);
	}
	
	/**
	 * 获取事实中的执行上下文对象
	 * @param context
	 * @return
	 */
	private Map<String , Object> getRuleContextInfo(RuleExecuteContext context) {
		FactsContainer container = context.getFactsContainer();
		List<Object> contextInfos = container.getFactsByType(BizFactConstants.RULE_CONTEXT_INFO);
		if(!BlankUtil.isBlank(contextInfos)) {
			//该对象应该只有一个
			return (Map) contextInfos.get(0);
		}
		return null;
	}
	
	/**
	 * 获取是否输出消耗事实
	 * @param contextInfo
	 * @return
	 */
	private boolean getIsOutputConsumeFacts(Map<String , Object> contextInfo) {
		Boolean isOutputConsumeFact = false;
		if(contextInfo != null ) {
			isOutputConsumeFact = (Boolean)contextInfo.get(BizFactConstants.RCI_IS_OUTPUT_CONSUME_FACTS);
		}
		isOutputConsumeFact = isOutputConsumeFact == null ? false : isOutputConsumeFact;
		return isOutputConsumeFact;
	}
	
	//设置规则消耗条件
	private void setConsumeFacts(RuleResult result, RuleExecuteContext context, boolean isOutputConsumeFact) {
		if(isOutputConsumeFact) {
			SaleFactsUtil.setConsumeFacts(result, context);
		} else {
			//某些旧版外部系统接口, 若设置为null, 因为没判空会导致异常
			result.setConditionFacts(new ArrayList<Object>());
			result.setConditionFactTypes(null);
		}
	}
	
	/**
	 * 自动匹配中优先级低的结果,推荐的不用过滤
	 * @return
	 */
	private List<RuleResult> getMostPriorityResult(List<RuleResult> results) {
		List<RuleResult> mostPriorityResults = new ArrayList<RuleResult>();
		//先降序排序
		Collections.sort(results , new Comparator<RuleResult>(){
			public int compare(RuleResult o1, RuleResult o2) {
			    return o2.getRuleVO().getPriority() - o1.getRuleVO().getPriority();
			}
		});
		//只取最优先的值
		int maxPriority = Integer.MIN_VALUE;
		for(RuleResult result : results) {
			//推荐的直接加入.
			if(RuleExecuteMode.AUTO_MATCH_RECOMMEND.name().equals(result.getRuleVO().getExecuteMode())) {
				mostPriorityResults.add(result);
				continue;
			}
			//自动匹配的规则, 只加入最大优先级的
			if(result.getRuleVO().getPriority() >= maxPriority) {
				mostPriorityResults.add(result);
				maxPriority = result.getRuleVO().getPriority();
			} 
		}
		return mostPriorityResults;
	}

}
