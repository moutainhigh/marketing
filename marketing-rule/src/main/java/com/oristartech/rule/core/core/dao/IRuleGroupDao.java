package com.oristartech.rule.core.core.dao;

import java.util.Date;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.core.model.RuleType;
import com.oristartech.rule.vos.core.enums.RuleStatus;

/**
 * 规则组保存dao
 * @author chenjunfei
 *
 */
public interface IRuleGroupDao extends IRuleBaseDao<RuleGroup, Integer> {

	/**
	 * 更新规则组及其中规则状态
	 * @param id
	 * @param statusEm
	 */
	Integer updateStatus(Integer id, RuleStatus statusEm);

	/**
	 * 物理删除规则组
	 * @param groupId
	 */
	void deleteGroup(Integer groupId);
	
	/**
	 * 获取指定groupid的类型
	 * @param groupId
	 * @return
	 */
	String getRuleTypeName(Integer groupId);
	
	/**
	 * 获取指定groupid的类型
	 * @param groupId
	 * @return
	 */
	RuleType getRuleType(Integer groupId);

	/**
	 * 存在rulegroup
	 * @param groupId
	 * @return
	 */
	boolean existRuleGroup(Integer groupId);

	/**
	 * 修改规则组的有效时间，会同时修改所有规则的有效时间
	 * @param groupId
	 * @param start
	 * @param end
	 */
	void modifyValidDate(Integer groupId, Date start, Date end);

	/**
	 * 更新rulegroup,及其包含的所有规则的关联的业务编码
	 * @param groupId
	 * @param relateBizCode
	 */
	void updateBizCode(Integer groupId, String relateBizCode); 
	
	/**
	 * 获取指定规则组状态
	 * @param groupId
	 * @return
	 */
	RuleStatus getRuleGroupStatus(Integer groupId);
	
}
