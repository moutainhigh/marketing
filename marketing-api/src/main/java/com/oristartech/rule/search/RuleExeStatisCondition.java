package com.oristartech.rule.search;

import com.oristartech.rule.vos.core.enums.RuleExeStatisConType;

/**
 * 规则统计查询类
 * @author chenjunfei
 *
 */
public class RuleExeStatisCondition {
	public Integer ruleId;
	public RuleExeStatisConType constraintDateType;
	public String appointRunRestrainRange;
	public Integer constraintAmount;

	public Integer getRuleId() {
		return ruleId;
	}

	public RuleExeStatisConType getConstraintDateType() {
    	return constraintDateType;
    }


	public void setConstraintDateType(RuleExeStatisConType constraintDateType) {
    	this.constraintDateType = constraintDateType;
    }


	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getAppointRunRestrainRange() {
    	return appointRunRestrainRange;
    }

	public void setAppointRunRestrainRange(String appointRunRestrainRange) {
    	this.appointRunRestrainRange = appointRunRestrainRange;
    }

	public Integer getConstraintAmount() {
		return constraintAmount;
	}

	public void setConstraintAmount(Integer constraintAmount) {
		this.constraintAmount = constraintAmount;
	}
}
