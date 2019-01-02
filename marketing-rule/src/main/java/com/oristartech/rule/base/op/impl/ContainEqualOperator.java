package com.oristartech.rule.base.op.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;

/**
 * 包含份数是否大于等于1, 适用允许倍数的情况. 就是除法运算后,商是否大于等于1
 * 例如操作数是2, 但客户端传过来是4时, 若是用普通的== 比较, 
 * 在某些情况下是不正确的.
 * @author chenjunfei
 *
 */
public class ContainEqualOperator {
	/**
	 * 当商数大于等于1时为true
	 * @param currentValue
	 * @param parameter
	 * @return
	 */
	public static boolean calc(CustomOpParameter opParam) {
		return true;
	}
}
