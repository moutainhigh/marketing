package com.oristartech.rule.core.core.service;

import java.util.Date;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.rule.vos.core.enums.RuleStatus;


/**
 * 规则组service
 * @author chenjunfei
 *
 */
public interface IRuleGroupService {
	
//	/**
//	 * 修改有效期，会同时修改所有规则的有效时间, 若规则是运行中的，加到下发表
//	 */
//	public void modifyValidDate(Integer groupId, String startDate, String endDate) throws ServiceException;
//	
//	/**
//	 * 修改有效期，会同时修改所有规则的有效时间, 若规则是运行中的，加到下发表
//	 */
//	public void modifyValidDate(Integer groupId, Date startDate, Date endDate) throws ServiceException;
	
	/**
	 * 更新rulegroup,及其包含的所有规则的关联的业务编码
	 * @param groupId
	 * @param relateBizCode
	 */
	void updateRuleGroupBizCode(Integer groupId, String relateBizCode);
	
	/**
	 * 删除规则组， 会根据规则状态确定是逻辑删除，还是物理删除
	 * @param groupId
	 */
	void removeRuleGroup(Integer groupId) ;
	/**
	 * 逻辑删除规则组
	 * @param groupId
	 */
	void removeRuleGroupLogical(Integer groupId);
	
	/**
	 * 修改规则组状态
	 * @param id
	 * @param status RuleStatus
	 * @return 更新条数
	 */
	Integer updateRuleGroupStatus(Integer id, RuleStatus status);
}
