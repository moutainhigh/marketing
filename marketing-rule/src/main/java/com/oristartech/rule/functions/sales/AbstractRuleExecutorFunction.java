package com.oristartech.rule.functions.sales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.core.functions.IRuleExecutorFunction;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;
import com.oristartech.rule.vos.result.action.RuleConsumeActionResult;

/**
 * IRuleExecutorFunction 抽象实现类, 其他具体执行方法处理类应该继承本类。
 * @author chenjunfei
 *
 */
public abstract class AbstractRuleExecutorFunction implements IRuleExecutorFunction {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public RuleActionResult execute(RuleExecuteContext context, ActionExecuteContext acContext,  ResultRuleVO resultRuleVO, RuleActionVO actionVO) {
		long start = 0; 
		if(log.isDebugEnabled()) {
			start = System.currentTimeMillis();
			logDebugMsg(context, acContext,  actionVO);
		}
		
		RuleActionResult anResult = null;
		try {
			anResult = run(context, acContext, resultRuleVO, actionVO);
			RuleResult ruleResult = context.getRuleResult(resultRuleVO.getId());
			if(anResult != null) {
				afterGetActionResult(ruleResult, anResult, actionVO);
			}
		} catch (Exception e) {
			throw new RuleExecuteRuntimeException(e);
		}
		 
		if(log.isDebugEnabled()) {
			log.debug("execute time =" + (System.currentTimeMillis() - start));
		}
		return anResult;
	}
	
	/**
	 * 子类真正要运行的逻辑方法
	 * @param context 当前rule执行context
	 * @param acContext 当前action context
	 * @param resultRuleVO 当前规则信息
	 * @param actionVO 当前执行方法信息
	 * @return
	 */
	protected abstract RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext,  ResultRuleVO resultRuleVO, RuleActionVO actionVO) ;
	
	/**
	 * 创建一个RuleActionResult
	 * @param actionVO
	 * @return
	 */
	protected RuleActionResult prepareRuleActionResult(RuleActionVO actionVO) {
		RuleActionResult result = new RuleActionResult();
		prepareRuleActionResult(result, actionVO);
		return result;
	}
	
	protected RuleConsumeActionResult prepareRuleConsumeActionResult(RuleActionVO actionVO) {
		RuleConsumeActionResult conResult = new RuleConsumeActionResult();
		prepareRuleActionResult(conResult, actionVO);
		return conResult;
	}
	
	/**
	 * 创建一个RuleActionResult
	 * @param actionVO
	 * @return
	 */
	private void prepareRuleActionResult(RuleActionResult result, RuleActionVO actionVO) {
		result.setFnCategoryName(actionVO.getFnCategoryName());
		result.setFnEffect(actionVO.getFnEffect());
		result.setRuleActionId(actionVO.getId());
		result.setFnUniqueName(actionVO.getActionFnUniqueName());
		result.setCnName(actionVO.getCnName());
		result.setFnId(actionVO.getActionFnId());
		result.setFnResultType(actionVO.getFnResultType().toString());
	}
	/**
	 * 输出调试
	 * @param context
	 * @param actionVO
	 */
	protected void logDebugMsg(RuleExecuteContext context, ActionExecuteContext acContext, RuleActionVO actionVO) {
		if(log.isDebugEnabled()) {
			log.debug("=========Modify Function params = " + actionVO.getParameterMap());
			log.debug("---------match facts= " + acContext.getCurMatchFacts());
			log.debug("---------all facts= " + context.getAllCloneFacts());
		}
	}
	
	private void afterGetActionResult(RuleResult ruleResult, RuleActionResult anResult,  RuleActionVO actionVO) {
	}
	
}
