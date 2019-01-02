package com.oristartech.rule.core.data.sync.service;


import com.oristartech.rule.core.data.sync.model.TaskDefine;

public interface ITaskDefineService {
	
	/**
	 * 根据类型获取任务类型
	 * @return
	 */
	public TaskDefine getTaskdeDefineByType(String taskOperateType);
}
