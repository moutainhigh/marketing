package com.oristartech.rule.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class DateTimeUtil {
	public static final String FMT_TIME_HOUR_MINUTE = "HH:mm";
	public static final String FMT_TIME_HOUR_MINUTE_SEC = "HH:mm:ss";
	
	public static final String FMT_YEAR = "yyyy";
	public static final String FMT_YEAR_MONTH = "yyyy-MM";
	public static final String FMT_MONTH_DAY = "MM-dd";
	public static final String FMT_DATE = "yyyy-MM-dd";
	
	public static final String FMT_DATE_TIME_MINUTE = "yyyy-MM-dd HH:mm";	
	public static final String FMT_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 把日期,时间, 或日期时间字符串变为日期,如'10:12:50', '2013-10-12', '2013-10-20 01:15:20'
	 * @param timeStr
	 * @param formats
	 * @return
	 */
	public static Date convertStr2Date(String timeStr, String format) {
		if(BlankUtil.isBlank(timeStr)) {
			return null;
		}
		if(BlankUtil.isBlank(format)) {
			format = getDateTimeFormat(timeStr);
		}
		
		Date time = DateUtil.convertStrToDate(timeStr, format);
		return time;
	}
	
	public static Date convertObj2Date(Object value) {
		if(BlankUtil.isBlank(value)) {
			return null;
		}
		Date date = null;
		if(value instanceof String) {
			date = DateTimeUtil.convertStr2Date((String)value, null);
		} else if( value instanceof Long) {
			date = new Date((Long)value);
		} else if(value instanceof Date){
			date = (Date)value;
		} else {
			throw new RuntimeException("日期参数有误");
		}
		
		return date;
	}
	
	/**
	 * 先把日期转化为目标格式, 再转回来, 便于忽略不需要比较的时间属性
	 * @param value
	 * @param format
	 * @return
	 */
	public static Date trimTimeWithFormat(Object value, String format) {
		if(value == null || format == null) {
			return null;
		}
		Date source = convertObj2Date(value);
		Date time = DateTimeUtil.convertStr2Date(DateUtil.convertDateToStr(source, format), format);
		return time;
	}
	
	/**
	 * 获取小时,分,秒的模式
	 * @param timeStr
	 * @return
	 */
	public static String getTimeFormat(String timeStr) {
		if(!BlankUtil.isBlank(timeStr)) {
			if(timeStr.indexOf(":") <= 0) {
				return null;
			}
			if(timeStr.split(":").length > 2) {
				return FMT_TIME_HOUR_MINUTE_SEC;
			} else {
				return FMT_TIME_HOUR_MINUTE;
			}
		}
		return null;
	}
	
	/**
	 * 获取时间日期模式
	 * @param dateStr
	 * @return
	 */
	public static String getDateTimeFormat(String dateStr) {
		if(!BlankUtil.isBlank(dateStr) ) {
			if(dateStr.indexOf("-") > 0) {
				if(dateStr.length() == FMT_MONTH_DAY.length()) {
					return FMT_MONTH_DAY;
				}else if(dateStr.length() <= FMT_YEAR_MONTH.length()) {
					return FMT_YEAR_MONTH;
				} else if(dateStr.length() <= FMT_DATE.length() ) {
					return FMT_DATE;
				} else if (dateStr.length() <= FMT_DATE_TIME_MINUTE.length()){
					return FMT_DATE_TIME_MINUTE;
				} else {
					return FMT_DATE_TIME_SECOND;
				}
			} else if ((dateStr.length() == FMT_YEAR.length()) && (dateStr.indexOf(":") < 0) ){
				return FMT_YEAR;
			} else {
				return getTimeFormat(dateStr);
			}
		}
		return null;
	}
	
	/**
	 * 方法用途和描述: 获取本周的第一天的日期,从星期一开始，星期日结束
	 * <p>
	 * 方法的实现逻辑描述:
	 * 
	 * @return
	 * @author huangtao 新增日期：2008-8-12
	 * @author huangtao 修改日期：2008-8-12
	 */
	public static Date getBeginDateOfWeek() {
		Calendar cal = Calendar.getInstance();
		int amount = cal.get(Calendar.DAY_OF_WEEK);
		if (amount == 1) {
			cal.add(Calendar.DAY_OF_MONTH, -6);
		} else {
			cal.add(Calendar.DAY_OF_MONTH, 2 - amount);
		}
		return cal.getTime();
	}
	
	/**
	 * 方法用途和描述: 获取本周的最后一天的日期,从星期一开始，星期日结束
	 * <p>
	 * 方法的实现逻辑描述:
	 * 
	 * @return
	 * @author huangtao 新增日期：2008-8-12
	 * @author huangtao 修改日期：2008-8-12
	 */
	public static Date getEndDateOfWeek() {
		Calendar cal = Calendar.getInstance();
		int amount = cal.get(Calendar.DAY_OF_WEEK);
		if (amount == 1) {
			cal.add(Calendar.DAY_OF_MONTH, 0);
		} else {
			cal.add(Calendar.DAY_OF_MONTH, 8 - amount);
		}
		return cal.getTime();
	}
	
	/**
	 * 方法用途和描述: 获取本月的第一天
	 * <p>
	 * 方法的实现逻辑描述:
	 * 
	 * @return
	 * @author huangtao 新增日期：2008-8-12
	 * @author huangtao 修改日期：2008-8-12
	 */
	public static Date getBeginDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * 方法用途和描述: 获取本月的最后一天
	 * <p>
	 * 方法的实现逻辑描述:
	 * 
	 * @return
	 * @author huangtao 新增日期：2008-8-12
	 * @author huangtao 修改日期：2008-8-12
	 */
	public static Date getEndDayOfMonth() {
		Calendar cal = Calendar.getInstance();    
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return cal.getTime();
	}
	
	
	/**
	 * 获取当季第一天
	 * @return
	 */
	public static Date getBeginDayOfSeason() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1; // Calendar的月从0开始
		cal.set(Calendar.DAY_OF_MONTH, 1);
		if(month <= 3) {
			//春季
			month = 0;
		} else if(month > 3 && month <= 6){
			//夏季
			month = 3;
		} else if(month > 6 && month <= 9) {
			//秋季
			month = 6;
		} else {
			month = 9;
			//冬季
		}
		cal.set(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	/**
	 * 获取当年第一天
	 * @return
	 */
	public static Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 0);
		return cal.getTime();
	}
	
	/** 
     * 获取当年的最后一天 
     * @param year 
     * @return 
     */  
    public static Date getEndDayOfYear(){  
        Calendar cal = Calendar.getInstance();    
        int currentYear = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.YEAR, currentYear);  
        cal.roll(Calendar.DAY_OF_YEAR, -1);  
        return cal.getTime();
    } 
    
	/**
	 * 获取两个日期间的月份列表
	 * @param start
	 * @param end end必须大于等于start
	 * 
	 * @return
	 */
	public static Set<Integer> getBetweenMonths(Calendar startCal, Calendar endCal) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		//把时分秒等设置为空.
		String startDate = DateUtil.convertDateToStr(startCal.getTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		String endDate = DateUtil.convertDateToStr(endCal.getTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		start.setTime(DateUtil.convertStrToDate(startDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT));
		end.setTime(DateUtil.convertStrToDate(endDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT));
		start.set(Calendar.DAY_OF_MONTH, 1);
		end.set(Calendar.DAY_OF_MONTH, 1);
		
		
		Set<Integer> months = new HashSet<Integer>();
		while(start.compareTo(end) <=0 ) {
			months.add(start.get(Calendar.MONTH));
			start.add(Calendar.MONTH, 1);
		}
		return months;
	}
	
	public static void  main(String[] args){
		System.out.println(getBeginDateOfWeek());
		System.out.println(getEndDateOfWeek());
		System.out.println(getBeginDayOfMonth());
		System.out.println(getEndDayOfMonth());
		System.out.println(getBeginDayOfYear());
		System.out.println(getEndDayOfYear());
	
		String s = "[[\"2017-05-01\",\"2017-06-15\"]]";
		String[] a = s.replaceAll("\"", "").replace('[', ' ').replace(']', ' ').trim().split(",");
		System.out.println(a);
	}
}
