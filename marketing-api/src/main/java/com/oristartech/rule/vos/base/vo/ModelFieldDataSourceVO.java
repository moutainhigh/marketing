package com.oristartech.rule.vos.base.vo;

import java.io.Serializable;

public class ModelFieldDataSourceVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1920661962225523194L;
	private Integer id;
	/**
	 * 显示值
	 */
	private String label;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 顺序号
	 */
	private Integer seqNum;
	
	/**
	 * 归属modelfield
	 */
	private Integer modelFieldId;
	
	/**
	 * 联动的属性(即本值选中,则相关属性显示, 否则隐藏)， 最好少用本属性控制。若控制多个联动，用|号隔开多个id
	 */
	private String linkageId;
	

	public Integer getId() {
    	return id;
    }

	public void setId(Integer id) {
    	this.id = id;
    }

	public String getLabel() {
    	return label;
    }

	public void setLabel(String label) {
    	this.label = label;
    }

	public String getValue() {
    	return value;
    }

	public void setValue(String value) {
    	this.value = value;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public String getLinkageId() {
		return linkageId;
	}

	public void setLinkageId(String linkageId) {
		this.linkageId = linkageId;
	}

	public Integer getModelFieldId() {
    	return modelFieldId;
    }

	public void setModelFieldId(Integer modelFieldId) {
    	this.modelFieldId = modelFieldId;
    }

}
