package com.oristartech.rule.drools.executor;

import java.util.List;
import java.util.Map;

import com.oristartech.rule.drools.executor.context.FactsContainer;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * 规则执行器
 * @author chenjunfei
 *
 */

public interface IRuleExecutorService {
	/**
	 * 匹配指定类型规则
	 * @param type
	 * @param factsContainer
	 * @return
	 */
	public RuleExecuteContext process(String tenantId,String type, FactsContainer factsContainer);
	
	/**
	 * 根据ruleid 匹配规则
	 * @param ruleId
	 * @param type
	 * @param factsContainer
	 * @return
	 */
	public RuleExecuteContext processByRuleId(String tenantId,Integer ruleId, String type, FactsContainer factsContainer);
	
	/**
	 * 根据rule ids 匹配规则
	 * @param ruleIds 若干规则id
	 * @param type
	 * @param factsContainer
	 * @return
	 */
	public RuleExecuteContext processByRuleIds(String tenantId,List<Integer> ruleIds, String type, FactsContainer factsContainer);
	
	/**
	 * 匹配指定组下的规则
	 * @param ruleGroupId
	 * @param type
	 * @param factsContainer
	 * @return
	 */
	public RuleExecuteContext processByGroupId(String tenantId,Integer ruleGroupId, String type, FactsContainer factsContainer);
	
	/**
	 * 匹配指定若干组下的规则
	 * @param ruleGroupIds
	 * @param type
	 * @param factsContainer
	 * @return
	 */
	public RuleExecuteContext processByGroupIds(String tenantId,List<Integer> ruleGroupIds, String type, FactsContainer factsContainer);
	
	/**
	 * 匹配指定类型规则, 并指定全局对象
	 * @param type
	 * @param factsContainer
	 * @param globals
	 * @return
	 */
	public RuleExecuteContext process(String tenantId,String type, FactsContainer factsContainer, Map<String, Object> globals);
	
	/**
	 * 测试指定规则组
	 * @param groupVO
	 * @param listFacts
	 * @return
	 */
	public RuleExecuteContext processWithGroupTest(RuleGroupVO groupVO, FactsContainer factsContainer);

}
