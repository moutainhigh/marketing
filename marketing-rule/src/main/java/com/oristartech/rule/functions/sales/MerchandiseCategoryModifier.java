package com.oristartech.rule.functions.sales;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.constants.RuleConstans;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 卖品分类优惠价调整
 * @author chenjunfei
 *
 */
public class MerchandiseCategoryModifier extends AbstractModifyPriceFunction {
	//选择分类方式
	private static final String MER_CATEGORY_METHOD = "merCategoryMethod";
	//指定分类
	private static final String APPOINT_CATEGORY = "appointCategory";
	//全部分类
	private static final String ALL_MER_CATEGORY = "allMerCategory";
	
	//分类
	private static final String MER_CATEGORIES = "classCode";
	
	//调价方式
	private static final String MODIFY_WAY_PARAM = "modifyWay";
	//调价值
	private static final String MODIFY_VALUE_PARAM = "modifyValue";
	
	//固定金额
	private static final String MODIFY_WAY_FIX = "fixPrice";
	//零售价打折
	private static final String MODIFY_VALUE_DISCOUNT = "discountPrice";
	//零售价-N元
	private static final String MODIFY_VALUE_SUB = "subPrice";
	
	//调整数量方式
	private static final String MODIFY_AMOUNT_METHOD = "modifyAmountMethod";
	private static final String ALL_MODIFY_AMOUNT = "all";
	private static final String APPOINT_MODIFY_AMOUNT = "appointAmount";
	
	//数量
	private static final String MODIFY_AMOUNT_PARAM = "modifyAmount";
	
	//折扣后小数调整模式参数名称
	protected static final String DECIMAL_ROUND_MODE_PARAM = "decimalRoundMode";
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO, RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		RuleActionResult result =  prepareRuleActionResult(actionVO);
		RuleResult ruleResult = context.getRuleResult(ruleVO.getId());
		List<Object> merFacts = context.cloneFactsByType(BizFactConstants.MER_INFO);
		
		log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId() + " params=" + JsonUtil.objToJson(params)
			+ " merFacts=" + JsonUtil.objToJson(merFacts));
		
		if(BlankUtil.isBlank(merFacts)) {
			log.info("<run> merFacts=null");
			return null;
		}
		
		String modifyWay = getModifyWay(params);
		BigDecimal modifyValue = getModifyValue(params);
		Set<String> merCategories = getModifyMerCategoryes(params);
		BigDecimal modifyAmount = getModifyAmount(params, acContext);
		
		//优先改价格高的
		super.sortSaleFact(merFacts);
		
		for(Object fact : merFacts) {
			validateMerFact(fact);
			String merClassCode = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_CLASS_CODE);
			if(BlankUtil.isBlank(merClassCode) || (merCategories != null && !merCategories.contains(merClassCode))) {
				continue;
			}
			
			Map<String, Object> resultFact = modifyMerItem(acContext, modifyWay, modifyValue, modifyAmount, params, fact, ruleResult);
			if(resultFact != null) {
				result.getFnResults().add(resultFact);
				BigDecimal alreadyModifyAmount = (BigDecimal)resultFact.get(BizFactConstants.AMOUNT);
				modifyAmount = modifyAmount.subtract(alreadyModifyAmount);
				if(modifyAmount.compareTo(BigDecimal.ZERO) <= 0) {
					//已经调整完
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取调整方式
	 * @param params
	 * @return
	 */
	protected String getModifyWay(Map<String, String> params) {
		String modifyWay = params.get(MODIFY_WAY_PARAM);
		if(MODIFY_VALUE_DISCOUNT.equals(modifyWay) || MODIFY_VALUE_SUB.equals(modifyWay)) {
			return modifyWay;
		}
		throw new RuleExecuteRuntimeException("需要设置调整价格方式");
	}
	
	/**
	 * 获取调整值
	 * @param params
	 * @return
	 */
	protected BigDecimal getModifyValue(Map<String, String> params) {
		String modifyValue = params.get(MODIFY_VALUE_PARAM);
		if(!BlankUtil.isBlank(modifyValue)) {
			return new BigDecimal(modifyValue);
		}
		throw new RuleExecuteRuntimeException("需要设置调整值");
	}
	
	/**
	 * 获取调整分类, 若返回null,表示调整所有
	 * @param params
	 * @return
	 */
	private Set<String> getModifyMerCategoryes(Map<String, String> params) {
		String merCategoryMethod = params.get(MER_CATEGORY_METHOD);
		
		if(ALL_MER_CATEGORY.equals(merCategoryMethod)) {
			return null ;
		} else if(APPOINT_CATEGORY.equals(merCategoryMethod)) {
			//category 是逗号分隔的字符串
			String merCategories = params.get(MER_CATEGORIES);
			if(BlankUtil.isBlank(merCategories)) {
				throw new RuleExecuteRuntimeException("需要设置卖品分类");
			}
			String[] categories = merCategories.split(RuleConstans.RULE_PARAMETER_SPLITER);
			Set<String> result = new HashSet<String>();
			for(String cat : categories) {
				result.add(cat);
			}
			return result;
		}
		//都不是则设置分类方式
		throw new RuleExecuteRuntimeException("需要设置选择分类方式");
	}
	
	protected Map<String, Object> modifyMerItem(ActionExecuteContext acContext, 
			                                  String modifyWay, 
			                                  BigDecimal modifyValue, 
			                                  BigDecimal modifyAmount, 
			                                  Map<String, String> params ,
			                                  Object fact, 
			                                  RuleResult ruleResult) {
		
		String key = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
		BigDecimal itemAmount = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.AMOUNT);
		
		if(modifyAmount.compareTo(itemAmount) > 0 ) {
			modifyAmount = itemAmount;
		}
		
		//是否已经修改过
		Object modifiedItem = ruleResult.getModifiedFact(key);
		if(modifiedItem != null) {
			return null;
		}

		Map<String, Object> resultFact = BizFunctionUtil.getSimpleSaleFactForModify(fact);
		
		BigDecimal newPrice = calNewPrice(modifyWay, modifyValue, fact ,params);
		newPrice = roundDecimalNumber(newPrice, params.get(DECIMAL_ROUND_MODE_PARAM));
		
		resultFact.put(BizFactConstants.PRICE, newPrice);
		resultFact.put(BizFactConstants.AMOUNT, modifyAmount);
		splitSaleItemKeys(resultFact, modifyAmount, null);
		
		//记录修改过的fact
		Map<String, Object> newModifiedItem = BizFunctionUtil.getSaleFactForRecordModified(fact);
		newModifiedItem.put(BizFactConstants.AMOUNT, modifyAmount);
		newModifiedItem.put(BizFactConstants.SALE_ITEM_KEY, resultFact.get(BizFactConstants.SALE_ITEM_KEY));
		ruleResult.addModifiedFact(key, newModifiedItem);
		
		return resultFact;
	}
	
	/**
	 * 计算新价格
	 * @param modifyWay
	 * @param modifyValue
	 * @param fact
	 * @return
	 */
	protected BigDecimal calNewPrice(String modifyWay, BigDecimal modifyValue , Object fact, Map<String, String> params) {
		BigDecimal oldPrice = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.PRICE);
		
		BigDecimal newPrice = null;
		
		if(MODIFY_VALUE_DISCOUNT.equals(modifyWay)){
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
	 * 获取调整数量
	 * @param params
	 * @return
	 */
	protected BigDecimal getModifyAmount(Map<String, String> params, ActionExecuteContext acContext) {
		String modifyAmountMethod = params.get(MODIFY_AMOUNT_METHOD);
		if(ALL_MODIFY_AMOUNT.equals(modifyAmountMethod)) {
			return new BigDecimal(Integer.MAX_VALUE);
		} else if(APPOINT_MODIFY_AMOUNT.equals(modifyAmountMethod)) {
			String modifyAmount = params.get(MODIFY_AMOUNT_PARAM);
			if(BlankUtil.isBlank(modifyAmount)) {
				throw new RuleExecuteRuntimeException("需要设置卖品修改数量");
			}
			Integer amount = Integer.valueOf(modifyAmount);
			
			//因为本方法根据影票条件可以多次运行,所以runTimes在本方法有意义
			Integer runTimes = acContext.getRunTimes() == null ? 1 : acContext.getRunTimes();
			amount *= runTimes;
			return new BigDecimal(amount);
		}
		
		throw new RuleExecuteRuntimeException("需要设置卖品修改数量方式");
	}
}
