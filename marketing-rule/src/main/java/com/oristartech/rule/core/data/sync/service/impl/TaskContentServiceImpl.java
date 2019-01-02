package com.oristartech.rule.core.data.sync.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.core.data.sync.dao.ITaskContentDao;
import com.oristartech.rule.core.data.sync.model.TaskContent;
import com.oristartech.rule.core.data.sync.service.ITaskContentService;

@Component
@Transactional
public class TaskContentServiceImpl extends RuleBaseServiceImpl  implements ITaskContentService {
	
	@Autowired
	private ITaskContentDao ruleTaskContentDao;

	public void setRuleTaskContentDao(ITaskContentDao ruleTaskContentDao) {
		this.ruleTaskContentDao = ruleTaskContentDao;
	}
	
	/**
	 * 保存任务内容记录
	 */
	public void save(TaskContent taskContent) {
		this.ruleTaskContentDao.saveOrUpdate(taskContent);
	}
}
