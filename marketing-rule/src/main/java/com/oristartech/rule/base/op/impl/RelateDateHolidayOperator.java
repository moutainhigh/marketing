package com.oristartech.rule.base.op.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BeanUtils;

/**
 * 指定日期的属性相关属性是否节假日，若日期属性名是name，则会判断名称name_holiday 是否为true, 
 * @author chenjunfei
 *
 */
public class RelateDateHolidayOperator {
	public static boolean calc(CustomOpParameter opParam) {
		Object fact = opParam.getFact();
		String fieldName = opParam.getModelFieldName();
	
		String relateFieldName = fieldName + "_holiday";
		Object value = BeanUtils.getProperty(fact, relateFieldName);
		if(value == null) {
			return false;
		}
		if(value instanceof Boolean) {
			return (Boolean) value;
		}
		if(value instanceof String) {
			return Boolean.valueOf((String)value);
		}
		return false;
	}

}
