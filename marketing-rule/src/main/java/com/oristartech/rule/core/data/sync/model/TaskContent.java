package com.oristartech.rule.core.data.sync.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 任务内容
 * @author chenjunfei
 * @version 1.0
 * @created 14-五月-2014 13:23:18
 */
@Entity
@Table(name="RULE_SYNC_TASK_CONTENT")
public class TaskContent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	/**
	 * 下发json 主体数据对象
	 */
	@Lob
	@Column(name="CONTENT",columnDefinition="TEXT",nullable=false)
	private String content;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}