package com.oristartech.config.converter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.CustomConverter;

import com.oristartech.rule.common.util.DateUtil;

public class DateDozerConverter implements CustomConverter{

	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,Class<?> sourceClass) {
		if (sourceFieldValue == null) {
			return null;
		}
		Object dest = null;
		if (sourceClass.equals(Date.class)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			dest = formatter.format(sourceFieldValue);
	        return dest;
		} else if (sourceClass.equals(String.class)) {
			return DateUtil.convertStrToDate((String)sourceFieldValue, "yyyy-MM-dd hh:mm:ss");
		}else if (sourceClass.equals(Timestamp.class)){
			return DateUtil.convertStrToDate(sourceFieldValue.toString(), "yyyy-MM-dd hh:mm:ss");
		}
		return null;
	}

}
