package com.oristartech.marketing.core.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.oristartech.marketing.core.service.IRuleBaseService;


public class RuleBaseServiceImpl implements IRuleBaseService {
	
	@Autowired
	Mapper ruleMapper;
	
	public Mapper getMapper() {
    	return ruleMapper;
    }

}
