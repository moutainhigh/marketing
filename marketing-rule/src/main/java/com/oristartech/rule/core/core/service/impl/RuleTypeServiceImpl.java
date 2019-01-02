package com.oristartech.rule.core.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.core.dao.IRuleTypeDao;
import com.oristartech.rule.core.core.model.RuleType;
import com.oristartech.rule.core.core.service.IRuleTypeService;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;

@Component
@Transactional
public class RuleTypeServiceImpl extends RuleBaseServiceImpl implements IRuleTypeService {
	
	@Autowired
	private IRuleTypeDao ruleTypeDao ;
	
	public void setRuleTypeDao(IRuleTypeDao ruleTypeDao) {
    	this.ruleTypeDao = ruleTypeDao;
    }

	public Map<String, RuleTypeVO> getAllRuleTypeVOsMap() {
		List<RuleType> ruleTypes = ruleTypeDao.getAll();
		
		if(!BlankUtil.isBlank(ruleTypes)) {
			Map<String ,RuleTypeVO> typeMap = new HashMap<String, RuleTypeVO>() ;
			for(RuleType type : ruleTypes) {
				typeMap.put(type.getName(), getMapper().map(type, RuleTypeVO.class));
			}
			return typeMap;
		}
	    return null;
	}
	
	public RuleTypeVO getByName(String name) {
		if(!BlankUtil.isBlank(name)) {
			RuleType ruleType = ruleTypeDao.getByName(name);
			if(ruleType != null) {
				return getMapper().map(ruleType, RuleTypeVO.class);
			}
		}
		
		return null;
	}
	
}
