package com.oristartech.rule.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.oristartech.rule.core.match.service.IRuleService;
import com.oristartech.rule.facade.IRuleFacade;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;

@Service(version = "1.0.0", interfaceClass = IRuleFacade.class)
public class RuleFacadeImpl implements IRuleFacade {
	
	@Autowired
	IRuleService ruleService;

	@Override
	public String matchRuleById(String tenantId, Integer id, String facts) {
		return ruleService.matchRuleById(tenantId, id, facts);
	}

	@Override
	public List<RuleResult> matchRuleById(String tenantId, Integer id, List<Object> facts) {
		return ruleService.matchRuleById(tenantId, id, facts);
	}

	@Override
	public List<RuleResult> matchRuleByIds(String tenantId, List<Integer> ids, List<Object> facts) {
		return ruleService.matchRuleByIds(tenantId, ids, facts);
	}

	@Override
	public String matchRuleByGroupId(String tenantId, Integer ruleGroupId, String facts) {
		return ruleService.matchRuleByGroupId(tenantId, ruleGroupId, facts);
	}

	@Override
	public List<RuleResult> matchRuleByGroupId(String tenantId, Integer ruleGroupId, List<Object> facts) {
		return ruleService.matchRuleByGroupId(tenantId, ruleGroupId, facts);
	}

	@Override
	public List<RuleResult> matchRuleByGroupIds(String tenantId, List<Integer> ruleGroupIds, List<Object> facts) {
		return ruleService.matchRuleByGroupIds(tenantId, ruleGroupIds, facts);
	}

	@Override
	public String matchRule(String tenantId, String typeName, String facts) {
		return ruleService.matchRule(tenantId, typeName, facts);
	}

	@Override
	public List<RuleResult> matchRule(String tenantId, String typeName, List<Object> facts) {
		return ruleService.matchRule(tenantId, typeName, facts);
	}

	@Override
	public RuleVO getRuleBasicInfo(Integer ruleId) {
		return ruleService.getRuleBasicInfo(ruleId);
	}

	@Override
	public RuleVO getRuleBasicInfoInGroup(Integer groupId, Integer ruleId) {
		return ruleService.getRuleBasicInfoInGroup(groupId, ruleId);
	}

	@Override
	public boolean existRuleInGroup(Integer groupId, Integer ruleId) {
		return ruleService.existRuleInGroup(groupId, ruleId);
	}

	@Override
	public RuleVO getRuleFullInfo(Integer ruleId) {
		return ruleService.getRuleFullInfo(ruleId);
	}

	@Override
	public List<RuleResult> testRuleGroup(RuleGroupVO groupVO, List<Object> facts) {
		return ruleService.testRuleGroup(groupVO, facts);
	}

	@Override
	public boolean existRule(Integer ruleId) {
		return ruleService.existRule(ruleId);
	}

}
