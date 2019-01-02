package com.oristartech.rule.core.data.sync.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.data.sync.dao.ITaskContentDao;
import com.oristartech.rule.core.data.sync.model.TaskContent;

@Repository
public class TaskContentDaoImpl extends RuleBaseDaoImpl<TaskContent, Long> implements ITaskContentDao {

}
