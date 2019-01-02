package com.oristartech.rule.vos.core.enums;

public enum FunctionExecuteMode {
	/**
	 * 函数逻辑会由引擎执行
	 */
	EXE_BUY_ENGINE,
	
	/**
	 * 只是个方法占位符, 方法逻辑不由引擎执行, 例如一些webservice方法, 对引擎来说, 只是一条记录, 不会把参数加载到规则文件中.
	 */
	TAG_FUNC
}
