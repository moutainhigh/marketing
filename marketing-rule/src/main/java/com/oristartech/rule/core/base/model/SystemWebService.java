package com.oristartech.rule.core.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import com.oristartech.rule.vos.base.enums.ServiceType;

/**
 * webService管理类
 * @author chenjunfei
 * @version 1.0
 * @created 18-一月-2014 18:52:17
 */
@Entity
@Table(name="RULE_BASE_SYSTEM_WEB_SERVICE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class SystemWebService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * service类型
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "SERVICE_TYPE")
	private ServiceType serviceType;
	
	/**
	 * 若serviceType是Bean的情况下,设置本service bean名称
	 */
	@Column(name = "SERVICE_BEAN_NAME")
	private String serviceBeanName;
	/**
	 * service入口(通常是service方法)
	 */
	@Column(name = "SERVICE_ENTRY", length=50)
	private String serviceEntry;
	
	/**
	 * 入口中的方法(通常是一个入口多个方法的情况)
	 */
	@Column(name = "ENTRY_METHOD", length=50)
	private String entryMethod;
	
	/**
	 * 关联的业务系统
	 */
	@ManyToOne
	@ForeignKey(name = "FK_SYS_WEB_SERVICE_BIZ_SYSTEM")
	@JoinColumn(name = "BIZ_SYSTEM_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private BusinessSystem bizSystem;
	
	/**
	 * 中文名称
	 */
	@Column(name = "CN_NAME", length=30)
	private String cnName;
	
	/**
	 * 英文名称(唯一)
	 */
	@Column(name = "NAME", length=30, unique=true)
	private String name;

	
	/**
	 * 接口版本号
	 */
	@Column(name = "SERVICE_VERSION", length=10)
	private String serviceVersion;
	
	@Column(name ="URL_SYSTEM_NAME", length=30)
	private String urlSystemName;
	
	/**
	 * @return the urlSystemName
	 */
	public String getUrlSystemName() {
		return urlSystemName;
	}

	/**
	 * @param urlSystemName the urlSystemName to set
	 */
	public void setUrlSystemName(String urlSystemName) {
		this.urlSystemName = urlSystemName;
	}

	public String getServiceVersion() {
    	return serviceVersion;
    }

	public void setServiceVersion(String serviceVersion) {
    	this.serviceVersion = serviceVersion;
    }

	public Integer getId() {
    	return id;
    }

	public void setId(Integer id) {
    	this.id = id;
    }

	public ServiceType getServiceType() {
    	return serviceType;
    }

	public void setServiceType(ServiceType serviceType) {
    	this.serviceType = serviceType;
    }

	public String getServiceEntry() {
    	return serviceEntry;
    }

	public void setServiceEntry(String serviceEntry) {
    	this.serviceEntry = serviceEntry;
    }

	public String getEntryMethod() {
    	return entryMethod;
    }

	public void setEntryMethod(String entryMethod) {
    	this.entryMethod = entryMethod;
    }

	public BusinessSystem getBizSystem() {
    	return bizSystem;
    }

	public void setBizSystem(BusinessSystem bizSystem) {
    	this.bizSystem = bizSystem;
    }

	public String getCnName() {
    	return cnName;
    }

	public void setCnName(String cnName) {
    	this.cnName = cnName;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getServiceBeanName() {
    	return serviceBeanName;
    }

	public void setServiceBeanName(String serviceBeanName) {
    	this.serviceBeanName = serviceBeanName;
    }
}