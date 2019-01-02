package com.oristartech.rule.functions.sales;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 送商品, 会多次运行.
 * @author chenjunfei
 *
 */
public class MerchandisePresent extends MerchandiseAddWithPrice {
	@Override
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO,
	        RuleActionVO actionVO) {
		Integer runTimes = acContext.getRunTimes();
		runTimes = runTimes == null ? 1 : runTimes;
		RuleActionResult result = null;
		
		log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId() + " runTimes=" + runTimes);
		
		for(int i = 0; i < runTimes; i++) {
			RuleActionResult tempResult = super.run(context, acContext, ruleVO, actionVO);
			if(result == null) {
				result = tempResult;
			} else if(tempResult != null && !BlankUtil.isBlank(tempResult.getFnResults())){
				result.getFnResults().addAll(tempResult.getFnResults());
			}
		}
		return result;
	}
}
