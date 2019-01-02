package com.oristartech.rule.base.op.number.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.functions.op.util.NumberCompareOpEnum;
import com.oristartech.rule.functions.op.util.NumberCompareUtil;

/**
 * 自定义数值大于, 便于控制检查忽略某些指定的属性比较
 * @author chenjunfei
 *
 */
public class CustomGreater {
	public static boolean calc(CustomOpParameter opParam) {
		return NumberCompareUtil.compare(opParam, NumberCompareOpEnum.GREATER);
	}
}	
