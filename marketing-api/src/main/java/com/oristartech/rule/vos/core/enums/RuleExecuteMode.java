package com.oristartech.rule.vos.core.enums;

/**
 * 规则执行模式
 * @author chenjunfei
 * @version 1.0
 * @created 14-十二月-2013 15:08:51
 */
public enum RuleExecuteMode {
	/**
	 * 引擎自动匹配
	 */
	AUTO_MATCH,
	
	/**
	 * 自动匹配执行, 但是结果需要提示客户端
	 */
	AUTO_MATCH_RECOMMEND,
	
	/**
	 * 其他
	 */
	OTHER
	
}