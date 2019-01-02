package com.oristartech.rule.base.op.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;
import com.oristartech.rule.common.util.JsonUtil;

/**
 * 
 * @author chenjunfei
 *
 */
public class TimeRangeContainOperator  {
	
	private static final String FORMAT_KEY = "timeFormat";
	private static final String DATE_RANGES = "dateRanges";
	/**
	 * 判断currentValue 在 parameter指定的时段范围内
	 * @param currentValue
	 * @param parameter
	 * @return
	 */
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
		String parameter = opParam.getConditionParams();
	
		if(currentValue == null || BlankUtil.isBlank(parameter)) {
			return false;
		}
		if(currentValue != null && BlankUtil.isBlank(currentValue.toString())) {
			return false;
		}
		
		Map<String, Object> param = prepareParameter(parameter);
		String format = (String)param.get(FORMAT_KEY);
		List<List<Date>> dateRanges = (List<List<Date>>) param.get(DATE_RANGES);
		
		Date time = DateTimeUtil.trimTimeWithFormat(currentValue, format);
		if(BlankUtil.isBlank(dateRanges)) {
			return false;
		}
		
		for(List<Date> dateGroup : dateRanges ) {
			Date start = dateGroup.get(0) == null ? createStartTimeOfDay(new Date(), format)  :  dateGroup.get(0)  ;
			Date end = dateGroup.get(1) == null ? createEndTimeOfDay(new Date(), format) : dateGroup.get(1) ;
			
			boolean result = false;
			if((DateTimeUtil.FMT_TIME_HOUR_MINUTE.equals(format) || DateTimeUtil.FMT_TIME_HOUR_MINUTE_SEC.equals(format))
				&& (start.compareTo(end) > 0 )) {
				//时段设置象晚上（17:00—02:00）时, 表示跨了日
				Date endOfStartDay = createEndTimeOfDay(start, format); 
				Date startOfEndDay = createStartTimeOfDay(end, format);
				if(isBetween(time, start, endOfStartDay) || isBetween(time, startOfEndDay, end)) {
					result = true;
				}
			} else {
				result = isBetween(time, start, end);
			}
			
			//只要符合一段时间,则返回true
			if(result) {
				return true;
			}
		}
		return false;
	}
	
	
	//创建当前日期,时间是23:59:59秒
	private static Date createEndTimeOfDay(Date day, String format) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(day);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		if(DateTimeUtil.FMT_TIME_HOUR_MINUTE.equals(format)) {
			ca.set(Calendar.SECOND, 0);
		}
		return ca.getTime();
	}
	
	private static Date createStartTimeOfDay(Date day, String format) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(day);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return ca.getTime();
	}
	
	
	private static boolean isBetween(Date time, Date start, Date end) {
		boolean greater = true;
		boolean less = true;
		if(start != null) {
			greater = time.compareTo(start) >= 0 ;
		}
		if(end != null) {
			less = time.compareTo(end) <= 0;
		}
		//只要符合一段时间,则返回true
		if(greater && less) {
			return true;
		}
		return false;
	}
	
	private static Map<String, Object> prepareParameter(String parameter) {
		Map<String, Object> result = new HashMap<String, Object>();
		String timeFormat = null;
		List<List<Date>> dates = new ArrayList<List<Date>>();
		if(!BlankUtil.isBlank(parameter)) {
			List<List> dateRanges = JsonUtil.jsonArrayToList(parameter, List.class);
			if(!BlankUtil.isBlank(dateRanges)) {
				for(List dateGroup : dateRanges) {
					if(!BlankUtil.isBlank(dateGroup)) {
						List<Date> dGroup = new ArrayList<Date>();
						if(dateGroup.size() < 2) {
							throw new RuntimeException("日期段,时间段格式错误");
						}
						for(Object obj : dateGroup) {
							String timeStr = (String) obj;
							if(timeFormat == null) {
								timeFormat = DateTimeUtil.getDateTimeFormat(timeStr);
							}
							if(timeFormat != null) {
								result.put(FORMAT_KEY, timeFormat);
							}
							dGroup.add(DateTimeUtil.convertStr2Date(timeStr, timeFormat));
						}
						dates.add(dGroup);
					}
				}
			}
		}
		result.put(DATE_RANGES, dates);
		return result;
	}
}
