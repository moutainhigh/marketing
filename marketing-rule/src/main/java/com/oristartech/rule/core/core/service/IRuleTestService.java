package com.oristartech.rule.core.core.service;

import java.util.List;

import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

public interface IRuleTestService {
	
	/**
	 * 设置rulegroup中的实体id为假id 
	 * @param group
	 */
	public void setFakeIdForGroupInTest(RuleGroup group);
	
	/**
	 * 规则测试, 返回RuleResult数组json
	 * @param groupVO
	 * @return
	 */
	public String testRuleGroup(String ruleGroup, String facts) ;
	
	/**
	 * 规则测试,返回RuleResult数组json
	 * @param groupVO
	 * @return
	 */
	public String testRuleGroup(String ruleGroupStr, List<Object> facts);
	

	/**
	 * 在测试页面, 测试时规则信息可能还没保存, 本方法提供把没保存的信息组装出来
	 * @param ruleGroupStr
	 * @param needFakeId 是否填充假id, 假id用负数表示, 在营销活动
	 * @return
	 */
	public RuleGroupVO getTempGroupForTest(String ruleGroupStr, boolean needFakeId);
}
