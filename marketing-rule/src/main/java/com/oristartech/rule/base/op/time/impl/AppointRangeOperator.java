package com.oristartech.rule.base.op.time.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;


/**
 * 动态周期中指定周期比较
 * @author chenjunfei
 *
 */
public class AppointRangeOperator {
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
		String parameter = opParam.getConditionParams();
	
		if(currentValue == null && parameter == null) {
			return true;
		}
		if(currentValue != null && currentValue.equals(parameter)) {
			return true;
		}
		return false;
	}
}
