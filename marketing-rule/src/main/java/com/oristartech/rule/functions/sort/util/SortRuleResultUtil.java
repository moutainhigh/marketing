package com.oristartech.rule.functions.sort.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

public class SortRuleResultUtil {
	private static final String DEFAULT_PRICING_CATEGORY_ACTION = "Pricing"; 
	private static final String DEFAULT_PRICING_CONFIG_NAME = "PRICING_ACTION_CATEGORY_NAME"; 
	
	//影院补贴
	private static final String CINEMA_PAY_AMOUNT = "cinemaPayAmount";
	
	private static String pricingAction = DEFAULT_PRICING_CATEGORY_ACTION;
	
	public static List<ResultsConsumeFactGroup> sortLowestPriceInGroup(List<ResultsConsumeFactGroup> groups, List<Object> saleItems) {
		if(BlankUtil.isBlank(groups)) {
			return null;
		}
		
		if(!BlankUtil.isBlank(groups) && groups.size() == 1 ) {
			return groups;
		}
		
		List<ResultsConsumeFactGroup> finalResults = new ArrayList<ResultsConsumeFactGroup>();
		
		List<RuleResultGroupAmountPair> resultGroupAmountPairs = composeResultGroupsAmountPair(groups, saleItems);
		//排序有计价方法规则
		Collections.sort(resultGroupAmountPairs, new Comparator<RuleResultGroupAmountPair>() {
			public int compare(RuleResultGroupAmountPair o1, RuleResultGroupAmountPair o2) {
			    return o1.getAmount().compareTo(o2.getAmount());
			}
		});
		
		for(RuleResultGroupAmountPair resultPair : resultGroupAmountPairs) {
			finalResults.add(resultPair.getGroup());
		}
		
		return finalResults;
	}
	
	/**
	 * 获取两两比较最优惠的规则
	 * @param finalResults
	 * @param results
	 * @param saleItems
	 * @return
	 */
	public static List<RuleResult> sortLowestPriceResult( List<RuleResult> results, List<Object> saleItems ) {
		if(BlankUtil.isBlank(saleItems) || (results.size() == 1) ) {
			return results;
		}
		List<RuleResult> finalResults = new ArrayList<RuleResult>();
		List<RuleResult> noPricingResults = filterNoPricingResult(results);
		
		if(!BlankUtil.isBlank(results)) {
			List<RuleResultAmountPair> resultAmountPairs = composeResultAmountPair(results, saleItems);
			//排序有计价方法规则
			Collections.sort(resultAmountPairs, new Comparator<RuleResultAmountPair>() {
				public int compare(RuleResultAmountPair o1, RuleResultAmountPair o2) {
				    return o1.getAmount().compareTo(o2.getAmount());
				}
			});
			
			//需求改变, 返回所有结果, 所以只返回排序后的结果;
			for(RuleResultAmountPair resultPair : resultAmountPairs) {
				finalResults.add(resultPair.getResult());
			}
			
			//只取最优惠的值
//			BigDecimal minAmount = new BigDecimal(Integer.MAX_VALUE);
//			for(RuleResultAmountPair resultPair : resultAmountPairs) {
//				if(resultPair.getAmount().compareTo(minAmount) <=0 ) {
//					finalResults.add(resultPair.getResult());
//					minAmount = resultPair.getAmount();
//				} else {
//					//因为已排序, 所以只要有大于,就可以跳出了
//					break;
//				}
//			}
		}
		
		//把没调价的放最后
		if(!BlankUtil.isBlank(noPricingResults)) {
			finalResults.addAll(noPricingResults);
		}
		
		return finalResults;
	}
	
	/**
	 * 排序推荐的规则, 先按优先级, 优先级相同则价格优先
	 * @param results
	 * @param saleItems
	 * @return
	 */
	public static List<RuleResult> sortRecommendModeRule(List<RuleResult> results, List<Object> saleItems) {
		if(BlankUtil.isBlank(saleItems) || (results.size() == 1) ) {
			return results;
		}
		List<RuleResult> finalResults = new ArrayList<RuleResult>();
		
		if(!BlankUtil.isBlank(results)) {
			List<RuleResultAmountPair> resultAmountPairs = composeResultAmountPair(results, saleItems);
			//排序
			Collections.sort(resultAmountPairs, new Comparator<RuleResultAmountPair>() {
				public int compare(RuleResultAmountPair o1, RuleResultAmountPair o2) {
				    int delta = o2.getResult().getRuleVO().getPriority() - o1.getResult().getRuleVO().getPriority();
				    if(delta == 0) {
				    	//优先级相同则价格优先
				    	return o1.getAmount().compareTo(o2.getAmount());
				    }
				    return delta;
				}
			});
			
			//需求改变, 返回所有结果, 所以只返回排序后的结果;
			for(RuleResultAmountPair resultPair : resultAmountPairs) {
				finalResults.add(resultPair.getResult());
			}
		}
		return finalResults;
	}
	
	/**
	 * 为每一个计价规则计算优惠后总价
	 * @param results
	 * @return
	 */
	private static List<RuleResultGroupAmountPair> composeResultGroupsAmountPair(List<ResultsConsumeFactGroup> groups, List<Object> saleItems ) {
		List<RuleResultGroupAmountPair> resultAmounts = new ArrayList<RuleResultGroupAmountPair>();
		Map<String , Object > saleItemMap = convertToMapSaleItem(saleItems);
		for(ResultsConsumeFactGroup group : groups) {
			Map<String , Object > saleItemMapCopys = (HashMap<String , Object > )SerializationUtils.clone((HashMap<String , Object >)saleItemMap);
			for(RuleResult result : group.getResults()) {
				List<RuleActionResult> anResults = result.getResults();
				if(!BlankUtil.isBlank(anResults)) {
					modifySaleItems(saleItemMapCopys, anResults);
				}
			}
			
			BigDecimal sum = calSaleSum(saleItemMapCopys.values());
			RuleResultGroupAmountPair resultPair = new RuleResultGroupAmountPair();
			resultPair.setGroup(group);
			resultPair.setAmount(sum);
			resultAmounts.add(resultPair);
		}
		
		return resultAmounts;
	}
	
	/**
	 * 为每一个计价规则计算优惠后总价
	 * @param results
	 * @return
	 */
	private static List<RuleResultAmountPair> composeResultAmountPair(List<RuleResult> results, List<Object> saleItems ) {
		List<RuleResultAmountPair> resultAmounts = new ArrayList<RuleResultAmountPair>();
		Map<String , Object > saleItemMap = convertToMapSaleItem(saleItems);
		for(RuleResult result : results) {
			Map<String , Object > saleItemMapCopys = (HashMap<String , Object > )SerializationUtils.clone((HashMap<String , Object >)saleItemMap);
			List<RuleActionResult> anResults = result.getResults();
			if(!BlankUtil.isBlank(anResults)) {
				modifySaleItems(saleItemMapCopys, anResults);
			}
			BigDecimal sum = calSaleSum(saleItemMapCopys.values());
			RuleResultAmountPair resultPair = new RuleResultAmountPair();
			resultPair.setResult(result);
			resultPair.setAmount(sum);
			resultAmounts.add(resultPair);
		}
		
		return resultAmounts;
	}
	
	/**
	 * 把saleItem list 变为map,便于获取数据.key是num
	 * @param saleItems
	 * @return
	 */
	private static Map<String, Object> convertToMapSaleItem(List<Object> saleItems) {
		Map<String, Object> itemMap = new HashMap<String, Object>();
		for(Object item :  saleItems) {
			String itemNum = BeanUtils.getPropertyStr(item, FactConstants.CATEGORY_NUM_KEY);
			itemMap.put(itemNum, item);
		}
		return itemMap;
	}
	/**
	 * 过滤出没有计价方法的规则,这些规则全部返回, 会修改传入的results,去掉没计价方法的规则
	 * @param results
	 * @return 同时返回没有计价方法的规则
	 */
	private static List<RuleResult> filterNoPricingResult(List<RuleResult> results) {
		List<RuleResult> noPricingResults = new ArrayList<RuleResult>();
		Iterator<RuleResult> it = results.iterator();
		while(it.hasNext()) {
			RuleResult result = it.next();
			Collection<RuleActionResult> anResults = result.getResults();
			boolean hasPricing = false;
			if(!BlankUtil.isBlank(anResults)) {
				for(RuleActionResult anResult : anResults) {
					if(pricingAction.equals( anResult.getFnCategoryName())) {
						hasPricing = true;
						break;
					}
				}
			}
			if(hasPricing) {
				break;
			}
			if(!hasPricing) {
				noPricingResults.add(result);
				it.remove();
			}
		}
			
		return noPricingResults;
	}
	
	/**
	 * 修改卖品价格
	 * @param saleItemCopys
	 * @param actionResults
	 */
	private static void modifySaleItems(Map<String , Object > saleItemMap , List<RuleActionResult> actionResults) {
		for(RuleActionResult anResult : actionResults) {
			if(pricingAction.equals( anResult.getFnCategoryName()) && !BlankUtil.isBlank(anResult.getFnResults())) {
				for(Object obj : anResult.getFnResults()) {
					//修改的是原来传过来的fact才比较, 其他送,或添加的fact不比较
					Object isFact = BeanUtils.getProperty(obj, FactConstants.IS_FACT_KEY);
					if(isFact == null || ((Boolean)isFact) == false || !BizFunctionUtil.isSaleInfo(obj)) {
						continue;
					}
					String num = BeanUtils.getPropertyStr(obj, FactConstants.CATEGORY_NUM_KEY);
					BigDecimal payPrice = getClientPay(obj);
					BigDecimal amount = BizFunctionUtil.getBigDecimal(obj, BizFactConstants.AMOUNT);
					if(payPrice != null && amount != null) {
						Object srcSaleItem = saleItemMap.get(num);
						if(srcSaleItem == null) {
							continue;
						}
						BigDecimal itemAmount = BizFunctionUtil.getBigDecimal(srcSaleItem, BizFactConstants.AMOUNT);
						BeanUtils.setProperty(srcSaleItem, BizFactConstants.PRICE, payPrice);
						BeanUtils.setProperty(srcSaleItem, BizFactConstants.OLD_AMOUNT, itemAmount);
						if(amount.compareTo(itemAmount) >= 0 ) {
							continue;
						} else {
							//修改部分商品
							BeanUtils.setProperty(srcSaleItem, BizFactConstants.AMOUNT, amount);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取不用客户出的那一部分的数额, 例如影院补贴
	 * @param modifyFact
	 * @return
	 */
	private static BigDecimal getNotClientPay(Object modifyFact) {
		return BizFunctionUtil.getBigDecimal(modifyFact, CINEMA_PAY_AMOUNT, BigDecimal.ZERO);
	}
	
	/**
	 * 获取客户真正需要支付的数额.
	 * @param modifyFact
	 * @return
	 */
	private static BigDecimal getClientPay(Object modifyFact) {
		BigDecimal price = BizFunctionUtil.getBigDecimal(modifyFact, BizFactConstants.PRICE);
		if(price != null) {
			BigDecimal noNeedPay = getNotClientPay(modifyFact);
			BigDecimal result = price.subtract(noNeedPay);
			if(result.compareTo(BigDecimal.ZERO) <= 0) {
				return BigDecimal.ZERO;
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 计算卖品中所有总价
	 * @param saleItems
	 * @return
	 */
	private static BigDecimal calSaleSum(Collection<Object> saleItems) {
		BigDecimal sum = BigDecimal.ZERO;
		for(Object item : saleItems) {
			BigDecimal itemPrice = BizFunctionUtil.getBigDecimal(item, BizFactConstants.PRICE);
			BigDecimal oldPrice = BizFunctionUtil.getBigDecimal(item, BizFactConstants.OLD_PRICE);
			oldPrice = oldPrice == null ? itemPrice : oldPrice;
			
			BigDecimal amount = BizFunctionUtil.getBigDecimal(item, BizFactConstants.AMOUNT);
			BigDecimal oldAmount = BizFunctionUtil.getBigDecimal(item, BizFactConstants.OLD_AMOUNT);
			oldAmount = oldAmount == null ? amount : oldAmount;
			
			if(itemPrice != null) {
				BigDecimal modifySum = itemPrice.multiply(amount).add(oldPrice.multiply(oldAmount.subtract(amount)));
				sum = sum.add(modifySum);
			}
		}
		return sum;
	}
	
	/**
	 * 获取事实中的卖品
	 * @param context
	 * @return
	 */
	public static ArrayList<Object> getSaleItems(RuleExecuteContext context) {
		List<Object> allFacts = context.getAllCloneFacts();
		ArrayList<Object> saleItems = new ArrayList<Object>();
		for(Object obj : allFacts) {
			if(BizFunctionUtil.isSaleInfo(obj)) {
				BigDecimal amount = BizFunctionUtil.getBigDecimal(obj, BizFactConstants.AMOUNT); 
				BigDecimal price = BizFunctionUtil.getBigDecimal(obj, BizFactConstants.PRICE);
				if(price == null) {
					throw new RuleExecuteRuntimeException("卖品价格为空");
				}
				if(amount == null) {
					BeanUtils.setProperty(obj, BizFactConstants.AMOUNT, new BigDecimal("1"));
				}
				saleItems.add(obj);
			}
		}
		return saleItems;
	}
	
}
