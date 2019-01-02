package com.oristartech.rule.functions.result.filters.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.core.result.filters.IResultFilter;
import com.oristartech.rule.core.result.filters.IResultFilterChain;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.functions.sort.util.SalesActivityUtil;
import com.oristartech.rule.functions.sort.util.SortRuleResultUtil;
import com.oristartech.rule.vos.core.enums.RuleExecuteMode;
import com.oristartech.rule.vos.result.RuleResult;

/**
 * 把销售结果两两比较排序后的结果, 选出最好结果
 * @author chenjunfei
 */
@Component(value="ruleSimpleRuleResultFilter")
public class SimpleSaleRuleResultFilter implements IResultFilter {
	
	
	public SimpleSaleRuleResultFilter() {
//		ISystemConfigService configService = ApplicationContextHelper.getContext().getBean(ISystemConfigService.class);
//		pricingAction = configService.getValueByName(DEFAULT_PRICING_CONFIG_NAME);
//		if(!BlankUtil.isBlank(pricingAction)) {
//			pricingAction = DEFAULT_PRICING_CATEGORY_ACTION;
//		}
	}
	//商品,影票等价格属性名称
	public void doFilter(RuleExecuteContext context, IResultFilterChain chain) {
		if(context == null) {
			return;
		}
		
		List<RuleResult> tempResults = context.getRuleResults();
		if(BlankUtil.isBlank(tempResults) ) {
			return;
		}
		if(tempResults.size() > 1) {
			Map<String, List<RuleResult>> ruleResultMap = categoryByExecuteMode(tempResults);
			if(isOutGroupPriceResult(context)) {
				tempResults = genGroupResult(ruleResultMap, context);
			} else {
				tempResults = genPriceResult(ruleResultMap, context);
			}
			
			context.setRuleResults(tempResults);
		}
		
	    chain.doFilter(context);
	}
	
	/**
	 * 普通的排序并返回
	 * @param ruleResultMap
	 * @param context
	 * @return
	 */
	private List<RuleResult> genPriceResult(Map<String, List<RuleResult>> ruleResultMap, RuleExecuteContext context ) {
		//分别对execute mode分类进行排序
		List<Object> saleItems = SortRuleResultUtil.getSaleItems(context);
		List<RuleResult> tempResults = context.getRuleResults();
		for(String exeMode : ruleResultMap.keySet()) {
			tempResults = ruleResultMap.get(exeMode);
			if(RuleExecuteMode.AUTO_MATCH_RECOMMEND.name().equals(exeMode)) {
				tempResults = SortRuleResultUtil.sortRecommendModeRule(tempResults, saleItems);
			} else {
				tempResults = SortRuleResultUtil.sortLowestPriceResult(tempResults, saleItems);
			}
			ruleResultMap.put(exeMode, tempResults);
		}
		tempResults = new ArrayList<RuleResult>();
		//auto 排在前面
		if(!BlankUtil.isBlank(ruleResultMap.get(RuleExecuteMode.AUTO_MATCH.toString()))) {
			tempResults.addAll(ruleResultMap.get(RuleExecuteMode.AUTO_MATCH.toString()));
		}
		for(String exeMode : ruleResultMap.keySet()) {
			if(!exeMode.equals(RuleExecuteMode.AUTO_MATCH.toString()) && !BlankUtil.isBlank(ruleResultMap.get(exeMode))) {
				tempResults.addAll(ruleResultMap.get(exeMode));
			}
		}
		return tempResults;
	}
	
	/**
	 * 分组组合排序并返回
	 * @param ruleResultMap
	 * @param context
	 * @return
	 */
	private List<RuleResult> genGroupResult(Map<String, List<RuleResult>> ruleResultMap, RuleExecuteContext context) {
		//分组返回, 只返回自动执行的, 因为通常是第三方这样.
		List<RuleResult> tempResults = ruleResultMap.get(RuleExecuteMode.AUTO_MATCH);
		tempResults = SalesActivityUtil.chooseMostPriceFavorables(context);
		context.setRuleResults(tempResults);
		return tempResults;
	}
	
	/**
	 * 是否按分组组合选优
	 * @param context
	 * @return
	 */
	private boolean isOutGroupPriceResult(RuleExecuteContext context) {
		List<Object> contextInfos = context.getFactsContainer().getFactsByType(BizFactConstants.RULE_CONTEXT_INFO);
		if(!BlankUtil.isBlank(contextInfos)) {
			Boolean isOutGroup = (Boolean)BeanUtils.getProperty(contextInfos.get(0), BizFactConstants.RCI_IS_OUT_GROUP_PRICE_RESULT);
			if(isOutGroup != null) {
				return isOutGroup;
			}
		}
		return false;
	}
	
	/**
	 * 根据execute分类, 返回map，key是execute mode的name，value是对应的result
	 * @param srcResults
	 * @return
	 */
	private Map<String, List<RuleResult>> categoryByExecuteMode(List<RuleResult> srcResults) {
		Map<String, List<RuleResult>> result = new HashMap<String, List<RuleResult>>();
		for(RuleResult rt : srcResults) {
			if(!result.containsKey(rt.getRuleVO().getExecuteMode())) {
				result.put(rt.getRuleVO().getExecuteMode(), new ArrayList<RuleResult>());
			}
			result.get(rt.getRuleVO().getExecuteMode()).add(rt);
		}
		return result;
	}
	
}
