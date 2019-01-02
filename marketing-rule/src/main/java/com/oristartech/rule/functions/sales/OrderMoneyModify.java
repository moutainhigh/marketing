package com.oristartech.rule.functions.sales;

import java.util.List;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 整单金额扣减
 * @author chenjunfei
 *
 */
public class OrderMoneyModify extends AbstractModifyPriceFunction {
	@Override
	protected RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO resultRuleVO,
			RuleActionVO actionVO) {
		RuleResult ruleResult = context.getRuleResult(resultRuleVO.getId());
		List<Object> allFacts = context.getAllCloneFacts();
		super.removeNotSaleFact(allFacts);
		if(!BlankUtil.isBlank(allFacts)) {
			RuleActionResult result =  prepareRuleActionResult(actionVO);
		}
		return null;
	}
}
