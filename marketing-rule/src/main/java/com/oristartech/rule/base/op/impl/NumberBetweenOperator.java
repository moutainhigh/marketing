package com.oristartech.rule.base.op.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;


/**
 * 数字区间运算符
 * @author chenjunfei
 *
 */
public class NumberBetweenOperator {

	public static boolean calc(CustomOpParameter opParam) {
		Object currentValue = opParam.getFieldValue();
		String parameter = opParam.getConditionParams();
		
		if(currentValue != null && !BlankUtil.isBlank(parameter) && !BlankUtil.isBlank(currentValue.toString()) ) {
			BigDecimal value = new BigDecimal(currentValue.toString());
			List range = JsonUtil.jsonArrayToList(parameter, List.class);
			if(!BlankUtil.isBlank(range)) {
				if(!(range.get(0) instanceof List)) {
					return compareOneRange(value, range);
				}
				for(int i=0; i < range.size(); i++) {
					List childList = (List)range.get(i);
					boolean result = compareOneRange(value, childList);
					if(result) {
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
	private static boolean compareOneRange(BigDecimal value, List rangeList) {
		if(!BlankUtil.isBlank(rangeList) && rangeList.size() == 2) {
			Object start = rangeList.get(0);
			Object end = rangeList.get(1);
			if(start != null && end != null) {
				BigDecimal bdStart = new BigDecimal(start.toString());
				BigDecimal bdEnd = new BigDecimal(end.toString());
				if(value.compareTo(bdStart) >=0 && value.compareTo(bdEnd) <=0 ) {
					return true;
				}
			}
		}
		return false;
	}

}
