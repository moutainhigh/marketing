package com.oristartech.rule.base.op.time.impl;

import java.util.Calendar;
import java.util.Date;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;
import com.oristartech.rule.common.util.DateUtil;

/**
 * 当月前后N日，忽略年份
 * @author chenjunfei
 *
 */
public class EqualCurrentDayOffset {
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
		
		Date now = DateUtil.convertStrToDate(DateUtil.getCurrDateStr(DateUtil.DEFAULT_SHORT_DATE_FORMAT), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		Date fieldDate = DateUtil.convertStrToDate(
				DateUtil.convertDateToStr(fieldValueDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT),
				DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		
		Calendar caNow = Calendar.getInstance();
		caNow.setTime(now);
		
		Calendar caFieldDate = Calendar.getInstance();
		caFieldDate.setTime(fieldDate);
		caFieldDate.set(Calendar.YEAR , caNow.get(Calendar.YEAR));
		
		Calendar minCal = caNow;
		Calendar maxCal = caFieldDate;
		
		if(minCal.compareTo(maxCal) > 0) {
			minCal = caFieldDate;
			maxCal = caNow;
		}
		
		//小到大日期相邻
		int less = (int)DateUtil.dateDiff("dd", minCal.getTime(), maxCal.getTime());
		if(less <= offset) {
			return true;
		}
		
		//大日期到一年后小日期
		minCal.add(Calendar.YEAR, 1);
		int more = (int)DateUtil.dateDiff("dd", maxCal.getTime(), minCal.getTime());
		if(more <= offset) {
			return true;
		}
		
		return false; 
	}
}
