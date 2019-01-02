package com.oristartech.rule.core.functions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.FactUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.search.RuleExeStatisCondition;
import com.oristartech.rule.vos.core.enums.RuleExeStatisConType;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.ResultRuleVO;

/**
 * 业务实体工具类
 * @author chenjunfei
 *
 */
public class BizFunctionUtil {
	
	//执行次数
	private final static String RUN_AMOUNT = "runAmount";
	
	//约束类型
	private final static String RUN_RESTRAIN_TYPE = "runRestrainType";
	
	//指定期限
	private final static String APPOINT_RUNRESTRAIN_RANGE = "appointRunRestrainRange";
	
	protected static final Logger log = LoggerFactory.getLogger(BizFunctionUtil.class);

	/**
	 * 修改影票方法标记
	 */
	public static final String FN_MODIFY_FILM_TICKET_PRICE = "modifyFilmTicketPrice";
	
	/**
	 * 修改商品标记
	 */
	public static final String FN_MODIFY_SALE_ITEM_PRICE = "modifySaleItemPrice";
	
	/**
	 * 创建规则执行次数限制查询条件
	 * @param bizMap
	 * @return
	 */
	public static RuleExeStatisCondition createStatCondition(ResultRuleVO ruleVO) {
		Map<String, Object> ruleBizMap = ruleVO.getBizPropertyMap();
		if(BlankUtil.isBlank(ruleBizMap)) {
			return null;
		}
		Object amountObj = ruleBizMap.get(RUN_AMOUNT);
		if(amountObj != null) {
			RuleExeStatisCondition condition = new RuleExeStatisCondition();
			Integer amount = Integer.valueOf(ruleBizMap.get(RUN_AMOUNT).toString());
			RuleExeStatisConType statType = RuleExeStatisConType.valueOf(ruleBizMap.get(RUN_RESTRAIN_TYPE).toString());
			String appointRunRange = ruleBizMap.get(APPOINT_RUNRESTRAIN_RANGE) != null 
			                            ? ruleBizMap.get(APPOINT_RUNRESTRAIN_RANGE).toString() : null;
			condition.setConstraintDateType(statType);
			condition.setAppointRunRestrainRange(appointRunRange);
			condition.setConstraintAmount(amount);
			condition.setRuleId(ruleVO.getId());
			return condition;
		}
		return null;
	}
	
	/**
	 * 创建规则执行次数限制查询条件
	 * @param bizMap
	 * @return
	 */
	public static RuleExeStatisCondition createStatCondition(RuleVO ruleVO) {
		Map<String, Object> ruleBizMap = ruleVO.getBizPropertyMap();
		if(BlankUtil.isBlank(ruleBizMap)) {
			return null;
		}
		Object amountObj = ruleBizMap.get(RUN_AMOUNT);
		if(amountObj != null) {
			RuleExeStatisCondition condition = new RuleExeStatisCondition();
			Integer amount = Integer.valueOf(ruleBizMap.get(RUN_AMOUNT).toString());
			RuleExeStatisConType statType = RuleExeStatisConType.valueOf(ruleBizMap.get(RUN_RESTRAIN_TYPE).toString());
			String appointRunRange = ruleBizMap.get(APPOINT_RUNRESTRAIN_RANGE) != null 
			                            ? ruleBizMap.get(APPOINT_RUNRESTRAIN_RANGE).toString() : null;
			condition.setConstraintDateType(statType);
			condition.setAppointRunRestrainRange(appointRunRange);
			condition.setConstraintAmount(amount);
			condition.setRuleId(ruleVO.getId());
			return condition;
		}
		return null;
	}
	
	/**
	 * 获取是否使用新影票消耗
	 * @param facts
	 * @return
	 */
	public static Boolean getIsUseNewConsumeItem(List<Object> facts) {
		 Object ruleContextInfo = FactUtil.getFactByType(facts, BizFactConstants.RULE_CONTEXT_INFO);
	     Boolean isUseNewConsumeItem = BizFunctionUtil.getBoolean(ruleContextInfo, BizFactConstants.RCI_IS_USE_NEW_CONSUME_ITEM);
	     return isUseNewConsumeItem;
	}
	/**
	 * 是否卖品
	 * @param type
	 * @return
	 */
	public static boolean isSaleInfo(Object fact) {
		String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
		if(BizFactConstants.FILM_INFO.equals(type) || BizFactConstants.MER_INFO.equals(type)) {
			return true;
		}
		return false;
	}
	
	public static Boolean getBoolean(Object source, String property) {
		if(source == null || property == null) {
			return null;
		}
		Boolean result = null;
		try {
			Object temp = BeanUtils.getProperty(source, property);
			if(temp == null) {
				return null;
			}
			if(temp instanceof String && !BlankUtil.isBlank((String)temp)) {
				result = Boolean.valueOf(temp.toString());
			} else if(temp instanceof String && BlankUtil.isBlank((String)temp)) {
				return null;
			} else if(temp instanceof Boolean) { 
				result = (Boolean)temp;
			} 
		} catch(Exception e) {
			log.error("无法转换为boolean", e);
		}
		
		return result;
	}
	
	/**
	 * 获取BigDecimal属性
	 */
	public static BigDecimal getBigDecimal(Object source, String property) {
		if(source == null || property == null) {
			return null;
		}
		BigDecimal result = null;
		Object p = null;
		try {
			p = BeanUtils.getProperty(source, property);
			if(p == null) {
				return null;
			}
			if(p instanceof String && !BlankUtil.isBlank((String)p)) {
				result = new BigDecimal(String.valueOf(p));
			} else if(p instanceof String && BlankUtil.isBlank((String)p)) {
				return null;
			} else if(p instanceof BigDecimal) { 
				result = (BigDecimal)p;
			} else {
				result = new BigDecimal(String.valueOf(p));
			}
		} catch(Exception e) {
			log.error("数字格式错误:" + p.toString());
		}
		
		return result;
	}
	
	/**
	 * 获取BigDecimal属性
	 */
	public static BigDecimal getBigDecimal(Object source, String property, BigDecimal defaultValue) {
		BigDecimal result = getBigDecimal(source, property);
		if(result == null) {
			return defaultValue;
		}
		
		return result;
	}
	
	/**
	 * 获取Integer属性
	 */
	public static Integer getInteger(Object source, String property) {
		if(source == null || property == null) {
			return null;
		}
		Integer result = null;
		Object p = null;
		try {
			p = BeanUtils.getProperty(source, property);
			if(p == null) {
				return null;
			}
			if(p instanceof String && !BlankUtil.isBlank((String)p)) {
				result = new Integer(String.valueOf(p));
			} else if(p instanceof String && BlankUtil.isBlank((String)p)) {
				return null;
			} else if(p instanceof Integer) { 
				result = (Integer)p;
			} else {
				result = new Integer(String.valueOf(p));
			}
		} catch(Exception e) {
			log.error("数字格式错误:" + p.toString());
		}
		
		return result;
	}
	
	
	/**
	 * 获取BigDecimal属性
	 */
	public static Integer getInteger(Object source, String property, Integer defaultValue) {
		Integer result = getInteger(source, property);
		if(result == null) {
			return defaultValue;
		}
		
		return result;
	}
	
	/**
	 * 获取记录已修改的商品
	 * @param saleItemInfo
	 * @return
	 */
	public static Map<String, Object> getSaleFactForRecordModified(Object saleItemInfo) {
		if(saleItemInfo == null) {
			return null;
		}
		if(isSaleInfo(saleItemInfo)) {
			Map<String, Object> resultFact = new HashMap<String, Object>();
			String type = BeanUtils.getPropertyStr(saleItemInfo, FactConstants.CATEGORY_TYPE_KEY);
			String itemType = BeanUtils.getPropertyStr(saleItemInfo, BizFactConstants.SALE_ITEM_TYPE);
			resultFact.put(FactConstants.CATEGORY_TYPE_KEY, type);
			resultFact.put(BizFactConstants.SALE_ITEM_TYPE, itemType);
			resultFact.put(FactConstants.CATEGORY_NUM_KEY, BeanUtils.getPropertyStr(saleItemInfo, FactConstants.CATEGORY_NUM_KEY));
			BigDecimal amount = BizFunctionUtil.getBigDecimal(saleItemInfo, BizFactConstants.AMOUNT, new BigDecimal("1"));
			resultFact.put(BizFactConstants.AMOUNT, amount);
			resultFact.put(BizFactConstants.SALE_ITEM_KEY, BeanUtils.getProperty(saleItemInfo, BizFactConstants.SALE_ITEM_KEY));
			resultFact.put(BizFactConstants.MER_KEY, BeanUtils.getProperty(saleItemInfo, BizFactConstants.MER_KEY));
			return resultFact;
		}
		return null;
	}
	
	/**
	 * 获取简单的SaleItemInfo属性,避免返回太多没用属性
	 * 
	 */
	public static Map<String, Object> getSimpleSaleFactForModify(Object saleItemInfo) {
		if(saleItemInfo == null) {
			return null;
		}
		
		if(isSaleInfo(saleItemInfo)) {
			Map<String, Object> resultFact = new HashMap<String, Object>();
			String type = BeanUtils.getPropertyStr(saleItemInfo, FactConstants.CATEGORY_TYPE_KEY);
			String itemType = BeanUtils.getPropertyStr(saleItemInfo, BizFactConstants.SALE_ITEM_TYPE);
			resultFact.put(FactConstants.CATEGORY_TYPE_KEY, type);
			resultFact.put(BizFactConstants.SALE_ITEM_TYPE, itemType);
			resultFact.put(BizFactConstants.OLD_PRICE, BizFunctionUtil.getBigDecimal(saleItemInfo, BizFactConstants.PRICE));
			resultFact.put(BizFactConstants.PRICE, BizFunctionUtil.getBigDecimal(saleItemInfo, BizFactConstants.PRICE));
			resultFact.put(BizFactConstants.OTHER_PRICE, BizFunctionUtil.getBigDecimal(saleItemInfo, BizFactConstants.PRICE));
			resultFact.put(FactConstants.CATEGORY_NUM_KEY, BeanUtils.getPropertyStr(saleItemInfo, FactConstants.CATEGORY_NUM_KEY));
			BigDecimal amount = BizFunctionUtil.getBigDecimal(saleItemInfo, BizFactConstants.AMOUNT, new BigDecimal("1"));
			resultFact.put(BizFactConstants.AMOUNT, amount);
			resultFact.put(BizFactConstants.OLD_AMOUNT, amount);
			resultFact.put(FactConstants.IS_FACT_KEY, BeanUtils.getProperty(saleItemInfo, FactConstants.IS_FACT_KEY));
			resultFact.put(BizFactConstants.SALE_ITEM_KEY, BeanUtils.getProperty(saleItemInfo, BizFactConstants.SALE_ITEM_KEY));
			resultFact.put(BizFactConstants.MER_KEY, BeanUtils.getProperty(saleItemInfo, BizFactConstants.MER_KEY));
			if(BizFactConstants.FILM_ITEM.equals(itemType)) {
				Object code = BeanUtils.getProperty(saleItemInfo, BizFactConstants.FILM_PLAN_CODE);
				if(code != null) {
					resultFact.put(BizFactConstants.FILM_PLAN_CODE, code);
				}
				resultFact.put(BizFactConstants.LOW_PRICE, BizFunctionUtil.getBigDecimal(saleItemInfo, BizFactConstants.LOW_PRICE));
//				resultFact.put(BizFactConstants.FILM_STANDARD_PRICE, BizFunctionUtil.getBigDecimal(saleItemInfo, BizFactConstants.FILM_STANDARD_PRICE));
			}
			return resultFact;
		}
		return null;
	}
}
