package com.oristartech.rule.vos.base.vo;

import java.io.Serializable;
import java.util.List;

import com.oristartech.rule.vos.base.enums.ModelCategoryType;

/**
 * 业务分类
 * @author chenjunfei
 * @version 1.0
 * @updated 21-十一月-2013 09:51:18
 */
public class ModelCategoryVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1989810156146782788L;
	
	private Integer id;
	/**
	 * 英文名称（由英文字母和下横线组成）
	 */
	private String name;
	/**
	 * 中文名称
	 */
	private String cnName;
	
	/**
	 * 归属业务系统,该业务数据归属的系统
	 */
	private Integer businessSystemId;
	
	/**
	 * 业务分类类型
	 */
	private ModelCategoryType type;
	
	/**
	 * 关联的业务属性
	 */
	private List<ModelFieldVO> modelFields;

	/**
	 * 一次匹配是否多个fact, 用于测试页面是否可以添加多个category
	 */
	private Boolean isMultiFact;
	
	/**
	 * true则调用业务系统主动加载属性数据
	 */
	private Boolean isAutoLoad;
	
	/**
	 * 加载属性数据的SystemWebService name
	 */
	private String loadServiceName;
	
	/**
	 * 加载属性数据的查询field name
	 */
	private String loadKeyFieldNames;
	
	/**
	 * 主要用于生成drools文件时,是否把同级的同category的并且临近的条件合并到一个category中
	 */
	private Boolean isMergeNearFieldCon;
	
	/**
	 * 父名称
	 */
	private String parentName;
	
	/**
	 * 是否抽象category, 控制生成vo类
	 */
	private Boolean isAbstract;
	
	/**
	 * 本category 对象缓存时间, 单位分钟
	 */
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

	public String getLoadKeyFieldNames() {
    	return loadKeyFieldNames;
    }

	public void setLoadKeyFieldNames(String loadKeyFieldNames) {
    	this.loadKeyFieldNames = loadKeyFieldNames;
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

	public Integer getBusinessSystemId() {
    	return businessSystemId;
    }

	public void setBusinessSystemId(Integer businessSystemId) {
    	this.businessSystemId = businessSystemId;
    }

	public List<ModelFieldVO> getModelFields() {
    	return modelFields;
    }

	public void setModelFields(List<ModelFieldVO> modelFields) {
    	this.modelFields = modelFields;
    }
}
