package com.oristartech.rule.functions.sort.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 价格相关攻击类
 * @author chenjunfei
 *
 */
public class PriceRuleUtil {
	private final static String MODIFY_FILMT_ICKET_PRICE = "modifyFilmTicketPrice";
	private final static String MODIFY_SALE_ITEM_PRICE = "modifySaleItemPrice";
	
	/**
	 * 去掉不是打折的方法结果
	 * @param result
	 * @return
	 */
	public static RuleResult cleanActionResultInListStyle(RuleResult result) {
		List<RuleActionResult> anResults = result.getResults();
		if(!BlankUtil.isBlank(anResults)) {
			Iterator<RuleActionResult> anIt = anResults.iterator();
			while(anIt.hasNext()) {
				RuleActionResult anResult = anIt.next();
				if(!MODIFY_FILMT_ICKET_PRICE.equals(anResult.getFnEffect()) 
					&& !MODIFY_SALE_ITEM_PRICE.equals(anResult.getFnEffect())) {
					anIt.remove();
				}
				if(BlankUtil.isBlank(anResult.getFnResults())) {
//					anIt.remove();
				}
			}
			result.setResults(anResults);
		}
		if(BlankUtil.isBlank(anResults)) {
			return null;
		} else {
			return result;
		}
	}
	
	/**
	 * 去掉不是打折的方法结果
	 * @param result
	 * @return
	 */
	public static RuleResult cleanActionResultInMapStyle(RuleResult result) {
		Map<String, Object> anResultMap = result.getResultMap();
		if(!BlankUtil.isBlank(anResultMap)) {
			Iterator<Map.Entry<String, Object>> it = anResultMap.entrySet().iterator();
	        while(it.hasNext()){
	            Map.Entry<String, Object> entry = it.next();
	            String key = entry.getKey();
	            if(!MODIFY_FILMT_ICKET_PRICE.equals(key) 
						&& !MODIFY_SALE_ITEM_PRICE.equals(key)) {
//					it.remove();
				}
	        }
		}
		if(BlankUtil.isBlank(anResultMap)) {
			return null;
		} else {
			return result;
		}
	}
}
