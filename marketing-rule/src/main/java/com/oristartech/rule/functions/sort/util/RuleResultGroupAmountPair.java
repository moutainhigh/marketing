package com.oristartech.rule.functions.sort.util;

import java.math.BigDecimal;

public class RuleResultGroupAmountPair {
	private ResultsConsumeFactGroup group;
	private BigDecimal amount;

	public ResultsConsumeFactGroup getGroup() {
    	return group;
    }

	public void setGroup(ResultsConsumeFactGroup group) {
    	this.group = group;
    }

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
