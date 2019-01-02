package com.oristartech.rule.core.data.sync.service;

import com.oristartech.rule.core.data.sync.model.TaskContent;



public interface ITaskContentService {
	
	/**
	 * 保存任务内容记录
	 */
	public void save(TaskContent taskContent);
}
