package com.oristartech.rule.base.op.impl;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.RuleConstans;

/**
 * 输入是逗号分隔的字符串，参数也是逗号分隔的字符串，若输入中的某个值出现在参数中，则返回true
 * 即输入和参数有交集
 * @author chenjunfei
 *
 */
public class StringSplitContainOneOperator {
	
	/**
	 * @param currentValue 当前值
	 * @param parameter 规则中保存的参数
	 * @return
	 */
	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
		String parameter = opParam.getConditionParams();
	
		if(!BlankUtil.isBlank(currentValue) && !BlankUtil.isBlank(parameter)) {
			String[] curValues = currentValue.toString().split(RuleConstans.RULE_PARAMETER_SPLITER);
			String[] paramValues = parameter.split(RuleConstans.RULE_PARAMETER_SPLITER);
			for(int i=0; i < curValues.length; i++) {
				for(int j=0; j < paramValues.length; j++) {
					if(curValues[i].equals(paramValues[j])) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
