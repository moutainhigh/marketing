package com.oristartech.rule.functions.sales;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 单品优惠价调整
 * @author chenjunfei
 *
 */
public class MerchandiseModifyPrice extends AbstractModifyPriceFunction {
	private static final String MER_KEY_PARAM = "merKey";
	//调价方式
	private static final String MODIFY_WAY_PARAM = "modifyWay";
	private static final String MODIFY_VALUE_PARAM = "modifyValue";
	private static final String MODIFY_AMOUNT_PARAM = "modifyAmount";
	
	//固定金额
	private static final String MODIFY_WAY_FIX = "fixPrice";
	//零售价打折
	private static final String MODIFY_VALUE_DISCOUNT = "discountPrice";
	//零售价-N元
	private static final String MODIFY_VALUE_SUB = "subPrice";
	
	//数量方式
	private static final String MODIFY_AMOUNT_METHOD = "modifyAmountMethod";
	
	private static final String ALL_MODIFY_AMOUNT = "all";
	private static final String APPOINT_MODIFY_AMOUNT = "appointAmount";
	//返回需要消费积分数
	private static final String INTEGRAL_AMOUNT = "integralAmount";
	private static final String INTEGRAL_MONEY = "integralMoney";
	
	//折扣后小数调整模式参数名称
	protected static final String DECIMAL_ROUND_MODE_PARAM = "decimalRoundMode";
	
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO, RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		RuleActionResult result =  prepareRuleActionResult(actionVO);
		RuleResult ruleResult = context.getRuleResult(ruleVO.getId());
		List<Object> allFacts = context.getAllCloneFacts();
		
		String modifyWay = params.get(MODIFY_WAY_PARAM);
		BigDecimal modifyValue = BizFunctionUtil.getBigDecimal(params, MODIFY_VALUE_PARAM);
		String modifyMerKey = params.get(MER_KEY_PARAM);
		
		log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId() + " params=" + JsonUtil.objToJson(params));
		
		if(BlankUtil.isBlank(modifyWay) || modifyValue == null || modifyMerKey == null) {
			return result;
		}
		
		for(Object fact : allFacts) {
			String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
			String merKey = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_KEY);
			//若是卖品
			if(BizFactConstants.MER_INFO.equals(type) ) {
				String[] curMerKeys = merKey.split(",");
				String[] modifyMerKeys = modifyMerKey.split(",");
				validateMerFact(fact);
//				if(!modifyMerKey.equals(merKey) ) {
//					continue;
//				}
				for(int i=0; i < curMerKeys.length; i++) {
					for(int j=0; j < modifyMerKeys.length; j++) {
						if(curMerKeys[i].equals(modifyMerKeys[j])) {
							Map<String, Object> resultFact = modifyMerItem(acContext, params, fact, ruleResult);
							if(resultFact != null) {
								result.getFnResults().add(resultFact);
							}
						}
					}
				}
				
			}
		}
		return result;
	}
	
	private Map<String, Object> modifyMerItem(ActionExecuteContext acContext, Map<String, String> params, Object fact, RuleResult ruleResult) {
		BigDecimal modifyAmount = getModifyAmount(params, acContext);
		BigDecimal modifyValue = BizFunctionUtil.getBigDecimal(params, MODIFY_VALUE_PARAM);
		String modifyWay = params.get(MODIFY_WAY_PARAM);
		
		String key = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
		BigDecimal itemAmount = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.AMOUNT);
		
		if(modifyAmount == null || modifyAmount.compareTo(itemAmount) > 0 ) {
			modifyAmount = itemAmount;
		}
		
		Object modifiedItem = ruleResult.getModifiedFact(key);
		if(modifiedItem != null) {
			return null;
		}

		Map<String, Object> resultFact = BizFunctionUtil.getSimpleSaleFactForModify(fact);
		
		setIntegralPayResult(resultFact, params);
		
		BigDecimal newPrice = calNewPrice(modifyWay, modifyValue, fact, params);
		newPrice = roundDecimalNumber(newPrice, params.get(DECIMAL_ROUND_MODE_PARAM));
		
		resultFact.put(BizFactConstants.PRICE, newPrice);
		resultFact.put(BizFactConstants.AMOUNT, modifyAmount);
		splitSaleItemKeys(resultFact, modifyAmount, null);
		
		Map<String, Object> newModifiedItem = BizFunctionUtil.getSaleFactForRecordModified(fact);
		newModifiedItem.put(BizFactConstants.AMOUNT, modifyAmount);
		newModifiedItem.put(BizFactConstants.SALE_ITEM_KEY, resultFact.get(BizFactConstants.SALE_ITEM_KEY));
		ruleResult.addModifiedFact(key, newModifiedItem);
		
		return resultFact;
	}
	
	/**
	 * 设置额外的积分消费数和消费金额
	 */
	private void setIntegralPayResult(Map<String, Object> resultFact, Map<String, String> params) {
		String integralAmount = params.get(INTEGRAL_AMOUNT);
		resultFact.put(INTEGRAL_AMOUNT,  !BlankUtil.isBlank(integralAmount) ? Integer.parseInt(integralAmount)  : null);
		
		String integralMoney = params.get(INTEGRAL_MONEY);
		resultFact.put(INTEGRAL_MONEY,  !BlankUtil.isBlank(integralMoney) ? new BigDecimal(integralMoney)  : null);
	}
	
	/**
	 * 计算新价格
	 * @param modifyWay
	 * @param modifyValue
	 * @param fact
	 * @return
	 */
	private BigDecimal calNewPrice(String modifyWay, BigDecimal modifyValue , Object fact, Map<String, String> params) {
		BigDecimal oldPrice = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.PRICE);
		
		BigDecimal newPrice = null;
		
		if(MODIFY_WAY_FIX.equals(modifyWay)) {
			newPrice = modifyValue;
		} else if(MODIFY_VALUE_DISCOUNT.equals(modifyWay)){
			newPrice = oldPrice.multiply(modifyValue).divide(new BigDecimal(100));
			newPrice = newPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		} else if(MODIFY_VALUE_SUB.equals(modifyWay)) {
			if(modifyValue.compareTo(oldPrice) <= 0) {
				newPrice = oldPrice.subtract(modifyValue);
			} else {
				newPrice = BigDecimal.ZERO;
			}
		}
		newPrice = addAmountAfterDiscount(newPrice, params);
		newPrice = super.getCorrectPrice(newPrice, oldPrice);
		
		return newPrice;
	}
	/**
	 * 获取修改数量
	 * @return
	 */
	private BigDecimal getModifyAmount(Map<String, String> params, ActionExecuteContext acContext) {
		String amountMethod = params.get(MODIFY_AMOUNT_METHOD);
		if(APPOINT_MODIFY_AMOUNT.equals(amountMethod)) {
			//因为本方法根据影票条件可以多次运行,所以runTimes在本方法有意义
			Integer runTimes = acContext.getRunTimes() == null ? 1 : acContext.getRunTimes();
			
			BigDecimal modifyAmount = BizFunctionUtil.getBigDecimal(params, MODIFY_AMOUNT_PARAM);
			modifyAmount = modifyAmount.multiply(new BigDecimal(runTimes));
			return modifyAmount;
		}
		return null;
	}
}
