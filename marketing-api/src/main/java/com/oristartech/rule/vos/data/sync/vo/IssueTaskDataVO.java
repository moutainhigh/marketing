package com.oristartech.rule.vos.data.sync.vo;

import java.io.Serializable;

/**
 * 规则下发任务返回结果
 * @author hy
 *
 */
public class IssueTaskDataVO implements Serializable {

	private static final long serialVersionUID = 3775620942648345939L;

	/**
	 * 下发任务ID
	 */
	private Long taskId;
	
	/**
	 * 下发任务内容
	 */
	private Long taskContent;
	
	/**
	 * 加密后任务内容
	 */
	private String md5TaskContent;
	
	/**
	 * 任务执行serviceBean
	 */
	private Long taskDefine;
	
	/**
	 * 任务执行Method
	 */
	private String serviceMethod;
	
	/**
	 * 是否保存数据库
	 */
	private Long destNote;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(Long taskContent) {
		this.taskContent = taskContent;
	}

	public Long getTaskDefine() {
		return taskDefine;
	}

	public void setTaskDefine(Long taskDefine) {
		this.taskDefine = taskDefine;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public Long getDestNote() {
		return destNote;
	}

	public void setDestNote(Long destNote) {
		this.destNote = destNote;
	}

	public String getMd5TaskContent() {
		return md5TaskContent;
	}

	public void setMd5TaskContent(String md5TaskContent) {
		this.md5TaskContent = md5TaskContent;
	}

}
