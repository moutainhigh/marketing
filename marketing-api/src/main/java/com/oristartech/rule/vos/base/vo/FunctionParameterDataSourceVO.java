package com.oristartech.rule.vos.base.vo;

import java.io.Serializable;




public class FunctionParameterDataSourceVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2409177480664586415L;
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
	 * 归属参数
	 */
	private Integer functionParameterId;
	
	/**
	 * 联动的属性(即本值选中,则相关属性显示, 否则隐藏)， 最好少用本属性控制。若控制多个联动，用|号隔开多个id
	 */
	private String linkageId;
	
	
	/**
	 * 联动的属性验证规则, 几个联动的参数都指向一个参数时有用. 多个时，用|号隔开多个校验规则对象
	 * 否则应该用回RuleActionFunctionParameter中的validateRule属性指定验证规则
	 */
	private String linkageValidateRule;
	
	public String getLinkageValidateRule() {
    	return linkageValidateRule;
    }

	public void setLinkageValidateRule(String linkageValidateRule) {
    	this.linkageValidateRule = linkageValidateRule;
    }

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

	public Integer getFunctionParameterId() {
    	return functionParameterId;
    }

	public void setFunctionParameterId(Integer functionParameterId) {
    	this.functionParameterId = functionParameterId;
    }

	public String getLinkageId() {
		return linkageId;
	}

	public void setLinkageId(String linkageId) {
		this.linkageId = linkageId;
	}

}
