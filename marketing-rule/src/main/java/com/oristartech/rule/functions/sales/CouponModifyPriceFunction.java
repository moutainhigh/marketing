package com.oristartech.rule.functions.sales;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.RuleSectionResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 票券调整零售价, 执行优惠逻辑. 优惠券在优惠后低于最低发行价后, 会添加影院补贴. 兑换券在抵用后才计算影院补贴
 * @author chenjunfei
 *
 */
public class CouponModifyPriceFunction extends AbstractModifyPriceFunction {
	
	//调整零售价方法
	protected static final String MODIFY_PRICE_METHOD = "priceModifyMethod";
	
	//调整零售价方法值
	protected static final String MODIFY_PRICE_VALUE = "priceModifyValue";
	//调整数量
	public static final String MODIFY_AMOUNT = "amount";
	
	//调整零售价方式
	private static final String LOWEST_PRICE = "lowest_price";
	private static final String FIX_PRICE = "fix_price";
	private static final String SUB_PRICE = "subPrice";
	private static final String APPOINT_PERCENT = "appointPercent";
	private static final String IS_MODIFY_STANDARD_PRICE = "isModifyStandardPrice";
	//低于应收时支付主体
	protected final static String PAYER = "payer";
	//影院支付限额
	protected final static String PAYER_PAY_AMOUNT = "payerPayAmount";
	
	protected final static String CINEMA = "cinema"; 
	
	protected final static String CLIENT = "client"; 
	
	protected final static String DECIMAL_ROUND_MODE = "decimalRoundMode" ;
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext,  ResultRuleVO resultRuleVO, RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		validateParams(params);
		
		RuleResult ruleResult = context.getRuleResult(resultRuleVO.getId());
		RuleSectionResult sectionResult = ruleResult.getSectionResult(actionVO.getRuleSectionId());
		//本方法可以在同一次匹配, 多次运行(若有多个影票或商品),把这些修改合并到之前运行的结果中
		RuleActionResult result = sectionResult.getActionResult(actionVO.getId(), acContext.getRunIndex());
		if(result == null) {
			result = prepareRuleActionResult(actionVO);
		}
		List<Object> matchFacts = acContext.getCurMatchFacts();
		super.removeNotSaleFact(matchFacts);
		
		log.info("<run> ruleid=" + resultRuleVO.getId() + " actionid=" + actionVO.getId() + " params=" + JsonUtil.objToJson(params)
			+ " merFacts=" + JsonUtil.objToJson(matchFacts));
		
		if(BlankUtil.isBlank(matchFacts)) {
			log.info("<run> matchFacts=null");
			return null;
		}
		RuleVO ruleVO = acContext.getCurRuleVO();
		//获取当前修改商品类型
		String conSaleItemType = ruleVO.findFirstConditionElementValue(actionVO.getRuleSectionId(), BizFactConstants.SALE_ITEM_INFO, BizFactConstants.SALE_ITEM_TYPE);
		//先排序, 按最高价往下打折
		sortSaleFact(matchFacts);
		//待修改数量
		BigDecimal modifyAmount = getModifyAmount(params);
		
		//已修改事实数量
		BigDecimal modifiedFactAmount = BigDecimal.ZERO;
		for(Object fact : matchFacts) {
			if(modifyAmount != null && modifyAmount.compareTo(BigDecimal.ZERO) <= 0) {
				break;
			}
			
			Map<String, Object> resultFact = BizFunctionUtil.getSimpleSaleFactForModify(fact);
			
			BigDecimal curModifyAmount = null;
			if(BizFactConstants.FILM_ITEM.equals(conSaleItemType)) {
				validateFilmFact(fact);
				curModifyAmount = modifyFilmPrice(resultFact, params, modifyAmount, ruleResult);					
			} else if(BizFactConstants.MER_ITEM.equals(conSaleItemType)) {
				validateMerFact(fact);
				curModifyAmount = modifyMerItem(resultFact, params, fact, modifyAmount, ruleResult);
			}
			if(curModifyAmount == null || curModifyAmount.intValue() == 0) {
				continue;
			}
			result.getFnResults().add(resultFact);
			modifiedFactAmount = modifiedFactAmount.add(curModifyAmount);
			modifyAmount = getRemainAmount(modifyAmount, curModifyAmount);
		}
		result.setModifyFactAmount(modifiedFactAmount.intValue());
		sectionResult.setModifyFactAmount(modifiedFactAmount.intValue());
		
		return result;
	}
	
	/**
	 * 获取剩下的修改数量
	 * @param remainAmount
	 * @param curModifyAmount
	 * @return
	 */
	private BigDecimal getRemainAmount(BigDecimal remainAmount, BigDecimal curModifyAmount) {
		if(remainAmount != null) {
			return remainAmount.subtract(curModifyAmount);
		} else {
			return null;
		}
	}
	/**
	 * 获取同一条优惠项可以再优惠的数量.
	 * @param configModifyAmount
	 * @param anResult
	 * @return
	 */
	private BigDecimal getRemainAmountInSameAction(BigDecimal configModifyAmount, RuleActionResult anResult) {
		//null 表示要修改全部.
		if(configModifyAmount == null) {
			return null;
		}
		List<Map<String, Object>> results = anResult.getFnResults();
		BigDecimal existModifyAmount = BigDecimal.ZERO;
		if(!BlankUtil.isBlank(results)) {
			for(Map<String, Object> result : results) {
				existModifyAmount = existModifyAmount.add(BizFunctionUtil.getBigDecimal(result, BizFactConstants.AMOUNT));
			}
		}
		configModifyAmount = configModifyAmount.subtract(existModifyAmount);
		if(configModifyAmount.compareTo(BigDecimal.ZERO) <= 0) {
			configModifyAmount = BigDecimal.ZERO;
		}
		return configModifyAmount;
	}
	
	private BigDecimal modifyMerItem(Map<String, Object> resultFact, Map<String, String> params, Object fact, BigDecimal modifyAmount , RuleResult ruleResult) {
		
		if(modifyAmount != null && modifyAmount.compareTo(BigDecimal.ZERO) <= 0) {
			return null;
		}
		
		String key = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
		BigDecimal itemAmount = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.AMOUNT);
		//若不设数量，即修改全部该商品
		if(modifyAmount == null) {
			modifyAmount = itemAmount;
		}
		
		//同一规则, 有多个小卖项，不同的小卖优惠项，可能会匹配相同的商品， 遵守从前到后进行优先修改。
		Map<String, Object> modifiedItem = (Map<String, Object>)ruleResult.getModifiedFact(key);
		if(modifiedItem != null) {
			//已经修改的数量
			BigDecimal existModifyAmount = BizFunctionUtil.getBigDecimal(modifiedItem, BizFactConstants.AMOUNT);
			//剩下商品数量
			itemAmount = itemAmount.subtract(existModifyAmount);
			if(itemAmount.compareTo(BigDecimal.ZERO) <= 0) {
				//已经修改完所有该商品数量
				return null;
			}
		}
		
		if(modifyAmount.compareTo(itemAmount) > 0) {
			modifyAmount = itemAmount;
		}
		
		modify(resultFact, params, BizFactConstants.PRICE);
		//小卖要设置修改数量
		BeanUtils.setProperty(resultFact, BizFactConstants.AMOUNT, modifyAmount);
		
		if(modifiedItem == null) {
			splitSaleItemKeys(resultFact, modifyAmount, null);
			Map<String, Object> newModifiedItem = BizFunctionUtil.getSaleFactForRecordModified(fact);
			newModifiedItem.put(BizFactConstants.AMOUNT, modifyAmount);
			newModifiedItem.put(BizFactConstants.SALE_ITEM_KEY, resultFact.get(BizFactConstants.SALE_ITEM_KEY));
			ruleResult.addModifiedFact(key, newModifiedItem);
		} else {
			String existSaleItemKeys = (String)modifiedItem.get(BizFactConstants.SALE_ITEM_KEY);
			splitSaleItemKeys(resultFact, modifyAmount, existSaleItemKeys);
			
			BigDecimal existModifyAmount = BizFunctionUtil.getBigDecimal(modifiedItem, BizFactConstants.AMOUNT);
			modifiedItem.put(BizFactConstants.AMOUNT, existModifyAmount.add(modifyAmount));
			modifiedItem.put(BizFactConstants.SALE_ITEM_KEY, existSaleItemKeys + BizFactConstants.SALE_ITEM_KEY_SPLITER + resultFact.get(BizFactConstants.SALE_ITEM_KEY));
		} 
		return modifyAmount;
	}
	
	/**
	 * 获取修改数量
	 * @return
	 */
	protected BigDecimal getModifyAmount(Map<String , String > params) {
		String amountStr = params.get(MODIFY_AMOUNT); 
		BigDecimal modifyAmount = !BlankUtil.isBlank(amountStr) ? new BigDecimal(amountStr) : null;
		
		return modifyAmount;
	}
	
	//如果条件设的是影票, 并且不是对标准价打折, 因为各个影票的价格可能不一样,  对matchFacts里面的影票倒序排序
	private void sortFilmFact(String conSaleItemType, List facts , final String property) {
		if(BlankUtil.isBlank(facts) || facts.size() < 2) {
			return;
		}
		if(BizFactConstants.FILM_ITEM.equals(conSaleItemType) ) {
			Collections.sort(facts, new Comparator<Object>() {
				public int compare(Object o1, Object o2) {
					BigDecimal price1 = BizFunctionUtil.getBigDecimal(o1, property);
					BigDecimal price2 = BizFunctionUtil.getBigDecimal(o2, property);
				    return price2.compareTo(price1);
				}
			});
		}
	}
	
	/**
	 * 修改影票价格
	 * @param fact
	 * @param params
	 */
	private BigDecimal modifyFilmPrice(Map<String, Object> fact, Map<String, String> params, BigDecimal modifyAmount, RuleResult ruleResult) {
		if(modifyAmount != null && modifyAmount.compareTo(BigDecimal.ZERO) <= 0) {
			return null;
		}
		
		Boolean isModifyStandardPrice = Boolean.valueOf(params.get(IS_MODIFY_STANDARD_PRICE));
		String priceProperty = isModifyStandardPrice ? BizFactConstants.FILM_STANDARD_PRICE : BizFactConstants.PRICE;
		
		BigDecimal itemAmount = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.AMOUNT);
		//若不设数量，即修改全部该商品
		if(modifyAmount == null) {
			modifyAmount = itemAmount;
		}
		if(modifyAmount.compareTo(itemAmount) > 0) {
			modifyAmount = itemAmount;
		}
		String modifyMethod = params.get(MODIFY_PRICE_METHOD);
		if(LOWEST_PRICE.equals(modifyMethod)) {
			Object lowestPrice = BeanUtils.getProperty(fact, BizFactConstants.LOW_PRICE);
			Object oldPrice = BeanUtils.getProperty(fact, BizFactConstants.PRICE);
			BeanUtils.setProperty(fact, BizFactConstants.PRICE, lowestPrice);
			BeanUtils.setProperty(fact, BizFactConstants.OLD_PRICE, oldPrice);
		} else {
			modify(fact, params, priceProperty);
		}
		splitSaleItemKeys(fact, modifyAmount, null);
		BeanUtils.setProperty(fact, BizFactConstants.AMOUNT, modifyAmount);
		return modifyAmount;
	}
	
	private void validateParams(Map<String, String> params) {
		String priceModifyMethod = params.get(MODIFY_PRICE_METHOD);
		String modifyValue = params.get(MODIFY_PRICE_VALUE);
		if(BlankUtil.isBlank(priceModifyMethod)) {
			throw new RuleExecuteRuntimeException("票券必须优惠方法!");
		}
		
		//只有最低发行价可空
		if(!LOWEST_PRICE.equals(priceModifyMethod) && BlankUtil.isBlank(modifyValue)) {
			throw new RuleExecuteRuntimeException("票券必须设置调整零售价方法值");
		}
	}
	
	/**
	 * 修改卖品, 影票价格
	 * @param fact
	 * @param params
	 */
	private void modify(Object fact, Map<String, String>  params, String priceProperty)  {
		String modifyMethod = params.get(MODIFY_PRICE_METHOD);
		String modifyValue = params.get(MODIFY_PRICE_VALUE);
		
		BigDecimal oldPrice = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.PRICE);
		BigDecimal price = BizFunctionUtil.getBigDecimal(fact, priceProperty);
		BigDecimal val = new BigDecimal(modifyValue);
		BigDecimal newPrice = null;
		if(FIX_PRICE.equals(modifyMethod)) {
			newPrice = val;
		} else if(SUB_PRICE.equals(modifyMethod) && price != null){
			newPrice = price.subtract(val);
		} else if(APPOINT_PERCENT.equals(modifyMethod) && price != null) {
			newPrice = price.multiply(val).divide(new BigDecimal(100));
			newPrice = newPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		newPrice = getCorrectPrice(newPrice, price);
		newPrice = roundDecimalNumber(newPrice, params.get(DECIMAL_ROUND_MODE));
		
		String itemType = BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_TYPE);
		//若影票, 判断是否要修正价格
		if(BizFactConstants.FILM_ITEM.equals(itemType) ) {
			newPrice = adjustPriceWhenLessThenLowest(fact, newPrice, params);
		}
		BeanUtils.setProperty(fact, BizFactConstants.PRICE, newPrice);
		BeanUtils.setProperty(fact, BizFactConstants.OLD_PRICE, oldPrice);
	}
	
	/**
	 * 若优惠的是影票, 判断在低于最低发行价时, 是否要调整价格和添加影院补贴.优惠券才
	 * @return
	 */
	protected BigDecimal adjustPriceWhenLessThenLowest(Object fact, BigDecimal newPrice, Map<String, String>  params) {
		BigDecimal lowestPrice = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.LOW_PRICE);
		//若价格小于最低发行价
		if(newPrice.compareTo(lowestPrice) < 0) {
			addCinemaPayAfterModifyPrice(params, fact, newPrice);
			newPrice = lowestPrice;
		}
		return newPrice;
	}
	
	/**
	 * 添加影院补贴
	 * @param params
	 * @param fact
	 * @param newPrice
	 */
	protected void addCinemaPayAfterModifyPrice(Map<String, String>  params, Object fact, BigDecimal newPrice) {
		BigDecimal lowestPrice = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.LOW_PRICE);
		String payer = params.get(PAYER);
		//只有优惠券才会设置本参数
		if(CINEMA.equals(payer)) {
			String cinemaPayStr = params.get(PAYER_PAY_AMOUNT);
			BigDecimal cinemaPay = !BlankUtil.isBlank(cinemaPayStr) ? new BigDecimal(cinemaPayStr) : BigDecimal.ZERO;
			BigDecimal delta = lowestPrice.subtract(newPrice);
			//若差额小于最大限额, 只需要补贴差额
			if(delta.compareTo(cinemaPay) < 0) {
				cinemaPay = delta;
			}
			BeanUtils.setProperty(fact, BizFactConstants.CINEMA_PAY_AMOUNT, cinemaPay);
		}
	}
}
