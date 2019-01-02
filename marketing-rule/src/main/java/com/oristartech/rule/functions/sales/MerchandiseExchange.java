package com.oristartech.rule.functions.sales;

import java.util.HashMap;
import java.util.Map;

import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 加价换购商品
 * @author user
 *
 */
public class MerchandiseExchange extends AbstractRuleExecutorFunction {
	private static final String MER_KEY_PARAM = "merKey";
	private static final String ADD_MONEY_PARAM = "price";
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO, RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		RuleActionResult result =  prepareRuleActionResult(actionVO);
		
		log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId() + " params=" + JsonUtil.objToJson(params));
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put(FactConstants.CATEGORY_TYPE_KEY, BizFactConstants.MER_INFO);
		map.put(BizFactConstants.MER_KEY, params.get(MER_KEY_PARAM));
		map.put(BizFactConstants.PRICE, params.get(ADD_MONEY_PARAM));
		map.put(BizFactConstants.AMOUNT, 1);
		result.getFnResults().add(map);
		
		return result;
	}

}
