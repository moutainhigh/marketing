package com.oristartech.rule.functions.sales;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 以优惠价格增加单品, 若价格是0，则为送商品
 * @author user
 *
 */
public class MerchandiseAddWithPrice extends AbstractRuleExecutorFunction {
	//商品类型
	
	private static final String MER_KEY_PARAM = "merKey";
	private static final String MER_PRICE_PARAM = "price";
	private static final String AMOUNT_PARAM = "amount";
	private static final Integer FIX_AMOUNT = 1;
	
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO, RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		RuleActionResult result =  prepareRuleActionResult(actionVO);
		
		String merKey = params.get(MER_KEY_PARAM);
		BigDecimal priceParam = !BlankUtil.isBlank(params.get(MER_PRICE_PARAM)) ? 
				                new BigDecimal(params.get(MER_PRICE_PARAM).toString()) : BigDecimal.ZERO; 
	    BigDecimal amount = !BlankUtil.isBlank(params.get(AMOUNT_PARAM)) ? 
				                		new BigDecimal(params.get(AMOUNT_PARAM).toString()) : null; 
		
		log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId() + " merKey=" + merKey
			+ " priceParam=" + priceParam + " amount=" + amount);
				                		
	    if(BlankUtil.isBlank(merKey) || BlankUtil.isBlank(amount) ) {
	    	return result;
	    }
	    Integer intAmount = amount.intValue();
	    //因为小卖是一个一个添加, 所以根据amount, 加入若amount个对象, 方便前台处理
	    for(int i=0 ; i < intAmount; i++) {
	    	Map<String , Object> map = new HashMap<String, Object>();
			map.put(FactConstants.CATEGORY_TYPE_KEY, BizFactConstants.MER_INFO);
			map.put(BizFactConstants.MER_KEY, merKey);
			map.put(BizFactConstants.PRICE, priceParam);
			map.put(BizFactConstants.AMOUNT, FIX_AMOUNT);
			result.getFnResults().add(map);
	    }
		
		return result;
	}
}
