package com.oristartech.rule.core.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 规则执行统计类
 * @author chenjunfei
 *
 */
@Entity
@Table(name="RULE_CORE_EXE_STATIS")
public class RuleExeStatis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	/**
	 * 关联的ruleid
	 */
	@Column(name = "RULE_ID")
	private Integer ruleId;
	
	/**
	 * 执行日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "EXE_DATE")
	private Date exeDate;
	
	/**
	 * 在exeDate指定的日期中的运行次数
	 */
	@Column(name = "AMOUNT")
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
