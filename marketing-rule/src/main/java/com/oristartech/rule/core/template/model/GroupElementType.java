package com.oristartech.rule.core.template.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 规则元素类别
 * 
 * @author chenjunfei
 * @version 1.0
 * @created 21-十一月-2013 09:56:22
 */
@Entity
@Table(name = "RULE_TPL_GROUP_ELEMENT_TYPE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class GroupElementType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	/**
	 * 名称
	 */
	@Column(length = 50, nullable = false, name="NAME")
	private String name;
	
	/**
	 * 中文名称
	 */
	@Column(length = 50, nullable = false, name="CN_NAME")
	private String cnName;
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;

	/**
	 * 备注
	 */
	@Column(name="REMARK")
	private String remark;
	
	@OneToMany(mappedBy="groupElementType",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
	private List<FieldGroup> fieldGroups;
	
	@OneToMany(mappedBy="groupElementType",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
	private List<FunctionGroup> funcGroups;
	
	public List<FunctionGroup> getFuncGroups() {
    	return funcGroups;
    }

	public void setFuncGroups(List<FunctionGroup> funcGroups) {
    	this.funcGroups = funcGroups;
    }

	public List<FieldGroup> getFieldGroups() {
    	return fieldGroups;
    }

	public void setFieldGroups(List<FieldGroup> fieldGroups) {
    	this.fieldGroups = fieldGroups;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }
	
}