package com.oristartech.rule.base.op.time.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;

/**
 * 等于当月, 若对象属性值是null，则返回true
 */
public class EqualCurrentMonthIgnoreNull {
	public static boolean calc(CustomOpParameter opParam) {
		Object fieldValue = opParam.getFieldValue();
	
		if(fieldValue == null ) {
			return true;
		}
		
		return EqualCurrentMonth.calc(opParam);
	}
}
