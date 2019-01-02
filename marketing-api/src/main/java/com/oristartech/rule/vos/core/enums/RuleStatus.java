package com.oristartech.rule.vos.core.enums;

/**
 * @author chenjunfei
 * @version 1.0
 * @created 21-十月-2013 10:17:57
 */
public enum RuleStatus {

	/**
	 * 新建
	 */
	NEW,
	/**
	 * 锁定
	 */
	LOCK,
	/**
	 * 执行中
	 */
	RUNNING,
	/**
	 * 停用
	 */
	STOP,
	/**
	 * 作废
	 */
	DELETE;
	
	public static RuleStatus get(int i){
		if(i == 0)
			return RuleStatus.NEW;
		else if(i == 1)
			return RuleStatus.LOCK;
		else if(i == 2)
			return RuleStatus.RUNNING;
		else if(i == 3)
			return RuleStatus.STOP;
		else
			return RuleStatus.DELETE;
	}

}