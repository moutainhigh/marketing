package com.oristartech.rule.core.data.sync.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.data.sync.dao.ITaskNoteDao;
import com.oristartech.rule.core.data.sync.model.TaskNote;

@Repository
public class TaskNoteDaoImpl extends RuleBaseDaoImpl<TaskNote, Long> implements ITaskNoteDao {

	public TaskNote getTaskNoteByIpAndPost(String ipAddress, String port){
		String hql=" from TaskNote tn where tn.ipAddress=? and tn.port=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(ipAddress);
		params.add(port);
		return (TaskNote) uniqueResult(hql,params.toArray());
	}
}
