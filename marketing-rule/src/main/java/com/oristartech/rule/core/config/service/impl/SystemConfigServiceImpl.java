package com.oristartech.rule.core.config.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.core.config.dao.ISystemConfigDao;
import com.oristartech.rule.core.config.service.ISystemConfigService;

@Component
@Transactional
public class SystemConfigServiceImpl extends RuleBaseServiceImpl implements ISystemConfigService {

	@Autowired
	private ISystemConfigDao ruleSystemConfigDao;
	
	public String getValueByName(String name) {
		return ruleSystemConfigDao.getValueByName(name);
	}
}
