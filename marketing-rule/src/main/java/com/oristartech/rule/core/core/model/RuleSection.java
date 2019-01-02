package com.oristartech.rule.core.core.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * 规则块, 一个块代表一个when then,可以嵌套, 即相当于程序的if(){ if(){ if(){}}}, 为避免规则的复杂度, 最好只嵌套一层.
 * @author chenjunfei
 * @version 1.0
 * @updated 05-十二月-2013 16:19:16
 */
@Entity
@Table(name="RULE_CORE_RULE_SECTION")
public class RuleSection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	/**
	 * section间顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	
	/**
	 * 条件集合
	 */
	@OneToMany(mappedBy="ruleSection",fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@OnDelete(action=OnDeleteAction.CASCADE)
	private List<RuleCondition> ruleConditions;
	/**
	 * 规则动作集合
	 */
	@OneToMany(mappedBy="ruleSection",fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@OnDelete(action=OnDeleteAction.CASCADE)
	private List<RuleAction> ruleActions;
	/**
	 * 归属规则
	 */
	@ManyToOne
	@ForeignKey(name="FK_RULE_SECTION_RULE")
	@JoinColumn(name="RULE_ID")
	private Rule rule;
	
	
	/**
	 * 关联的规则组,因为规则组中可以有共同的属性和操作
	 */
	@ManyToOne
	@ForeignKey(name="FK_RULE_SECTION_RULE_GROUP")
	@JoinColumn(name="RULE_GROUP_ID")
	private RuleGroup ruleGroup;
	
	/**
	 * 和紧跟后面的section是否串行关系, null,或false 是并行, true是串行. 
	 */
	@Column(name = "IS_SERIAL")
	private Boolean isSerial;

	public Long getId() {
    	return id;
    }

	public void setId(Long id) {
    	this.id = id;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public List<RuleCondition> getRuleConditions() {
		return ruleConditions;
	}

	public void setRuleConditions(List<RuleCondition> ruleConditions) {
		this.ruleConditions = ruleConditions;
	}

	public List<RuleAction> getRuleActions() {
		return ruleActions;
	}

	public void setRuleActions(List<RuleAction> ruleActions) {
		this.ruleActions = ruleActions;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}


	public RuleGroup getRuleGroup() {
		return ruleGroup;
	}

	public void setRuleGroup(RuleGroup ruleGroup) {
		this.ruleGroup = ruleGroup;
	}

	public Boolean getIsSerial() {
    	return isSerial;
    }

	public void setIsSerial(Boolean isSerial) {
    	this.isSerial = isSerial;
    }
}