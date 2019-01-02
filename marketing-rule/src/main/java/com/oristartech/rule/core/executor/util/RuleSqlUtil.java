package com.oristartech.rule.core.executor.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.RuleConstans;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;

/**
 * 规则生成sql工具类
 * @author chenjunfei
 *
 */
public class RuleSqlUtil {
	private static final String PLACEHOLDER_PREFIX = "$";
	
	public static String getSqlName(RuleConditionElementVO conEle) {
		if(conEle.getModelField() != null && !BlankUtil.isBlank(conEle.getModelField().getSqlName())) {
			return conEle.getModelField().getSqlName();
		} else {
			return conEle.getModelFieldName();
		}
	}
	/**
	 * 获取类似in比较符号操作数
	 * @param value
	 * @return
	 */
	public static String getMultiOperand(String value) {
		if(!BlankUtil.isBlank(value)) {
			List list = convertMultiOperands(value);
			return createMultiOperand(list);
		}
		return "";
	}
	
	/**
	 * replace比较符号中的占位符号
	 * 符号$开头, 后面跟0开始的数字
	 * @param opCode
	 * @param opNum
	 * @param value
	 * @return
	 */
	public static String replacePlaceholderInOp(String opCode, String opNumStr, String value) {
		Integer opNum = Integer.valueOf(opNumStr);
		String result = opCode;
		if(opNum > 0 && !BlankUtil.isBlank(value)) {
			List list = convertMultiOperands(value);
			for(int i=0; i < opNum ; i++) {
				result = result.replace(getPlaceholder(i), getQuoteOperand(String.valueOf(list.get(i))));
			}
		} else if( opNum == -1) {
			result = result.replace(getPlaceholder(0), getMultiOperand(value));
		} else if(opNum < -1) {
			List<String> resultList = new ArrayList<String>();
			opNum = Math.abs(opNum);
			//list中对象是list
			List<List> lists = JsonUtil.jsonArrayToList(value, List.class);;
			if(!BlankUtil.isBlank(lists)) {
				for(List list :lists) {
					result = opCode;
					for(int i=0; i < opNum ; i++) {
						result = result.replace(getPlaceholder(i), getQuoteOperand(String.valueOf(list.get(i))));
					}
					resultList.add(result);
				}
				result = "( " + StringUtils.join(resultList, " AND ") + ")";
			}
		}
		
		return result;
	}
	
	private static String getQuoteOperand (String value) {
		return "'" + value + "'";
	}
	
	private static List convertMultiOperands(String value) {
		if(!BlankUtil.isBlank(value)) {
			if(value.trim().startsWith("[")) {
				return JsonUtil.jsonArrayToList(value, Object.class);
			} else {
				String[] values = value.split(RuleConstans.RULE_PARAMETER_SPLITER);
				return Arrays.asList(values);
			}
		}
		return new ArrayList<Object>();
	}
	
	private static String getPlaceholder(int count) {
		return PLACEHOLDER_PREFIX + count;
	}
	
	private static String createMultiOperand(List<Object> list) {
		StringBuilder result = new StringBuilder();
		if(!BlankUtil.isBlank(list)) {
			for(int i=0; i < list.size(); i++ ) {
				Object obj = list.get(i);
				result.append("'");
				result.append(String.valueOf(obj));
				result.append("'");
				if(i < list.size() - 1) {
					result.append(",");
				}
			}
		}
		return result.toString();
	}
}
