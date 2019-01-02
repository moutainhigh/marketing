package com.oristartech.rule.drools.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderConfiguration;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.marketing.core.exception.RuleParseRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;



/**
 * drool 规则添加移除等操作类
 * 
 * @author chenjunfei
 * 
 */
public class RuleKnowledgeWrapper extends AbstractRuleKnowledgeWrapper {
	private static String ENABLE_JMX = "rule.enable.drools.jmx";
	private static String TRUE = "true";
	private final static String EMPTY_KNOWLEDGE_MAP_KEY = "_EMPTY_KNOWLEDGE_MAP_KEY_";

	private RuleKnowledgeWrapper() {
		System.setProperty("drools.dateformat", "yyyy-MM-dd");
	}

	public static RuleKnowledgeWrapper getInstance() {
		if(instance == null) {
			instance = new RuleKnowledgeWrapper();
			knowledgeBasesCache = new ConcurrentHashMap<String, Map<String, InternalKnowledgeBase>>();
		}
		return instance;
	}

	private static final Logger log = LoggerFactory.getLogger(RuleKnowledgeWrapper.class);
	private static RuleKnowledgeWrapper instance = getInstance();
	private static Map<String,Map<String, InternalKnowledgeBase>> knowledgeBasesCache;

	/**
	 * 匹配规则，移除规则可以调用本方法获取KnowledgeBase。
	 * 
	 * @param knowledgeMapKey
	 * @return
	 */
	private InternalKnowledgeBase getKnowledgeBase(String tenantId,String knowledgeMapKey) {
		return getKnowledgeBase(tenantId,knowledgeMapKey, false);
	}

	private InternalKnowledgeBase getKnowledgeBase(String tenantId,String knowledgeMapKey, Boolean forLoadToEngine) {
		if(knowledgeBasesCache.containsKey(tenantId)) {
			if(knowledgeBasesCache.get(tenantId).containsKey(knowledgeMapKey))
				return knowledgeBasesCache.get(tenantId).get(knowledgeMapKey);
		}

		// 若果不是初始化加载，并接缓存中没有，表明现在来匹配规则时，还没有初始化相关KnowledgeBase。创建一个特别的KnowledgeBase。让匹配为空。
		if(forLoadToEngine == null || forLoadToEngine == false) {
			knowledgeMapKey = EMPTY_KNOWLEDGE_MAP_KEY;
		}
		return createKnowledgeBase(tenantId,knowledgeMapKey);
	}

	/**
	 * 创建KnowledgeBase， 并缓存在马匹中，这里必须同步，否则可能会创建多个同一类型的KnowledgeBase
	 * 
	 * @param knowledgeMapKey
	 * @return
	 */
	private synchronized InternalKnowledgeBase createKnowledgeBase(String tenantId,String knowledgeMapKey) {
		if(knowledgeBasesCache.containsKey(tenantId)) {
			// 若有则直接返回。同时移动到这里，是为了getKnowledgeBase不用同步。
			if(knowledgeBasesCache.get(tenantId).containsKey(knowledgeMapKey)) {
				return knowledgeBasesCache.get(tenantId).get(knowledgeMapKey);
			}
		}
		

		KieBaseConfiguration  kConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();

//		Properties ruleSystemConfigProps = (Properties) ApplicationContextHelper.getContext().getBean("ruleSystemConfigProps");
//		Object enableJmx = ruleSystemConfigProps.get(ENABLE_JMX);
//		if(TRUE.equals(enableJmx)) {
//			kConf.setOption(MBeansOption.ENABLED);
//		}

		InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase(kConf);
		
		
		if(knowledgeBasesCache.containsKey(tenantId)) {
			knowledgeBasesCache.get(tenantId).put(knowledgeMapKey, kBase);
		}else{
			Map<String, InternalKnowledgeBase> BaseCache = new ConcurrentHashMap<String, InternalKnowledgeBase>();
			BaseCache.put(knowledgeMapKey, kBase);
			knowledgeBasesCache.put(tenantId, BaseCache);
		}
		return kBase;
	}

	public StatelessKieSession getStatelessSession(String tenantId,String knowledgeMapKey) {
		return this.getKnowledgeBase(tenantId,knowledgeMapKey).newStatelessKieSession();
	}

	public KieSession getStatefulSession(String tenantId,String knowledgeMapKey) {
		return this.getKnowledgeBase(tenantId,knowledgeMapKey).newKieSession();
	}

	/**
	 * 是否存在对应的rule
	 * 
	 * @param knowledgeMapKey 缓存KnowledgeBase的key，通常是规则类型。
	 * @param packageName package 名
	 * @param ruleName
	 *            规则名
	 * @return
	 */
	public boolean existRule(String tenantId,String knowledgeMapKey, String packageName, String ruleName) {
		InternalKnowledgeBase kbase = this.getKnowledgeBase(tenantId,knowledgeMapKey);
		Rule kbRule = kbase.getRule(packageName, ruleName);
		return kbRule != null;
	}

	/**
	 * 移除指定规则
	 * 
	 * @param knowledgeMapKey
	 * @param packageName
	 * @param names
	 */
	public void removeRule(String tenantId,String knowledgeMapKey, String packageName, List<String> names) {
		InternalKnowledgeBase kbase = this.getKnowledgeBase(tenantId,knowledgeMapKey);
		if(!BlankUtil.isBlank(names)) {
			for(String name : names) {
				if(existRule(tenantId,knowledgeMapKey, packageName, name)) {
					kbase.removeRule(packageName, name);
					if(log.isInfoEnabled()) {
						log.info("remove rule, packageName: {0}, ruleName: {1} ", packageName, name);
					}
				}
			}
		}
	}

	/**
	 * 移除指定规则package， 指定package里面的rule也会移除
	 * 
	 * @param knowledgeMapKey
	 * @param packageNames
	 */
	public void removeKnowledgePackage(String tenantId,String knowledgeMapKey, List<String> packageNames) {
		InternalKnowledgeBase kbase = this.getKnowledgeBase(tenantId,knowledgeMapKey);
		if(kbase != null && !BlankUtil.isBlank(packageNames)) {
			Map<String, Boolean> removedMap = new HashMap<String, Boolean>();
			for(String pkName : packageNames) {
				if(removedMap.get(pkName) == null && kbase.getKiePackage(pkName) != null) {
					removedMap.put(pkName, true);
					kbase.removeKiePackage(pkName);
					log.info("remove rule, packageName: " + pkName);
				}
			}
		}
	}

	/**
	 * 
	 * @param knowledgeMapKey
	 *            缓存KnowledgeBase的key，通常是规则类型。
	 * @param rulePackageMap
	 *            要添加的rule的对应的package和rulename，
	 *            在已存在rule时，先remove改rule。key是packagename, value
	 *            是包含的rulename的list
	 * @param content
	 *            规则内容
	 * @return
	 */
	protected List<String> addKnowledgePackage(String tenantId,String knowledgeMapKey, Map<String, List<String>> removeRulesPackageMap,
	        String... content) {
		if(content == null || content.length < 1) {
			return null;
		}
		validRuleContent(content);
		removePackageRules(tenantId,knowledgeMapKey, removeRulesPackageMap);
		Collection<KiePackage> kpackages = loadKnowledgePackagesFromString(content);
		InternalKnowledgeBase kbase = this.getKnowledgeBase(tenantId,knowledgeMapKey, true);
		kbase.addPackages(kpackages);
		return getKnowledgePackageNames(kpackages);
	}

	private void removePackageRules(String tenantId,String knowledgeMapKey, Map<String, List<String>> removeRulesPackageMap) {
		if(!BlankUtil.isBlank(removeRulesPackageMap)) {
			// 现在的模板是一条规则一个package，直接移除package
			removeKnowledgePackage(tenantId,knowledgeMapKey, new ArrayList(removeRulesPackageMap.keySet()));
			// for(String packageName : removeRulesPackageMap.keySet()) {
			// removeRule(knowledgeMapKey, packageName,
			// removeRulesPackageMap.get(packageName));
			// }
		}
	}

	private List<String> getKnowledgePackageNames(Collection<KiePackage> kpackages) {
		if(!BlankUtil.isBlank(kpackages)) {
			List<String> names = new ArrayList<String>();
			for(KiePackage kpackage : kpackages) {
				names.add(kpackage.getName());
			}
			return names;
		}
		return null;
	}

	/**
	 * 创建规则信息
	 * 
	 * @param content
	 * @return
	 */
	private Collection<KiePackage> loadKnowledgePackagesFromString(String... content) {
		return loadKnowledgePackagesFromString(null, content);
	}

	private Collection<KiePackage> loadKnowledgePackagesFromString(KnowledgeBuilderConfiguration kbuilderConf,
	        String... content) {
		long start = System.currentTimeMillis();
		if(content == null || content.length < 1) {
			return null;
		}
		if(kbuilderConf == null) {
			kbuilderConf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
		}
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(kbuilderConf);
		if(log.isDebugEnabled()) {
			log.debug("--------create newKnowledgeBuilder time =" + (System.currentTimeMillis() - start));
		}
		for(String r : content) {
			if(!BlankUtil.isBlank(content)) {
				kbuilder.add(ResourceFactory.newByteArrayResource(r.getBytes()), ResourceType.DRL);
			}

		}
		if(kbuilder.hasErrors()) {
			throw new RuleParseRuntimeException(kbuilder.getErrors().toString());
		}
		Collection<KiePackage> knowledgePackages = kbuilder.getKnowledgePackages();

		if(log.isDebugEnabled()) {
			log.debug("--------create KnowledgePackages time =" + (System.currentTimeMillis() - start));
		}
		return knowledgePackages;
	}

	private void validRuleContent(String... content) {
		if(content == null || content.length < 1) {
			throw new RuleParseRuntimeException("规则内容不能为空");
		}
		for(String c : content) {
			if(BlankUtil.isBlank(c)) {
				throw new RuleParseRuntimeException("规则内容不能为空");
			}
		}
	}

	/**
	 * 移除整个kbase
	 * 
	 * @param knowledgeMapKey
	 */
	public void removeKBase(String tenantId,String knowledgeMapKey) {
		InternalKnowledgeBase kbase = this.getKnowledgeBase(tenantId,knowledgeMapKey);
		if(kbase != null) {
			// Collection<KnowledgePackage> pks = kbase.getKnowledgePackages();
			// if(!BlankUtil.isBlank(pks)) {
			// for(KnowledgePackage pk : pks) {
			// kbase.removeKnowledgePackage(pk.getName());
			// }
			// }
			knowledgeBasesCache.get(tenantId).remove(knowledgeMapKey);
		}

	}
}
