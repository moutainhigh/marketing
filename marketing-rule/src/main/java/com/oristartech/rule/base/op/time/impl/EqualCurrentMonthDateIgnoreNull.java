package com.oristartech.rule.base.op.time.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;
/**
 * 等于当月当日, 只比较月份和日期, 若对象属性值为null，返回true
 */
public class EqualCurrentMonthDateIgnoreNull {
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
	
		if(currentValue == null ) {
			return true;
		}
		return EqualCurrentMonthDate.calc(opParam);
	}
}
