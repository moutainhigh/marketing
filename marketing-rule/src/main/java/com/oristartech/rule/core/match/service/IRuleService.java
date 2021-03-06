package com.oristartech.rule.core.match.service;

import java.util.List;
import java.util.Map;

import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

/**
 * 规则执行接口service
 * @author chenjunfei
 * @version 1.0
 * @updated 15-十月-2013 10:09:52
 */
public interface IRuleService {

	/**
	 * 指定匹配某条规则
	 * @param id 规则id
	 * @param facts
	 * @return
	 */
	public String matchRuleById(String tenantId,Integer id, String facts);
	
	/**
	 * 指定匹配某条规则
	 * @param id 规则id
	 * @param facts
	 * @return
	 */
	public List<RuleResult> matchRuleById(String tenantId,Integer id, List<Object> facts);
	
	/**
	 * 指定匹配某条规则
	 * @param id 规则id
	 * @param facts
	 * @return
	 */
	public List<RuleResult> matchRuleByIds(String tenantId,List<Integer> ids, List<Object> facts);
	
	/**
	 * 匹配某规则组下的规则
	 * @param ruleGroupId    规则id
	 * @param facts    业务属性数据和值
	 */
	public String matchRuleByGroupId(String tenantId,Integer ruleGroupId, String facts);
	
	/**
	 * 匹配某规则组下的规则
	 * @param ruleGroupId    规则id
	 * @param facts    业务属性数据和值
	 */
	public List<RuleResult> matchRuleByGroupId(String tenantId,Integer ruleGroupId, List<Object> facts);
	
	/**
	 * 匹配若干规则组下的规则
	 * @param ruleGroupId    规则id
	 * @param facts    业务属性数据和值
	 */
	public List<RuleResult> matchRuleByGroupIds(String tenantId,List<Integer> ruleGroupIds, List<Object> facts);

	/**
	 * 匹配某类型的规则
	 * @param typeName    规则类别名称
	 * @param facts    业务属性数据和值
	 */
	public String matchRule(String tenantId,String typeName, String facts);
	
	/**
	 * 匹配某类型的规则
	 * @param type    规则类别名称
	 * @param facts    业务属性数据和值
	 */
	public List<RuleResult> matchRule(String tenantId,String typeName, List<Object> facts);
	
	/**
	 * 根据ruleid获取rule信息, 只获取基本信息, 不会加载条件和操作信息
	 * @param ruleId
	 * @return
	 */
	public RuleVO getRuleBasicInfo(Integer ruleId);
	
	/**
	 * 根据ruleid, groupid获取rule信息, 该rule必须在指定的group下才有返回, 否则即使有对应的rule也返回null.
	 * 只获取基本信息, 不会加载条件和操作信息
	 * @param groupId
	 * @param ruleId
	 * @return
	 */
	public RuleVO getRuleBasicInfoInGroup(Integer groupId, Integer ruleId);
	
	/**
	 * 是否存在指定id的rule
	 * @param groupId
	 * @param ruleId
	 * @return
	 */
	public boolean existRuleInGroup(Integer groupId, Integer ruleId);
	
	/**
	 * 根据ruleid获取rule信息, 包含加载条件和操作信息
	 * @param ruleId
	 * @return
	 */
	public RuleVO getRuleFullInfo(Integer ruleId);
	
	/**
	 * 根据ruleid, groupid获取rule信息, 该rule必须在指定的group下才有返回, 否则即使有对应的rule也返回null.
	 * 包含加载条件和操作信息
	 * @param groupId
	 * @param ruleId
	 * @return
	 */
	public RuleVO getRuleFullInfoInGroup(Integer groupId, Integer ruleId);
	
	public String searchRule(RuleSearchCondition condition);
	
	public String searchRuleFilterResult(String type, String facts);

	/**
	 * 模拟测试规则
	 * @param groupVO
	 * @param facts
	 */
	public List<RuleResult> testRuleGroup(RuleGroupVO groupVO, String facts);
	
	/**
	 * 模拟测试规则
	 * @param groupVO
	 * @param facts
	 */
	public List<RuleResult> testRuleGroup(RuleGroupVO groupVO, List<Object> facts);
	
	/**
	 * 调用规则中的若干执行型方法, 返回的结果是actionId, action结果对
	 * @param ruleId
	 * @return
	 */
//	public Map<Long, String> invodeRuleExeAction(Integer ruleId, List<Long> actionIds, Map<String, Object> params);

	/**
	 * 调用规则中的执行型方法
	 * @param ruleId
	 * @param otherParams 调用该action需要传递的其他参数, 即不包含在action数据库记录的参数. 该参数会在最终的params Map用otherParams key指定.
	 * @return
	 */
	public RuleWSResultVO invodeRuleExeAction(Integer ruleId, Long actionId, Map<String, Object> otherParams);
	
	/**
	 * 调用规则中的所有执行型方法
	 * @param ruleId
	 * @param otherParams 调用该action需要传递的其他参数, 即不包含在action数据库记录的参数. 该参数会在最终的params Map用otherParams key指定.
	 * @return
	 */
	public RuleWSResultVO invodeExeActions(Integer ruleId, Map<String, Object> otherParams);
	
	/**
	 * 调用规则中的所有执行型方法
	 * @param ruleId
	 * @param otherParams 调用该action需要传递的其他参数, 即不包含在action数据库记录的参数. 该参数会在最终的params Map用otherParams key指定.
	 * @param ignoreFail 若规则中有多个要执行的方法，是否存在一个失败马上返回，还是忽略失败，继续执行下个方法。若设置伟true， 本方法返回永远为成功。
	 * @return
	 */
	public RuleWSResultVO invodeExeActions(Integer ruleId, Map<String, Object> otherParams, boolean ignoreFail);
	

	/**
	 * 是否存在指定规则
	 * @param ruleId
	 */
	public boolean existRule(Integer ruleId);
}