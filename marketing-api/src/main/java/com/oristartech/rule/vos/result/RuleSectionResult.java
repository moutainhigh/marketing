package com.oristartech.rule.vos.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 规则块结果
 * @author chenjunfei
 *
 */
public class RuleSectionResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4315258281970268182L;

	/**
	 * 所属规则块
	 */
	private Long ruleSectionId;
	
	/**
	 * 匹配本section 的facts map, 便于section判断是否已对该fact运行过修改, key是在ruleService放进去的_num
	 */
	@JsonIgnore
	private Map<String, Object> matchFactMap = new HashMap<String, Object>();
	
	/**
	 * 下面每个action的结果
	 */
	List<RuleActionResult> actionResults = new ArrayList<RuleActionResult>();

	/**
	 * actionResult 的Map, 方便获取指定存在的RuleActionResult, key 是action id
	 */
	@JsonIgnore
	private Map<String, RuleActionResult> actionResultMap = new HashMap<String, RuleActionResult>();
	
	/**
	 * 结果中修改事实的数量, 某些业务方法才设置本属性, 某些不会, 具体看业务.
	 */
	@JsonIgnore
	private int modifyFactAmount = 0;
	
	public int getModifyFactAmount() {
    	return modifyFactAmount;
    }

	public void setModifyFactAmount(int modifyFactAmount) {
    	this.modifyFactAmount = modifyFactAmount;
    }

	public RuleActionResult getActionResult(Long actionId, Integer runIndex) {
		String key = composeActionResultKey(actionId, runIndex);
		return actionResultMap.get(key);
	}
	
	private String composeActionResultKey(Long actionId, Integer runIndex) {
		return actionId + "-" + runIndex;
	}
	
	public void addMatchFacts(List<Object> facts, String keyName) {
		if(!BlankUtil.isBlank(facts)) {
			try {
				for(Object fact : facts) {
					String key = BeanUtils.getProperty(fact, keyName);
					if(!BlankUtil.isBlank(key) && !matchFactMap.containsKey(key)) {
						matchFactMap.put(key, fact);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}
	}
	
	public boolean existMatchFact(String num) {
		return matchFactMap.containsKey(num);
	}
	
	public Long getRuleSectionId() {
    	return ruleSectionId;
    }

	public void setRuleSectionId(Long ruleSectionId) {
    	this.ruleSectionId = ruleSectionId;
    }

	public List<RuleActionResult> getActionResults() {
    	return actionResults;
    }

	public void setActionResults(List<RuleActionResult> actionResults) {
    	this.actionResults = actionResults;
    	this.actionResultMap.clear();
    	if(!BlankUtil.isBlank(actionResults)) {
    		for(RuleActionResult anResult : actionResults) {
    			String key = composeActionResultKey(anResult.getRuleActionId(), anResult.getRunIndex());
    			actionResultMap.put(key, anResult);
    		}
    	}
    }
	
	public void addActionResult(RuleActionResult result) {
		if(result == null ) {
			return ;
		}
		String key = composeActionResultKey(result.getRuleActionId(), result.getRunIndex());
		
		if(actionResultMap.containsKey(key)) {
			return;
		}
		if(this.actionResults == null) {
			this.actionResults = new ArrayList<RuleActionResult>(); 
		}
		this.actionResults.add(result);
		actionResultMap.put(key, result);
	}
}
