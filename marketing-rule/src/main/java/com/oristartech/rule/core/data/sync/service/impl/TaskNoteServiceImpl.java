package com.oristartech.rule.core.data.sync.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.core.data.sync.dao.ITaskNoteDao;
import com.oristartech.rule.core.data.sync.model.TaskNote;
import com.oristartech.rule.core.data.sync.service.ITaskNoteService;

@Component
@Transactional
public class TaskNoteServiceImpl extends RuleBaseServiceImpl  implements ITaskNoteService {
	
	@Autowired
	private ITaskNoteDao ruleTaskNoteDao;

	public void setRuleTaskNoteDao(ITaskNoteDao ruleTaskNoteDao) {
		this.ruleTaskNoteDao = ruleTaskNoteDao;
	}
	
	public boolean saveTaskNote(String ipAddress, String port, String remark, boolean isSave){
		TaskNote taskNote = ruleTaskNoteDao.getTaskNoteByIpAndPost(ipAddress, port);
		if(taskNote != null){//节点已存在
			return false;
		}else{
			taskNote = new TaskNote();
			taskNote.setIpAddress(ipAddress);
			taskNote.setPort(port);
			taskNote.setRemark(remark);
			taskNote.setIsSave(isSave);
			ruleTaskNoteDao.saveOrUpdate(taskNote);
			return true;
		}
	}
	
	/**
	 * 获取所有节点记录
	 * @return
	 */
	public List<TaskNote> getAllNote(){
		List<TaskNote> list = this.ruleTaskNoteDao.getAll();
		return list;
	}
}
