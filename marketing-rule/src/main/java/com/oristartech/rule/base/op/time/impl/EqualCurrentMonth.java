package com.oristartech.rule.base.op.time.impl;

import java.util.Calendar;
import java.util.Date;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;

/**
 * 等于当月
 */
public class EqualCurrentMonth {
	public static boolean calc(CustomOpParameter opParam) {
		Object fieldValue = opParam.getFieldValue();
	
		if(fieldValue == null ) {
			return false;
		}
		Date curValueDate = null;
		if(fieldValue instanceof Date) {
			curValueDate = (Date)fieldValue;
		} else if((fieldValue instanceof String ) && !BlankUtil.isBlank(fieldValue)){ 
			String format = DateTimeUtil.getDateTimeFormat(fieldValue.toString());
			curValueDate = DateTimeUtil.convertStr2Date(fieldValue.toString(), format);
		} else {
			return false;
		}
		
		Calendar srcCalendar = Calendar.getInstance();
		Calendar destCalendar = Calendar.getInstance();
		srcCalendar.setTime(curValueDate);
		
		return srcCalendar.get(Calendar.MONTH) == destCalendar.get(Calendar.MONTH); 
	}
}
