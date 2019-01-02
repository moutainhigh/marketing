package com.oristartech.rule.functions.op.util;

import java.math.BigDecimal;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.DataTypeConvertUtil;

/**
 * 自定义数值比较
 * @author chenjunfei
 *
 */
public class NumberCompareUtil {
	public static boolean compare(CustomOpParameter opParam, NumberCompareOpEnum op) {
		Object currentValue = opParam.getFieldValue();
		
		if(OpUtil.isCurFieldIgnore(opParam)) {
			return true;
		}
		if(currentValue == null) {
			return false;
		}
		String parameter = opParam.getConditionParams();
		BigDecimal curNum = DataTypeConvertUtil.toBigDecimal(currentValue);
		BigDecimal paramNum = DataTypeConvertUtil.toBigDecimal(parameter);
		int result = curNum.compareTo(paramNum);
		switch(op) {
		case EQUAL : return result == 0; 
		case GREATER : return result > 0;
		case GREATER_EQUAL : return result >= 0;
		case LESS : return result < 0;
		case LESS_EQUAL : return result <= 0;
		}
		
		return false;
	}
}
