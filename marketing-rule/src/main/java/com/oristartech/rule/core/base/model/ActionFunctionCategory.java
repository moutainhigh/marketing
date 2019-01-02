package com.oristartech.rule.core.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.oristartech.rule.vos.core.enums.FunctionExecuteMode;

/**
 * 业务方法类型
 * @author chenjunfei
 * @version 1.0
 * @created 28-一月-2014 14:24:35
 */
@Entity
@Table(name="RULE_BASE_ACTION_FUNCTION_CATEGORY")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class ActionFunctionCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 分类名称
	 */
	@Column(length=30, name="NAME", unique= true, nullable=false)
	private String name;
	
	/**
	 * 中文名称
	 */
	@Column(length=50, name="CN_NAME")
	private String cnName;

	/**
	 * 方法执行模式
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "EXECUTE_MODE")
	private FunctionExecuteMode executeMode;
	
	
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

	public FunctionExecuteMode getExecuteMode() {
    	return executeMode;
    }

	public void setExecuteMode(FunctionExecuteMode executeMode) {
    	this.executeMode = executeMode;
    }
}