package com.oristartech.rule.core.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import com.oristartech.rule.core.base.model.ActionFunction;

/**
 * 方法组和方法关系
 * 
 * @author chenjunfei
 * @version 1.0
 * @created 25-十一月-2013 17:06:31
 */
@Entity
@Table(name = "RULE_TPL_FUNCTION_GROUP_R_FUNCTION")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class FunctionGroupAndFunctionRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 执行方法
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FUNCTION_GROUP_ACTION_FUNCTION")
	@JoinColumn(name = "ACTION_FUNCTION_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ActionFunction actionFunction;
	/**
	 * 方法组
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FUNCTION_GROUP_FUNCTION_GROUP")
	@JoinColumn(name = "FUNCTION_GROUP_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private FunctionGroup functionGroup;

	/**
	 * 顺序号
	 */
	@Column(name="SEQ_NUM")
	private Integer seqNum;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ActionFunction getActionFunction() {
		return actionFunction;
	}

	public void setActionFunction(ActionFunction actionFunction) {
		this.actionFunction = actionFunction;
	}

	public FunctionGroup getFunctionGroup() {
		return functionGroup;
	}

	public void setFunctionGroup(FunctionGroup functionGroup) {
		this.functionGroup = functionGroup;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
}