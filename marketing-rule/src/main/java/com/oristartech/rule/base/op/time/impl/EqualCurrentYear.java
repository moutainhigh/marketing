package com.oristartech.rule.base.op.time.impl;

import java.util.Calendar;
import java.util.Date;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;

/**
 * 等于当年
 * @author chenjunfei
 */
public class EqualCurrentYear {
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
	
		if(currentValue == null ) {
			return false;
		}
		Date curValueDate = null;
		if(currentValue instanceof Date) {
			curValueDate = (Date)currentValue;
		} else if((currentValue instanceof String ) && !BlankUtil.isBlank(currentValue)){
			String format = DateTimeUtil.getDateTimeFormat(currentValue.toString());
			curValueDate = DateTimeUtil.convertStr2Date(currentValue.toString(), format);
		} else {
			return false;
		}
		
		Calendar srcCalendar = Calendar.getInstance();
		Calendar destCalendar = Calendar.getInstance();
		srcCalendar.setTime(curValueDate);
		
		return srcCalendar.get(Calendar.YEAR) == destCalendar.get(Calendar.YEAR); 
	}
}
