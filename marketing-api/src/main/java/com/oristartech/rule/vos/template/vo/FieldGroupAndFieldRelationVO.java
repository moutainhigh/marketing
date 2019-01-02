package com.oristartech.rule.vos.template.vo;

import java.io.Serializable;

import com.oristartech.rule.vos.base.vo.ModelFieldVO;

public class FieldGroupAndFieldRelationVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2092879359697425390L;
	
	private Integer id;
	/**
	 * 业务属性
	 */
	private ModelFieldVO modelField;

	private Integer fieldGroupId;

	/**
	 * 顺序号
	 */
	private Integer seqNum;

	/**
	 * 是否隐藏field属性
	 */
	private Boolean isHideField;

	/**
	 * 若test页面中, 当关联的fieldGroup出现, 若是必要的,即使条件中没有也要让用户添加. 
	 * 即依然在测试页面自动添加.
	 */
	private Boolean isRequiredInTest;
	
	/**
	 * field在这个表里面的名字
	 */
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

	public ModelFieldVO getModelField() {
		return modelField;
	}

	public void setModelField(ModelFieldVO modelField) {
		this.modelField = modelField;
	}

	public Integer getFieldGroupId() {
		return fieldGroupId;
	}

	public void setFieldGroupId(Integer fieldGroupId) {
		this.fieldGroupId = fieldGroupId;
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
