package com.oristartech.rule.core.executor.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.cache.IRuleDataCache;
import com.oristartech.rule.core.executor.IRuleLoaderService;
import com.oristartech.rule.drools.executor.IRuleDroolsManagerService;
import com.oristartech.rule.vos.core.enums.RuleRunMode;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 规则加载器, 不同的类型可能使用不同的加载方式.
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class RuleLoaderServiceImpl implements IRuleLoaderService {
	
	private static final Logger log = LoggerFactory.getLogger(RuleLoaderServiceImpl.class);
	
	@Autowired
	private IRuleDataCache ruleDataCache;
	@Autowired
	private IRuleDroolsManagerService ruleDroolsManagerService ;
	
	public void loadRuleGroup(RuleGroupVO groupVo) {
//		ruleDataCache.flushGroup(groupVo);
//		for(RuleVO ruleVO : groupVo.getRules()) {
//			//要在外部显式调用才cache
//			ruleDataCache.flushRule(ruleVO);
//		}
		RuleTypeVO ruleType = ruleDataCache.getRuleTypeByGroupId(groupVo.getId());
		if(RuleRunMode.DROOLS.equals(ruleType.getRunMode())) {
//			ruleDroolsManagerService.removeRuleInEngine(groupVo);
			ruleDroolsManagerService.loadRuleToWorkMemory(groupVo);
		}
	}
	
	@Transactional
	public void loadRuleGroups(List<RuleGroupVO> groupVOs) {
	    if(!BlankUtil.isBlank(groupVOs)) {
	    	for(RuleGroupVO groupVo : groupVOs) {
	    		loadRuleGroup(groupVo);
	    	}
	    }
	}
	
	public void removeRuleGroup(RuleGroupVO groupVo) {
		ruleDataCache.evitGroup(groupVo);
		for(RuleVO ruleVO : groupVo.getRules()) {
			//要在外部显式调用才cache
			ruleDataCache.evitRule(ruleVO);
		}
		
		RuleTypeVO ruleType = ruleDataCache.getRuleTypeByGroupId(groupVo.getId());
		if(RuleRunMode.DROOLS.equals(ruleType.getRunMode())) {
			ruleDroolsManagerService.removeRuleInEngine(groupVo);
		}
	}
	
}
