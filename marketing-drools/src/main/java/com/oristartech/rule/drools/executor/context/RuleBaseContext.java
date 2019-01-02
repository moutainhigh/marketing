package com.oristartech.rule.drools.executor.context;

import com.oristartech.rule.vos.core.vo.RuleGroupVO;

public abstract class RuleBaseContext {

	/**
	 * 保持事实的容器, 这里放进去的事实不能修改, 子类RuleExecuteContext, 提供clone方法
	 */
	protected FactsContainer factsContainer ;
	
	/**
	 * 是否在测试规则
	 */
	private boolean isTest;
	/**
	 * 若是测算, 测算传入的rule group, 因为测算的rule可能还没保存, 所以通过这里获取
	 */
	private RuleGroupVO testGroupVO;
	
	/**
	 * 不同的规则匹配可能需要暂缓一些数据，而且时效在一次匹配过程中。
	 */
	private RuleContextCacheManager contextManager = new RuleContextCacheManager();
	
	public FactsContainer getFactsContainer() {
    	return factsContainer;
    }

	public void setFactsContainer(FactsContainer factsContainer) {
    	this.factsContainer = factsContainer;
    }
	
	/**
	 * @return the contextManager
	 */
	public RuleContextCacheManager getContextManager() {
		return contextManager;
	}
	
	public RuleGroupVO getTestGroupVO() {
    	return testGroupVO;
    }

	public void setTestGroupVO(RuleGroupVO testGroupVO) {
    	this.testGroupVO = testGroupVO;
    }

	public boolean isTest() {
    	return isTest;
    }

	public void setTest(boolean isTest) {
    	this.isTest = isTest;
    }

	public void setContextManager(RuleContextCacheManager contextManager) {
		this.contextManager = contextManager;
	}
}
