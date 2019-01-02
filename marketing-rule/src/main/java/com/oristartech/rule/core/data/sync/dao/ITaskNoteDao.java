package com.oristartech.rule.core.data.sync.dao;


import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.data.sync.model.TaskNote;

public interface ITaskNoteDao extends IRuleBaseDao<TaskNote, Long> {

	/**
	 * 根据ip地址、端口查询注册节点
	 * @param ipAddress
	 * @param post
	 * @return
	 */
	public TaskNote getTaskNoteByIpAndPost(String ipAddress, String port);
	
}
