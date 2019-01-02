package com.oristartech.rule.core.core.service;

import java.util.List;

import com.oristartech.rule.vos.core.enums.RuleStatus;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 提供给外部的接口, 便于访问,保存规则
 * @author chenjunfei
 *
 */
public interface IRuleManagerService {
	
//	/**
//	 * 保存规则
//	 * @param ruleVO
//	 * @return
//	 */
//	Integer saveRule(RuleVO ruleVO);
//	
//	/**
//	 * 同时保存多条规则
//	 * @param ruleVOs
//	 * @return
//	 */
//	Integer saveRules(List<RuleVO>  ruleVOs) ;
	
	/**
	 * 保存及更新RuleGroup json方式接口
	 * @param groupVo groupVO json对象,其中不需要包含规则
	 * @param deleteRules 删除的ruleId集合, 格式为[1,2,3]
	 * @param bizCode 业务单据code, 可空
	 * @return
	 */
	Integer saveRuleGroup(String groupVo, String deleteRules, String bizCode) ;
	
	/**
	 * 保存及更新RuleGroup
	 * @param groupVO  groupVO json对象,其中不需要包含规则
	 * @param editRuleVOs 要保存的规则集合
	 * @param deleteRules 删除了的ruleId集合
	 * @return
	 */
	Integer saveRuleGroup(RuleGroupVO groupVO, List<Integer> deleteRules);
	
	/**
	 * 获取规则组信息, 不会初始化label和外部数据的显示
	 * @param id
	 * @return
	 */
	RuleGroupVO getRuleGroupById(Integer id);
	
//	/**
//	 * 获取规则组信息, 会初始化外部数据label显示文字, 应该在编辑规则时使用, 其他地方调用getRuleGroupNoInitExternal
//	 * @param id
//	 * @return
//	 */
//	RuleGroupVO getRuleGroupInitExternal(Integer id);
	
	/**
	 * 获取规则组信息, 但不会初始化label, 及外部数据等定义信息
	 * @param id
	 * @return
	 */
	RuleGroupVO getRuleGroupNoInitExternal(Integer id) ;
	
	/**
	 * 获取规则组信息, 同时加载定义本规则组的属性组, 方法组.
	 * @param RuleGroup id
	 * @return
	 */
	RuleGroupVO getRGWithDefinData(Integer id);
	
//	/**
//	 * 获取规则组json字符串表示,操作数label 会同时初始化;
//	 * @param id
//	 * @param escapeJs 若为true 对结果javascript Escape. 及若返回的不是json mime, 而是text, 想在js文件中自己parseJSON, 可以设置本参数
//	 * @return
//	 */
//	String getRuleGroupJsonById(Integer id, Boolean escapeJs);
	
	/**
	 * 获取规则组json字符串表示,同时加载定义本规则组的属性组, 方法组.
	 * @param id RuleGroup id
	 * @param escapeJs 若为true 对结果javascript Escape. 及若返回的不是json mime, 而是text, 想在js文件中自己parseJSON, 可以设置本参数
	 * @return
	 */
	String getRGJsonWithDefineData(Integer id, Boolean escapeJS);
	
	/**
	 * copy规则组信息,删除相关id, 业务编码,操作数label 会同时初始化;
	 * @param id
	 * @return
	 */
	RuleGroupVO copyRuleGroupById(Integer id);
	
	/**
	 * copy规则组信息,返回json, 删除相关id, 业务编码,操作数label 会同时初始化;
	 * @param id
	 * @param escapeJS 是否转义js特殊字符
	 * @return
	 */
	String copyRuleGroupJsonById(Integer id, Boolean escapeJS);
	
	/**
	 * copy规则组信息,返回json, 删除相关id, 业务编码,操作数label 会同时初始化, 并返回规则编辑界面需要的定义信息, 例如操作符号列表, 下拉列表数据等;
	 * @param id
	 * @param escapeJS 是否转义js特殊字符
	 * @return
	 */
	String copyRuleGroupJsonWithDefineData(Integer id, Boolean escapeJS);
	
	/**
	 * 修改规则组状态
	 * @param id
	 * @param status RuleStatus
	 * @return 更新条数
	 */
	Integer updateRuleGroupStatus(Integer id, RuleStatus status);
	
//	/**
//	 * 根据groupId获取关联的所有规则, 只会返回规则基本信息, 不会返回条件,动作.
//	 * @param groupId
//	 * @return
//	 */
//	List<RuleVO> getSimpleRulesByGroupId(Integer groupId) ;
	
	/**
	 * 根据groupId获取关联的所有规则, 只会返回规则基本信息, 不会返回条件,动作.
	 * @param groupId
	 * @return
	 */
	List<RuleVO> getSimpleRulesByGroupIds(List<Integer> groupIds) ;
	
//	/**
//	 * 根据groupId获取关联的所有规则, 会初始化所有条件data
//	 * @param groupId
//	 * @return
//	 */
//	List<RuleVO> getRulesByGroupId(Integer groupId);
	
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
	 * 规则测试, 返回RuleResult数组json
	 * @param groupVO
	 * @return
	 */
	String testRuleGroup(String ruleGroupStr, String facts);
	
	/**
	 * 规则测试,返回RuleResult数组json
	 * @param groupVO
	 * @return
	 */
	String testRuleGroup(String ruleGroupStr, List<Object> facts);
	
	/**
	 * 在测试页面, 测试时规则信息可能还没保存, 本方法提供把没保存的信息组装出来
	 * @param ruleGroupStr
	 * @param needFakeId 是否填充假id, 假id用负数表示, 在营销活动
	 * @return
	 */
	RuleGroupVO getTempGroupForTest(String ruleGroupStr, boolean needFakeId);
	
	/**
	 * 根据id 获取rule
	 * @param id
	 * @return
	 */
	RuleVO getRuleById(Integer id);
	
	/**
	 * 根据id列表 获取rule
	 * @param id
	 * @return
	 */
	List<RuleVO> getRuleByIds(List<Integer> ids);

	/**
	 * 是否存在rulegroup
	 * @param groupId
	 * @return
	 */
	boolean existRuleGroup(Integer groupId) ;
	
	/**
	 * 更新rulegroup,及其包含的所有规则的关联的业务编码
	 * @param groupId
	 * @param relateBizCode
	 */
	void updateRuleGroupBizCode(Integer groupId, String relateBizCode);
}
