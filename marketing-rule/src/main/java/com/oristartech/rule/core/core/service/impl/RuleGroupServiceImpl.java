package com.oristartech.rule.core.core.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.core.core.dao.IRuleGroupDao;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.core.service.IRuleGroupService;
import com.oristartech.rule.core.data.sync.model.RuleTaskDefineType;
import com.oristartech.rule.core.data.sync.service.ITaskDataService;
import com.oristartech.rule.vos.core.enums.RuleStatus;

@Component
@Transactional
public class RuleGroupServiceImpl extends RuleBaseServiceImpl implements IRuleGroupService {

	@Autowired
	private ITaskDataService ruleTaskDataService;
	@Autowired
	private IRuleGroupDao ruleGroupDao;
	
//	/**
//	 * 更新规则有效期，会同时修改所有规则的有效时间， 若规则是运行中的，加到下发表
//	 * @throws ServiceException 
//	 */
//	public void modifyValidDate(Integer groupId, String startDate, String endDate) throws ServiceException {
//		if(groupId != null && startDate != null && endDate != null) {
//			Date start = DateUtil.convertStrToDate(startDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
//			Date end = DateUtil.convertStrToDate(endDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
//			modifyValidDate(groupId, start, end);
//			
//		} else {
//			throw new ServiceException(BizExceptionEnum.SYSTEM_VALIDATE_FAIL);
//		}
//
//	}
//
//	public void modifyValidDate(Integer groupId, Date startDate, Date endDate) throws ServiceException {
//		if(groupId != null && startDate != null && endDate != null) {
//			ruleGroupDao.modifyValidDate(groupId, startDate, endDate);
//			if(RuleStatus.RUNNING.equals(ruleGroupDao.getRuleGroupStatus(groupId))) {
//				ruleTaskDataService.saveTaskData(groupId, RuleTaskDefineType.ENABLE);
//			}
//		} else {
//			throw new ServiceException(BizExceptionEnum.SYSTEM_VALIDATE_FAIL);
//		}
//	    
//	}
	
	/**
	 * 更新rulegroup组,及下面的所有规则的关联的业务编码
	 */
	public void updateRuleGroupBizCode(Integer groupId, String relateBizCode) {
	    if(groupId != null) {
	    	ruleGroupDao.updateBizCode(groupId, relateBizCode);
	    }
	}
	
	
	public void removeRuleGroupLogical(Integer groupId) {
	    this.updateRuleGroupStatus(groupId, RuleStatus.DELETE);
	}
	
	public Integer updateRuleGroupStatus(Integer id, RuleStatus status) {
		if(id != null && status != null) {
			return ruleGroupDao.updateStatus(id, status);
		}
	    return 0;
	}
	
	public void removeRuleGroup(Integer groupId) {
		if(groupId != null ) {
			RuleGroup group = ruleGroupDao.get(groupId);
			if(RuleStatus.NEW.equals(group.getStatus())) {
				ruleGroupDao.deleteGroup(groupId);
			} else {
				removeRuleGroupLogical(groupId);
			}
		}
	}
}
