package com.oristartech.rule.core.functions;

import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 规则操作的接口, 实现类通常由相关业务系统开发人员开发, 和具体业务相关, 实现本接口的类不要有实例状态,  因为实例可能是共享的.
 * @author chenjunfei
 *
 */
public interface IRuleExecutorFunction {
	/**
	 * 操作执行方法
	 * @param context 规则运行的context
	 * @param acContext 动作的当前context
	 * @param ruleVO 规则vo
	 * @param actionVO 规则的动作vo
	 * @return
	 */
	public RuleActionResult execute(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleResultVO, RuleActionVO actionVO );
}
