package com.oristartech.rule.base.op.string.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.RuleConstans;

/**
 * 属性值中的字符串出现在某个操作数中, 操作数是用逗号隔开的多个字符串, 多个字符串一个一个测试, 只要有一个包含属性值, 
 * 返回true
 * @author chenjunfei
 *
 */
public class FieldStringIndexOfOperator {
	public static boolean calc(CustomOpParameter opParam) {
		Object fieldValue = opParam.getFieldValue();
		
		if(fieldValue == null ) {
			return false;
		}
		String curFieldVal = fieldValue.toString();
		String parameter = opParam.getConditionParams();
		if(!BlankUtil.isBlank(parameter) && !BlankUtil.isBlank(curFieldVal)) {
			String[] paramStrs = parameter.split(RuleConstans.RULE_PARAMETER_SPLITER);
			for(String param : paramStrs) {
				if(param.indexOf(curFieldVal) >=0 ) {
					return true;
				}
			}
		}
		
		return false;
	}
}
