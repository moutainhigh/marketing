package com.oristartech.rule.base.op.number.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.functions.op.util.NumberCompareOpEnum;
import com.oristartech.rule.functions.op.util.NumberCompareUtil;

public class CustomLessEqual {
	public static boolean calc(CustomOpParameter opParam) {
		return NumberCompareUtil.compare(opParam, NumberCompareOpEnum.LESS_EQUAL);
	}
}
