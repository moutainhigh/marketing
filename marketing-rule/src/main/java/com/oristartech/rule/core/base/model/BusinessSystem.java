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
 * @author chenjunfei
 * @version 1.0
 * @created 18-十月-2013 15:05:56
 */
@Entity
@Table(name = "RULE_BASE_BUSINESS_SYSTEM")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class BusinessSystem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 系统名称
	 */
	@Column(length = 50, nullable = false, name = "NAME")
	private String name;
	/**
	 * 中文名称
	 */
	@Column(length = 50, name = "CN_NAME")
	private String cnName;
	/**
	 * 系统版本
	 */
	@Column(length = 10, name = "VERSION")
	private String version;

	/**
	 * 系统webService baseUrl
	 */
	@Column(name = "BASE_URL")
	private String baseUrl;

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

	public String getVersion() {
    	return version;
    }

	public void setVersion(String version) {
    	this.version = version;
    }

	public String getBaseUrl() {
    	return baseUrl;
    }

	public void setBaseUrl(String baseUrl) {
    	this.baseUrl = baseUrl;
    }
}