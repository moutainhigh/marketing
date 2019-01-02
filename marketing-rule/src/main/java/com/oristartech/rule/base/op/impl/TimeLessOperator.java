package com.oristartech.rule.base.op.impl;

import java.util.Date;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;

/**
 * 时间小于
 * 会根据参数模式只比较模式中出现的时间属性,
 * 如若参数是'2013-02-01', 则只比较年月日, 而不比较小时等属性.
 * @author chenjunfei
 *
 */
public class TimeLessOperator {
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
		String parameter = opParam.getConditionParams();
	
		if(currentValue == null || BlankUtil.isBlank(parameter)) {
			return false;
		}
		if(currentValue != null && BlankUtil.isBlank(currentValue.toString())) {
			return false;
		}
		String format = DateTimeUtil.getDateTimeFormat(parameter);
		
		Date date = DateTimeUtil.trimTimeWithFormat(currentValue, format);
		Date paramDate = DateTimeUtil.convertStr2Date(parameter, format);
		if(date.before(paramDate)) {
			return true;
		}
		return false;
	}

}
