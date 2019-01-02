package com.oristartech.rule.drools.executor;

import java.util.List;
import java.util.Map;

public abstract class AbstractRuleKnowledgeWrapper {

	/**
	 * 
	 * @param knowledgeMapKey 缓存KnowledgeBase的key，通常是规则类型。
	 * @param rulePackageMap 要添加的rule的对应的package和rulename，
	 *                       在已存在rule时，先remove改rule。key是packagename, value 是包含的rulename的list
	 * @param content        若干规则内容
	 * @return
	 */
	public List<String> addRules(String tenantId,String knowledgeMapKey, Map<String, List<String>> removeRulesPackageMap, String ... content) {
		return this.addKnowledgePackage(tenantId,knowledgeMapKey, removeRulesPackageMap, content);
	}

	protected abstract List<String> addKnowledgePackage (String tenantId,String knowledgeMapKey ,Map<String, List<String>> removeRulesPackageMap, String... content);
	
	/**
	 * 是否存在对应的rule
	 * @param knowledgeMapKey 缓存KnowledgeBase的key，通常是规则类型。
	 * @param packageName  package 名
	 * @param ruleName 规则名
	 * @return
	 */
	public abstract boolean existRule(String tenantId,String knowledgeMapKey, String packageName, String ruleName);

	/**
	 * 移除指定规则
	 * @param knowledgeMapKey
	 * @param packageName
	 * @param names
	 */
	public abstract void removeRule(String tenantId,String knowledgeMapKey, String packageName, List<String> names);
	
	/**
	 * 移除指定规则package， 指定package里面的rule也会移除
	 * @param knowledgeMapKey
	 * @param packageNames
	 */
	public abstract void removeKnowledgePackage(String tenantId,String knowledgeMapKey, List<String> packageNames);

	/**
	 * 移除整个kbase
	 * @param knowledgeMapKey
	 */
	public abstract void removeKBase(String tenantId,String knowledgeMapKey) ;

}
