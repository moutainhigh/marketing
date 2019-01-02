package com.oristartech.rule.functions.sort.util;

import java.math.BigDecimal;

import com.oristartech.rule.vos.result.RuleResult;

//计价动作规则和总计内部类,便于排序
public class RuleResultAmountPair {
	private RuleResult result;
	private BigDecimal amount;
	public RuleResult getResult() {
    	return result;
    }
	public void setResult(RuleResult result) {
    	this.result = result;
    }
	public BigDecimal getAmount() {
    	return amount;
    }
	public void setAmount(BigDecimal amount) {
    	this.amount = amount;
    }
}
