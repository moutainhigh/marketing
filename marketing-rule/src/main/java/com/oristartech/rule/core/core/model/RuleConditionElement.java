package com.oristartech.rule.core.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.base.model.Operator;

/**
 * @author chenjunfei
 * @version 1.0
 * @created 21-十月-2013 10:17:57
 */
@Entity
@Table(name="RULE_CORE_RULE_CONDITION_ELEMENT")
public class RuleConditionElement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/**
	 * 逻辑符号，OR或AND， true为AND，false为OR
	 */
	@Column(name = "IS_AND")
	private Boolean isAnd;

	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	
	/**
	 * 左括号数
	 */
	@Column(name = "LEFT_BRACKET_NUM")
	private Integer leftBracketNum;
	/**
	 * 右括号数
	 */
	@Column(name = "RIGHT_BRACKET_NUM")
	private Integer rightBracketNum;
	 
	/**
	 * 运算符
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@ForeignKey(name = "FK_RULE_CONDITION_OPERATOR")
	@JoinColumn(name="OPERATOR_ID")
	private Operator operator;

	/**
	 * 操作数。多个操作数，用&ldquo;,&rdquo;号隔开，若某个操作数是复合对象用json表示
	 */
	@Column(name = "OPERAND", length=65535)
	private String operand;
	
	/**
	 * 业务属性
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@ForeignKey(name="FK_RULE_CONDITION_MODEL_FIELD")
	@JoinColumn(name="MODEL_FIELD_ID")
	private ModelField modelField;
	/**
	 * 归属条件
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@ForeignKey(name="FK_RULE_CONDITION_ELEMENT_CONDITION")
	@JoinColumn(name="RULE_CONDITION_ID")
	private RuleCondition ruleCondition;
	/**
	 * 操作数对应的显示label, 格式和operand一一对应
	 */
	@Column(name="OPERAND_LABEL")
	private String operandLabel;
	
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

	public Boolean getIsAnd() {
    	return isAnd;
    }

	public void setIsAnd(Boolean isAnd) {
    	this.isAnd = isAnd;
    }

	public Integer getLeftBracketNum() {
    	return leftBracketNum;
    }

	public void setLeftBracketNum(Integer leftBracketNum) {
    	this.leftBracketNum = leftBracketNum;
    }

	public Integer getRightBracketNum() {
    	return rightBracketNum;
    }

	public void setRightBracketNum(Integer rightBracketNum) {
    	this.rightBracketNum = rightBracketNum;
    }

	public ModelField getModelField() {
    	return modelField;
    }

	public void setModelField(ModelField modelField) {
    	this.modelField = modelField;
    }

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public void setLeftBracketNum(int leftBracketNum) {
    	this.leftBracketNum = leftBracketNum;
    }

	public void setRightBracketNum(int rightBracketNum) {
    	this.rightBracketNum = rightBracketNum;
    }

	public RuleCondition getRuleCondition() {
    	return ruleCondition;
    }

	public void setRuleCondition(RuleCondition ruleCondition) {
    	this.ruleCondition = ruleCondition;
    }

	public String getOperandLabel() {
    	return operandLabel;
    }

	public void setOperandLabel(String operandLabel) {
    	this.operandLabel = operandLabel;
    }
}