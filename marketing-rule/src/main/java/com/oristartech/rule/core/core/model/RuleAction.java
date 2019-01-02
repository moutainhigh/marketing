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
//import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.oristartech.rule.core.base.model.ActionFunction;

/**
 * @author chenjunfei
 * @version 1.0
 * @created 21-十月-2013 10:17:57
 */
@Entity
@Table(name="RULE_CORE_RULE_ACTION")
public class RuleAction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	/**
	 * 与或关系，true是And，false是or
	 */
	@Column(name = "IS_AND")
	private Boolean isAnd;

	/**
	 * 操作函数
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@ForeignKey(name = "FK_RULE_ACTION_FUNCTION")
	@JoinColumn(name = "ACTION_FUNCTION_ID")
	private ActionFunction actionFunction;
	/**
	 * 所属规则块
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@ForeignKey(name = "FK_RULE_ACTION_RULE_SECTION")
	@JoinColumn(name = "RULE_SECTION_ID")
	private RuleSection ruleSection;

	/**
	 * 对应的functionGroup 的id,定义时动态产生的动作用到。 可空, 为空表名不是动态添加的动作
	 */
	@Column(name = "FUNC_GROUP_ID")
	private Integer funcGroupId;
	
	/**
	 * 包含参数值
	 */
	@OneToMany(mappedBy="ruleAction", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private List<RuleActionParameter> parameters;
	
	public Long getId() {
    	return id;
    }

	public void setId(Long id) {
    	this.id = id;
    }

	public RuleSection getRuleSection() {
    	return ruleSection;
    }

	public void setRuleSection(RuleSection ruleSection) {
    	this.ruleSection = ruleSection;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public Boolean getIsAnd() {
		return isAnd;
	}

	public void setIsAnd(Boolean isAnd) {
		this.isAnd = isAnd;
	}

	public ActionFunction getActionFunction() {
		return actionFunction;
	}

	public void setActionFunction(ActionFunction actionFunction) {
		this.actionFunction = actionFunction;
	}

	public Integer getFuncGroupId() {
    	return funcGroupId;
    }

	public void setFuncGroupId(Integer funcGroupId) {
    	this.funcGroupId = funcGroupId;
    }

	public List<RuleActionParameter> getParameters() {
    	return parameters;
    }

	public void setParameters(List<RuleActionParameter> parameters) {
    	this.parameters = parameters;
    }
}