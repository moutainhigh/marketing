package com.oristartech.rule.drools.util;

public class RuleUtil {
	private final static String RULE_PARAMETER_SPLITER = ",";
	private final static String RULE_GROUP_PREFIX = "Group";
	private final static String RULE_RULE_PREFIX = "Rule";
	
	/**
	 * 获取规则在drools文件中的名称
	 * @param rule
	 * @return
	 */
	public static String getDroolRuleName(Integer groupId,Integer ruleId,String ruleType) {
		return RULE_GROUP_PREFIX + "_" + groupId + "_" + RULE_RULE_PREFIX + "_" + ruleId + "_" + ruleType;
	}
	
	/**
	 * 获取规则在drools文件中规则组前缀的名称
	 * @param groupId
	 * @return
	 */
	public static String getRuleGroupPrefix(Integer groupId) {
		return RULE_GROUP_PREFIX + "_" + groupId + "_";
	}
	
	/**
	 * 获取规则在drools文件中规则组中缀的名称
	 * @param groupId
	 * @return
	 */
	public static String getRuleMidName(Integer ruleId) {
		return "_" + RULE_RULE_PREFIX + "_" + ruleId + "_";
	}
	
}
