package com.oristartech.rule.vos.base.enums;

/**
 * @author chenjunfei
 * @version 1.0
 * @created 21-十月-2013 10:17:57
 */
public enum OperatorType {
	/**
	 * 适合drools和el
	 */
	RULE_DROOLS_EL,
	
	/**
	 * 只适合drools
	 */
	RULE_DROOLS,
	/**
	 * 只适合el
	 */
	RULE_EL,
	
	/**
	 * sql 的关系运算符号
	 */
	SQL_RELATION
	
}