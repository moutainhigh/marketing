package com.oristartech.rule.core.core.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.core.model.Rule;
import com.oristartech.rule.core.core.model.RuleType;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 规则保存dao
 * @author chenjunfei
 *
 */
public interface IRuleDao extends IRuleBaseDao<Rule, Integer> {
	
	/**
	 * 获取组下面所有规则的id
	 * @param id
	 * @return
	 */
	List<Integer> getRuleIdsByGroup(Integer id);

	/**
	 * 根据规则组id获取规则
	 * @param groupId
	 * @return
	 */
	List<Rule> getRulesByGroup(Integer groupId);
	
	/**
	 * 根据groupId获取关联的所有规则, 只会返回规则基本信息, 不会返回条件,动作.
	 * @param groupId
	 * @return
	 */
	List<Rule> getSimpleRulesByGroup(Integer groupId);
	
	/**
	 * 根据ruleid, groupid获取rule信息, 该rule必须在指定的group下才有返回, 否则即使有对应的rule也返回null
	 * @param groupId
	 * @param ruleId
	 * @return
	 */
	Rule getRuleInGroup(Integer groupId, Integer ruleId);

	/**
	 * 根据groupIds 获取关联的所有规则, 只会返回规则基本信息, 不会返回条件,动作.
	 * @param groupIds
	 * @return
	 */
	List<RuleVO> getSimpleRulesByGroupIds(List<Integer> groupIds);

	/**
	 * 获取规则类型
	 * @param integer
	 * @return
	 */
	String getRuleTypeName(Integer ruleId);
	
	/**
	 * 获取规则类型
	 * @param integer
	 * @return
	 */
	RuleType getRuleType(Integer ruleId);

	/**
	 * 是否存在指定id的rule
	 * @param groupId
	 * @param ruleId
	 * @return
	 */
	boolean existRuleInGroup(Integer groupId, Integer ruleId);

	/**
	 * 根据规则id列表批量获取规则
	 * @param groupId
	 * @param ruleId
	 * @return
	 */
	public List<Rule> getRulesByIds(List<Integer> ids);
}
