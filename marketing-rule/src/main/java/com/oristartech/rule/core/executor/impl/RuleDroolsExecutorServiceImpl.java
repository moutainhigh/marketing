package com.oristartech.rule.core.executor.impl;

import java.util.List;
import java.util.Map;

import org.kie.api.runtime.rule.AgendaFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.init.service.IRuleExecutorInitService;
import com.oristartech.rule.drools.executor.AbstractRuleExecutorServiceImpl;
import com.oristartech.rule.drools.executor.IRuleDroolsManagerService;
import com.oristartech.rule.drools.executor.IRuleExecutorService;
import com.oristartech.rule.drools.executor.context.FactsContainer;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleMatchContext;
import com.oristartech.rule.drools.filters.RuleGroupPrefixAgendaFilter;
import com.oristartech.rule.drools.filters.RuleGroupsAgendaFilter;
import com.oristartech.rule.drools.filters.RuleNamesAgendaFilter;
import com.oristartech.rule.drools.filters.RuleWithNameAgendaFilter;
import com.oristartech.rule.drools.util.DrlUtil;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * 规则执行器实现类
 * @see IRuleExecutorService
 * @author chenjunfei
 */
@Component
public class RuleDroolsExecutorServiceImpl extends AbstractRuleExecutorServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(RuleDroolsExecutorServiceImpl.class);
	@Autowired
	private IRuleDroolsManagerService ruleDroolsManagerService;
	@Autowired
	private IRuleExecutorInitService ruleExecutorInitService;
	
	public RuleExecuteContext process(String tenantId,String type, FactsContainer factsContainer) {
		RuleExecuteContext context = null;
		if(!isRuleInitCompleted()) {
			return context;
		}
		
		if (!BlankUtil.isBlank(factsContainer.getFacts())) {
			context = new RuleExecuteContext();
			
			// return matchByStatefulSession(type, facts, null);
			return matchByStatelessSession(tenantId,new RuleMatchContext(),  context, type, factsContainer, null, null);
		}
		return context;
	}

	public RuleExecuteContext processByRuleId(String tenantId,Integer ruleId, String type, FactsContainer factsContainer) {
		RuleExecuteContext context = null;
		if(!isRuleInitCompleted()) {
			return context;
		}
		
		if (ruleId != null && !BlankUtil.isBlank(factsContainer.getFacts())) {
			context = new RuleExecuteContext();
			// return matchByStatefulSession(type, facts, new
			// RuleNameEqualsAgendaFilter(RuleUtil.getRuleName(type, ruleId)));
			AgendaFilter filter = new RuleWithNameAgendaFilter(ruleId);
			return matchByStatelessSession(tenantId,new RuleMatchContext() ,context, type, factsContainer, filter , null);
		}
		return context;
	}

	public RuleExecuteContext processByRuleIds(String tenantId,List<Integer> ruleIds, String type, FactsContainer factsContainer) {
		RuleExecuteContext context = null;
		if(!isRuleInitCompleted()) {
			return context;
		}
		
		if (!BlankUtil.isBlank(ruleIds) && !BlankUtil.isBlank(factsContainer.getFacts())) {
			context = new RuleExecuteContext();
			AgendaFilter filter = new RuleNamesAgendaFilter(ruleIds);
			return matchByStatelessSession(tenantId,new RuleMatchContext() ,context, type, factsContainer, filter , null);
		}
		return context;
	}
	
	public RuleExecuteContext processByGroupId(String tenantId,Integer ruleGroupId, String type, FactsContainer factsContainer) {
		RuleExecuteContext context = null;
		if(!isRuleInitCompleted()) {
			return context;
		}
		
		if (ruleGroupId != null && !BlankUtil.isBlank(factsContainer.getFacts())) {
			context = new RuleExecuteContext();
			AgendaFilter filter = new RuleGroupPrefixAgendaFilter(ruleGroupId);
			return matchByStatelessSession(tenantId,new RuleMatchContext() , context, type, factsContainer, filter , null);
		}
		return context;
	}
	
	public RuleExecuteContext processByGroupTest(String tenantId,RuleGroupVO groupVO, String type, FactsContainer factsContainer) {
		RuleExecuteContext context = null;
		if(!isRuleInitCompleted()) {
			return context;
		}
		
		if (groupVO != null && groupVO.getId() != null && !BlankUtil.isBlank(factsContainer.getFacts())) {
			context = new RuleExecuteContext();
			context.setTest(true);
			context.setTestGroupVO(groupVO);
			
			RuleMatchContext matchContext = new RuleMatchContext();
			matchContext.setTest(true);
			matchContext.setTestGroupVO(groupVO);
			
			AgendaFilter filter = new RuleGroupPrefixAgendaFilter(groupVO.getId());
			return matchByStatelessSession(tenantId,matchContext , context, type, factsContainer, filter , null);
		}
		return context;
	}
	
	public RuleExecuteContext processByGroupIds(String tenantId,List<Integer> ruleGroupIds, String type, FactsContainer factsContainer) {
		RuleExecuteContext context = null;
		if(!isRuleInitCompleted()) {
			return context;
		}
		
		if (!BlankUtil.isBlank(ruleGroupIds) && !BlankUtil.isBlank(factsContainer.getFacts())) {
			context = new RuleExecuteContext();
			AgendaFilter filter = new RuleGroupsAgendaFilter(ruleGroupIds);
			return matchByStatelessSession(tenantId,new RuleMatchContext() , context, type, factsContainer, filter , null);
		}
		return context;
	}
	
	public RuleExecuteContext process(String tenantId,String type, FactsContainer factsContainer, Map<String, Object> globals) {
		RuleExecuteContext context = null;
		if(!isRuleInitCompleted()) {
			return context;
		}
		
		if (!BlankUtil.isBlank(factsContainer.getFacts())) {
			return matchByStatelessSession(tenantId,new RuleMatchContext() , context, type, factsContainer, null , globals);
		}

		return context;
	}
	
	public RuleExecuteContext processWithGroupTest(RuleGroupVO groupVO, FactsContainer factsContainer) {
		//获取一个测试的pakcage名称, 每个测试都在各种的package名下进行测试
		String knowledgeMapKey = DrlUtil.getDrlTestPackageName();
		List<String> pkNames = null;
		try {
			if(groupVO.getId() == null) {
				//若干还没报存的vo要先设一下group, rule, section, id, 因为rule生成模板要这些id
				DrlUtil.initRuleGroupIdsForTest(groupVO);
			}
			pkNames = ruleDroolsManagerService.loadRuleToWorkMemory(groupVO, knowledgeMapKey, true);
			return processByGroupTest(String.valueOf(groupVO.getTenantId()),groupVO, knowledgeMapKey, factsContainer);
		} finally {
			//测试完要移除 
			if(!BlankUtil.isBlank(pkNames)) {
				ruleDroolsManagerService.removeKBaseInTest(String.valueOf(groupVO.getTenantId()),knowledgeMapKey);
			}
		}
	}
	
	protected boolean isRuleInitCompleted() {
		//在启动后，所有规则加载完后才可以调用接口
		return ruleExecutorInitService.isInitCompleted();
	}
}
