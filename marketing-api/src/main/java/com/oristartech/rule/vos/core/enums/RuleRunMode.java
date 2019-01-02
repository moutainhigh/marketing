package com.oristartech.rule.vos.core.enums;

/**
 * 规则运行模式, 指定规则用什么方式执行.
 * @author chenjunfei
 * @version 1.0
 * @created 14-十二月-2013 15:08:51
 */
public enum RuleRunMode {
	
	/**
	 * 解析为sql
	 */
	SQL,
	
	/**
	 * drools引擎解析
	 */
	DROOLS,
	
	/**
	 * 用el表达式
	 */
	EL,
	
	/**
	 * 自己写java解析
	 */
	CUSTOM 
}