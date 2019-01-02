package com.oristartech.rule.core.data.sync.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oristartech.rule.vos.data.sync.enums.TaskOperateType;

/**
 * 任务类型定义
 * @author chenjunfei
 * @version 1.0
 * @created 14-五月-2014 13:23:25
 */
@Entity
@Table(name="RULE_SYNC_TASK_DEFINE")
public class TaskDefine {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	/**
	 * 唯一名称
	 */
	@Column(name="NAME", nullable=false)
	private String name;
	
	/**
	 * 名称
	 */
	@Column(name="CN_NAME")
	private String cnName;
	
	/**
	 * 是否允许
	 */
	@Column(name="IS_ENABLE")
	private boolean isEnable;
	
	/**
	 * 处理接收任务service名称
	 */
	@Column(name="SERVICE_BEAN_NAME")
	private String serviceBeanName;
	
	/**
	 * service的method, 所有method都只接收json参数.
	 */
	@Column(name="SERVICE_METHOD")
	private String serviceMethod;
	
	/**
	 * 任务类型
	 */
	@Column(name="TASK_OPERATE_TYPE", nullable=false)
	private TaskOperateType taskOperateType;
	
	/**
	 * 备注
	 */
	@Column(name="REMARK")
	private String remark;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getServiceBeanName() {
		return serviceBeanName;
	}

	public void setServiceBeanName(String serviceBeanName) {
		this.serviceBeanName = serviceBeanName;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public TaskOperateType getTaskOperateType() {
		return taskOperateType;
	}

	public void setTaskOperateType(TaskOperateType taskOperateType) {
		this.taskOperateType = taskOperateType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}