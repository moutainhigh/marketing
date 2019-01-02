package com.oristartech.rule.core.init.service;

/**
 * 执行模块初始化，把rule文件load进内存中
 * @author chenjunfei
 */
public interface IRuleExecutorInitService {
	public void init();
	/**
	 * 初始化是否完成
	 * @return
	 */
	public boolean isInitCompleted();
}
