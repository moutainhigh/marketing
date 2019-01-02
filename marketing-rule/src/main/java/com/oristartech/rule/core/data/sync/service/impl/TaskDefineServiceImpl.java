package com.oristartech.rule.core.data.sync.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.core.data.sync.dao.ITaskDefineDao;
import com.oristartech.rule.core.data.sync.model.TaskDefine;
import com.oristartech.rule.core.data.sync.service.ITaskDefineService;

/**
 * 任务定义service
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class TaskDefineServiceImpl extends RuleBaseServiceImpl  implements ITaskDefineService {
	
	
	private static Logger log = LoggerFactory.getLogger(TaskDefineServiceImpl.class);
	
	@Autowired
	private ITaskDefineDao ruleTaskDefineDao;

	public void setRuleTaskDefineDao(ITaskDefineDao ruleTaskDefineDao) {
		this.ruleTaskDefineDao = ruleTaskDefineDao;
	}
	/**
	 * 根据类型获取任务类型
	 * @return
	 */
	public TaskDefine getTaskdeDefineByType(String taskOperateType){
		return ruleTaskDefineDao.getTaskdeDefineByName(taskOperateType);
	}
	
}
