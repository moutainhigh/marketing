package com.oristartech.rule.core.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 业务功能配置参数
 * 
 * @author chenjunfei
 * @version 1.0
 * @created 18-十月-2013 15:05:56
 */
@Entity
@Table(name="RULE_CFG_PARAMETER")
public class Parameter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 名称
	 */
	@Column(length = 50, nullable = false, name="NAME")
	private String name;
	/**
	 * 参数值
	 */
	@Column(name = "VALUE")
	private String value;
	/**
	 * 中文名称
	 */
	@Column(length = 50, name="CN_NAME")
	private String cnName;
	/**
	 * 参数number
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;
	/**
	 * 是否启用
	 */
	@Column(name = "IS_ENABLE")
	private boolean isEnable;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

}