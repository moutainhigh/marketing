package com.oristartech.rule.drools.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.kie.api.command.Command;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.RuleTemplateContants;
import com.oristartech.rule.drools.executor.context.FactsContainer;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleMatchContext;

/**
 * 规则执行抽象类
 * @see IRuleExecutorService
 * @author chenjunfei
 *
 */
public abstract class AbstractRuleExecutorServiceImpl implements IRuleExecutorService {
	
	private static final Logger log = LoggerFactory.getLogger(AbstractRuleExecutorServiceImpl.class);
	
	protected abstract boolean isRuleInitCompleted();
	/**
	 * 用有状态session匹配
	 * 
	 * @param type
	 * @param facts
	 * @param filter
	 */
	protected RuleExecuteContext matchByStatefulSession(String tenantId,RuleMatchContext matchContext, RuleExecuteContext context, String type, FactsContainer factsContainer,
	        AgendaFilter filter, Map<String, Object> globals) {
		RuleKnowledgeWrapper wrapper = RuleKnowledgeWrapper.getInstance();
		KieSession session = null;
		try {
			session = wrapper.getStatefulSession(tenantId,type);
			for (Object fact : factsContainer.getFacts()) {
				session.insert(fact);
			}
			setGlobals(matchContext, context, session, factsContainer, globals);

			session.fireAllRules(filter);
		} catch (Exception e) {
			log.error("match rule exception", e);
			throw new RuleExecuteRuntimeException(e);
		} finally {
			session.dispose();
		}
		return context;
	}

	/**
	 * 用无状态session匹配
	 * 
	 * @param type
	 * @param facts
	 * @param filter
	 */
	protected RuleExecuteContext matchByStatelessSession(String tenantId,RuleMatchContext matchContext, RuleExecuteContext context, String type, FactsContainer factsContainer,
	        AgendaFilter filter, Map<String, Object> globals) {
		List<Command<?>> commands = new ArrayList<Command<?>>();
		commands.add(CommandFactory.newInsertElements(factsContainer.getFacts()));
		if(filter != null) {
			commands.add(new FireAllRulesCommand(filter));
		}

		RuleKnowledgeWrapper wrapper = RuleKnowledgeWrapper.getInstance();
		StatelessKieSession session = wrapper.getStatelessSession(tenantId,type);
		
		//for debug
		if(log.isDebugEnabled()) {
			session.addEventListener(new DebugRuleRuntimeEventListener());
		}
		
		setGlobals(matchContext, context, session, factsContainer, globals);
		try {
			session.execute(CommandFactory.newBatchExecution(commands));
		} catch (Exception e) {
			log.error("match rule exception", e);
			throw new RuleExecuteRuntimeException(e);
		}
		
		return context;
	}

	/**
	 * 设置全局变量，context，规则方法中可以访问context中相关对象
	 * 
	 * @param session
	 * @param facts
	 */
	private void setGlobals(RuleMatchContext matchContext, RuleExecuteContext exeContext, StatelessKieSession session, FactsContainer factsContainer,  Map<String, Object> globals) {
		exeContext.setFactsContainer(factsContainer);
		matchContext.setFactsContainer(factsContainer);
		session.setGlobal(RuleTemplateContants.RULE_EXE_CONTEXT_NAME, exeContext);
		session.setGlobal(RuleTemplateContants.RULE_MATCH_CONTEXT_NAME, matchContext);
		if (!BlankUtil.isBlank(globals)) {
			for (String key : globals.keySet()) {
				session.setGlobal(key, globals.get(key));
			}
		}
	}

	/**
	 * 设置全局变量
	 * 
	 * @param session
	 * @param facts
	 */
	private void setGlobals(RuleMatchContext matchContext, RuleExecuteContext exeContext, KieSession session, FactsContainer factsContainer,  Map<String, Object> globals) {
		exeContext.setFactsContainer(factsContainer);
		session.setGlobal(RuleTemplateContants.RULE_EXE_CONTEXT_NAME, exeContext);
		session.setGlobal(RuleTemplateContants.RULE_MATCH_CONTEXT_NAME, matchContext);
		if (!BlankUtil.isBlank(globals)) {
			for (String key : globals.keySet()) {
				session.setGlobal(key, globals.get(key));
			}
		}
	}
}
