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

import com.oristartech.rule.core.base.model.ModelField;

/**
 * 属性组和业务属性关系
 * @author chenjunfei
 * @version 1.0
 * @updated 25-十一月-2013 17:04:46
 */
@Entity
@Table(name="RULE_TPL_FIELD_GROUP_R_FIELD")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class FieldGroupAndFieldRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 业务属性
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FIELD_GROUP_FIELD_RELATION_FIELD")
	@JoinColumn(name = "MODEL_FIELD_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ModelField modelField;
	/**
	 * 属性组
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FIELD_GROUP_FIELD_RELATION_FIELD_GROUP")
	@JoinColumn(name = "FIELD_GROUP_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private FieldGroup fieldGroup;
	
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	
	/**
	 * 是否隐藏field属性, 需要联动的属性,开始时是隐藏的. 
	 */
	@Column(name = "IS_HIDE_FIELD")
	private Boolean isHideField;
	
	/**
	 * 若test页面中, 当关联的fieldGroup出现, 若是必要的,即使条件中没有也要让用户添加. 
	 * 即依然在测试页面自动添加.
	 */
	@Column(name="IS_REQUIRED_IN_TEST")
	private Boolean isRequiredInTest;
	
	/**
	 * field在这个表里面的名字
	 */
	@Column(name="ALIAS")
	private String alias;
	
	public String getAlias() {
    	return alias;
    }

	public void setAlias(String alias) {
    	this.alias = alias;
    }

	public Boolean getIsRequiredInTest() {
    	return isRequiredInTest;
    }

	public void setIsRequiredInTest(Boolean isRequiredInTest) {
    	this.isRequiredInTest = isRequiredInTest;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ModelField getModelField() {
		return modelField;
	}

	public void setModelField(ModelField modelField) {
		this.modelField = modelField;
	}

	public FieldGroup getFieldGroup() {
		return fieldGroup;
	}

	public void setFieldGroup(FieldGroup fieldGroup) {
		this.fieldGroup = fieldGroup;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Boolean getIsHideField() {
		return isHideField;
	}

	public void setIsHideField(Boolean isHideField) {
		this.isHideField = isHideField;
	}
	
}