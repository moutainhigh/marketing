package com.oristartech.rule.core.data.sync.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 执行节点
 * @author chenjunfei
 * @version 1.0
 * @updated 14-五月-2014 13:23:01
 */
@Entity
@Table(name="RULE_SYNC_TASK_NOTE")
public class TaskNote {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	/**
	 * ip地址
	 */
	@Column(name="IP_ADDRESS", nullable=false)
	private String ipAddress;
	/**
	 * 端口
	 */
	@Column(name="PORT")
	private String port;
	
	/**
	 * 备注
	 */
	@Column(name="REMARK")
	private String remark;
	
	/**
	 * 是否保存
	 */
	@Column(name="IS_SAVE",nullable=false)
	private Boolean isSave = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsSave() {
		return isSave;
	}

	public void setIsSave(Boolean isSave) {
		this.isSave = isSave;
	}

	
	
	
	

}