package com.oristartech.rule.base.op.impl;

import java.util.Calendar;
import java.util.Date;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;
import com.oristartech.rule.constants.RuleConstans;

/**
 * 指定星期范围, 从传入的时间中,找出星期, 对比是否在范围内. 
 * 星期参数是逗号分隔的字符串如: 1,2,4等,1表示星期一,依次类推
 * @author chenjunfei
 *
 */
public class WeekDateRangeOperator {
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
		String parameter = opParam.getConditionParams();
	
		if(currentValue == null ||  BlankUtil.isBlank(parameter)) {
			return false;
		}
		
		if(currentValue != null && BlankUtil.isBlank(currentValue.toString())) {
			return false;
		}
		
		String[] weeks = parameter.split(RuleConstans.RULE_PARAMETER_SPLITER);
		String week = getInputWeek(currentValue);
		for(String w : weeks) {
			if(w.equals(week)) {
				return true;
			}
		}
		return false;
	}
	
	private static String getInputWeek(Object currentValue) {
		Date date = null;
		if(currentValue instanceof String ) {
			date = DateTimeUtil.convertStr2Date((String) currentValue, null);
		} else if(currentValue instanceof Long) {
			date = new Date((Long)currentValue);
		} else {
			date = (Date) currentValue;
		}
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		//注意jdk的星期是从星期日开始的,即1是星期日。
		int week = ca.get(Calendar.DAY_OF_WEEK);
		if(week == 1) {
			week = 7;
		} else {
			week = week -1;
		}
		return new Integer(week).toString();
	}
}
