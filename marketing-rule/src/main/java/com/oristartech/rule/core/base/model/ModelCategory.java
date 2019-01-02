package com.oristartech.rule.core.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import com.oristartech.rule.vos.base.enums.ModelCategoryType;


/**
 * 业务分类
 * @author chenjunfei
 * @version 1.0
 * @updated 21-十一月-2013 09:51:18
 */
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
@Entity
@Table(name="RULE_BASE_MODEL_CATEGORY")
public class ModelCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 英文名称（由英文字母和下横线组成）
	 */
	@Index(name="INDEX_MODEL_CATEGORY_NAME")
	@Column(length = 50, nullable = false, name="NAME" ,unique=true)
	private String name;
	/**
	 * 中文名称
	 */
	@Column(length = 50, name="CN_NAME")
	private String cnName;
	
	/**
	 * 归属业务系统,该业务数据归属的系统
	 */
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@ManyToOne
	@ForeignKey(name="FK_MODEL_CATEGORY_BUSINESS_SYSTEM")
	@JoinColumn(name = "BUSINESS_SYSTEM_ID")
	private BusinessSystem businessSystem;
	
	/**
	 * 业务分类类型
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TYPE")
	private ModelCategoryType type;
	
	/**
	 * 关联的业务属性
	 */
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
	@OneToMany(mappedBy="modelCategory", fetch=FetchType.LAZY)
	private List<ModelField> modelFields;

	/**
	 * 一次匹配是否多个fact, 用于测试页面是否可以添加多个category
	 */
	@Column(name = "IS_MULTI_FACT")
	private Boolean isMultiFact;
	
	/**
	 * true则调用业务系统主动加载属性数据
	 */
	@Column(name = "IS_LOAD_AUTO")
	private Boolean isAutoLoad;
	
	/**
	 * 加载属性数据的SystemWebService name
	 */
	@Column(name = "LOAD_SERVICE_NAME")
	private String loadServiceName;
	
	/**
	 * 加载属性数据的查询field name
	 */
	@Column(name = "LOAD_KEY_FIELD_NAMES")
	private String loadKeyFieldNames;
	
	/**
	 * 主要用于生成drools文件时,是否把同级的同category的并且临近的条件合并到一个category中
	 */
	@Column(name = "IS_MERGE_NEAR_FIELD_CON")
	private Boolean isMergeNearFieldCon;
	
	/**
	 * 父category name, 直接用名称,避免太多关联
	 */
	@Column(name = "PARENT_NAME")
	private String parentName;
	
	/**
	 * 是否抽象category, 控制生成vo类
	 */
	@Column(name = "IS_ABSTRACT")
	private Boolean isAbstract;
	
	/**
	 * 本category 对象缓存时间, 单位分钟
	 */
	@Column(name = "CACHE_TIME_AMOUNT")
	private Integer cacaeTimeAmount;
	
	public Integer getCacaeTimeAmount() {
    	return cacaeTimeAmount;
    }

	public void setCacaeTimeAmount(Integer cacaeTimeAmount) {
    	this.cacaeTimeAmount = cacaeTimeAmount;
    }

	public Boolean getIsAbstract() {
    	return isAbstract;
    }

	public void setIsAbstract(Boolean isAbstract) {
    	this.isAbstract = isAbstract;
    }

	public String getParentName() {
    	return parentName;
    }

	public void setParentName(String parentName) {
    	this.parentName = parentName;
    }

	public Boolean getIsMergeNearFieldCon() {
    	return isMergeNearFieldCon;
    }

	public void setIsMergeNearFieldCon(Boolean isMergeNearFieldCon) {
    	this.isMergeNearFieldCon = isMergeNearFieldCon;
    }

	public String getLoadKeyFieldNames() {
    	return loadKeyFieldNames;
    }

	public void setLoadKeyFieldNames(String loadKeyFieldNames) {
    	this.loadKeyFieldNames = loadKeyFieldNames;
    }

	public Boolean getIsAutoLoad() {
    	return isAutoLoad;
    }

	public void setIsAutoLoad(Boolean isAutoLoad) {
    	this.isAutoLoad = isAutoLoad;
    }

	public String getLoadServiceName() {
    	return loadServiceName;
    }

	public void setLoadServiceName(String loadServiceName) {
    	this.loadServiceName = loadServiceName;
    }

	public Boolean getIsMultiFact() {
    	return isMultiFact;
    }

	public void setIsMultiFact(Boolean isMultiFact) {
    	this.isMultiFact = isMultiFact;
    }

	public ModelCategoryType getType() {
    	return type;
    }

	public void setType(ModelCategoryType type) {
    	this.type = type;
    }

	public List<ModelField> getModelFields() {
    	return modelFields;
    }

	public void setModelFields(List<ModelField> modelFields) {
    	this.modelFields = modelFields;
    }

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

	public BusinessSystem getBusinessSystem() {
    	return businessSystem;
    }

	public void setBusinessSystem(BusinessSystem businessSystem) {
    	this.businessSystem = businessSystem;
    }
}