package com.oristartech.rule.functions.result.filters.coupon.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DataTypeConvertUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.BizResultConstants;
import com.oristartech.rule.core.executor.util.RuleExecutorUtil;
import com.oristartech.rule.core.result.filters.IResultFilter;
import com.oristartech.rule.core.result.filters.IResultFilterChain;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.functions.sales.CouponModifyPriceFunction;
import com.oristartech.rule.functions.sales.ExchangeCouponFunction;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleSectionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.RuleSectionResult;

/**
 * 票券匹配度filter, 在结果中添加匹配情况. 只需要在票券类的RuleType中配置本filter.
 * @author chenjunfei
 * 
 * 计算逻辑:
 * 规则匹配程度”，
 * 0代表完全匹配；
 * 1代表影票匹配，卖品不完全匹配；
 * 2代表影票不完全匹配，卖品完全匹配；
 * 3代表影票和卖品都不完全匹配。
 * 匹配指的是兑换规则中，能兑换的商品与外部系统传入的商品的对比，影票只对比数量，卖品需要对比卖品条目和数量。
 *　单独只有影票或者单独只有卖品，没有完全匹配时，属于3
 */
@Component(value="couponMatchResultFilter")
public class CouponMatchResultFilter implements IResultFilter {
	private final String RULE_TYPE_NAME = "CouponSale" ;
	private final String FILM_SECTION_AMOUNT = "filmAectionAmount" ;
	private final String MER_SECTION_AMOUNT = "merAectionAmount" ;
	
	public void doFilter(RuleExecuteContext context, IResultFilterChain chain) {
		if(context == null) {
			return;
		}
		
		List<RuleResult> tempResults = context.getRuleResults();
		if(BlankUtil.isBlank(tempResults) ) {
			return;
		}
		
		for(RuleResult result : tempResults) {
			if(RULE_TYPE_NAME.equals(result.getRuleVO().getRuleType())) {
				setMatchInfoToResult(context, result);
			}
		}
		chain.doFilter(context);
	}

	private void setMatchInfoToResult(RuleExecuteContext context, RuleResult ruleResult) {
		RuleVO ruleVO = RuleExecutorUtil.getRule(context, ruleResult.getRuleVO().getId());
		Map<String, Map<Long, Integer>> sectionAmount = getSectionAmoutMap(ruleVO);
		CouponRuleMatchState matchState = CouponRuleMatchState.ALL_MATCH;
		if(!BlankUtil.isBlank(sectionAmount)) {
			CouponRuleSaleItemMatchState filmMatchState = isSecitionAllMatch(sectionAmount.get(FILM_SECTION_AMOUNT), ruleResult);
			CouponRuleSaleItemMatchState merMatchState = isSecitionAllMatch(sectionAmount.get(MER_SECTION_AMOUNT), ruleResult);
			switch(filmMatchState) {
			case NONE : switch(merMatchState) {
						case PART :  matchState = CouponRuleMatchState.FIML_PART_MER_PART; break;
						case ALL : matchState = CouponRuleMatchState.ALL_MATCH; break;
						}
						break;
						
			case ALL : switch(merMatchState) {
						case NONE :  matchState = CouponRuleMatchState.ALL_MATCH; break;
						case ALL : matchState = CouponRuleMatchState.ALL_MATCH; break;
						case PART : matchState = CouponRuleMatchState.FILM_ALL_MER_PART; break;
						}
						break;
						
			case PART : switch(merMatchState) {
						case NONE :  matchState = CouponRuleMatchState.FIML_PART_MER_PART; break;
						case ALL : matchState = CouponRuleMatchState.FILM_PART_MER_ALL; break;
						case PART : matchState = CouponRuleMatchState.FIML_PART_MER_PART; break;
						}
						break;
			}
		}
		ruleResult.getBizPropertyMap().put(BizResultConstants.COUPON_MATCH_STATUS, matchState.ordinal());
	}
	
	
	//匹配修改情况
	private CouponRuleSaleItemMatchState isSecitionAllMatch(Map<Long, Integer> sectionAmount, RuleResult ruleResult) {
		//若存在影票兑换条目, 所有条目,而且数量都满足则是全匹配
		//若不存在影票兑换条目, 直接返回全匹配
		if(BlankUtil.isBlank(sectionAmount)) {
			return CouponRuleSaleItemMatchState.NONE;
		}
		//可以从result中找出的对应sectionid的结果数量
		int relateSecResultCount = 0; 
		for(Long secId : sectionAmount.keySet()) {
			Integer needAmount = sectionAmount.get(secId);
			RuleSectionResult secResult = ruleResult.getSectionResult(secId);
			if(secResult != null) {
				relateSecResultCount ++;
				Integer existModifyAmount = secResult.getModifyFactAmount() ;
				if(existModifyAmount < needAmount) {
					return CouponRuleSaleItemMatchState.PART;
				}
			}
		}
		if(relateSecResultCount < sectionAmount.keySet().size()) {
			return CouponRuleSaleItemMatchState.PART;
		}
		return CouponRuleSaleItemMatchState.ALL;
	}
	
	/**
	 * 根据商品类型(影票,卖品)获取对应的section, 和他的数量
	 * @param ruleVO
	 * @param saleItemtype (影票,卖品)
	 * @return map key是sectionId, value是商品数量
	 */
	private Map<String, Map<Long, Integer>> getSectionAmoutMap(RuleVO ruleVO) {
		Map<Long, Integer> filmSectionAmount = new HashMap<Long, Integer>();
		Map<Long, Integer> merSectionAmount = new HashMap<Long, Integer>();
		
		for(RuleSectionVO section : ruleVO.getRuleSections()) {
			Map<String, String> param = section.findFirstActionParam(ExchangeCouponFunction.class.getSimpleName());
			//表示不是兑换券规则
			if(BlankUtil.isBlank(param)) {
				return null;
			}
			String itemType = getSaleItemInCon(section);
			Integer amount = DataTypeConvertUtil.toInteger(param.get(CouponModifyPriceFunction.MODIFY_AMOUNT));
			if(BizFactConstants.FILM_ITEM.equals(itemType)) {
				filmSectionAmount.put(section.getId(), amount);
			} else {
				merSectionAmount.put(section.getId(), amount);
			}
		}
		Map<String, Map<Long, Integer>> result = new HashMap<String, Map<Long, Integer>>();
		result.put(FILM_SECTION_AMOUNT, filmSectionAmount);
		result.put(MER_SECTION_AMOUNT, merSectionAmount);
		
		return result;
	}
	
	private String getSaleItemInCon(RuleSectionVO section) {
		List<RuleConditionElementVO> elements = section.findConditionElements(BizFactConstants.SALE_ITEM_INFO, BizFactConstants.SALE_ITEM_TYPE);
		if(BlankUtil.isBlank(elements)) {
			elements = section.findConditionElements(BizFactConstants.FILM_INFO, BizFactConstants.SALE_ITEM_TYPE);
			if(BlankUtil.isBlank(elements)) {
				elements = section.findConditionElements(BizFactConstants.MER_INFO, BizFactConstants.SALE_ITEM_TYPE);
			}
		}
		if(BlankUtil.isBlank(elements)) {
			throw new RuleExecuteRuntimeException("票券没设置商品类型条件");
		}
		return elements.get(0).getOperand();
	}
}
