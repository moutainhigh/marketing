package com.oristartech.rule.base.op.common.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;

/**
 * 判断是否null
 * @author chenjunfei
 *
 */
public class NullOperator {
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
		if(currentValue == null) {
			return true;
		}
		return false;
	}
}
