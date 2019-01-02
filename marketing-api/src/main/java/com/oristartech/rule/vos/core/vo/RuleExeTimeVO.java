package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;
import java.util.Date;

public class RuleExeTimeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7651278641045490738L;

	private RuleVO ruleVO;
	
	private Integer ruleId;
	
	private Integer currentAmount;
	
	private Integer usedAmount;
	
	private Integer totalAmount;
	
	private Date startTime;
	
	private Date endTime;
	
	private String reason;

	public RuleVO getRuleVO() {
		return ruleVO;
	}

	public void setRuleVO(RuleVO ruleVO) {
		this.ruleVO = ruleVO;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Integer currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(Integer usedAmount) {
		this.usedAmount = usedAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
