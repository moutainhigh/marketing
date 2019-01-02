package com.oristartech.marketing.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.oristartech.marketing.service.IRuleService;
import com.oristartech.rule.facade.IRuleFacade;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;

@Service
public class RuleServiceImpl implements IRuleService {

	private Logger logger = LoggerFactory.getLogger(RuleServiceImpl.class);
	
	@Reference(version = "1.0.0")
	IRuleFacade ruleFacade;
	
	@Override
	public String matchRuleById(String tenantId, Integer id, String facts) {
		return ruleFacade.matchRuleById(tenantId, id, facts);
	}

	@Override
	public List<RuleResult> matchRuleById(String tenantId, Integer id, List<Object> facts) {
		return ruleFacade.matchRuleById(tenantId, id, facts);
	}

	@Override
	public List<RuleResult> matchRuleByIds(String tenantId, List<Integer> ids, List<Object> facts) {
		return ruleFacade.matchRuleByIds(tenantId, ids, facts);
	}

	@Override
	public String matchRuleByGroupId(String tenantId, Integer ruleGroupId, String facts) {
		return ruleFacade.matchRuleByGroupId(tenantId, ruleGroupId, facts);
	}

	@Override
	public List<RuleResult> matchRuleByGroupId(String tenantId, Integer ruleGroupId, List<Object> facts) {
		return ruleFacade.matchRuleByGroupId(tenantId, ruleGroupId, facts);
	}

	@Override
	public List<RuleResult> matchRuleByGroupIds(String tenantId, List<Integer> ruleGroupIds, List<Object> facts) {
		return ruleFacade.matchRuleByGroupIds(tenantId, ruleGroupIds, facts);
	}

	@Override
	public String matchRule(String tenantId, String typeName, String facts) {
		return ruleFacade.matchRule(tenantId, typeName, facts);
	}

	@Override
	public List<RuleResult> matchRule(String tenantId, String typeName, List<Object> facts) {
		return ruleFacade.matchRule(tenantId, typeName, facts);
	}

	@Override
	public RuleVO getRuleBasicInfo(Integer ruleId) {
		return ruleFacade.getRuleBasicInfo(ruleId);
	}

	@Override
	public RuleVO getRuleBasicInfoInGroup(Integer groupId, Integer ruleId) {
		return ruleFacade.getRuleBasicInfoInGroup(groupId, ruleId);
	}

	@Override
	public boolean existRuleInGroup(Integer groupId, Integer ruleId) {
		return ruleFacade.existRuleInGroup(groupId, ruleId);
	}

	@Override
	public RuleVO getRuleFullInfo(Integer ruleId) {
		return ruleFacade.getRuleFullInfo(ruleId);
	}

	@Override
	public List<RuleResult> testRuleGroup(RuleGroupVO groupVO, List<Object> facts) {
		return ruleFacade.testRuleGroup(groupVO, facts);
	}

	@Override
	public boolean existRule(Integer ruleId) {
		return ruleFacade.existRule(ruleId);
	}

}
