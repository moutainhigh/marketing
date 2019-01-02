package com.oristartech.rule.functions.sales;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 兑换券调整价格
 * @author chenjunfei
 */
public class ExchangeCouponFunction extends CouponModifyPriceFunction {
	//抵用方法
	private final static String MONEY_METHOD = "moneyMethod";
	
	//零售价方法
	private final static String SALE_PRICE_METHOD = "sale_price";
	
	//固定金额方法
	private final static String FIX_PRICE_METHOD = "fix_price";
	
	//加价金额值
	private final static String ADD_PRICE_VALUE = "addPriceValue";
	//固定抵用金额
	private final static String FIX_MONEY_PRICE_VALUE = "fixPriceValue";
	//抵用金额做价格
	private final static String COUPON_MONEY_AS_PRICE = "couponMoneyAsPrice";
	
	private final static String COUPON_MONEY_AS_PRICE_VALUE = "1";
	//低于应收时支付主体
	private final static String PAYER = "payer";
	//影院支付限额
	private final static String PAYER_PAY_AMOUNT = "payerPayAmount";
	
	private final static String CINEMA = "cinema"; 
	
	private final static String CLIENT = "client"; 
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO,  RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		validateParams(params);
		RuleActionResult result = super.run(context, acContext, ruleVO, actionVO); 
		if(result != null && !BlankUtil.isBlank(result.getFnResults())) {
			calCouponMoneyParams(result.getFnResults(), params);
		} else {
			result = null;
		}
		return result;
	}
	
	/**
	 * 覆盖父类添加影院补贴方法, 因为这里是自己控制调整
	 */
	@Override
	protected void addCinemaPayAfterModifyPrice(Map<String, String> params, Object fact, BigDecimal newPrice) {
	    return ;
	}
	
	private void validateParams(Map<String, String> params) {
		//因为兑换券和优惠券共用本方法, 对于兑换券,要校验数量, 它的数量必填
		BigDecimal modifyAmount = getModifyAmount(params);
		
		if(modifyAmount == null || modifyAmount.compareTo(BigDecimal.ZERO) <= 0 ) {
			throw new RuleExecuteRuntimeException("兑换券必须设置兑换数量!");
		}
	}
	
	/**
	 * 计算抵用面额, 影院支付限额
	 * @param sectionResult
	 * @param params
	 * @return
	 */
	private void calCouponMoneyParams(List<Map<String, Object>> fnResults, Map<String, String> params) {
		String moneyMethod = params.get(MONEY_METHOD);
		
		for(Map<String, Object> factResult : fnResults) {
			//若还没调整过影院补贴
			if(factResult.get(BizFactConstants.CINEMA_PAY_AMOUNT) == null) {
				setUseForMoneyProperty(factResult, params, moneyMethod);
			}
		}
	}
	
	/**
	 * 把抵用面额, 影院补贴直接设到结果中
	 * @param factResult
	 * @param params
	 * @param moneyMethod
	 */
	private void setUseForMoneyProperty(Map<String, Object> factResult, Map<String, String> params, String moneyMethod) {
		BigDecimal price = (BigDecimal)factResult.get(BizFactConstants.PRICE);
		BigDecimal cinemaPay = BigDecimal.ZERO;

		if(SALE_PRICE_METHOD.equals(moneyMethod)) {
			//零售价扣减加价
			String addPriceStr = params.get(ADD_PRICE_VALUE);
			BigDecimal addPrice = (addPriceStr != null && !"".equals(addPriceStr)) ? new BigDecimal(addPriceStr) : BigDecimal.ZERO;
			if(price.compareTo(addPrice) <= 0) {
				BeanUtils.setProperty(factResult, BizFactConstants.USE_FORM_MONEY_AMOUNT, 0);
			} else {
				BeanUtils.setProperty(factResult, BizFactConstants.USE_FORM_MONEY_AMOUNT, price.subtract(addPrice));
			}
		} else if(FIX_PRICE_METHOD.equals(moneyMethod)) {
			//固定金额, 加影院补贴
			String fixPriceStr = params.get(FIX_MONEY_PRICE_VALUE);
			BigDecimal fixPrice = (fixPriceStr != null && !"".equals(fixPriceStr)) ? new BigDecimal(fixPriceStr) : BigDecimal.ZERO;
			if(price.compareTo(fixPrice) <= 0) {
				//价格小于固定抵用,
				String couponMoneyAsPrice = params.get(COUPON_MONEY_AS_PRICE);
				//若设置了抵用做价格
				if(COUPON_MONEY_AS_PRICE_VALUE.equals(couponMoneyAsPrice)) {
					price = fixPrice;
					//重新提升价格
					BeanUtils.setProperty(factResult, BizFactConstants.PRICE, price);
				}
				//直接把价格设为抵用面额
				BeanUtils.setProperty(factResult, BizFactConstants.USE_FORM_MONEY_AMOUNT, price);
			} else {
				//当抵用面额小于价格时, 计算影院补贴, 小于等于设置的限额
				BeanUtils.setProperty(factResult, BizFactConstants.USE_FORM_MONEY_AMOUNT, fixPrice);
				String payer = params.get(PAYER);
				if(CINEMA.equals(payer)) {
					String cinemaPayStr = params.get(PAYER_PAY_AMOUNT);
					cinemaPay =  (cinemaPayStr != null && !"".equals(cinemaPayStr)) ? new BigDecimal(cinemaPayStr) : BigDecimal.ZERO;
					BigDecimal delta = price.subtract(fixPrice);
					//若抵用差额小于最大限额, 只需要补贴差额
					if(delta.compareTo(cinemaPay) < 0) {
						cinemaPay = delta;
					}
				}
			}
		}
		BeanUtils.setProperty(factResult, BizFactConstants.CINEMA_PAY_AMOUNT, cinemaPay);
	}
	
}
