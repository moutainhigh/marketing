package com.oristartech.rule.core.init.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.rule.config.RuleProperties;
import com.oristartech.rule.core.data.sync.service.ITaskNoteRegisterService;
import com.oristartech.rule.core.init.service.IRuleExecutorInitService;
import com.oristartech.rule.core.init.service.IRuleSystemInitService;

@Component
public class RuleSystemInitServiceImpl implements IRuleSystemInitService {

	@Autowired
	private IRuleExecutorInitService ruleExecutorInitService;
	@Autowired
	private ITaskNoteRegisterService ruleTaskNoteRegisterService;
	@Autowired
	private RuleProperties ruleSystemConfigProps;
	private static String INIT_LOAD_VALUE = "1";
	
	public void setRuleTaskNoteRegisterService(ITaskNoteRegisterService ruleTaskNoteRegisterService) {
    	this.ruleTaskNoteRegisterService = ruleTaskNoteRegisterService;
    }

	/**
	 * 初始化系统
	 */
	public void init() {
		initExecutor();
		initRegisterThread();
	}
	
	private void initExecutor() {
		String initConfig = ruleSystemConfigProps.getInitLoad();
		if(INIT_LOAD_VALUE.equals(initConfig) && ruleExecutorInitService != null) {
			ruleExecutorInitService.init();
		}
	}
	
	private void initRegisterThread() {
		Thread thread = new Thread(ruleTaskNoteRegisterService);
		thread.start();
	}
	public  void setRuleExecutorInitService(IRuleExecutorInitService ruleExecutorInitService) {
    	this.ruleExecutorInitService = ruleExecutorInitService;
    }

}
