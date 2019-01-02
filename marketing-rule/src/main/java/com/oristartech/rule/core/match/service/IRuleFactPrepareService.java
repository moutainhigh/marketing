package com.oristartech.rule.core.match.service;

import java.util.List;

import com.oristartech.rule.drools.executor.context.FactsContainer;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;

public interface IRuleFactPrepareService {
	/**
	 * 匹配前对事实数据进行预处理, 设置相应的数据类型.
	 * @param isTest 是否测试
	 * @param srcFacts
	 * @param ruleType 规则类型
	 * @return
	 */
	FactsContainer prepareFacts(String facts, boolean isTest, RuleTypeVO ruleType) ;
	
	/**
	 * 匹配前对事实数据进行预处理, 设置相应的数据类型.
	 * @param isTest 是否测试
	 * @param srcFacts 
	 * @param ruleType 规则类型
	 * @return
	 */
	FactsContainer prepareFacts(List<Object> facts,  boolean isTest,  RuleTypeVO ruleType) ;
}
