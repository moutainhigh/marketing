package com.oristartech.rule.functions.result.filters.coupon.impl;

/**
 * 票券中商品条目的匹配度
 * @author chenjunfei
 *
 */
public enum CouponRuleSaleItemMatchState {
	ALL, //全匹配
	PART, //部分匹配
	NONE  //票券规则中不存在商品(影票,卖品)优惠条目
}
