package com.oristartech.rule.core.data.sync.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.data.sync.model.TaskData;
import com.oristartech.rule.vos.data.sync.enums.TaskStatus;

public interface ITaskDataDao extends IRuleBaseDao<TaskData, Long> {

	/**
	 * 根据节点ID得到一个未执行任务
	 * @param noteId	节点ID
	 * @return
	 */
	public TaskData getFirstTaskDataByNoteId(Long noteId);
	
	/**
	 * 更新任务状态
	 * @param taskId	任务ID
	 * @param status	任务状态
	 * @return
	 */
	public int updateTaskStatus(Long taskId,TaskStatus status, String remark);
	
	/**
	 * 根据节点ID得到一个未执行任务
	 * @param noteId	节点ID
	 * @return
	 */
	public List<TaskData> getTaskDatasByNoteId(Long noteId,int taskNum);
	
}
