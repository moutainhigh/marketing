package com.oristartech.rule.vos.template.vo;

import java.io.Serializable;

/**
 * 定义模板vo
 * @author chenjunfei
 *
 */
public class RuleElementTemplateVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1838764326515572757L;
	
	private Integer id;
	/**
	 * 中文名称
	 */
	private String cnName;
	/**
	 * 英文名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 类型中文名称
	 */
	private String cnType;
	
	/**
	 * 顺序号
	 */
	private Integer seqNum;

	/**
	 * 是否启用
	 */
	private Boolean isEnable;
	
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

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getType() {
    	return type;
    }

	public void setType(String type) {
    	this.type = type;
    }

	public String getCnType() {
    	return cnType;
    }

	public void setCnType(String cnType) {
    	this.cnType = cnType;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public Boolean getIsEnable() {
    	return isEnable;
    }

	public void setIsEnable(Boolean isEnable) {
    	this.isEnable = isEnable;
    }
}
