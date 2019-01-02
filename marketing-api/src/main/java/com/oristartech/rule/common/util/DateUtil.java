/**
 * Copyright (c) 2008,中企动力华南研发中心
 * All rights reserved.
 */
package com.oristartech.rule.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;



/**
 * 逻辑、功能相关描述:日期工具类，提供有关日期操作方面的方法
 * @version 1.0
 * @author 陈树旭  编写日期：2008-01-21
 * @author <a href="mailto:zhangxianxin331@hotmail.com">张宪新</a> 修改日期：2010-07-23
 */
public class DateUtil {

    /**
	 * 长日期型
	 */
	public static final int LONG = DateFormat.LONG;
	/**
	 * 中日期型
	 */
	public static final int MEDIUM = DateFormat.MEDIUM;
	/**
	 * 短日期型
	 */
	public static final int SHORT = DateFormat.SHORT;
	/**
     * 一天的毫秒数
     */
    public final static long DAY_MILLISECOND = 24 * 60 * 60 * 1000;
    /**
     * 时间格式: HH:mm:ss:SSS
     */
    public final static String TIME_FORMAT = "HH:mm:ss:SSS" ;
    /**
     * 缺省短时间格式: HH:mm:ss
     */
    public final static String DEFAULT_SHORT_TIME_FORMAT = "HH:mm:ss" ;
    /**
     * 缺省短日期格式: yyyy-MM-dd 
     */
    public final static String DEFAULT_SHORT_DATE_FORMAT = "yyyy-MM-dd" ;
    /**
     * 缺省长日期格式: yyyy-MM-dd HH:mm:ss:SSS
     */
    public final static String DEFAULT_LONG_DATE_FORMAT = DEFAULT_SHORT_DATE_FORMAT.concat(" ").concat(TIME_FORMAT);
    /**
     * 缺省长日期格式: yyyy-MM-dd HH:mm:ss
     */
    public final static String DEFAULT_DATE_FORMAT = DEFAULT_SHORT_DATE_FORMAT.concat(" ").concat(DEFAULT_SHORT_TIME_FORMAT);
    /**
     * 默认的日期格式化对象，pattern：yyyy-MM-dd
     */
    public static final SimpleDateFormat formatInstance_DefaultDate = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT);
    /**
     * 默认的日期时间格式化对象，pattern： yyyy-MM-dd HH:mm:ss:SSS
     */
    public static final SimpleDateFormat formatInstance_DefaultDateTime = new SimpleDateFormat(DEFAULT_LONG_DATE_FORMAT);
    /**
     * 默认的日期时间格式化对象，pattern： yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat formatInstance_DefaultShortDateTime = new SimpleDateFormat(DEFAULT_LONG_DATE_FORMAT);
    /**
     * 默认的日期时间格式化对象
     * @deprecated
     * @see #formatInstance_DefaultDateTime
     */
    @Deprecated
	public static final SimpleDateFormat DEFAULT_DATE_TIME_FORMAT = formatInstance_DefaultDateTime;
    
    
    //----------------------------added by wuqiaoyun on 20060214-------------------------------//begin
    public static final String DATE_FORMAT="dd MMM yyyy";
    @Deprecated
    public static final String DATETIME_FORMAT = DEFAULT_SHORT_TIME_FORMAT;
    public static final String DATE_TIME_FORMAT="dd MMM yyyy HH:mm:ss";
    /**
     * 在mainmenu页面显示的当前日期的模板格式
     */
    public static final String MAINMENU_DATE_FORMAT="EEE, dd MMM yyyy";
    /**
     * 在mainmenu页面显示的当前日期的模板，pattern： EEE, dd MMM yyyy，locale：Locale.ENGLISH
     */
    public static final SimpleDateFormat MM_DATE_FORMAT = new SimpleDateFormat(MAINMENU_DATE_FORMAT,Locale.ENGLISH);
    /**
     * pattern：dd MMM yyyy，locale：Locale.ENGLISH
     */
    public static final SimpleDateFormat DIS_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT,Locale.ENGLISH);
    /**
     * pattern：dd MMM yyyy HH:mm:ss，locale：Locale.ENGLISH
     */
    public static final SimpleDateFormat DIS_DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT,Locale.ENGLISH);
    //----------------------------added by wuqiaoyun on 20060214-------------------------------//end
    
    
    
	/**
	 * 比较两个日期，忽略时间，如果date1 > date 2，返回1，date1 = date2，返回0，date1 < date2，返回-1
	 * @param date1
	 * @param date2
	 * @return
	 * @since SAAS-RES version(1.0.0)
	 */
	public static int compareDateIgnoreTime(Date date1, Date date2) {
		date1 = ignoreTime(date1);
		date2 = ignoreTime(date2);
		if (date1.after(date2))
			return 1;
		if (date1.before(date2))
			return -1;
		return 0;
	}
	
	/**
	 * 比较两个时间，忽略秒，如果date1 > date 2，返回1，date1 = date2，返回0，date1 < date2，返回-1
	 * @param date1
	 * @param date2
	 * @return
	 * @since SAAS-RES version(1.0.0)
	 */
	public static int compareDateTimeIgnoreSecond(Date date1, Date date2) {
		date1 = ignoreSecond(date1);
		date2 = ignoreSecond(date2);
		if (date1.after(date2))
			return 1;
		if (date1.before(date2))
			return -1;
		return 0;
	}

	/**
	 * 比较两个时间，忽略毫秒，如果date1 > date 2，返回1，date1 = date2，返回0，date1 < date2，返回-1
	 * @param date1
	 * @param date2
	 * @return
	 * @since SAAS-RES version(1.0.0)
	 */
	public static int compareDateTimeIgnoreMillisecond(Date date1, Date date2) {
		date1 = ignoreMillisecond(date1);
		date2 = ignoreMillisecond(date2);
		if (date1.after(date2))
			return 1;
		if (date1.before(date2))
			return -1;
		return 0;
	}

	/**
	 * 忽略时间，把时、分、秒、毫秒都变为0
	 * @param date
	 * @return
	 * @since SAAS-RES version(1.0.0)
	 */
	public static Date ignoreTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 方法用途和描述: 忽略秒，秒、毫秒都变为0
	 * @param date
	 * @return
	 * @author 张宪新 新增日期：2009-7-30
	 * @since SAAS-RES version(1.0.0)
	 */
	public static Date ignoreSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 方法用途和描述: 忽略毫秒，毫秒变为0
	 * @param date
	 * @return
	 * @author 张宪新 新增日期：2009-7-30
	 * @since SAAS-RES version(1.0.0)
	 */
	public static Date ignoreMillisecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 将日期对象格式化成yyyy-MM-dd的形式
	 * @param dt 日期对象
	 * @return String
	 * @since SAAS-RES version(1.0.0)
	 */
	public static String formatDate(Date dt) {
		if (dt == null)
			return "";
		return formatInstance_DefaultDate.format(dt);
	}

	/**
	* 方法用途和描述: 获取本周的开始时间
	* @param weekFirstDateForm 一周的第一天由星期几开始，如果为空则取默认的星期一开始
	* @return
	* @author 张宪新 新增日期：2010-1-3
	* @since SAAS-RES version(1.0.0)
	 */
	public static Date getCurrentWeekBeginDate(Integer weekFirstDateForm){
    	Calendar calendar = Calendar.getInstance();
    	boolean isSunday = isSunday(calendar);
    	if(weekFirstDateForm==null || weekFirstDateForm<Calendar.SUNDAY 
    			|| weekFirstDateForm>Calendar.SATURDAY)
    		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//默认设置从周一开始
    	else
    		calendar.set(Calendar.DAY_OF_WEEK, weekFirstDateForm);
		calendar.setTime(ignoreTime(calendar.getTime()));
		if(isSunday)
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-Calendar.DAY_OF_WEEK);
		return calendar.getTime();
	}

	/**
	* 方法用途和描述: 获取本周的结束时间
	* @param weekFirstDateForm 一周的第一天由星期几开始，如果为空则取默认的星期一开始 
	* @return
	* @author 张宪新 新增日期：2010-1-3
	* @since SAAS-RES version(1.0.0)
	 */
	public static Date getCurrentWeekEndDate(Integer weekFirstDateForm){
		Calendar calendar = Calendar.getInstance();
		boolean isSunday = isSunday(calendar);
		if(weekFirstDateForm==null || weekFirstDateForm<Calendar.SUNDAY 
    			|| weekFirstDateForm>Calendar.SATURDAY)
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置从周一开始
		else
			calendar.set(Calendar.DAY_OF_WEEK, weekFirstDateForm);
		calendar.setTime(ignoreTime(calendar.getTime()));
		if(!isSunday)
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
	
	/**
	* 方法用途和描述: 是否是星期天
	* @param date
	* @return
	* @author 张宪新 新增日期：2010-7-13
	*/
	public static boolean isSunday(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY;
	}
	
	/**
	 * 方法用途和描述: 是否是星期天
	 * @param calendar
	 * @return
	 * @author 张宪新 新增日期：2010-1-3
	 * @since SAAS-RES version(1.0.0)
	 */
	public static boolean isSunday(Calendar calendar){
		return calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY;
	}
    
    /**
	 * 方法用途和描述: 取得当前时间的字符串 例如：20080221050416
	 * @return
	 * @author 马达（mada@myce.net.cn） 新增日期：2008-2-21
	 * @author <a href="mailto:zhangxianxin331@hotmail.com">张宪新</a> 修改日期：Mar 7, 2008
	 * @since 运营运维管理系统 version(1.1)
	 */
	public static String getCurrentTimeStr(){
		String str = formatDateTime(new Date());
		str = str.trim();
		str = str.replaceAll("-", "");
		str = str.replaceAll(":", "");
		str = str.replaceAll(" ", "");
		return str;
	}
    
    
	/**
     * Function:把字符串转换为数据库类型Timestamp。
     * @param dateStr 日期字符串，只支持"yyyy-MM-dd"和"yyyy-MM-dd HH:mm:ss:SS"两种格式。
     * 				  如果为"yyyy-MM-dd"，系统会自动取得当前时间补上。
     * @return 转换为数据库类型的Timestamp
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Timestamp convertStrToTimestamp(String dateStr) {
        if (dateStr == null) {
            return null;
        }

        String dStr = new String(dateStr).trim();
        if (dStr.indexOf(" ") == -1){
            dStr = dStr + " " + getCurrDateStr(TIME_FORMAT) ;
        }

        Date utilDate = null;
        try {
            utilDate = formatInstance_DefaultDateTime.parse(dStr);
        }
        catch (Exception ex) {
            throw new RuntimeException("DateUtil.convertStrToTimestamp(): " +ex.getMessage()) ;
        }

        return new Timestamp(utilDate.getTime());
    }

	/**
     * Function:得到系统当前时间的Timestamp对象
     * @return  系统当前时间的Timestamp对象
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Timestamp getCurrTimestamp(){
        return new Timestamp(System.currentTimeMillis()) ;
    }

	/**
     * Function:把Date对象转化为Timestamp对象，用来比较时间
     * 			Timestamp的getTime方法 不返回毫秒,而oracle数据库也是不保存毫秒的
     * 			该方法避免用Date比较两个日期的精度问题
     * @param date 要转化的data对象
     * @return 转换为数据库类型的Timestamp
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Timestamp convertDate2Timestamp(Date date) {
    	if (date != null) {
    		long dateLong = date.getTime();
        	String dateStr = "" + dateLong;
        	//去除毫秒
        	dateStr = dateStr.substring(0, dateStr.length() - 3) + "000";
        	Long timeLong = new Long(dateStr);
        	return  new Timestamp(timeLong);
    	}else {
    		return null;
    	}

    }

	/**
     * Function:<p>
     * 			取得当前日期，并将其转换成格式为"dateFormat"的字符串
     * 			例子：假如当前日期是 2003-09-24 9:19:10，则：
     * 				<pre>
     * 					getCurrDateStr("yyyyMMdd")="20030924"
     * 					getCurrDateStr("yyyy-MM-dd")="2003-09-24"
     * 					getCurrDateStr("yyyy-MM-dd HH:mm:ss")="2003-09-24 09:19:10"
     * 				</pre>
     * 			</p>
     * @param dateFormat String 日期格式字符串
     * @return String 转化为日期的字符串表示形式
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static String getCurrDateStr(String dateFormat) {
        return convertDateToStr(new Date(),dateFormat);
    }

	/**
     * Function:将日期类型转换成指定格式的日期字符串
     * @param date 待转换的日期
     * @param dateFormat 日期格式字符串
     * @return String 转化为日期的字符串表示形式
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static String convertDateToStr(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }


	/**
     * Function:将指定格式的字符串转换成日期类型
     * @param date 待转换的日期字符串
     * @param dateFormat 日期格式字符串
     * @return String 转化的日期对象
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date convertStrToDate(String dateStr, String dateFormat) {
        if (dateStr==null || dateStr.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(dateStr);
        }
        catch (Exception e) {
            throw new RuntimeException("DateUtil.convertStrToDate():"+e.getMessage());
        }
    }

	/**
     * Function:计算两个日期之间的相隔的年、月、日。注意：只有计算相隔天数是准确的，相隔年和月都是
     * 			近似值，按一年365天，一月30天计算，忽略闰年和闰月的差别。
     * @param datepart 两位的格式字符串，yy表示年，MM表示月，dd表示日
     * @param startdate 开始日期
     * @param enddate 结束日期
     * @return 如果enddate>startdate，返回一个大于0的实数，否则返回一个小于等于0的实数
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static double dateDiff(String datepart, Date startdate, Date enddate) {
        if (datepart==null || datepart.equals("")){
            throw new IllegalArgumentException("DateUtil.dateDiff()方法非法参数值："+datepart) ;
        }

        double distance = (enddate.getTime() - startdate.getTime()) /
                		  (60 * 60 * 24 * 1000);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(enddate.getTime() - startdate.getTime());
        if (datepart.equals("yy")) {
            distance = distance / 365 ;
        }
        else if (datepart.equals("MM")) {
            distance = distance / 30 ;
        }
        else if (datepart.equals("dd")) {
            distance = (enddate.getTime() - startdate.getTime()) /
                (60 * 60 * 1000 * 24);
        }
        else if (datepart.equals("hh")) {
        	distance = (enddate.getTime() - startdate.getTime()) /
        		(60 * 60 * 1000);
        }else{
            throw new IllegalArgumentException("DateUtil.dateDiff()方法非法参数值："+datepart) ;
        }
        return distance;
    }

	/**
     * Function:计算两个日期之间的相隔的月。
     * @param startdate 开始日期
     * @param enddate 结束日期
     * @return 如果enddate>startdate，返回一个大于0的整数，否则返回0
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static int getMonthDiff(Date startdate, Date enddate){
		int k=0;
		GregorianCalendar temp = new GregorianCalendar();

		temp.setTime(startdate);
		temp.set(GregorianCalendar.MILLISECOND,0);
		temp.add(GregorianCalendar.DAY_OF_MONTH,1);

		int day=temp.getActualMaximum(GregorianCalendar.DATE);

		GregorianCalendar endCal=new GregorianCalendar();
		endCal.setTime(enddate);
		endCal.set(GregorianCalendar.MILLISECOND,0);
		endCal.add(GregorianCalendar.DAY_OF_MONTH,1);

		while(temp.getTime().before(endCal.getTime())){
			k++;
			day=temp.getActualMaximum(GregorianCalendar.DATE);
			temp.add(GregorianCalendar.DAY_OF_MONTH,day);

		}

		return k;
	}

	/**
     * Function:把日期对象加减年、月、日后得到新的日期对象
     * @param depart 年、月、日
     * @param number 加减因子
     * @param date 需要加减年、月、日的日期对象
     * @return 新的日期对象
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date addDate(String datepart, int number, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (datepart.equals("yy")) {
            cal.add(Calendar.YEAR, number);
        }
        else if (datepart.equals("MM")) {
            cal.add(Calendar.MONTH, number);
        }
        else if (datepart.equals("dd")) {
            cal.add(Calendar.DATE, number);
        }else{
            throw new IllegalArgumentException("DateUtil.addDate()方法非法参数值："+datepart) ;
        }

        return cal.getTime();
    }
	/**
     * Function:按照给定的格式style将指定的日期值转换成字符串。
	 * @param date: 待转换的日期
	 * @param style: 指定转化类型,style参数取静态常量LONG、MEDIUM和SHORT的值
	 * @param loc：字符定义对象
	 * @return 格式化后的日期字符串
	 * @throws IllegalArgumentException: style模板不符合格式时报异常
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static String formatDate(Date date,int style,Locale loc){
    	if (style<1 || style>3) {
    		throw new IllegalArgumentException("parameter is invalid.");
//    		IllegalArgumentOfException ie = new IllegalArgumentOfException();
//			ie.setMessageCode("resource_DateFormatUtil_002");
//			ie.setExtendMessage(String.valueOf(style));
//    		throw ie;
    	}
    	String newDate = "";
        if (loc==null){
        	loc = Locale.getDefault();
        }
        if (date != null) {
            DateFormat df = DateFormat.getDateInstance(style,loc);
            newDate = df.format(date);
        }
        return newDate;
    }

	/**
     * Function:按照给定的格式模板将指定的日期值转换成字符串。
     * @param date: 待转换的日期
     * @param pattern: 指定转化格式字符串,例如：yyyy-MM-dd
     * @param loc: 字符定义对象
     * @return 格式化后的日期字符串
     * @throws IllegalArgumentException: pattern模板不符合格式时报异常
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static String formatDate(Date date,String pattern,Locale loc){
    	if (pattern==null) {
    		throw new IllegalArgumentException("parameter is invalid.");
//    		IllegalArgumentOfException ie = new IllegalArgumentOfException();
//			ie.setMessageCode("resource_DateFormatUtil_002");
//			ie.setExtendMessage(pattern);
//    		throw ie;
    	}
    	String newDate = "";
        if (loc==null){
        	loc = Locale.getDefault();
        }
    	if (date != null) {
	     SimpleDateFormat sdf = new SimpleDateFormat(pattern,loc);
		 newDate = sdf.format(date);
        }
        return newDate;
    }

	/**
     * Function:按照不同的日期格式和时间格式，将指定的日期时间值转换成字符串。
     * @param date: 待转换的日期
     * @param dateStyle: 指定的日期类型,style参数取静态常量LONG、MEDIUM和SHORT的值
     * @param timeStyle：指定的时间类型,style参数取静态常量LONG、MEDIUM和SHORT的值
     * @param loc：字符定义对象
     * @return 格式化后的日期时间字符串
     * @throws IllegalArgumentOfException: 日期时间模板违反规定时
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static String formatDateTime(Date date,int dateStyle,int timeStyle,Locale loc){
    	if (dateStyle<1 || dateStyle>3) {
    		throw new IllegalArgumentException("parameter is invalid.");
//    		IllegalArgumentOfException ie = new IllegalArgumentOfException();
//			ie.setMessageCode("resource_DateFormatUtil_002");
//			ie.setExtendMessage(String.valueOf(dateStyle));
//    		throw ie;
    	}
    	if (timeStyle<1 || timeStyle>3) {
    		throw new IllegalArgumentException("parameter is invalid.");
//    		IllegalArgumentOfException ie = new IllegalArgumentOfException();
//			ie.setMessageCode("resource_DateFormatUtil_003");
//			ie.setExtendMessage(String.valueOf(timeStyle));
//    		throw ie;
    	}
    	String newDate = "";
    	if (loc==null){
         	loc = Locale.getDefault();
        }
    	if (date != null) {
 		     DateFormat df = DateFormat.getDateTimeInstance(dateStyle,timeStyle,loc);
 			 newDate = df.format(date);
        }
        return newDate;
    }

	/**
     * Function:按照不同的格式模板将指定的日期时间值转换成字符串。
     * @param date: 待转换的日期
     * @param dateStr: 指定日期转化格式字符串模板,例如:yyyy-MM-dd
     * @param timeStr: 指定时间转化格式字符串模板,例如:hh:mm:ss
     * @param loc：字符定义对象
     * @return 格式化后的日期时间字符串
     * @throws IllegalArgumentException: pattern模板不符合格式时报异常
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static String formatDateTime(Date date,String dateStr,String timeStr, Locale loc){
    	if (dateStr==null || timeStr==null) {
    		throw new IllegalArgumentException("parameter is invalid.");
    	}

    	String newDate = "";
    	if (loc==null) {
    	   loc = Locale.getDefault();
    	}
    	
    	if (date != null) {
    		String pattern = (dateStr==null?"":dateStr) +" "+ (timeStr==null?"":timeStr);
    		SimpleDateFormat sdf = new SimpleDateFormat(pattern,loc);
			newDate = sdf.format(date);
        }
		return newDate;
    }

    /**
     * Function:把日期时间格式化为yyyy-MM-dd HH:mm:ss格式
     * @param dt java.util.Date
     * @return 格式化后的日期时间字符串
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static String formatDateTime(Date dt) {
        String newDate = "";
        if (dt != null) {
            SimpleDateFormat dateStyle = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT.concat(" HH:mm:ss"), Locale.CHINESE);
            newDate = dateStyle.format(dt);
        }
        return newDate;
    }

    /**
     * Function:将指定的字符串转换成日期
     * @param dateStr:  待转换的日期符串,以yyyy-MM-dd模板进行转换
     * @return 返回标准的日期格式yyyy-MM-dd,与字符串dateStr对应的date对象
     * @throws ParseStringException
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date parseStrToDate(String dateStr) throws ParseException{
    	try {
          return formatInstance_DefaultDate.parse(dateStr);
    	}catch(ParseException e){
    		throw e;
    	}
    }

    /**
     * Function:按照不同的格式模板将指定的字符串转换成日期。
     * @param date: 待转换的日期符串
     * @param pattern: 字符串的格式模板,例如:yyyy-MM-dd hh:mm:ss
     * @return 与字符串dateStr对应的date对象
     * @throws ParseStringException
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date parseStrToDate(String date, String pattern) throws ParseException{
	    try {
    	   SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	       return sdf.parse(date);
	    }catch(ParseException e){
	    	throw e;
//	    	ParseStringException pse = new ParseStringException(e.getMessage(),e.getErrorOffset());
//	    	pse.setMessageCode("resource_DateFormatUtil_001");
//	    	pse.setExtendMessage(date);
//	    	throw pse;
	    }
    }

    /**
     * Function:获得当前日期，时间位置为00:00:00
     * @return Date实例
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * Function:获得当前系统的时间戳
     * @return 从1970-1-1到现在的毫秒数
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static long  getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Function:在给定的日期点上加入指定的天数
     * @param date 给定的日期点
     * @param days 天数，正数为向后；负数为向前
     * @return 返回改变后的时间点
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date addDate(Date date, int days) {
        if (date == null)
            return date;
        Locale loc = Locale.getDefault();
        GregorianCalendar cal = new GregorianCalendar(loc);
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        cal.set(year, month, day + days);
        return cal.getTime();
    }

    /**
     * Function:将给定日期的时分秒和毫秒清零
     * @param date	给定的日期
     * @return      清零后的日期
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date clearFromHoursToMillis(Date date){
    	if(date == null)
    		return date;
        Locale loc = Locale.getDefault();
        GregorianCalendar cal = new GregorianCalendar(loc);
        cal.setTime(date);
        cal.clear(Calendar.MILLISECOND);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.HOUR_OF_DAY);

        return cal.getTime();
    }

    /**
     * Function:在当前的日期点上加入指定的天数
     * @param days 天数，正数为向后；负数为向前
     * @return 返回改变后的时间
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date addDate(int days) {
    	Locale loc = Locale.getDefault();
        GregorianCalendar cal = new GregorianCalendar(loc);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        cal.set(year, month, day + days);
        return cal.getTime();
    }

    /**
     * Function:获得两个时间点之间相差的天数
     * @param date1  开始时间点
     * @param date2  结束时间点
     * @return  具体的天数
     * Create author:陈树旭
     * Create on:2008-01-21
     * Edit author: <a href="mailto:zhangxianxin331@hotmail.com">张宪新</a>
     * Edit on: 2010-05-28
     * Why:
     */
    @Deprecated
    public static int getDays(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 0;
        
        // FIXME 
        if(date1 instanceof java.sql.Date){
        	try {
				date1 = DateUtil.parseStrToDate(formatInstance_DefaultDateTime.format(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        
        if(date2 instanceof java.sql.Date){
        	try {
        		date2 = DateUtil.parseStrToDate(formatInstance_DefaultDateTime.format(date2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        
        // FIXME 存在两个日期相差小于24小时，但两个日期又不是在同一天时出现相减后为0的情况。
        final long times = date2.getTime() - date1.getTime();
        long days = times / DAY_MILLISECOND;
        if(days != 0)
        	return (int)days;
        
        /* 
         * days等于0 表示的情况有：
         * 1、两个时间相等
         * 2、同一天内的两个时间
         * 3、相隔两天内的两个时间，但时间差小于24小时
         */
        if(times == 0)
        	return 0;
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date2);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        int year2 = calendar.get(Calendar.YEAR);
        
        calendar.setTime(date1);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar.get(Calendar.YEAR);
        
        if(times > 0)
        	if(year2 > year1)
        		days = 1;
        	else
        		days = day2 - day1;
        else
        	if(year2 < year1)
        		days = -1;
        	else
        		days = day2 - day1;
        
        return (int)days;
    }

    /**
     * 
     * 
     * 功能描述：获得两个时间点之间相差的天数
     * 
     * @param endDate 结束日期
     * @param startDate 开始日期
     * @return 具体的天数
     * @author <a href="mailto:zhoujiliang@ceopen.cn">zhoujiliang</a>
     * @version
     * @since 程序版本号
     * create on: 2010-8-17
     *
     */
    public static int getDaysBetweenTwoDates(Date endDate, Date startDate){
    	if (startDate == null || endDate == null)
            return 0;
        
        // FIXME 
        if(startDate instanceof java.sql.Date){
        	try {
				startDate = DateUtil.parseStrToDate(formatInstance_DefaultDateTime.format(startDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        
        if(endDate instanceof java.sql.Date){
        	try {
        		endDate = DateUtil.parseStrToDate(formatInstance_DefaultDateTime.format(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        
        // FIXME 存在两个日期相差小于24小时，但两个日期又不是在同一天时出现相减后为0的情况。
        final long times = endDate.getTime() - startDate.getTime();
        long days = times / DAY_MILLISECOND;
        if(days != 0)
        	return (int)days;
        
        /* 
         * days等于0 表示的情况有：
         * 1、两个时间相等
         * 2、同一天内的两个时间
         * 3、相隔两天内的两个时间，但时间差小于24小时
         */
        if(times == 0)
        	return 0;
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(endDate);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        int year2 = calendar.get(Calendar.YEAR);
        
        calendar.setTime(startDate);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar.get(Calendar.YEAR);
        
        if(times > 0)
        	if(year2 > year1)
        		days = 1;
        	else
        		days = day2 - day1;
        else
        	if(year2 < year1)
        		days = -1;
        	else
        		days = day2 - day1;
        
        return (int)days;
    }
    
    /**
     * 方法描述：获取Date类型对应的周的第几天
     * 
     * @author 黎伟健
     * @param date 日期
     * @return 数字的第几天
     */
	public static int getDateWeek (Date date) {
		if(date.equals(null) || date.equals("")){
			return -1;
		}
		Calendar calendar = new GregorianCalendar();    	
    	calendar.setTime(date);
    	int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
    	return week;
	}
	   
    
    
    
    


    //add by messi(chencao)
    /**
     * Function:把参数日期的时,分,秒清零,返回下一天
     * 			如:参数为2006-08-22 12:33 那么该方法返回 2006-08-23 00:00
     * @param day
     * @return Date 代表下一天的日期对象
     * Create author:chencao
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date getNextDay(Date day) {
    	Calendar nowC = date2Clear(day);
    	nowC.add(Calendar.DATE, 1);
    	return nowC.getTime();
    }

    /**
     * Function:把参数日期的时,分,秒清零,返回前一天
     * 如:参数为2006-08-22 12:33 那么该方法返回 2006-08-21 00:00
     * @param day
     * @return
     * Create author:chencao
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date getPreDay(Date day) {
    	Calendar nowC = date2Clear(day);
    	nowC.add(Calendar.DATE, -1);
    	return nowC.getTime();
    }

    /**
     * Function:把参数日期的时,分,秒清零,返回前一天的最后一秒
     * 如:参数为2006-08-22 12:33 那么该方法返回 2006-08-21 23:59
     * @param day
     * @return
     * Create author:chencao
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
    public static Date getLastSecPreDay(Date day) {
    	Calendar nowC = date2Clear(day);
    	nowC.set(Calendar.SECOND, -1);
    	return nowC.getTime();
    }

    /**
     * Function:对传过来的日期,把它后面的小时,分,秒设置为零.
     * @param day
     * @return
     * Create author:chencao
     * Create on:2008-01-21
     * Edit author:
     * Edit on:
     * Why:
     */
	private static Calendar date2Clear(Date day) {
		Calendar nowC = Calendar.getInstance();
    	nowC.setTime(day);
    	nowC.set(Calendar.HOUR, 0);
    	nowC.set(Calendar.HOUR_OF_DAY, 0);
    	nowC.set(Calendar.MINUTE, 0);
    	nowC.set(Calendar.SECOND, 0);
    	nowC.set(Calendar.MILLISECOND, 0);
		return nowC;
	}

    /**
     * Function:根据帐期费用表缴款截止日期,得到计算滞纳金所需要的时间参数, param.get(5)是得到param对应的Calendar
	 * 的天数.如果系统时间的具体天数,如果大于 入参的具体天数,则返回系统时间.否则将系统时间的天数置为1.
	 *
	 * @param inDate：帐期费用表中的截止日期
	 * @return 计算滞纳金的日期
     * Create author:chencao
     * Create on:2008-01-21
     * Edit author: yuqinghong
     * Edit on: 2007-3-14
     * Why:
     */
	/*uuuuu/PCD:计算滞纳金所用时间方法的修改 / PCR NO: 11/ EditBy:yuqinghong/ Date:2007-3-14*/
	public static Date getLateFeeDate(Date inDate) {
		Calendar rightNow = Calendar.getInstance();
		Calendar param = Calendar.getInstance();
		param.setTime(inDate);
		//如果系统当前时间大于输入时间，则设置返回时间为系统时间
		//相反的如果系统时间小于输入的截至缴费时间
		if(rightNow.getTime().before(param.getTime())){
			//rightNow.setTime(inDate);
			//如果系统时间的月份和输入的截至缴费时间的月份相等，就将收费日期定在1号
			if(rightNow.get(Calendar.MONTH)==param.get(Calendar.MONTH)){
				System.out.println(rightNow.get(Calendar.MONTH));
				System.out.println(param.get(Calendar.MONTH));
				rightNow.set(Calendar.DAY_OF_MONTH, 1);
			}
		}
		//否则两个时间是同一天的话也要设置开始时间为1号
		else if(rightNow.get(Calendar.DAY_OF_MONTH)==param.get(Calendar.DAY_OF_MONTH)){
			rightNow.set(Calendar.DAY_OF_MONTH, 1);
		}
		return rightNow.getTime();
	}
	/*^^^^^/PCD:计算滞纳金所用时间方法的修改 / PCR NO: 11/ EditBy:yuqinghong/ Date:2007-3-14*/
	//end by messi(chencao)
	public static Date getSystemDate(){
	   	
	   	DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);//鏍煎紡鍖栬緭鍑?
			TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//鑾峰彇鏃跺尯
			dateFormatterChina.setTimeZone(timeZoneChina);//璁剧疆绯荤粺鏃跺尯
			Date curDate = new Date();//鑾峰彇绯荤粺鏃堕棿
			String Str = dateFormatterChina.format(curDate);
			try {
			curDate = dateFormatterChina.parse(Str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return curDate;
	   }
	
	public static void main(String[] args){
		DateUtil.convertStrToDate("2018-12-19 00:00:00","yyyy-MM-dd HH:mm:ss");
	}
}