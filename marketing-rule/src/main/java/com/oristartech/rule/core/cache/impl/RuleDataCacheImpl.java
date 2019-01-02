package com.oristartech.rule.core.cache.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.cache.IRuleDataCache;
import com.oristartech.rule.core.core.dao.IRuleDao;
import com.oristartech.rule.core.core.dao.IRuleGroupDao;
import com.oristartech.rule.core.core.dao.IRuleTypeDao;
import com.oristartech.rule.core.core.model.RuleType;
import com.oristartech.rule.core.core.service.IRuleManagerService;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 缓存管理接口，主要是调用IRuleManagerService中方法，但IRuleManagerService一般不缓存，所以集中到这里.
 * 
 * 主要提供给IRuleService用，即在匹配的时候，其他情况的用hibernate自己的缓存或不设缓存即可。
 * 
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class RuleDataCacheImpl extends RuleBaseServiceImpl implements IRuleDataCache {

	private static final Logger log = LoggerFactory.getLogger(RuleDataCacheImpl.class);
	
	@Autowired
	private IRuleManagerService ruleManagerService;
	@Autowired
	private IRuleGroupDao ruleGroupDao;
	@Autowired
	private IRuleDao ruleDao;
	@Autowired
	private IRuleTypeDao ruleTypeDao;
	
	public RuleVO getRuleById(Integer ruleId) {
		log.info("getRuleById hit cache , query from database!ruleId=" + ruleId);
		return ruleManagerService.getRuleById(ruleId);
	}
	
//	public RuleGroupVO getRuleGroupById(Integer groupId) {
//		log.info("getRuleGroupById not hit cache , query from database!groupId=" + groupId);
//	    return ruleManagerService.getRuleGroupById(groupId);
//	}

	public RuleTypeVO getRuleTypeByGroupId(Integer groupId) {
		log.info("getRuleTypeByGroupId not hit cache , query from database!groupId=" + groupId);
	    RuleType type = ruleGroupDao.getRuleType(groupId);
	    if(type != null) {
	    	return getMapper().map(type, RuleTypeVO.class);
	    }
	    return null;
	}
	
	public RuleTypeVO getRuleTypeByRuleId(Integer ruleId) {
		RuleType type =  ruleDao.getRuleType(ruleId);
		if(type != null) {
			return getMapper().map(type, RuleTypeVO.class);
		}
		return null;
	}
	
	public RuleTypeVO getDefaultRuleType() {
		RuleType type = ruleTypeDao.getDefaultType();
		if(type != null) {
			return getMapper().map(type, RuleTypeVO.class);
		}
		return null;
	}
	
	public RuleTypeVO getRuleTypeByName(String name) {
		RuleType type = ruleTypeDao.getByName(name);
		if(type != null) {
			return getMapper().map(type, RuleTypeVO.class);
		}
		return null;
	}
	
//	public RuleGroupVO flushGroup(RuleGroupVO groupVO) {
//		if(!BlankUtil.isBlank(groupVO.getRules())) {
//			for(RuleVO ruleVO : groupVO.getRules()) {
//				ruleVO.setCommonSection(groupVO.getRuleSectionVO());
//				//内部调用不会cache
//				//flushRule(ruleVO);
//			}
//		}
//		
//	    return groupVO;
//	}
//	
//	public RuleVO flushRule(RuleVO ruleVO) {
//	    return ruleVO;
//	}
	
	public void evitGroup(RuleGroupVO groupVO) {
		if(!BlankUtil.isBlank(groupVO.getRules())) {
			//for(RuleVO ruleVO : groupVO.getRules()) {
				//内部调用不会cache
				//evitRule(ruleVO);
			//}
		}
	}
	
	public void evitRule(RuleVO ruleVO) {
		return;
	}
	
}
