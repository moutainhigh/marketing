package com.oristartech.rule.base.op.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;
import com.oristartech.rule.constants.RuleConstans;

/**
 * 日期, 时间包含, 判断currentValue是否包含在parameter中, parameter是单个日期列表. 
 * 如"2013-01-02,2013-03-05",
 * 或"10:10:00,12:10:30"
 * 或"2013-02-10 10:10:10,2013-03-12 20:10:10"
 * 但需要在一次比较过程中格式一致
 * @author chenjunfei
 */
public class TimeContainOperator  {

	private static final String FORMAT_KEY = "timeFormat";
	private static final String DATES = "dates";
	
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
		List<Date> dates = (List<Date>) param.get(DATES);
		
		Date time = DateTimeUtil.trimTimeWithFormat(currentValue, format);
		if(BlankUtil.isBlank(dates)) {
			return false;
		}
		
		for(Date date : dates ) {
			if( time.compareTo(date) == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	private static Map<String, Object> prepareParameter(String parameter) {
		Map<String, Object> result = new HashMap<String, Object>();
		String timeFormat = null;
		List<Date> dateResults = new ArrayList<Date>();
		if(!BlankUtil.isBlank(parameter)) {
			String[] dates = parameter.split(RuleConstans.RULE_PARAMETER_SPLITER);
			for(String timeStr : dates) {
				if(!BlankUtil.isBlank(timeStr)) {
					if(timeFormat == null) {
						timeFormat = DateTimeUtil.getDateTimeFormat(timeStr);
					}
					if(timeFormat != null) {
						result.put(FORMAT_KEY, timeFormat);
					}
					dateResults.add(DateTimeUtil.convertStr2Date(timeStr, timeFormat));
				}
			}
		}
		result.put(DATES, dateResults);
		return result;
	}
}
