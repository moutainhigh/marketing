package com.oristartech.rule.core.data.sync.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.oristartech.rule.vos.data.sync.enums.TaskStatus;

/**
 * 任务数据
 * @author chenjunfei
 * @version 1.0
 * @created 14-五月-2014 13:23:11
 */
@Entity
@Table(name="RULE_SYNC_TASK_DATA")
public class TaskData {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	/**
	 * 目标节点
	 */
	@ManyToOne    
	@JoinColumn(name="DEST_NOTE_ID", nullable=false)
	private TaskNote destNote;
	
	/**
	 * 创建时间
	 */
	@Column(name="CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	/**
	 * 完成时间
	 */
	@Column(name="END_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	
	/**
	 * 任务类型
	 */
	@ManyToOne
	@JoinColumn(name="TASK_DEFINE_ID", nullable=false)
	private TaskDefine taskDefine;
	
	/**
	 * 任务状态
	 */
	@Column(name="STATUS", nullable=false)
	private TaskStatus status;
	
	@Column(name="REMARK")
	private String remark;
	
	/**
	 * 任务内容
	 */
	@ManyToOne
	@JoinColumn(name="TASK_CONTENT_ID", nullable=false)
	private TaskContent taskContent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TaskNote getDestNote() {
		return destNote;
	}

	public void setDestNote(TaskNote destNote) {
		this.destNote = destNote;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public TaskDefine getTaskDefine() {
		return taskDefine;
	}

	public void setTaskDefine(TaskDefine taskDefine) {
		this.taskDefine = taskDefine;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TaskContent getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(TaskContent taskContent) {
		this.taskContent = taskContent;
	}
	
	
	

}