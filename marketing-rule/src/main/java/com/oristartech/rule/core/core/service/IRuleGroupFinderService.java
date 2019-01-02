package com.oristartech.rule.core.core.service;

import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * rulegroup 查找service ,主要是提供给ruleManagerService调用. ruleManagerService是提供给外部的接口.
 * @author chenjunfei
 *
 */
public interface IRuleGroupFinderService {
	/**
	 * 获取规则组信息, 会初始化外部数据label显示文字, 应该在编辑规则时使用, 其他地方调用getRuleGroupNoInitExternal
	 * @param id
	 * @return
	 */
	public RuleGroupVO getRuleGroupInitExternal(Integer id);
	
	/**
	 * 获取规则组信息, 同时加载定义本规则组的属性组, 方法组.
	 * @param RuleGroup id
	 * @return
	 */
	public RuleGroupVO getRGWithDefinData(Integer id);
	
	/**
	 * 获取规则组信息, 但不会初始化label, 及外部数据等定义信息
	 * @param id
	 * @return
	 */
	public RuleGroupVO getRuleGroupNoInitExternal(Integer id);
	
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
	
}
