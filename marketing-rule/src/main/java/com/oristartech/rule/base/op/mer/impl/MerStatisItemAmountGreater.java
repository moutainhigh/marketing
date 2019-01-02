package com.oristartech.rule.base.op.mer.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.functions.op.util.MerStatisItemAmountUtil;
import com.oristartech.rule.functions.op.util.NumberCompareOpEnum;

/**
 * 若干商品统计累加数量大于, 通过获取SaleItemStatis 去统计, 统计那个属性，根据条件设置确定。
 * @author chenjunfei 
 *
 */
public class MerStatisItemAmountGreater {
	public static boolean calc(CustomOpParameter opParam) {
		return MerStatisItemAmountUtil.compare(opParam, NumberCompareOpEnum.GREATER);
	}
	
}
