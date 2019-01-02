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

/**
 * 方法和模板关系
 * @author chenjunfei
 * @version 1.0
 * @updated 25-十一月-2013 17:06:18
 */
@Entity
@Table(name="RULE_TPL_FUNCTION_GROUP_R_TEMPLATE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class FunctionGroupAndTemplateRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * 规则元素模板
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FUNCTION_GROUP_TPL_TEMPLATE")
	@JoinColumn(name = "RULE_EMEMENT_TEMPLATE_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private RuleElementTemplate ruleElementTemplate;
	/**
	 * 方法分组
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FUNCTION_GROUP_TPL_FUNCTION_GROUP")
	@JoinColumn(name = "FUNCTION_GROUP_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private FunctionGroup functionGroup;

	
	/**
	 * 顺序号
	 */
	@Column(name="SEQ_NUM")
	private Integer seqNum;
	
	/**
	 * 是否在本模板是必要条件
	 */
	@Column(name="IS_REQUIRED")
	private Boolean isRequired;
	
	public Boolean getIsRequired() {
    	return isRequired;
    }

	public void setIsRequired(Boolean isRequired) {
    	this.isRequired = isRequired;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FunctionGroup getFunctionGroup() {
    	return functionGroup;
    }

	public void setFunctionGroup(FunctionGroup functionGroup) {
    	this.functionGroup = functionGroup;
    }

	public RuleElementTemplate getRuleElementTemplate() {
		return ruleElementTemplate;
	}

	public void setRuleElementTemplate(RuleElementTemplate ruleElementTemplate) {
		this.ruleElementTemplate = ruleElementTemplate;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
}