package com.oristartech.rule.core.cache;

import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;
import com.oristartech.rule.vos.core.vo.RuleVO;


/**
 * 缓存管理接口，主要是调用IRuleManagerService中方法，但IRuleManagerService一般不缓存，所以集中到这里.
 * 
 * 主要提供给IRuleService用，即在匹配的时候。
 * 其他情况的查询缓存用hibernate自己的缓存即可，或不设缓存。
 * 
 * com-oristartech-rule-runtime-core-region区设为永久，清空规则缓存的时机是下发规则重新加载引擎时。
 * 
 * @author chenjunfei
 *
 */
public interface IRuleDataCache {
	/**
	 * 获取rule
	 * @param ruleId
	 * @return
	 */
	public RuleVO getRuleById(Integer ruleId) ;
	
//	public RuleGroupVO getRuleGroupById(Integer groupId);
	
//	/**
//	 * 更新缓存中的rulegroup
//	 * @param groupVO
//	 * @return
//	 */
//	public RuleGroupVO flushGroup(RuleGroupVO groupVO);
//	
//	/**
//	 * 更新缓存中的rule
//	 * @param ruleVO
//	 * @return
//	 */
//	public RuleVO flushRule(RuleVO ruleVO);

	/**
	 * 删除rulegroup缓存
	 * @param groupVO
	 */
	public void evitGroup(RuleGroupVO groupVO);
	
	
	/**
	 * 删除rule缓存
	 * @param groupVO
	 */
	public void evitRule(RuleVO ruleVO);
	
	public RuleTypeVO getRuleTypeByGroupId(Integer groupId);
	
	public RuleTypeVO getRuleTypeByRuleId(Integer ruleId);
	
	public RuleTypeVO getDefaultRuleType();
	
	public RuleTypeVO getRuleTypeByName(String name);

}
