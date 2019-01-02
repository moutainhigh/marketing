package com.oristartech.rule.core.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.core.dao.IRuleConditionElementDao;
import com.oristartech.rule.core.core.service.IRuleConditionElementService;

@Component
@Transactional
public class RuleConditionElementServiceImpl extends RuleBaseServiceImpl implements IRuleConditionElementService {
	@Autowired
	private IRuleConditionElementDao ruleConditionElementDao;
	
//	public void setRuleConditionElementDao(IRuleConditionElementDao ruleConditionElementDao) {
//    	this.ruleConditionElementDao = ruleConditionElementDao;
//    }
//
//	public String findFirstValueInSection(Long sectionId, String categoryName, String modelFieldName) {
//		if(sectionId != null && !BlankUtil.isBlank(categoryName) && !BlankUtil.isBlank(modelFieldName)) {
//			return ruleConditionElementDao.findFirstValueInSection(sectionId, categoryName, modelFieldName);
//		}
//	    return null;
//	}

}
