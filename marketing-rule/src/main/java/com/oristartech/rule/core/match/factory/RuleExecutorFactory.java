package com.oristartech.rule.core.match.factory;

import com.oristartech.marketing.core.ApplicationContextHelper;
import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.drools.executor.IRuleExecutorService;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;

/**
 * 根据不同规则类型选择不同ruleexecutoryService
 * @author chenjunfei
 *
 */
public class RuleExecutorFactory {
	
	/**
	 * 选择规则执行类型。
	 * @param ruleType
	 * @return
	 */
	public static IRuleExecutorService chooseRuleExecutor(RuleTypeVO ruleType) {
		if(ruleType != null && !BlankUtil.isBlank(ruleType.getExecutor())) {
			return ApplicationContextHelper.getContext().getBean( IRuleExecutorService.class);
		}
		throw new RuleExecuteRuntimeException("没指定规则类型，无法选择规则执行service");
	}
}
