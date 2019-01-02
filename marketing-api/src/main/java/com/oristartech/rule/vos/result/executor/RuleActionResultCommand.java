package com.oristartech.rule.vos.result.executor;

/**
 * 规则执行方法结果解析接口
 * @author Administrator
 *
 */
public interface RuleActionResultCommand {
	void execute(Object fnResult);
}
