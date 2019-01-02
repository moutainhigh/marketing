package com.oristartech.rule.drools.executor;

import java.util.List;

import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * drools规则管理service，主要负责加载规则，移除规则
 * @author chenjunfei
 *
 */
public interface IRuleDroolsManagerService {
	/**
	 * 加载一个规则组到WorkMemory
	 * @param ruleGroup
	 * @return
	 */
	public List<String> loadRuleToWorkMemory (RuleGroupVO ruleGroup);
	
	/**
	 * 加载若干规则组到WorkMemory
	 * @param ruleGroup
	 * @return
	 */
	public List<String> loadRuleToWorkMemory (List<RuleGroupVO> ruleGroups);
	
	/**
	 * 加载若干规则组到WorkMemory, 若isTest是true只会加载到测试package中
	 * @param ruleGroups
	 * @param knowledgeType 可空, 若是空, 默认用规则类型
	 * @param isTest 是否是测试状态
	 * @return 返回生成的规则package 名称列表
	 */
	public List<String> loadRuleToWorkMemory(List<RuleGroupVO> ruleGroups, String knowledgeMapKey, boolean isTest);
	
	/**
	 * 加载一个规则组到WorkMemory, 若isTest是true只会加载到测试package中
	 * @param ruleGroups
	 * @param knowledgeType 可空, 若是空, 默认用规则类型
	 * @param isTest 是否是测试状态
	 * @return 返回生成的规则package 名称列表
	 */
	public List<String> loadRuleToWorkMemory(RuleGroupVO groupVO, String knowledgeMapKey,  boolean isTest) ;
	
	/**
	 * 根据规则group移除在引擎中的规则
	 * @param ruleGroup
	 */
	public void removeRuleInEngine(RuleGroupVO ruleGroup);
	
	/**
	 * 移除指定类型的规则包
	 * @param ruleGroup 要移除的rule group
	 */
	public void removePackageInEngine(RuleGroupVO ruleGroup);
	
	/**
	 * 移除整个knowledgebase
	 * @param knowledgeMapKey
	 */
	public void removeKBaseInTest(String tenantId,String knowledgeMapKey) ;
	
	/**
	 * ruleGroup 中的规则是否在引擎中存在
	 * @param group
	 * @return
	 */
	public boolean existRulesInEngine(RuleGroupVO group);
}
