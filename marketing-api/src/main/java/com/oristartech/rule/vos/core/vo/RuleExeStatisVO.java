package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;
import java.util.Date;

public class RuleExeStatisVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -623384119496082296L;

	private Long id;
	
	/**
	 * 关联的ruleid
	 */
	private Integer ruleId;
	
	/**
	 * 执行日期
	 */
	private Date exeDate;
	
	/**
	 * 在exeDate指定的日期中的运行次数
	 */
	private Integer amount;

	public Long getId() {
    	return id;
    }

	public void setId(Long id) {
    	this.id = id;
    }

	public Integer getRuleId() {
    	return ruleId;
    }

	public void setRuleId(Integer ruleId) {
    	this.ruleId = ruleId;
    }

	public Date getExeDate() {
    	return exeDate;
    }

	public void setExeDate(Date exeDate) {
    	this.exeDate = exeDate;
    }

	public Integer getAmount() {
    	return amount;
    }

	public void setAmount(Integer amount) {
    	this.amount = amount;
    }
}
