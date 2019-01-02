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

import com.oristartech.rule.core.base.model.ModelCategory;

/**
 * @author chenjunfei
 * @version 1.0
 * @created 28-十一月-2013 15:03:47
 */
@Entity
@Table(name="RULE_CORE_RULE_CONDITION")
public class RuleCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	/**
	 * 业务分类
	 */
	@ManyToOne
	@ForeignKey(name = "FK_RULE_CONDITON_MODEL_CATEGORY")
	@JoinColumn(name = "RULE_MODEL_CATEGORY_ID")
	private ModelCategory modelCategory;
	
	/**
	 * 包含条件元素
	 */
	@OneToMany(mappedBy="ruleCondition",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private List<RuleConditionElement> conditionElements;
	/**
	 * 所属规则块
	 */
	@ManyToOne
	@ForeignKey(name = "FK_RULE_CONDITON_RULE_SECTION")
	@JoinColumn(name = "RULE_SECTION_ID")
	private RuleSection ruleSection;
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	
	/**
	 * 对应的fieldGroupId， 定义时动态产生的条件用到。 可空, 为空表名不是动态添加的条件
	 */
	@Column(name = "FIELD_GROUP_ID")
	private Integer fieldGroupId;

	/**
	 * 条件分类前导修饰符号, 例如exists, not等
	 */
	@Column(name = "MODIFIER", length=50)
	private String modifier;
	
	public String getModifier() {
    	return modifier;
    }

	public void setModifier(String modifier) {
    	this.modifier = modifier;
    }

	public Long getId() {
    	return id;
    }

	public void setId(Long id) {
    	this.id = id;
    }

	public ModelCategory getModelCategory() {
		return modelCategory;
	}

	public void setModelCategory(ModelCategory modelCategory) {
		this.modelCategory = modelCategory;
	}

	public List<RuleConditionElement> getConditionElements() {
    	return conditionElements;
    }

	public void setConditionElements(List<RuleConditionElement> conditionElements) {
    	this.conditionElements = conditionElements;
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

	public Integer getFieldGroupId() {
		return fieldGroupId;
	}

	public void setFieldGroupId(Integer fieldGroupId) {
		this.fieldGroupId = fieldGroupId;
	}
}