package com.oristartech.rule.core.executor.util;

import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.marketing.core.ApplicationContextHelper;
import com.oristartech.marketing.core.exception.RuleRuntimeBaseException;
import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.core.service.IRuleTypeService;
import com.oristartech.rule.core.functions.IRuleExecutorFunction;
import com.oristartech.rule.core.functions.sales.util.SaleFactsUtil;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleSectionVO;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.RuleSectionResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 规则执行相关工具类
 */
public class RuleExecutorUtil {
	
	protected static final Logger log = LoggerFactory.getLogger(RuleExecutorUtil.class);
	
	/**
	 * 根据类型获取是否允许多次匹配
	 * @param ruleTypeName
	 * @return
	 */
	public static boolean isMatchMulti(String ruleTypeName) {
		if(BlankUtil.isBlank(ruleTypeName)) {
			return false;
		}
		IRuleTypeService ruleTypeService = ApplicationContextHelper.getContext().getBean(IRuleTypeService.class);
		Map<String, RuleTypeVO> typeMap = ruleTypeService.getAllRuleTypeVOsMap();
		if(!BlankUtil.isBlank(typeMap)) {
			RuleTypeVO typeVO = typeMap.get(ruleTypeName);
			if(typeVO != null && typeVO.getIsMatchMulti() != null) {
				return typeVO.getIsMatchMulti();
			}
		}
		return false;
	}
	
	/**
	 * 根据类型是否可以多次允许执行方法
	 * @param ruleTypeName
	 * @return
	 */
	public static boolean isActionRunMulti(String ruleTypeName) {
		if(BlankUtil.isBlank(ruleTypeName)) {
			return false;
		}
		IRuleTypeService ruleTypeService = ApplicationContextHelper.getContext().getBean(IRuleTypeService.class);
		Map<String, RuleTypeVO> typeMap = ruleTypeService.getAllRuleTypeVOsMap();
		if(!BlankUtil.isBlank(typeMap)) {
			RuleTypeVO typeVO = typeMap.get(ruleTypeName);
			if(typeVO != null && typeVO.getIsActionRunMulti() != null) {
				return typeVO.getIsActionRunMulti();
			}
		}
		return false;
	}
	/**
	 * 规则类型是否可以多次匹配运行
	 * @param ruleTypeName
	 * @return
	 */
	public static String getMatchMultiStr(String ruleTypeName) {
		return Boolean.toString(isMatchMulti(ruleTypeName));
	}
	
	/**
	 * 获取多次执行的次数， 即不需要经过多次匹配，一次匹配就要计算出执行方法要运行多次
	 * @return
	 */
	public static int getMultiExeTimes(RuleVO ruleVO, RuleExecuteContext context, List<Object> matchFactWithAllFields) {
		boolean canRunMulti = isActionRunMulti(ruleVO.getRuleType());
		if(canRunMulti) {
			return SaleFactsUtil.getMultiExeTimes(ruleVO, context, matchFactWithAllFields);
		} else {
			return 1;
		}
	}
	
	/**
	 * 获取当前规则vo
	 * @param ruleId
	 * @param ruleName
	 * @param priority
	 * @param ruleType
	 * @return
	 */
	public static RuleVO getRule(RuleExecuteContext context, Integer ruleId) {
		if(ruleId != null) {
			return RuleExecutorDataHelper.getRuleVO(context, ruleId);
		}
		
	    return null;
	}
	
	public static RuleActionVO getAction(RuleVO rule, Long actionId) {
		if(rule != null && actionId != null) {
			RuleActionVO actionVO = rule.findAction(actionId);
			return actionVO;
		}
	    
	    return null;
	}
	
	/**
	 * 获取当前简单规则vo
	 * @param ruleId
	 * @param ruleName
	 * @param priority
	 * @param ruleType
	 * @return
	 */
	public static ResultRuleVO getResultRuleVO(RuleVO ruleVO) {
		if(ruleVO != null) {
			Mapper mapper = ApplicationContextHelper.getContext().getBean( Mapper.class);
		    return mapper.map(ruleVO, ResultRuleVO.class);
		}
		
	    return null;
	}
	
	/**
	 * 执行规则指定section中的所有action
	 * @param context
	 * @param ruleId
	 * @param sectionId
	 * @param curMatchFacts
	 */
	
	public static void runActions(RuleExecuteContext contexts, Integer ruleId, Long sectionId,  List<Object> curMatchFacts ,boolean isMatchMulti) {
		RuleVO ruleVO = getRule(contexts, ruleId);
		 if(ruleVO == null) {
			 throw new RuleExecuteRuntimeException("找不到相关规则. id=" + ruleId);
		 }
		 ResultRuleVO resultRuleVO = getResultRuleVO(ruleVO);
		 RuleResult ruleResult = contexts.getRuleResult(ruleId);
		 
		 if(ruleResult == null) {
		    ruleResult = new RuleResult();
		    ruleResult.setRuleVO(resultRuleVO);        
		    contexts.addRuleResult(ruleId, ruleResult);
		    ruleResult.addMatchFacts(curMatchFacts, FactConstants.CATEGORY_NUM_KEY);
		 } else if(!isMatchMulti){
		    ruleResult.addMatchFacts(curMatchFacts, FactConstants.CATEGORY_NUM_KEY);
		    //若已有结果，而且不允许多次匹配运行
		    return;
		 }
		 RuleSectionResult sectionResult = ruleResult.getSectionResult(sectionId);
		 
		 if(sectionResult == null) {
		    sectionResult = new RuleSectionResult();
		    sectionResult.setRuleSectionId(sectionId);
		    ruleResult.addSectionResult(sectionResult);
		 }  
		
		 sectionResult.addMatchFacts(curMatchFacts, FactConstants.CATEGORY_NUM_KEY);
		 
		 //设置当前section 的action 执行context
		 ActionExecuteContext acContext = new ActionExecuteContext();
		 acContext.setCurMatchFacts(curMatchFacts);
		 acContext.setCurRuleResult(ruleResult);
		 acContext.setCurSectionResult(sectionResult);
		 acContext.setCurRuleVO(ruleVO);
		 
		 int runTimes = getMultiExeTimes(ruleVO, contexts, curMatchFacts);
		 ruleResult.setRunTimes(runTimes);     
		 acContext.setRunTimes(runTimes);
		 
		 RuleSectionVO section = getRuleSection(ruleVO, sectionId);
		 for(RuleActionVO action : section.getRuleActions()) {
			 RuleActionResult actionResult = invokeAction(contexts, acContext, resultRuleVO, action);
			 if(actionResult != null && !BlankUtil.isBlank(actionResult.getFnResults())) {
		        sectionResult.addActionResult(actionResult);
		     } 
		 }
		 
	}
	
	private static  RuleActionResult invokeAction(RuleExecuteContext context, 
			  ActionExecuteContext acContext, ResultRuleVO resultRuleVO, RuleActionVO action) {
		try {
			IRuleExecutorFunction executor =(IRuleExecutorFunction)Class.forName(action.getClzName()).newInstance();
			RuleActionResult actionResult =  executor.execute(context, acContext, resultRuleVO, action);
			return actionResult;
		} catch(Exception e) {
			if(e instanceof RuleRuntimeBaseException) {
				throw (RuleRuntimeBaseException)e;
			} else {
				throw new RuleExecuteRuntimeException(e);
			}
			
		}
		
	}
	
	public static RuleSectionVO getRuleSection(RuleVO ruleVO, Long sectionId) {
		if(ruleVO.getRuleSections() != null) {
			for(RuleSectionVO section : ruleVO.getRuleSections()) {
				if(section.getId().equals(sectionId)) {
					return section;
				}
			}
		}
		return null;
	}
}
