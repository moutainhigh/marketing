package com.oristartech.rule.drools.executor.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.vos.result.RuleResult;

/**
 * 规则执行时的环境
 * @author chenjunfei
 *
 */
public class RuleExecuteContext extends RuleBaseContext {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5305295883378604279L;
	private Map<Integer, RuleResult> ruleResultMap = new HashMap<Integer, RuleResult>();
	private List<RuleResult> ruleResults = new ArrayList<RuleResult>();
	
	public void setRuleResultMap(Map<Integer, RuleResult> ruleResultMap) {
		this.ruleResultMap = ruleResultMap;
	}

	/**
	 * 获取进入规则前所有的事实数据。已经经过组装处理. 为了不影响别的规则逻辑, 事实源不应修改.
	 * 若要修改输入,先copy一份. 可以调用getAllCloneFacts获取所有clone的fact
	 * @return
	 */
	public List<Object> getAllFacts() {
		if(super.factsContainer != null) {
			return super.factsContainer.getFacts();
		} 
		return null; 
	}

	/**
	 * clone 一份所有fact，方便修改
	 * @return
	 */
	public List<Object> getAllCloneFacts() {
		if(super.factsContainer != null) {
			return super.factsContainer.getCloneFacts();
		} 
		return null;
	}
	
	/**
	 * 复制指定类型的fact
	 * @param type
	 * @return
	 */
	public List<Object> cloneFactsByType(String type) {
		if(BlankUtil.isBlank(type)) {
			return null;
		}
		if(super.factsContainer != null ) {
			return super.factsContainer.cloneFactsByType(type);
		}
		return null;
	}
	
	/**
	 * 返回所有规则结果列表
	 * @return
	 */
	public List<RuleResult> getRuleResults() {
    	return ruleResults;
    }

	/**
	 * 设置规则结果列表
	 * @return
	 */
	public void setRuleResults(List<RuleResult> results) {
		this.ruleResults = null;
		this.ruleResultMap = null;
	    if(!BlankUtil.isBlank(results)) {
	    	for(RuleResult result : results) {
	    		this.addRuleResult(result.getRuleVO().getId(), result);
	    	}
	    }
	}
	
	public RuleResult getRuleResult(Integer ruleId) {
		if(this.ruleResultMap != null) {
			return this.ruleResultMap.get(ruleId);
		}
	    return null;
	}
	
	/**
	 * 某次匹配调用后，所有匹配的规则，存放结果数据的地方。, key 是rule id
	 * @return
	 */
	public Map<Integer, RuleResult> getRuleResultMap() {
	    return this.ruleResultMap;
	}
	
	public void addRuleResult(Integer ruleId, RuleResult result) {
	    if(ruleId == null || result == null) {
	    	return;
	    }
	    if(ruleResultMap == null) {
	    	ruleResultMap = new HashMap<Integer, RuleResult>();
	    }
	    if(ruleResults == null) {
	    	ruleResults = new ArrayList<RuleResult>();
	    }
	    ruleResultMap.put(ruleId, result);
	    ruleResults.add(result);
	}
}
