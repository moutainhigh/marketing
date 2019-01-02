package com.oristartech.rule.core.data.sync.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.data.sync.dao.ITaskDefineDao;
import com.oristartech.rule.core.data.sync.model.TaskDefine;

@Repository
public class TaskDefineDaoImpl extends RuleBaseDaoImpl<TaskDefine, Long> implements ITaskDefineDao {

	/**
	 * 根据名称获取任务类型
	 * @param name	任务类型名称
	 * @return
	 */
	public TaskDefine getTaskdeDefineByName(String name){
		String hql = "from TaskDefine where name = ?";
		return (TaskDefine)uniqueResult(hql, name);
	}
}
