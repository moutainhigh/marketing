package com.oristartech.rule.common.util;

import java.math.BigDecimal;


public class DataTypeConvertUtil {
	public static BigDecimal toBigDecimal(Object obj) {
		if(obj == null) {
			return null;
		}
		
		if(obj instanceof BigDecimal) {
			return (BigDecimal)obj;
		} else if(obj instanceof String) {
			String str = (String) obj;
			if(BlankUtil.isBlank(str)) {
				return null;
			}
			return new BigDecimal((String) obj);
		} else {
			return new BigDecimal(String.valueOf(obj));
		}
	}
	
	public static String toString(Object obj) {
		if(obj == null) {
			return null;
		}
		return obj.toString();
	}
	
	public static Integer toInteger(Object obj) {
		if(obj == null) {
			return null;
		}
		
		if(obj instanceof Integer) {
			return (Integer)obj;
		} else if(obj instanceof String) {
			String str = (String) obj;
			if(BlankUtil.isBlank(str)) {
				return null;
			}
			return new Integer((String) obj);
		} else {
			return new Integer(String.valueOf(obj));
		}
	}
}
