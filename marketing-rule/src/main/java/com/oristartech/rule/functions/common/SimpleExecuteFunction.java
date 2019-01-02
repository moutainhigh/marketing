package com.oristartech.rule.functions.common;

import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 执行型方法, 该方法现在不会做太多逻辑, 而是直接把参数返回到前端, 让前端确定是否执行
 * @author chenjunfei
 * 
 */
public class SimpleExecuteFunction extends EchoParamFunction {
	
	/**
	 * 现在的执行型方法只是简单输出设置的参数.
	 */
	public RuleActionResult run(RuleExecuteContext context,ActionExecuteContext acContext,  ResultRuleVO ruleVO, RuleActionVO actionVO) {
	    return super.run(context, acContext, ruleVO, actionVO);
	}
}
