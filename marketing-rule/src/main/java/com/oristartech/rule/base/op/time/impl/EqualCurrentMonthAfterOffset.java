package com.oristartech.rule.base.op.time.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;

/**
 * 当月后N月
 * @author chenjunfei
 *
 */
public class EqualCurrentMonthAfterOffset {
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
		
		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTime(fieldValueDate);
		targetCalendar.set(Calendar.DAY_OF_MONTH, 1);
		
		int targetMonth = targetCalendar.get(Calendar.MONTH);
		
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.set(Calendar.DAY_OF_MONTH, 1);
		

		Calendar afterCal = Calendar.getInstance();
		afterCal.set(Calendar.DAY_OF_MONTH, 1);
		afterCal.add(Calendar.MONTH, offset);
		
		Set<Integer> betweenMonths = DateTimeUtil.getBetweenMonths(nowCalendar, afterCal);
		
		return betweenMonths.contains(targetMonth); 
	}
}
