package com.oristartech.rule.base.op.time.impl;

import java.util.Calendar;
import java.util.Date;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;
import com.oristartech.rule.constants.RuleConstans;

/**
 * 对比事实中的参数日期是否在参数指定的日列表中. 只对比日期, 不对比年,和月, 参数是又逗号"," 分隔的日期字符串 
 * @author chenjunfei
 *
 */
public class DayContainOperator {
	
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
		String parameter = opParam.getConditionParams();
	
		if(currentValue == null || BlankUtil.isBlank(parameter)) {
			return false;
		}
		if(currentValue != null && BlankUtil.isBlank(currentValue.toString())) {
			return false;
		}
		
		Date fieldDate = DateTimeUtil.convertObj2Date(currentValue);
		Calendar ca = Calendar.getInstance();
		ca.setTime(fieldDate);
		String curDay = String.valueOf(ca.get(Calendar.DAY_OF_MONTH));
		
		String[] days = parameter.split(RuleConstans.RULE_PARAMETER_SPLITER);
		for(String day : days) {
			if(curDay.equals(day.trim())) {
				return true;
			}
		}
		
		return false;
	}
}
