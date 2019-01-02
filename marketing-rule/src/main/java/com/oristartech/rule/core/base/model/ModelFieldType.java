package com.oristartech.rule.core.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 属性数据类型
 * 
 * @author chenjunfei
 * @version 1.0
 * @updated 21-十一月-2013 09:49:58
 */
@Entity
@Table(name="RULE_BASE_MODEL_FIELD_TYPE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class ModelFieldType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 类型全名,如java.util.Date,唯一
	 */
	@Column(length=30, name="NAME")
	private String name;
	/**
	 * 中文名称
	 */
	@Column(length=50, name="CN_NAME")
	private String cnName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

}