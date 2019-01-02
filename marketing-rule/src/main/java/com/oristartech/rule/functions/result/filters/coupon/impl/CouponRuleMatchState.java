package com.oristartech.rule.functions.result.filters.coupon.impl;

/**
 * 票券匹配度状态, 通常只有兑换券有区别, 其他都是完全匹配.
 * @author chenjunfei
 *
 */
public enum CouponRuleMatchState {
	//(完全匹配指数量和兑换行数都匹配)
	ALL_MATCH,   //影票,卖品都完全匹配  
	FILM_ALL_MER_PART , // 影票匹配，卖品不完全匹配
	FILM_PART_MER_ALL, //影票不完全匹配，卖品完全匹配
	FIML_PART_MER_PART //影票和卖品都不完全匹配
}
