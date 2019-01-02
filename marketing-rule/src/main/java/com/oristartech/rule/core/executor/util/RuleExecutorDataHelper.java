package com.oristartech.rule.core.executor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.marketing.core.ApplicationContextHelper;
import com.oristartech.rule.core.cache.IRuleDataCache;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 规则数据辅助类
 * @author chenjunfei
 *
 */
public class RuleExecutorDataHelper {
	
	private static Logger log = LoggerFactory.getLogger(RuleExecutorDataHelper.class);
	/**
	 * 获取规则vo, 因为在drl文件中的规则信息没有全部信息.(若要有全部信息, 要规则的信息转为json字符传,然后在使用时转回对象, 这样会影响效率, 虽然可缓存, 但就要自己控制.
	 * 而这里用回统一规则的接口, 用hibernate的缓存, 不用额外自己控制缓存json转回的对象.)
	 * 若是测算, 测算时,规则数据可能还没保存到数据库,是暂存在context中的. 
	 * @param ruleId
	 * @return
	 */
	public static RuleVO getRuleVO(RuleExecuteContext context, Integer ruleId) {
		RuleVO ruleVO = null;
		if(context.isTest()) {
			RuleGroupVO groupVO = context.getTestGroupVO();
			if(groupVO != null ) {
				ruleVO = groupVO.findRuleVO(ruleId);
			}
		} else {
			IRuleDataCache ruleDataCache = ApplicationContextHelper.getContext().getBean(IRuleDataCache.class);
			ruleVO = ruleDataCache.getRuleById(ruleId);
		}
		
		return ruleVO;
	}
	
	/**
	 * 根据id获取规则信息. 
	 * @param ruleId
	 * @return
	 */
	public static RuleVO getRuleVO(Integer ruleId) {
		RuleVO ruleVO = null;
		IRuleDataCache ruleDataCache = ApplicationContextHelper.getContext().getBean(IRuleDataCache.class);
		ruleVO = ruleDataCache.getRuleById(ruleId);
		return ruleVO;
	}
}
