package com.oristartech.rule.functions.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.marketing.core.ApplicationContextHelper;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.FactUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.core.functions.sales.util.SaleFactsUtil;
import com.oristartech.rule.core.ws.client.service.IRuleExternDataService;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.functions.dto.RuleTicketLimitInfo;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.action.RuleConsumeActionResult;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

/**
 * 影票相关方法工具类
 * @author Administrator
 *
 */
public class FilmFunctionUtil {
	
	private static final Logger log = LoggerFactory.getLogger(FilmFunctionUtil.class);
	
	/**
	 * 条件中是否设置影票数量；
	 */
	private static String FILM_HAS_COMPARE_AMOUNT_IN_CONDITION_KEY = "film_compare_amount_in_condition_";
	
	/**
	 * 数量限制cache key
	 */
	private static final String CARD_TYPE_TICKET_COUNT_KEY = "CARD_TYPE_TICKET_COUNT_KEY_";
	
	/**
	 * 每日使用数cache key
	 */
	private static final String PER_DAY_TICKET_AMOUNT_KEY = "PER_DAY_TICKET_AMOUNT_KEY";
	
	/**
	 * 每周使用数cache key
	 */
	private static final String PER_WEEK_TICKET_AMOUNT_KEY = "PER_WEEK_TICKET_AMOUNT_KEY";
	
	/**
	 * 每月用数cache key
	 */
	private static final String PER_MONTH_TICKET_AMOUNT_KEY = "PER_MONTH_TICKET_AMOUNT_KEY";
	
	//获取卡类型数量限制的方法
	private static final String CARD_TYPE_TICKET_COUNT_SERVICE = "GetMemberTypeFavFilmLimit";
	
	//获取会员某个活动，某段时间的已购买的优惠票数的方法
	private static final String GET_MEM_RULE_STATIS_TICKET_LIMIT_SERVICE = "GetMemRuleStatisTicketLimit";
	 
	//数量方式
	public static final String MODIFY_AMOUNT_METHOD = "modifyAmountMethod";
	
	//指定数量
	public static final String APPOINT_MODIFY_AMOUNT = "appointAmount";
	
	//每日每会员
	public static final String AMOUNT_PER_MEMBER_DAY = "perMemberDay";
		
	//每周每会员
	public static final String AMOUNT_PER_MEMBER_WEEK = "perMemberWeek";
	
	//每月每会员
	public static final String AMOUNT_PER_MEMBER_MONTH = "perMemberMonth";
	
	//调价商品数量
	public static final String MODIFY_AMOUNT_PARAM = "modifyAmount";
	
	/**
	 * 获取会员card号
	 * @param context
	 * @return
	 */
	public static String getMemberInfoCardNum(RuleExecuteContext context) {
		Object memberInfo = FactUtil.getFactByType(context.getAllFacts(), BizFactConstants.MEMBER_INFO);
		String cardNum = BeanUtils.getPropertyStr(memberInfo, BizFactConstants.MEMBER_CARD_NUM);
		return cardNum;
	}
	/**
	 * 计算影票限制
	 * @param context
	 * @return
	 */
	public static RuleTicketLimitInfo getTicketLimitInfo(RuleExecuteContext context, ActionExecuteContext acContext, RuleActionVO actionVO) {
		RuleTicketLimitInfo info = new RuleTicketLimitInfo();
		Object ruleContextFact  = getRuleContextFact(context.getAllFacts());
		//在第三方直接查询场次票价会忽略票数限制
		Boolean ignoreTicketAmount = BizFunctionUtil.getBoolean(ruleContextFact, BizFactConstants.RCI_IGNORE_TICKET_AMOUNT);
		Boolean ignoreCardTicketAmount = BizFunctionUtil.getBoolean(ruleContextFact, BizFactConstants.RCI_IGNORE_CARD_TICKET_AMOUNT);
		
		info.setIgnoreTicketAmount(ignoreTicketAmount);
		info.setIgnoreCardTicketAmount(ignoreCardTicketAmount);
		if(ignoreCardTicketAmount == null || ignoreCardTicketAmount == false) {
			info.setCardTypeTicketCountLimit(getCardTypeTicketCountLimit(context));
			info.setRuleFnMemTicketAmountLimit(getRuleFnMemTicketAmountLimit(context, acContext, actionVO));
		}
		return info;
	}
	
	/**
	 * 根据当前方法参数设置的每会员统计维度，获取当前的还可以优惠的票数
	 * @param context
	 * @return
	 */
	private static Integer getRuleFnMemTicketAmountLimit(RuleExecuteContext context, ActionExecuteContext acContext, RuleActionVO actionVO) {
		
		String cacheKey = null;
		String statisWay = null;
		
		RuleVO ruleVo = acContext.getCurRuleVO();
		
		Map<String, String> anParams = actionVO.getParameterMap();
		String amountMethod = anParams.get(MODIFY_AMOUNT_METHOD);
		if(amountMethod.equals(AMOUNT_PER_MEMBER_DAY)) {
			cacheKey = PER_DAY_TICKET_AMOUNT_KEY + "_" + ruleVo.getId();
			statisWay = "DAY";
		} else if(amountMethod.equals(AMOUNT_PER_MEMBER_WEEK)) {
			cacheKey = PER_WEEK_TICKET_AMOUNT_KEY + "_" + ruleVo.getId();
			statisWay = "WEEK";
		} else if(amountMethod.equals(AMOUNT_PER_MEMBER_MONTH)) {
			cacheKey = PER_MONTH_TICKET_AMOUNT_KEY + "_" + ruleVo.getId();
			statisWay = "MONTH";
		}
		//若方法中每设置统计属性
		if(BlankUtil.isBlank(cacheKey)) {
			return null;
		}
		
		//若方法设置了会员限制，而事实中没有会员，则返回0，表示不能优惠
		String cardNum = getMemberInfoCardNum(context);
		if(BlankUtil.isBlank(cardNum)) {
			return 0;
		}
		
		if(!context.getContextManager().containsKeyByClz(FilmFunctionUtil.class, cacheKey)) {
			IRuleExternDataService ruleExternDataService = ApplicationContextHelper.getContext().getBean(IRuleExternDataService.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardNum", cardNum);
			params.put("ruleId", ruleVo.getId());
			params.put("statisWay", statisWay);
			
			RuleWSResultVO wsReturnVO = ruleExternDataService.findExternDataResultVO(GET_MEM_RULE_STATIS_TICKET_LIMIT_SERVICE, params);
			if(wsReturnVO.isOk()) {
				Map<String, Object> amountMap = BeanUtils.convertToMap(wsReturnVO.getData());
				Integer existAmount = (Integer)amountMap.get("statisAmount");
				if(existAmount != null) {
					context.getContextManager().cacheByClz(FilmFunctionUtil.class , cacheKey,  existAmount);
				}
			} else {
				log.warn("根据统计日期查询本活动影票优惠票数出错" + wsReturnVO.getMessage());
			}
		} 
		Integer existAmount = (Integer)context.getContextManager().getCacheDataByClz(FilmFunctionUtil.class, cacheKey);
		Integer amountInParam = getMemberAmountParam(actionVO);
		if(existAmount != null) {
			Integer leftLimit = amountInParam - existAmount;
			if(leftLimit <= 0) {
				leftLimit = 0;
			}
			return leftLimit;
		} 
		return amountInParam;
	}
	
	/**
	 * 获取每会员时的数量参数
	 * @param actionVO
	 * @return
	 */
	private static Integer getMemberAmountParam(RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		String amountMethod = params.get(FilmFunctionUtil.MODIFY_AMOUNT_METHOD);
		Integer modifyAmount = null;
		if(FilmFunctionUtil.AMOUNT_PER_MEMBER_DAY.equals(amountMethod) ||
				FilmFunctionUtil.AMOUNT_PER_MEMBER_WEEK.equals(amountMethod) || 
				FilmFunctionUtil.AMOUNT_PER_MEMBER_MONTH.equals(amountMethod)) {
			
			modifyAmount = Integer.valueOf(params.get(MODIFY_AMOUNT_PARAM));
		}
		return modifyAmount;
	}
	
	/**
	 * 设置消耗数量
	 * 1，条件没数量， 就消耗任意。 2，只要条件有数量，就消耗全部
	 * @param anResult
	 * @param params
	 * @return
	 */
	public static Integer calcConsumeFactAmount(RuleExecuteContext context, ActionExecuteContext acContext, 
			Integer filmTotalAmount, Integer fnResultAmount, Integer filmAmountInActionParam, Integer finalModifyAmount) {
		RuleVO ruleVO = acContext.getCurRuleVO();
		Boolean hasFilmAmountCondition = hasFilmAmountCondition(context, ruleVO);
		if(hasFilmAmountCondition != null && hasFilmAmountCondition) {
			//条件出现数量，消耗全部
			return filmTotalAmount;
		} 
		
		return RuleConsumeActionResult.ANY_CONSUME_FACT_AMOUNT_TAG;
	}
	
	/**
	 * 获取会员卡等级票数限制
	 * @param context
	 * @param allFacts
	 * @return
	 */
	private static Integer getCardTypeTicketCountLimit(RuleExecuteContext context) {
		List<Object> allFacts = context.getAllFacts();
		Object memberFact = FactUtil.getFactByType(allFacts, BizFactConstants.MEMBER_INFO);
		Object filmFact = FactUtil.getFactByType(allFacts, BizFactConstants.FILM_INFO);
		if(memberFact == null || filmFact == null) {
			return null;
		}
		if(!context.getContextManager().containsKeyByClz(FilmFunctionUtil.class, CARD_TYPE_TICKET_COUNT_KEY)) {
			IRuleExternDataService ruleExternDataService = ApplicationContextHelper.getContext().getBean(IRuleExternDataService.class);
			Map<String, Object> params = new HashMap<String, Object>();
			String cardNum = BeanUtils.getPropertyStr(memberFact, BizFactConstants.MEMBER_CARD_NUM);
			String filmPlanKey = BeanUtils.getPropertyStr(filmFact, BizFactConstants.FILM_PLAN_KEY);
			if(BlankUtil.isBlank(cardNum) || BlankUtil.isBlank(filmPlanKey)) {
				return null;
			}
			params.put("cardNum", cardNum);
			params.put("filmPlanKey", filmPlanKey);
			RuleWSResultVO wsReturnVO = ruleExternDataService.findExternDataResultVO(CARD_TYPE_TICKET_COUNT_SERVICE, params);
			if(wsReturnVO.isOk()) {
				Map<String, Object> ticketCountMap = BeanUtils.convertToMap(wsReturnVO.getData());
				Integer ticketCountLimit = (Integer)ticketCountMap.get("ticketCountLimit");
				if(ticketCountLimit != null) {
					context.getContextManager().cacheByClz(FilmFunctionUtil.class , CARD_TYPE_TICKET_COUNT_KEY,  ticketCountLimit);
				}
			} else {
				log.warn("查询卡等级影票优惠出错" + wsReturnVO.getMessage());
			}
		} 
		return (Integer)context.getContextManager().getCacheDataByClz(FilmFunctionUtil.class, CARD_TYPE_TICKET_COUNT_KEY);
	}
	
	/**
	 * 获取设置忽略打折数量限制的的属性的事实，这里是RuleContextInfo
	 * @param allFacts
	 * @return
	 */
	private static Object getRuleContextFact(List<Object> allFacts) {
		return FactUtil.getFactByType(allFacts, BizFactConstants.RULE_CONTEXT_INFO);
	}
	
	/**
	 * 是否有影票数量条件
	 * @param context
	 * @param ruleVO
	 * @return
	 */
	private static Boolean hasFilmAmountCondition(RuleExecuteContext context, RuleVO ruleVO) {
		String cacheKey = FILM_HAS_COMPARE_AMOUNT_IN_CONDITION_KEY  + ruleVO.getId();
		Boolean cacheValue = (Boolean)context.getContextManager().getCacheDataByClz(FilmFunctionUtil.class, cacheKey);
		if(cacheValue != null) {
			return cacheValue;
		}
		
		List<RuleConditionElementVO> eles = ruleVO.findConditionElements(BizFactConstants.SALE_INFO, BizFactConstants.FILM_TICKET_AMOUNT);
		if(!BlankUtil.isBlank(eles)) {
			cacheValue = true;
		} else {
			cacheValue = false;
		}
		context.getContextManager().cacheByClz(SaleFactsUtil.class, cacheKey, cacheValue);
		return cacheValue;
	}
}
