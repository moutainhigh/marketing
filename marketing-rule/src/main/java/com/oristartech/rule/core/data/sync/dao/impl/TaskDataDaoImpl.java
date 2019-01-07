package com.oristartech.rule.core.data.sync.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.data.sync.dao.ITaskDataDao;
import com.oristartech.rule.core.data.sync.model.TaskData;
import com.oristartech.rule.vos.data.sync.enums.TaskStatus;

/**
 * 任务数据库交互
 * @author HY
 *
 */
@Repository
public class TaskDataDaoImpl extends RuleBaseDaoImpl<TaskData, Long> implements ITaskDataDao {

	/**
	 * 根据节点ID得到一个未执行任务
	 * @param noteId	节点ID
	 * @return
	 */
	public TaskData getFirstTaskDataByNoteId(Long noteId){
		String hql = "from TaskData td where td.destNote = ? and td.status = ?";
		return (TaskData)this.uniqueResult(hql, new Object[]{noteId,TaskStatus.CREATE});
	}
	
	/**
	 * 更新任务状态
	 * @param taskId	任务ID
	 * @param status	任务状态
	 * @return
	 */
	public int updateTaskStatus(Long taskId,TaskStatus status, String remark){
		String hql = "update TaskData set status = ?,endTime = ?,remark = ? where id = ?";
		return this.executeSaveOrUpdate(hql, status,new Date(),remark,taskId);
	}
	
	/**
	 * 根据节点ID得到一个未执行任务
	 * @param noteId	节点ID
	 * @return
	 */
	public List<TaskData> getTaskDatasByNoteId(Long noteId,int taskNum){
		String hql = "from TaskData td where td.destNote = ? and td.status = ?";
		Page<TaskData> page = searchPagedQuery(hql, 0, taskNum, new Object[]{ noteId,  TaskStatus.CREATE});
		if(page != null) {
			return page.getResult();
		}
		return null;
	}
}
