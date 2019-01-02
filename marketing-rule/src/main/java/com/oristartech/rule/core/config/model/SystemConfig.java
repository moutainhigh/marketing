package com.oristartech.rule.core.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenjunfei
 * @version 1.0
 * @created 18-十月-2013 15:05:56
 */
@Entity
@Table(name="RULE_CFG_SYSTEM_CONFIG")
public class SystemConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	/**
	 * 参数名称
	 */
	@Column(length=50,nullable=false, name="NAME", unique=true)
	private String name;
	/**
	 * 参数值
	 */
	@Column(name = "VALUE")
	private String value;
	
	@Column(name = "REMARK")
	private String remark;
	
	public String getRemark() {
    	return remark;
    }
	public void setRemark(String remark) {
    	this.remark = remark;
    }
	public int getId() {
    	return id;
    }
	public void setId(int id) {
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

}