package com.oristartech.rule.functions.sort.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.vos.result.RuleResult;

public class ResultKeyPair {
	private RuleResult result;
	private Set<String> factKeys = new HashSet<String>();

	public RuleResult getResult() {
		return result;
	}

	public void setResult(RuleResult result) {
		this.result = result;
	}

	public Set<String> getFactKeys() {
		return factKeys;
	}

	public void setFactKeys(Set<String> factKeys) {
		this.factKeys = factKeys;
	}

	public ResultKeyPair(RuleResult result) {
		this.result = result;
		List<Object> facts = result.getConditionFacts();

		if (!BlankUtil.isBlank(facts)) {
			for (Object fact : facts) {
				String key =  BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
				factKeys.add(key);
			}
		}
	}
	
	public boolean containFactKey(String key) {
		return this.factKeys.contains(key);
	}
}
