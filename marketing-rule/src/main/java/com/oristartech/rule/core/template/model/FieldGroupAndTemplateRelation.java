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
 * 属性分组和模板关系
 * 
 * @author chenjunfei
 * @version 1.0
 * @created 21-十一月-2013 09:56:21
 */
@Entity
@Table(name="RULE_TPL_FIELD_GROUP_R_TEMPLATE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class FieldGroupAndTemplateRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 属性分组
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FIELD_GROUP_TEMPLATE_RELATION_FIELD_GROUP")
	@JoinColumn(name = "FIELD_GROUP_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private FieldGroup fieldGroup;
	/**
	 * 规则元素模板
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FIELD_GROUP_TEMPLATE_RELATION_EMEMENT_TEMPLATE")
	@JoinColumn(name = "RULE_ELEMENT_TEMPLATE_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private RuleElementTemplate ruleElementTemplate;
	/**
	 * 是否公共条件
	 */
	@Column(name="IS_COMMON")
	private Boolean isCommon;
	/**
	 * isFix
	 */
	@Column(name="IS_FIX")
	private Boolean isFix;
	
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

	public FieldGroup getFieldGroup() {
		return fieldGroup;
	}

	public void setFieldGroup(FieldGroup fieldGroup) {
		this.fieldGroup = fieldGroup;
	}

	public RuleElementTemplate getRuleElementTemplate() {
		return ruleElementTemplate;
	}

	public void setRuleElementTemplate(RuleElementTemplate ruleElementTemplate) {
		this.ruleElementTemplate = ruleElementTemplate;
	}

	public Boolean getIsCommon() {
    	return isCommon;
    }

	public void setIsCommon(Boolean isCommon) {
    	this.isCommon = isCommon;
    }

	public Boolean getIsFix() {
		return isFix;
	}

	public void setIsFix(Boolean isFix) {
		this.isFix = isFix;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	
}