package com.oristartech.rule.core.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.core.dao.IRuleFinderDao;
import com.oristartech.rule.core.core.service.IRuleFinderService;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
 
/**
 * 专门用于执行时的规则查找
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class RuleFinderServiceImpl extends RuleBaseServiceImpl implements IRuleFinderService {

	@Autowired
	private IRuleFinderDao ruleFinderDao;
	
	public void setRuleFinderDao(IRuleFinderDao ruleFinderDao) {
    	this.ruleFinderDao = ruleFinderDao;
    }

	/**
	 * 多租户模式此方法过期
	 */
	@Deprecated
	public Page<RuleGroupVO> searchEngineExeRules(RuleSearchCondition searchCondition) {
		if(searchCondition == null) {
			return null;
		}
		Page<RuleGroupVO> pages = ruleFinderDao.findEngineExeRuleVOs(searchCondition);
		
		return pages;
	}
	
	public RuleGroupVO getRuleGroupForExe(Integer ruleGroupId) {
		if(ruleGroupId == null) {
			return null;
		}
		return ruleFinderDao.getRuleGroupForExe(ruleGroupId);
	}

	@Override
	public List<RuleGroupVO> assembleRuleGroupVO(List<RuleGroupVO> groupVOs) {
		return ruleFinderDao.assembleRuleGroupVO(groupVOs);
	}
}
