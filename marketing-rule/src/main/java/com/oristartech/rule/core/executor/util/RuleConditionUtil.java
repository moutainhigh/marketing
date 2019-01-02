package com.oristartech.rule.core.executor.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.RuleConstans;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;
import com.oristartech.rule.vos.core.vo.RuleSectionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 生成条件工具类
 * @author chenjunfei
 *
 */
public class RuleConditionUtil {
	/**
	 * 获取真正的需要比较的属性，有些属性只是别的属性的别名。又ModelFieldVO中的aliasForField指定真正的名称
	 * @param conEle
	 * @return
	 */
	public static String getRealFieldName(RuleConditionElementVO conEle) {
		if(!BlankUtil.isBlank(conEle.getModelField().getAliasForField())) {
			return conEle.getModelField().getAliasForField();
		}
		return conEle.getModelField().getName();
	}
	
	/**
	 * 获取条件分类名称
	 * @param conEle
	 * @return
	 */
	public static String getModelCategoryName(RuleConditionElementVO conEle) {
		if(conEle.getModelField() != null) {
			return conEle.getModelField().getModelCategoryName();
		}
		return null;
	}
	
	/**
	 * 获取单操作数
	 * @param conEle
	 * @return
	 */
	public static String getSingleOperandFromCon(RuleConditionElementVO conEle) {
		if(conEle != null ) {
			String value = conEle.getOperand();
			if(conEle.getOpNum() == 0 && BlankUtil.isBlank(value)) {
				value = conEle.getOpDefaultOperand();
			}
			
			if( BlankUtil.isBlank(value)) {
				return "";
			}
			String type = (conEle.getModelField() != null && conEle.getModelField().getType()!= null) ? conEle.getModelField().getType() : "String";
			return getSingleOperand(value, type);
		}
		return "";
	}
	
	public static boolean isEmptyCondition(RuleVO ruleVO) {
		if(ruleVO == null) {
			return true;
		}
		boolean result = true;
		if(ruleVO.getCommonSection() != null) {
			List<RuleConditionVO> cons = ruleVO.getCommonSection().getRuleConditions();
			if(!BlankUtil.isBlank(cons)) {
				return false;
			}
		}
		if(!BlankUtil.isBlank(ruleVO.getRuleSections())) {
			List<RuleSectionVO> sections = ruleVO.getRuleSections();
			for(RuleSectionVO secvo : sections) {
				List<RuleConditionVO> cons = secvo.getRuleConditions();
				if(!BlankUtil.isBlank(cons)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 获取类似in比较符号操作数
	 * @param value
	 * @return
	 */
	public static String getMultiOperand(RuleConditionElementVO conEle) {
		if(conEle != null && !BlankUtil.isBlank(conEle.getOperand())) {
			String value = conEle.getOperand();
			List list = convertMultiOperands(value);
			StringBuilder result = new StringBuilder();
			if(!BlankUtil.isBlank(list)) {
				for(int i=0; i < list.size(); i++ ) {
					Object obj = list.get(i);
					String type = (conEle.getModelField() != null && conEle.getModelField().getType()!= null) 
					                     ? conEle.getModelField().getType() : "String";
					String operand = getSingleOperand(String.valueOf(obj), type);
					result.append(operand);
					if(i < list.size() - 1) {
						result.append(",");
					}
				}
			}
			return result.toString();
		}
		return "";
	}
	
	private static String getQuoteString(String value) {
		if(!BlankUtil.isBlank(value)) {
			return "\"" + StringEscapeUtils.escapeJava(value) + "\"";
		}
		return "";
	}
	
	public static String getSingleOperand(String value, String type) {
		if(!BlankUtil.isBlank(value) && !BlankUtil.isBlank(type) ) {
			if(type.equals("java.lang.Long") || type.equals("java.lang.Integer") || type.equals("java.lang.Boolean")) {
				return value;
			} else if(type.equals("java.math.BigDecimal")) {
				return value + "B";
			} else if(type.equals("java.math.BigInteger")) {
				return value + "I";
			} else {
				return getQuoteString(value);
			}
		}
		return "";
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
}
