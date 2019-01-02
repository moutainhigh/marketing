package com.oristartech.rule.functions.sort.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.vos.result.RuleResult;

public class ResultsConsumeFactGroup {
	private List<RuleResult> results = new ArrayList<RuleResult>();
	//消耗事实的key
	private Set<String> factKeys = new HashSet<String>();
	
	private TreeSet<Integer> ruleIdSet = new TreeSet<Integer>();
	
	public List<RuleResult> getResults() {
    	return results;
    }
	public void setResults(List<RuleResult> results) {
    	this.results = results;
    }
	
	public Set<String> getFactKeys() {
    	return factKeys;
    }
	public void setFactKeys(Set<String> factKeys) {
    	this.factKeys = factKeys;
    }
	
	public boolean existNumKeyInConsumeFacts(ResultKeyPair keyPair){
		Set<String> keys = keyPair.getFactKeys();
		if(!BlankUtil.isBlank(keys)) {
			for(String key : keys) {
				if(factKeys.contains(key)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean addRuleResult(ResultKeyPair keyPair) {
		if(!existNumKeyInConsumeFacts(keyPair)) {
			this.results.add(keyPair.getResult());
			this.factKeys.addAll(keyPair.getFactKeys());
			this.ruleIdSet.add(keyPair.getResult().getRuleVO().getId());
			return true;
		}
		return false;
	}
	
	public boolean containGroup(ResultsConsumeFactGroup other) {
		return this.ruleIdSet.containsAll(other.ruleIdSet);
	}
}
