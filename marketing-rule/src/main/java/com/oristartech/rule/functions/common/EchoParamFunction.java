package com.oristartech.rule.functions.common;

import java.util.Map;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.functions.sales.AbstractRuleExecutorFunction;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 简单返回配置参数方法
 * @author chenjunfei
 */
public class EchoParamFunction extends AbstractRuleExecutorFunction {
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext,  ResultRuleVO resultRuleVO, RuleActionVO actionVO) {
		RuleActionResult result =  prepareRuleActionResult(actionVO);
		result.setIsParam(true);
		//重新从vo中获取, 是因为象邮件, 文件这些参数不会在规则文件中.
		RuleVO ruleVO = acContext.getCurRuleVO();
		if(ruleVO != null) {
			log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId());
			Map params = ruleVO.findActionParam(actionVO.getId());
			if(!BlankUtil.isBlank(params )) {
				result.addFnResult(params);
			}
		}
	    return result ;
	}
}
