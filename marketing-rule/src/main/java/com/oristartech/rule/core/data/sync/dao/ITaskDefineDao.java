package com.oristartech.rule.core.data.sync.dao;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.data.sync.model.TaskDefine;

public interface ITaskDefineDao extends IRuleBaseDao<TaskDefine, Long> {

	/**
	 * 根据名称获取任务类型
	 * @param name	任务类型名称
	 * @return
	 */
	public TaskDefine getTaskdeDefineByName(String name);
	
}
