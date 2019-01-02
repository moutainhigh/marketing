package com.oristartech.rule.base.op.time.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;
import com.oristartech.rule.common.util.DateUtil;

/**
 * 当月前后后N月
 * @author chenjunfei
 *
 */
public class EqualCurrentMonthOffset {
	public static boolean calc(CustomOpParameter opParam) {
		//要比较的属性值
		Object fieldValue = opParam.getFieldValue();
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
		
		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTime(fieldValueDate);
		targetCalendar.set(Calendar.DAY_OF_MONTH, 1);
		
		int targetMonth = targetCalendar.get(Calendar.MONTH);
		
		Date now = DateUtil.convertStrToDate(DateUtil.getCurrDateStr(DateUtil.DEFAULT_SHORT_DATE_FORMAT), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTime(now);
		nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar afterCal = Calendar.getInstance();
		afterCal.setTime(now);
		afterCal.set(Calendar.DAY_OF_MONTH, 1);
		afterCal.add(Calendar.MONTH, offset);
		
		Calendar beforeCal = Calendar.getInstance();
		beforeCal.setTime(now);
		beforeCal.set(Calendar.DAY_OF_MONTH, 1);
		beforeCal.add(Calendar.MONTH, -offset);
		
		Set<Integer> betweenMonths = DateTimeUtil.getBetweenMonths(beforeCal, afterCal);
		
		return betweenMonths.contains(targetMonth); 
	}
}
