package com.oristartech.rule.core.functions.sales.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.constants.RuleConstans;
import com.oristartech.rule.core.executor.util.RuleExecutorDataHelper;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;

/**
 * 销售类事实工具类
 * @author chenjunfei
 *
 */
public class SaleFactsUtil {
	/**
	 * 规则条件影票商品数量
	 */
	private static final String RULE_FILM_MAX_AMOUNT_KEY = "ruleFilmMaxAmountKey_";
	/**
	 * 规则条件小卖商品数量
	 */
	private static final String RULE_MER_MAX_AMOUNT_KEY = "ruleMerMaxAmountKey_";

	/**
	 * 影票可以计算倍数的值
	 */
	private static final String RULE_MULTI_FILM_AMOUNT_KEY = "ruleMultiFilmAmountKey_";
	
	/**
	 * 卖品可以计算倍数的值
	 */
	private static final String RULE_MULTI_MER_AMOUNT_KEY = "ruleMultiMerAmountKey_";
	
	/**
	 * 数值倍数符号
	 */
	private static final String MULTI_TIME_OPERATE = ">=";
	
	/**
	 * 获取多次执行的次数， 即不需要经过多次匹配，一次匹配就要计算出执行方法要运行多次
	 * @return
	 */
	public static int getMultiExeTimes(RuleVO ruleVO, RuleExecuteContext context, List<Object> matchFactWithAllFields) {
		//默认一次
		int times = 1;
		if(ruleVO != null) {
			Integer maxFilmAmountCon = getMultiTimeFilmAmountFromRule(ruleVO, context);
			Map<String, Integer> maxMerAmountCons = getMultiTimeMerAmountFromRule(ruleVO, context);
			List<Integer> multiples = new ArrayList<Integer>();
			if(maxFilmAmountCon != null ) {
				Integer factFilmAmount = getFilmAmountFromFact(matchFactWithAllFields);
				multiples.add(factFilmAmount/maxFilmAmountCon);
			}
			
			if(!BlankUtil.isBlank(maxMerAmountCons)) {
				Map<String, Integer> merAmounts = getMerAmountFromFact(maxMerAmountCons, matchFactWithAllFields);
				if(!BlankUtil.isBlank(merAmounts)) {
					for(String merKey : merAmounts.keySet()) {
						Integer amount = merAmounts.get(merKey);
						multiples.add(amount / maxMerAmountCons.get(merKey));
					}
				}
			}
			
			if(multiples.size() > 0) {
				Collections.sort(multiples);
				times = multiples.get(0);
			}
		}
		
		return times;
	}
	
	/**
	 * 设置规则消耗事实.
	 * @param result
	 * @param context
	 */
	public static void setConsumeFacts(RuleResult result, RuleExecuteContext context) {
		RuleVO ruleVO = RuleExecutorDataHelper.getRuleVO(context, result.getRuleVO().getId());
		
		Map<String, Integer> amountMap = initAmountMap();
		
		//先把修改的结果事实,变为消耗事实
		if(!BlankUtil.isBlank(result.getResults())) {
			addConsumeFactsInActionResult(result, amountMap);
		}
		addMatchFactsForContidion(result, context, amountMap);
		addOtherFilmConsumes(result, context, ruleVO, amountMap);
		//若有影票, 暂时实现是消耗所有影票
		if(amountMap.get(BizFactConstants.FILM_ITEM) != null && amountMap.get(BizFactConstants.FILM_ITEM) > 0) {
			addAllFilmConsumeFact(result, context);
		}
		if(BlankUtil.isBlank(result.getConditionFacts())) {
			//当条件,方法都没有票和卖品消耗时, 消耗所有.所以要在先把修改事实,匹配事实消耗后调用
			amountMap.clear();
			addConsumeFacts(context.getAllFacts(), result);
		} 
		initConditionFactTypes(result, amountMap);
	}
	
	/**
	 *  设置消耗条件类型
	 * @param result
	 * @param existAmountMap
	 */
	private static void initConditionFactTypes(RuleResult result, Map<String, Integer> amountMap) {
		//消耗事实类型列表
		Set<String> conditionFactTypes = new HashSet<String>();
		//消耗数量空,表示消耗所有
		if(BlankUtil.isBlank(amountMap) || 
			((amountMap.get(BizFactConstants.FILM_ITEM) == null || amountMap.get(BizFactConstants.FILM_ITEM) == 0)
			 && (amountMap.get(BizFactConstants.MER_ITEM) == null || amountMap.get(BizFactConstants.MER_ITEM) == 0)		
		    )) {
			conditionFactTypes.add(BizFactConstants.FILM_ITEM) ;
			conditionFactTypes.add(BizFactConstants.MER_ITEM);
		}
		if(amountMap.get(BizFactConstants.FILM_ITEM) != null && amountMap.get(BizFactConstants.FILM_ITEM) > 0) {
			conditionFactTypes.add(BizFactConstants.FILM_ITEM) ;
		}
		if(amountMap.get(BizFactConstants.MER_ITEM) != null && amountMap.get(BizFactConstants.MER_ITEM) > 0) {
			conditionFactTypes.add(BizFactConstants.MER_ITEM) ;
		}
		result.setConditionFactTypes(conditionFactTypes);
		
	}
	
	/**
	 * 初始化消耗数量map,暂时的实现是若有消耗,消耗所有同种东西
	 * @return
	 */
	private static Map<String, Integer> initAmountMap() {
		//map的key是商品类型, value是数量
		Map<String, Integer> amountMap = new HashMap<String, Integer>();
		
		amountMap.put(BizFactConstants.FILM_ITEM, 0);
		amountMap.put(BizFactConstants.MER_ITEM, 0);
		return amountMap;
		
	}
	private static void addAllFilmConsumeFact(RuleResult result, RuleExecuteContext context) {
		List<Object> facts = context.getAllFacts();
		if(!BlankUtil.isBlank(facts)) {
			for(Object fact : facts) {
				String itemType = BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_TYPE);
				if(BizFactConstants.FILM_ITEM.equals(itemType)) {
					addConsumeFilmFact(result, fact);
				}
			}
		}
	}
	/**
	 * 计算条件需要的其他销售类商品. 只拿不能简单匹配影票,小卖对象的条件. 如销售类中的影票数量, 因为影票数量不在影票事实中
	 * 
	 * @param ruleVO
	 * @param allFacts
	 */
	private static void addOtherFilmConsumes(RuleResult result, RuleExecuteContext context, RuleVO ruleVO, Map<String, Integer> existAmountMap) {
		if(ruleVO != null) {
			//获取影票数量条件
			Integer maxFilmAmountCon = getMaxFilmAmountFromRule(ruleVO, context);
			if(maxFilmAmountCon != null && maxFilmAmountCon > 0 ) {
				setConsumeFactWithSaleFact(result, context, BizFactConstants.FILM_INFO, null, maxFilmAmountCon);
				existAmountMap.put(BizFactConstants.FILM_ITEM, existAmountMap.get(BizFactConstants.FILM_ITEM) + maxFilmAmountCon);
			}
		}
	}
	
	/**
	 * 把方法结果的消耗事实,添加到规则消耗事实中.
	 * @param result
	 * @return 返回商品类型和对应数量
	 */
	private static void addConsumeFactsInActionResult(RuleResult result, Map<String, Integer> existAmountMap) {
		//不获取方法结果,
		//直接把修改的商品添加到消耗条件中, 所以方法应该把修改的物品放到该集合中;
		Map<String, Integer> matchSale = addConsumeFacts(result.getModifiedFacts(), result);
		mergeAmountMap(matchSale, existAmountMap);
    }
	
	/**
	 * 把匹配条件的的销售相关事实添加到消耗中.
	 * @param result
	 * @return 返回商品类型和对应数量
	 */
	private static void addMatchFactsForContidion(RuleResult result, RuleExecuteContext context, Map<String, Integer> existAmountMap) {
		Map<String, Integer> matchSale = addConsumeFacts(result.getMatchFacts(), result);
		Map<String, Integer> matchStatis = addStatisMatchMerToConsume(result, context);
		mergeAmountMap(matchStatis, existAmountMap);
		mergeAmountMap(matchSale, existAmountMap);
    }
	
	private static void mergeAmountMap(Map<String, Integer> source, Map<String, Integer> destMap) {
		if(!BlankUtil.isBlank(source)) {
			for(String key : destMap.keySet()) {
				Integer amount = source.get(key);
				amount = amount == null ? 0 : amount;
				destMap.put(key, amount + destMap.get(key));
			}
		}
	}
	/**
	 * 把集合中的事实发到消耗集合中
	 * @param facts
	 * @param result
	 * @return
	 */
	private static Map<String, Integer> addConsumeFacts(Collection<Object> facts, RuleResult result) {
		Integer filmAmount = 0;
		Integer merAmount = 0;
		Map<String , Integer> amountMap = new HashMap<String, Integer>();
		if(!BlankUtil.isBlank(facts)) {
			for(Object modifyFact : facts) {
				//只添加销售类型事实
	    		String type = BeanUtils.getPropertyStr(modifyFact, BizFactConstants.SALE_ITEM_TYPE);
	    		if(BizFactConstants.FILM_ITEM.equals(type)) {
	    			filmAmount += addConsumeFilmFact(result, modifyFact);
	    		} else if(BizFactConstants.MER_ITEM.equals(type)) {
	    			merAmount += addConsumeMerFact(result, modifyFact);
	    		} 
	    	}
		}
		amountMap.put(BizFactConstants.FILM_ITEM, filmAmount);
		amountMap.put(BizFactConstants.MER_ITEM, merAmount);
		return amountMap;
	}
	
	/**
	 * 
	 * @param maxMerAmountCons
	 * @param facts
	 * @return
	 */
	private static Map<String, Integer> getMerAmountFromFact(Map<String, Integer> maxMerAmountCons, List<Object> facts) {
		Map<String, Integer> merAmounts = new HashMap<String, Integer>();
		if(!BlankUtil.isBlank(facts) && !BlankUtil.isBlank(maxMerAmountCons)) {
			for(Object fact : facts) {
				String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
				if(BizFactConstants.MER_INFO.equals(type)) {
					BigDecimal amount = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.AMOUNT);
					String merKey = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_KEY);
					if(maxMerAmountCons.containsKey(merKey) && amount != null) {
						merAmounts.put(merKey, amount.intValue());
					}
				}
			}
		}
		return merAmounts;
	}
	
	/**
	 * 获取规则定义中的影票数量 
	 * @param facts
	 * @return
	 */
	private static Integer getFilmAmountFromFact(List<Object> facts) {
		if(!BlankUtil.isBlank(facts)) {
			for(Object fact : facts) {
				String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
				if(BizFactConstants.SALE_INFO.equals(type)) {
					String amountStr = BeanUtils.getPropertyStr(fact, BizFactConstants.FILM_TICKET_AMOUNT);
					if(!BlankUtil.isBlank(amountStr)) {
						return Integer.valueOf(amountStr);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取商品统计类匹配的指向的商品
	 * @param result
	 * @param context
	 * @return 是否存在这样的商品
	 */
	private static Map<String, Integer> addStatisMatchMerToConsume(RuleResult result, RuleExecuteContext context) {
		Integer filmAmount = 0;
		Integer merAmount = 0;
		Map<String , Integer> amountMap = new HashMap<String, Integer>();
		
		Collection<Object> facts = result.getMatchFacts();
		if(!BlankUtil.isBlank(facts)) {
			for(Object fact : facts) {
				String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
				if(BizFactConstants.MER_STATIS_INFO.equals(type)) {
					Map<String, Object> matchProps = new HashMap<String, Object>();
					String classKey = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_CLASS_CODE);
					String blandKey = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_BRAND_ID);
					if(!BlankUtil.isBlank(classKey)) {
						matchProps.put( BizFactConstants.MER_CLASS_CODE, classKey);
					}
					if(!BlankUtil.isBlank(blandKey)) {
						matchProps.put( BizFactConstants.MER_BRAND_ID, blandKey);
					}
					merAmount += addConsumeMersInCon(result, matchProps ,context.getAllCloneFacts() );
				}
			}
		}
		amountMap.put(BizFactConstants.FILM_ITEM, filmAmount);
		amountMap.put(BizFactConstants.MER_ITEM, merAmount);
		return amountMap;
	}
	
	/**
	 * 返回添加的数量
	 * @param result
	 * @param matchProps
	 * @param facts
	 * @return
	 */
	private static int addConsumeMersInCon(RuleResult result, Map<String, Object> matchProps, Collection<Object> facts) {
		if(!BlankUtil.isBlank(matchProps)) {
			for(Object fact : facts) {
				String type = BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_KEY);
				if(BizFactConstants.MER_ITEM.equals(type)) {
					boolean match = true;
					for(String key : matchProps.keySet()) {
						Object value = BeanUtils.getPropertyStr(fact, key);
						if(!matchProps.get(key).equals(value)) {
							match = false;
						}
					}
					if(match) {
						return addConsumeMerFact(result, fact);
					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * 获取规则定义中的商品key及数量, map中的key是商品key
	 * @param ruleVO
	 * @return
	 */
	private static Map<String, Integer> getMaxMerAmountFromRule(RuleVO ruleVO, RuleExecuteContext context) {
		String cacheKey = RULE_MER_MAX_AMOUNT_KEY  + ruleVO.getId();
		Object cacheValue = context.getContextManager().getCacheDataByClz(SaleFactsUtil.class, cacheKey);
		if(cacheValue != null) {
			return (Map<String, Integer>) cacheValue;
		}
		
		List<RuleConditionVO> merCons = ruleVO.findConditions(BizFactConstants.MER_INFO);
		Map<String, Integer> merAmountMap = new HashMap<String, Integer>();
		if(!BlankUtil.isBlank(merCons)) {
			for(RuleConditionVO conVO : merCons) {
				RuleConditionElementVO merKeyEle = conVO.findElementVO(BizFactConstants.MER_KEY);
				RuleConditionElementVO amountEle = conVO.findElementVO(BizFactConstants.AMOUNT);
				if(merKeyEle != null && amountEle != null) {
					String merKey = merKeyEle.getOperand();
					Integer merAmount = Integer.valueOf(amountEle.getOperand()) ; 
					Integer existAmount = merAmountMap.get(merKey);
					if(existAmount == null || merAmount > existAmount) {
						merAmountMap.put(merKey, merAmount);
					}
				}
			}
		}
		context.getContextManager().cacheByClz(SaleFactsUtil.class, cacheKey, merAmountMap);
		
		return merAmountMap;
	}
	
	/**
	 * 获取小卖数计算影票优惠倍数基数
	 * @param ruleVO
	 * @return
	 */
	private static Map<String, Integer> getMultiTimeMerAmountFromRule(RuleVO ruleVO, RuleExecuteContext context) {
		String cacheKey = RULE_MULTI_MER_AMOUNT_KEY  + ruleVO.getId();
		Object cacheValue = context.getContextManager().getCacheDataByClz(SaleFactsUtil.class, cacheKey);
		if(cacheValue != null) {
			return (Map<String, Integer>) cacheValue;
		}
		
		List<RuleConditionVO> merCons = ruleVO.findConditions(BizFactConstants.MER_INFO);
		Map<String, Integer> merAmountMap = new HashMap<String, Integer>();
		if(!BlankUtil.isBlank(merCons)) {
			for(RuleConditionVO conVO : merCons) {
				RuleConditionElementVO merKeyEle = conVO.findElementVO(BizFactConstants.MER_KEY);
				RuleConditionElementVO amountEle = conVO.findElementVO(BizFactConstants.AMOUNT);
				if(merKeyEle != null && amountEle != null) {
					String merKey = merKeyEle.getOperand();
					Integer merAmount = Integer.valueOf(amountEle.getOperand()) ;
					String opCode = amountEle.getOpCode();
					Integer existAmount = merAmountMap.get(merKey);
					if((existAmount == null ||  merAmount > existAmount) && opCode.equals(MULTI_TIME_OPERATE)) {
						merAmountMap.put(merKey, merAmount);
					} else if(!opCode.equals(MULTI_TIME_OPERATE)) {
						//若含有不是倍数比较符号, 立即返回null,取消倍数计算
						merAmountMap = new HashMap<String, Integer>() ;
						break;
					}
				}
			}
		}
		context.getContextManager().cacheByClz(SaleFactsUtil.class, cacheKey, merAmountMap);
		
		return merAmountMap;
	}
	
	/**
	 * 获取最大影票数
	 * @param ruleVO
	 * @return
	 */
	private static Integer getMaxFilmAmountFromRule(RuleVO ruleVO, RuleExecuteContext context) {
		String cacheKey = RULE_FILM_MAX_AMOUNT_KEY  + ruleVO.getId();
		Object cacheValue = context.getContextManager().getCacheDataByClz(SaleFactsUtil.class, cacheKey);
		if(cacheValue != null) {
			return (Integer) cacheValue;
		}
		List<RuleConditionElementVO> eles = ruleVO.findConditionElements(BizFactConstants.SALE_INFO, BizFactConstants.FILM_TICKET_AMOUNT);
		Integer max = null;
		try {
			//只有一个大于等于才需要这样
			if(!BlankUtil.isBlank(eles) && eles.size() == 1 ) {
				max = 0;
				for(RuleConditionElementVO eleVO : eles) {
					String value = eleVO.getOperand();
					if(!BlankUtil.isBlank(value)) {
						int val = Integer.valueOf(value);
						if(val > max) {
							max = val;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuleExecuteRuntimeException(e);
		}
		context.getContextManager().cacheByClz(SaleFactsUtil.class, cacheKey, max);
		return max;
	}
	
	/**
	 * 获取影票数计算影票优惠倍数基数
	 * @param ruleVO
	 * @return
	 */
	private static Integer getMultiTimeFilmAmountFromRule(RuleVO ruleVO, RuleExecuteContext context) {
		String cacheKey = RULE_MULTI_FILM_AMOUNT_KEY  + ruleVO.getId();
		Object cacheValue = context.getContextManager().getCacheDataByClz(SaleFactsUtil.class, cacheKey);
		if(cacheValue != null) {
			return (Integer) cacheValue;
		}
		List<RuleConditionElementVO> eles = ruleVO.findConditionElements(BizFactConstants.SALE_INFO, BizFactConstants.FILM_TICKET_AMOUNT);
		Integer result = null;
		try {
			//只有一个大于等于才需要这样
			if(!BlankUtil.isBlank(eles) && eles.size() == 1 ) {
				RuleConditionElementVO eleVO = eles.get(0);
				String value = eleVO.getOperand();
				String opCode = eleVO.getOpCode();
				if(!BlankUtil.isBlank(value) && opCode.equals(MULTI_TIME_OPERATE)) {
					result = Integer.valueOf(value);
				}
			}
		} catch (Exception e) {
			throw new RuleExecuteRuntimeException(e);
		}
		context.getContextManager().cacheByClz(SaleFactsUtil.class, cacheKey, result);
		return result;
	}
	
	/**
	 * 根据数量和key设置消耗商品条件. 现在不管数量
	 * @param result
	 * @param context
	 * @param factType 商品类型
	 * @param factKey  商品key,影票可null.
	 * @param amount
	 */
	private static void setConsumeFactWithSaleFact(RuleResult result, RuleExecuteContext context, String factType, String factKey, Integer amount) {
		List<Object> facts = context.getAllFacts();
		if(BlankUtil.isBlank(facts)) {
			return;
		}
		for(Object fact : facts) {
			String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
			if(!factType.equals(type)) {
				continue;
			}
			String merKey = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_KEY);
			if(BizFactConstants.FILM_INFO.equals(type) ) {
				addConsumeFilmFact(result, fact);
			} else if(BizFactConstants.MER_INFO.equals(type) && factKey.equals(merKey)) {
				//若是小卖,需要对比merKey
				addConsumeMerFact(result, fact);
			}
		}
	}
	
	//影票fact
	private static int addConsumeFilmFact(RuleResult result, Object fact) {
		String key = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
		Map<String, Object> consumeFact = new HashMap<String, Object>();
		String saleItemKey = BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_KEY);
		if(!BlankUtil.isBlank(saleItemKey) && !result.existConditionFact(key)) {
			consumeFact.put(BizFactConstants.SALE_ITEM_KEY, saleItemKey);
			consumeFact.put(BizFactConstants.SALE_ITEM_TYPE, BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_TYPE));
			consumeFact.put(BizFactConstants.MER_KEY, BeanUtils.getPropertyStr(fact, BizFactConstants.MER_KEY));
			consumeFact.put(FactConstants.CATEGORY_NUM_KEY, BeanUtils.getProperty(fact, FactConstants.CATEGORY_NUM_KEY));
			result.addConditionFact(consumeFact, key);
			return 1;
		}
		return 0;
	}
	
	//商品fact
	private static int addConsumeMerFact(RuleResult result, Object fact) {
		String key = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
		//购物车的item, key, 若是多个相同卖品, 用","号分隔item key 
		String saleItemKey = BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_KEY);
		String merKey = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_KEY);
		if(!BlankUtil.isBlank(saleItemKey)) {
			String[] itemKeys = saleItemKey.split(RuleConstans.RULE_PARAMETER_SPLITER);
			for(int i=0 ; i < itemKeys.length ; i++) {
				String itemKey = itemKeys[i];
				String mapKey = key + "_" + itemKey;
				if(result.existConditionFact(mapKey)) {
					continue;
				}
				Map<String, Object> consumeFact = new HashMap<String, Object>();
				consumeFact.put(BizFactConstants.SALE_ITEM_KEY, itemKey);
				consumeFact.put(BizFactConstants.SALE_ITEM_TYPE, BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_TYPE));
				consumeFact.put(BizFactConstants.MER_KEY, merKey);
				consumeFact.put(FactConstants.CATEGORY_NUM_KEY, BeanUtils.getProperty(fact, FactConstants.CATEGORY_NUM_KEY));
				result.addConditionFact(consumeFact, mapKey);
			}
			return itemKeys.length;
		}
		return 0;
	}
}
