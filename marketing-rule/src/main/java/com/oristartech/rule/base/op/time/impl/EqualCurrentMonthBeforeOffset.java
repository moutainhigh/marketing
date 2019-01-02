package com.oristartech.rule.base.op.time.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;

/**
 * 等于当月前n月
 */
public class EqualCurrentMonthBeforeOffset {
	public static boolean calc(CustomOpParameter opParam) {
		//要比较的属性值
		Object fieldValue = opParam.getFieldValue();
		//规则条件中的参数值
		String parameter = opParam.getConditionParams();
	
		if(fieldValue == null || BlankUtil.isBlank(parameter)) {
			return false;
		}
		
		int offset = Math.abs(Integer.valueOf(parameter));
		
		Date fieldValueDate = null;
		if(fieldValue instanceof Date) {
			fieldValueDate = (Date)fieldValue;
		} else if((fieldValue instanceof String ) && !BlankUtil.isBlank(fieldValue)){
			String format = DateTimeUtil.getDateTimeFormat(fieldValue.toString());
			fieldValueDate = DateTimeUtil.convertStr2Date(fieldValue.toString(), format);
		} else {
			return false;
		}
		
		Calendar tergetCalendar = Calendar.getInstance();
		tergetCalendar.setTime(fieldValueDate);
		tergetCalendar.set(Calendar.DAY_OF_MONTH, 1);
		
		int targetMonth = tergetCalendar.get(Calendar.MONTH);
		
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar beforeCal = Calendar.getInstance();
		beforeCal.set(Calendar.DAY_OF_MONTH, 1);
		beforeCal.add(Calendar.MONTH, -offset);
		
		Set<Integer> betweenMonths = DateTimeUtil.getBetweenMonths(beforeCal, nowCalendar);
		
		return betweenMonths.contains(targetMonth) ; 
	}
}
