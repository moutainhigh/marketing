package com.oristartech.rule.functions.sales;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.FactUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.functions.dto.RuleTicketLimitInfo;
import com.oristartech.rule.functions.util.FilmFunctionUtil;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;
import com.oristartech.rule.vos.result.action.RuleConsumeActionResult;

/**
 * 修改单票售价为最低发行价+N元
 * @author user
 *
 */
public class FilmTicketPriceModify extends AbstractModifyPriceFunction {
	//调价方式
	private static final String MODIFY_WAY_PARAM = "modifyWay";
	//调价值
	private static final String MODIFY_VALUE_PARAM = "modifyValue";
	
	//超限处理方法
	private static final String LESS_PROCESS_METHOD = "lessProcessMethod";
	
	//影院最多补贴N元
	private static final String CINEMA_PAY_AMOUNT = "cinemaPayAmount";
	
	private static final String ALL_MODIFY_AMOUNT = "all";
	
	//返回需要消费积分数和金额
	private static final String INTEGRAL_AMOUNT = "integralAmount";
	private static final String INTEGRAL_MONEY = "integralMoney";
		
	//折扣后小数调整模式参数名称
	protected static final String DECIMAL_ROUND_MODE_PARAM = "decimalRoundMode";
	
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO, RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		
		RuleResult ruleResult = context.getRuleResult(ruleVO.getId());
		
		List<Object> filmFacts = getModifyFilmFacts(context, acContext, ruleVO, actionVO);
		
		log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId() + " params=" + JsonUtil.objToJson(params)
			+ " merFacts=" + JsonUtil.objToJson(filmFacts));
		
		if(!validate(params)) {
			return null;
		}
		
		if(BlankUtil.isBlank(filmFacts)) {
			log.info("<run> filmFacts=null");
			return null;
		}
		
		RuleTicketLimitInfo limitInfo = FilmFunctionUtil.getTicketLimitInfo(context, acContext, actionVO);
		
		Integer modifyAmount = getModifyAmount(context ,acContext, actionVO, limitInfo );
		
		if(modifyAmount != null && modifyAmount == 0) {
			log.info("<run> modifyAmount=" + modifyAmount + "  limitInfo=" + JsonUtil.objToJson(limitInfo));
			return null;
		}
        
        super.sortSaleFact(filmFacts);
        RuleConsumeActionResult anResult = null;
        Boolean isUseNewConsumeItem = BizFunctionUtil.getIsUseNewConsumeItem(context.getAllFacts());
        
        if(isUseNewConsumeItem == null || isUseNewConsumeItem == false) {
        	anResult = doModify(ruleResult, actionVO ,params, filmFacts, modifyAmount);
        } else {
        	anResult = doModify(ruleResult, actionVO ,params, filmFacts, modifyAmount);
        	processResults(context, acContext, anResult, actionVO, modifyAmount, limitInfo, isUseNewConsumeItem);
        }
        return anResult;
	}
	
	/**
	 * 设置真正的结果数量，消耗相关属性
	 * @param finalModifyAmount 是计算了会员剩余优惠数的数量
	 * @param anResult
	 */
	private void processResults(RuleExecuteContext context, 
			                   ActionExecuteContext acContext,
			                   RuleConsumeActionResult anResult,
			                   RuleActionVO actionVO,
			                   Integer finalModifyAmount, 
			                   RuleTicketLimitInfo limitInfo,
			                   Boolean isUseNewConsumeItem) {
		List<Map<String, Object>> fnResults = anResult.getFnResults();
		if(isUseNewConsumeItem != null && isUseNewConsumeItem) {
			//本数量只是设置在方法中的数量，没包含会员其他设置的数量， finalModifyAmount是计算了会员剩余优惠数的数量
			finalModifyAmount = (finalModifyAmount == null || finalModifyAmount > fnResults.size()) ? fnResults.size() : finalModifyAmount;
			anResult.setUseAmountInResult(finalModifyAmount);
			anResult.setGlobalAmountLimit(limitInfo.calcCurTicketLimit());
			
			anResult.setConsumeAmount(FilmFunctionUtil.calcConsumeFactAmount(context, acContext,
													  getFilmFactAmount(context),
					                                  fnResults.size(), 
					                                  getModifyAmountInParam(actionVO, acContext), 
					                                  finalModifyAmount));
		}
	}
	
	/**
	 * 获取可以进行修改的影票事实
	 * @return
	 */
	protected List<Object> getModifyFilmFacts(RuleExecuteContext context, ActionExecuteContext acContext,
			ResultRuleVO ruleVO, RuleActionVO actionVO) {
		List<Object> filmFacts = context.cloneFactsByType(BizFactConstants.FILM_INFO);
//		List<Object> matchFilmFacts = FactUtil.collectFactsByType(acContext.getCurMatchFacts(), BizFactConstants.FILM_INFO);
//		//若匹配事实包含影票,表示条件中有影票相关条件,不能修改所有影票
//		if(!BlankUtil.isBlank(matchFilmFacts)) {
//			filmFacts = matchFilmFacts;
//		}
		
		return filmFacts;
	}
	
	/**
	 * 获取影票数量属性
	 * @param context
	 * @return
	 */
	protected Integer getFilmFactAmount(RuleExecuteContext context) {
		Object saleInfo = FactUtil.getFactByType(context.getAllFacts(), BizFactConstants.SALE_INFO);
		String amountStr = BeanUtils.getPropertyStr(saleInfo, BizFactConstants.FILM_TICKET_AMOUNT);
		if(!BlankUtil.isBlank(amountStr)) {
			return Integer.parseInt(amountStr);
		}
		return 0;
	}
	
	/**
	 * 校验基本参数
	 * @param params
	 * @return
	 */
	protected boolean validate(Map<String, String> params) {
		String modifyWayStr = params.get(MODIFY_WAY_PARAM);
		BigDecimal modifyValue = BizFunctionUtil.getBigDecimal(params, MODIFY_VALUE_PARAM);
		String lessProcessMethodStr = params.get(LESS_PROCESS_METHOD);
		if(BlankUtil.isBlank(modifyWayStr) || modifyValue == null || BlankUtil.isBlank(lessProcessMethodStr)) {
			log.info("<validate> modifyWayStr=" + modifyWayStr + " lessProcessMethodStr=" + lessProcessMethodStr
					+ " modifyValue=" + modifyValue);
			return false;
		}
		return true;
	}
	
	private RuleConsumeActionResult doModify(RuleResult ruleResult, RuleActionVO actionVO, Map<String, String> params, List<Object> allFacts, Integer modifyAmount ) {
		RuleConsumeActionResult result =  prepareRuleConsumeActionResult(actionVO);
		BigDecimal cinemaPayAmount =  BizFunctionUtil.getBigDecimal(params, CINEMA_PAY_AMOUNT, BigDecimal.ZERO);
		ModifyPriceWay modifyWay = 	ModifyPriceWay.valueOf(params.get(MODIFY_WAY_PARAM));	                        
	    LessProcessWay lessWay = LessProcessWay.valueOf(params.get(LESS_PROCESS_METHOD));
	    BigDecimal modifyValue = BizFunctionUtil.getBigDecimal(params, MODIFY_VALUE_PARAM);
	    
		int changedAmount = 0;
		for(Object fact : allFacts) {
			validateFilmFact(fact);
			String key = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
			Object modifiedItem = ruleResult.getModifiedFact(key);
			if(modifiedItem != null) {
				continue;
			}
			BigDecimal price = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.PRICE);
			BigDecimal lowestPrice = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.LOW_PRICE);
			
			Map<String, Object> resultFact = BizFunctionUtil.getSimpleSaleFactForModify(fact);
			BigDecimal newPrice = null;
			switch(modifyWay) {
			//固定金额
			case fixPrice : newPrice = modifyValue; break;
			//零售价打折
			case discountPrice : newPrice = discountPriceFn(price, modifyValue); break;
			//零售价-N元
			case subPrice :  newPrice = subPriceFn(price, modifyValue); break;
			//最低发行价N元
			case addToLowestPrice : newPrice = addToLowestPriceFn(lowestPrice, modifyValue); break;
			}
			
			setIntegralPayResult(resultFact, params);
			newPrice = addAmountAfterDiscount(newPrice, params);
			
			newPrice = getCorrectPrice(newPrice, price);
			newPrice = roundDecimalNumber(newPrice, params.get(DECIMAL_ROUND_MODE_PARAM));
			
			resultFact.put(BizFactConstants.PRICE, newPrice);
			changedAmount ++;
			
			resultFact = tooLessPriceProcess(newPrice, lowestPrice, cinemaPayAmount, resultFact, lessWay);
			if(resultFact != null) {
				result.getFnResults().add(resultFact);
			}
			
			Map<String, Object> newModifiedItem = BizFunctionUtil.getSaleFactForRecordModified(fact);
			ruleResult.addModifiedFact(key, newModifiedItem);
			
			if(modifyAmount != null && changedAmount >= modifyAmount) {
				break;
			}
		}
		return result;
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
	 * 获取现在需要修改的票数，要根据规则方法参数，和现在会员卡等级（若是会员）的限制票数决定一个值。
	 * @param context
	 * @param acContext
	 * @param actionVO
	 * @param fnModifyAmount 规则方法中设置的限制
	 * @return
	 */
	private Integer getModifyCountLimit(RuleExecuteContext context, ActionExecuteContext acContext, 
								RuleActionVO actionVO, Integer fnModifyAmount, RuleTicketLimitInfo limitInfo) {
		Integer curLeftAmountCanUse = limitInfo.calcCurTicketLimit();
		//若没限制，用方法的参数
		if(curLeftAmountCanUse == null) {
			return fnModifyAmount;
		}
		
		Integer existTicketCount =  getExistTicketCount(acContext, actionVO);
		
		//若有限制，用两者小的那个值。
		Integer currentLimit = curLeftAmountCanUse - existTicketCount;
		if(currentLimit < 0 ) {
			currentLimit = 0;
		}
		if(fnModifyAmount == null || fnModifyAmount > currentLimit) {
			return currentLimit;
		}
		return fnModifyAmount;
	}
	
	/**
	 * 获取之前方法的修改票数
	 * @param context
	 * @param actionVO
	 * @return
	 */
	private Integer getExistTicketCount(ActionExecuteContext context, RuleActionVO actionVO) {
		List<RuleActionResult> anResults = context.getCurRuleResult().getResults();
		Integer existCount = 0;
		if(!BlankUtil.isBlank(anResults)) {
			for(RuleActionResult result : anResults) {
				if(result.getFnEffect().equals(actionVO.getFnEffect()) && !BlankUtil.isBlank(result.getFnResults())) {
					existCount += result.getFnResults().size();
				}
			}
		}
		return existCount;
	}
	
	/**
	 * 获取可以打折的数量
	 * @param context
	 * @param acContext
	 * @param actionVO
	 * @return
	 */
	private Integer getModifyAmount(RuleExecuteContext context, ActionExecuteContext acContext, RuleActionVO actionVO, RuleTicketLimitInfo limitInfo) {
		
		Integer modifyAmount = null;
		if(limitInfo.getIgnoreTicketAmount() == null || limitInfo.getIgnoreTicketAmount() == false) {
			modifyAmount = getModifyAmountInParam(actionVO, acContext);
		}
		
		if(limitInfo.getIgnoreCardTicketAmount() == null || limitInfo.getIgnoreCardTicketAmount() == false) {
			modifyAmount = getModifyCountLimit(context, acContext, actionVO, modifyAmount, limitInfo);
		}
		return modifyAmount;
	}
	
	/**
	 * 获取修改数量，若要修改全部，返回null
	 * @return
	 */
	private Integer getModifyAmountInParam(RuleActionVO actionVO, ActionExecuteContext acContext) {
		Map<String, String> params = actionVO.getParameterMap();
		String amountMethod = params.get(FilmFunctionUtil.MODIFY_AMOUNT_METHOD);
		Integer modifyAmount = null;
		if(FilmFunctionUtil.APPOINT_MODIFY_AMOUNT.equals(amountMethod) ) {
			//因为本方法根据影票条件可以多次运行,所以runTimes在本方法有意义
			Integer runTimes = acContext.getRunTimes() == null ? 1 : acContext.getRunTimes();
			
			modifyAmount = Integer.valueOf(params.get(FilmFunctionUtil.MODIFY_AMOUNT_PARAM));
			modifyAmount = runTimes * modifyAmount;
		} else if(!ALL_MODIFY_AMOUNT.equals(amountMethod) ) {
			modifyAmount = Integer.valueOf(params.get(FilmFunctionUtil.MODIFY_AMOUNT_PARAM));
		}
		return modifyAmount;
	}
	
	/**
	 * 设置低于最低发行价处理
	 * @param price
	 * @param lowestPrice
	 * @param cinemaPayAmount
	 * @param resultFact
	 * @param lessWay
	 */
	private Map<String, Object> tooLessPriceProcess(BigDecimal price, BigDecimal lowestPrice, BigDecimal cinemaPayAmount, Map<String, Object> resultFact, LessProcessWay lessWay) {
		BigDecimal cnPayAmount = BigDecimal.ZERO;
		//价格小于最低发行价
		if(price.compareTo(lowestPrice) < 0) {
			BigDecimal delta = lowestPrice.subtract(price);
			switch(lessWay) {
			//不允许出票, 不添加到结果中, 即按原价卖
			case notAllowSale : return null; 
			case clientPay : resultFact.put(BizFactConstants.PRICE, lowestPrice); break;
			case cinemaPay : 
				        //当允许出票,有影院补贴时, 先直接把价格设为最低发行价, 因为必须要用最低发行价出票.
				        resultFact.put(BizFactConstants.PRICE, lowestPrice);
				        if(delta.compareTo(cinemaPayAmount) < 0) {
							//若差额小于设置的最大影院补贴, 影院补贴设为差额.
							cnPayAmount = delta;
						} else {
							cnPayAmount = cinemaPayAmount;
						}
				        break;
			}
		}
		resultFact.put(CINEMA_PAY_AMOUNT, cnPayAmount);
		return resultFact;
		
	}
	
	private BigDecimal discountPriceFn(BigDecimal price, BigDecimal modifyValue) {
		price = price.multiply(modifyValue).divide(new BigDecimal(100));
	    price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
	    return price;
	}
	
	private BigDecimal subPriceFn(BigDecimal price, BigDecimal modifyValue) {
		if(modifyValue.compareTo(price) <= 0) {
			price = price.subtract(modifyValue);
		} else {
		 	price = BigDecimal.ZERO;
		}
		return price;
	}
	
	private BigDecimal addToLowestPriceFn(BigDecimal lowestPrice, BigDecimal modifyValue) {
		lowestPrice = lowestPrice.add(modifyValue);
		if(lowestPrice.compareTo(BigDecimal.ZERO) < 0) {
			lowestPrice = BigDecimal.ZERO;
		}
		return lowestPrice;
	}
	
	enum ModifyPriceWay {
		//固定金额
		fixPrice,
		//零售价打折
		discountPrice,
		//零售价-N元
		subPrice,
		//最低发行价N元
		addToLowestPrice
	}
	
	enum LessProcessWay {
		//不允许出票
		notAllowSale,
		//影院补贴
		cinemaPay,
		//用户补贴
		clientPay
	}

}
