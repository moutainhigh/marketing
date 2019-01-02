package com.oristartech.rule.vos.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 规则执行结果接口
 * @author chenjunfei
 *
 */
public class RuleResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7015596517306499936L;

	/**
	 * 关联的rule信息
	 */
	private ResultRuleVO ruleVO;
	
	/**
	 * 运行次数
	 */
	private Integer runTimes;

	/**
	 * 记录匹配规则条件需要的fact。
	 */
	private List<Object> conditionFacts = new ArrayList<Object>();
	
	/**
	 * 匹配规则条件事实类型
	 */
	private Set<String> conditionFactTypes ;
	
	/**
	 * 额外的和业务相关的结果属性. 例如票券是否兑换完的属性. 便于过滤处理,和结果返回. 
	 */
	private Map<String, Object> bizPropertyMap = new HashMap<String, Object>();
	
	/**
	 * 记录匹配规则条件需要的fact, map, 方便排重, 不会输出到客户端.
	 */
	@JsonIgnore
	private Map<String, Object> conditionFactMap = new HashMap<String, Object>();

	/**
	 * 新写的规则接口最好使用本属性. 通过设置RuleContextInfo fact中的isMapResult属性.
	 * 为方便获取结果，解析结果。提供map的结果结构
	 * key为action的fnEffect， value为action结果，而且value类型是对象，还是list和方法相关。
	 * 执行方法要实现指定方法设置进去。
	 * 要注意，不同的方法可以有相同的fnEffect。
	 * 新接口应该使用resultMap获取结果。
	 * 
	 */
	private Map<String, Object> resultMap = new HashMap<String, Object>();
	
	/**
	 * 结果中，执行方法的分类。
	 */
	private Set<String> fnCategories = new HashSet<String>();
	
	/**
	 * 提供list的结果结构.其中包含每一个执行方法结果。
	 * 新接口应该使用resultMap解析结果， 保留是为了兼容已使用的接口。
	 * 
	 * results和resultMap只返回其中一个，最后在结果filter中确定返回那个。 
	 * 
	 * 以后只在内部使用,外部应该用resultMap。 
	 * @deprecated
	 */
	private List<RuleActionResult> results = new ArrayList<RuleActionResult>();
	
	/**
	 * 匹配的facts map
	 */
	@JsonIgnore
	private Map<String, Object> matchFactMap = new HashMap<String, Object>();
	
	/**
	 * 保存方法修改的参数fact, 额外添加的对象例如送东西,不放在里面， 方便在需要判断已修改时有用。 key 现在是fact的num
	 */
	@JsonIgnore
	private Map<String, Object> modifiedFactMap = new HashMap<String, Object>();
	
	/**
	 * 包含的所有子项结果Map
	 */
	@JsonIgnore
	private Map<Long, RuleSectionResult> sectionResultsMap = new HashMap<Long, RuleSectionResult>();
	
	public Map<String, Object> getConditionFactMap() {
		return this.conditionFactMap;
	}
	
	public Map<String, Object> getBizPropertyMap() {
    	return bizPropertyMap;
    }

	/**
	 * @return the fnCategories
	 */
	public Set<String> getFnCategories() {
		return fnCategories;
	}

	/**
	 * @param fnCategories the fnCategories to set
	 */
	public void setFnCategories(Set<String> fnCategories) {
		this.fnCategories = fnCategories;
	}

	/**
	 * @return the resultMap
	 */
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	/**
	 * @param resultMap the resultMap to set
	 */
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public List<Object> getConditionFacts() {
    	return conditionFacts;
    }

	public Integer getRunTimes() {
    	return runTimes;
    }

	public void setRunTimes(Integer runTimes) {
    	this.runTimes = runTimes;
    }


	public void setConditionFacts(List<Object> conditionFacts) {
    	this.conditionFacts = conditionFacts;
    }

	
	/**
	 * 添加规则消耗事实
	 * @param fact
	 * @param keyProperty
	 */
	public void addConditionFact(Object fact, String key) {
		if(!BlankUtil.isBlank(key) && !this.conditionFactMap.containsKey(key)) {
			this.conditionFactMap.put(key, fact);
			this.conditionFacts.add(fact);
		}
	}
	
	
	/**
	 * 是否已经存在条件事实
	 * @param key
	 * @return
	 */
	public boolean existConditionFact(String key) {
		if(!BlankUtil.isBlank(this.conditionFactMap) && this.conditionFactMap.containsKey(key)) {
			return true;
		}
		return false;
	}
	
	public Map<String, Object> getModifiedFactMap() {
		return modifiedFactMap;
	}

	@JsonIgnore
	public Collection<Object> getModifiedFacts() {
		if(!BlankUtil.isBlank(modifiedFactMap )) {
			return modifiedFactMap.values();
		}
		return null;
	}
	
	public Object getModifiedFact(String key) {
		if(this.modifiedFactMap != null) {
			return this.modifiedFactMap.get(key);
		}
		return null;
	}
	
	/**
	 * 保存方法修改的参数fact, 额外添加的对象例如送东西,不放在里面， 方便在需要判断已修改时有用。 key 现在是fact的num
	 */
	public void addModifiedFact(String key, Object fact) {
		if(this.modifiedFactMap == null) {
			this.modifiedFactMap = new HashMap<String, Object>();
		}
		if(!BlankUtil.isBlank(key) && !this.modifiedFactMap.containsKey(key)) {
			this.modifiedFactMap.put(key, fact);
		}
	}
	
	/**
	 * results只是为了方便返回，每次都会从sectionResults中获取，因为sectionResults 才最终保存结果
	 * 一般在执行方法中调用的的addSectionResult。若需要修改result 集合，可以获取到结果后 进行修改
	 * @return
	 */
	@Deprecated
	public List<RuleActionResult> getResults() {
		if(!BlankUtil.isBlank(this.sectionResultsMap)) {
			List<RuleActionResult> finalResults = new ArrayList<RuleActionResult>();
			for(RuleSectionResult secResult : this.sectionResultsMap.values()) {
				if(!BlankUtil.isBlank(secResult.getActionResults())) {
					finalResults.addAll(secResult.getActionResults());
				}
			}
			return finalResults;
		}
    	return null;
    }

	/**
	 * 若直接调用本方法，会清空所有sectionResult。
	 * 而且若参数不空，会设置一个section id为-1的sectionResult,
	 * 一般在执行方法中调用的的addSectionResult。
	 * @param results
	 */
	@Deprecated
	public void setResults(List<RuleActionResult> results) {
		clearResults();
		if(!BlankUtil.isBlank(results)) {
			RuleSectionResult secResult = new RuleSectionResult();
			secResult.setRuleSectionId(-1L);
			secResult.setActionResults(results);
			this.addSectionResult(secResult);
		}
	}
	
	public void clearResults() {
		if(!BlankUtil.isBlank(this.sectionResultsMap)) {
			this.sectionResultsMap.clear();
		}
	}
	
	public void addMatchFacts(List<Object> facts, String keyName) {
		if(!BlankUtil.isBlank(facts)) {
			for(Object fact : facts) {
				String key = getBeanValue(fact, keyName);
				if(!BlankUtil.isBlank(key) && !matchFactMap.containsKey(key) ) {
					matchFactMap.put(key, fact);
				}
			}
		}
	}

	@JsonIgnore
	public Collection<Object> getMatchFacts() {
		if(matchFactMap != null) {
			return matchFactMap.values();
		}
    	return null;
    }

	public ResultRuleVO getRuleVO() {
    	return ruleVO;
    }

	public void setRuleVO(ResultRuleVO ruleVO) {
    	this.ruleVO = ruleVO;
    }

	@JsonIgnore
	public Collection<RuleSectionResult> getSectionResults() {
		if(!BlankUtil.isBlank(this.sectionResultsMap)) {
			return this.sectionResultsMap.values();
		}
    	return new ArrayList<RuleSectionResult>();
    }

	public void addSectionResult(RuleSectionResult sectionResult) {
		sectionResultsMap.put(sectionResult.getRuleSectionId(), sectionResult);
	}
	
	public RuleSectionResult getSectionResult(Long ruleSectionId) {
		if(!BlankUtil.isBlank(sectionResultsMap) ) {
			return sectionResultsMap.get(ruleSectionId);
		}
		return null;
	}
	
	public boolean existSectionResult(Long ruleSectionId) {
		if(!BlankUtil.isBlank(sectionResultsMap) ) {
			if(sectionResultsMap.containsKey(ruleSectionId)) {
				return true;
			}
		}
		return false;
	}
	
	private String getBeanValue(Object obj ,String property) {
		if(obj != null && !BlankUtil.isBlank(property)) {
			try {
				return BeanUtils.getProperty(obj, property);
			} catch (Exception e) {
				throw new RuntimeException("获取对象属性值错误", e);
			}
		}
		return null;
	}

	public Set<String> getConditionFactTypes() {
    	return conditionFactTypes;
    }

	public void setConditionFactTypes(Set<String> conditionFactTypes) {
    	this.conditionFactTypes = conditionFactTypes;
    }
	
	/**
	 * 从resultMap获取对应的key的值
	 * @param key
	 * @return
	 */
	public Object getActionResult(String key) {
		if(BlankUtil.isBlank(this.resultMap)) {
			return null;
		}
		return this.resultMap.get(key);
	}
}
