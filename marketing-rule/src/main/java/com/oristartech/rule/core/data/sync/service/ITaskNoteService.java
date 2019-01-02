package com.oristartech.rule.core.data.sync.service;

import java.util.List;

import com.oristartech.rule.core.data.sync.model.TaskNote;



public interface ITaskNoteService {
	
	/**
	 * 规则同步 - 新增注册节点
	 * @param ipAddress
	 * @param port
	 * @param remark
	 * @param isSave 是否需要保存
	 * @return 成功新增true,失败false
	 */
	public boolean saveTaskNote(String ipAddress, String port, String remark, boolean isSave);
	/**
	 * 获取所有节点记录
	 * @return
	 */
	public List<TaskNote> getAllNote();
}
