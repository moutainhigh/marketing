package com.oristartech.rule.base.op.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;
import com.oristartech.rule.common.util.JsonUtil;

/**
 * 时间区间比较符号，参数是数组，包含两个值，开始和结束；
 * @author chenjunfei
 *
 */
public class TimeBetweenOperator  {
	
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
		List<Date> dateRanges = (List<Date>) param.get(DATE_RANGES);
		
		Date time = DateTimeUtil.trimTimeWithFormat(currentValue, format);
		Date start = dateRanges.get(0);
		Date end = dateRanges.get(1);
		if(time.compareTo(start) >= 0 && time.compareTo(end) <=0) {
			return true;
		}
		return false;
	}
	
	private static Map<String, Object> prepareParameter(String parameter) {
		Map<String, Object> result = new HashMap<String, Object>();
		String timeFormat = null;
		List<Date> dates = new ArrayList<Date>();
		List<String> dateRangeStrParams = JsonUtil.jsonArrayToList(parameter, String.class);
		if(dateRangeStrParams.size() < 2) {
			throw new RuleExecuteRuntimeException("日期段,时间段格式错误");
		}
		String start = dateRangeStrParams.get(0);
		String end = dateRangeStrParams.get(1);
		
		timeFormat = DateTimeUtil.getDateTimeFormat(start);
		if(timeFormat == null) {
			throw new RuleExecuteRuntimeException("日期段,时间段格式错误");
		}
		result.put(FORMAT_KEY, timeFormat);
		dates.add(DateTimeUtil.convertStr2Date(start, timeFormat));
		dates.add(DateTimeUtil.convertStr2Date(end, timeFormat));
		result.put(DATE_RANGES, dates);
		return result;
	}
}
