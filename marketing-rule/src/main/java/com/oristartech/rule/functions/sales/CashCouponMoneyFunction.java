package com.oristartech.rule.functions.sales;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.functions.common.EchoParamFunction;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.RuleSectionResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 代金券面值（抵用金额）
 * @author chenjunfei
 *
 */
public class CashCouponMoneyFunction extends EchoParamFunction {

	/**
	 * 简单把参数,即面值返回
	 */
	public RuleActionResult run(RuleExecuteContext context, 
									ActionExecuteContext acContext, 
									ResultRuleVO resultRuleVO, 
									RuleActionVO actionVO) {	
		
		RuleResult ruleResult = context.getRuleResult(resultRuleVO.getId());
		RuleSectionResult sectionResult = ruleResult.getSectionResult(actionVO.getRuleSectionId());
		if(sectionResult == null || BlankUtil.isBlank(sectionResult.getActionResults())) {
			//表示代金券以有结果
			return super.run(context, acContext, resultRuleVO, actionVO);
		} else {
			return null;
		}
	}

}
