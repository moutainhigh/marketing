package com.oristartech.rule.vos.result;

import java.io.Serializable;

/**
 * 结果中基本规则vo信息
 * @author chenjunfei
 *
 */
public class BaseResultRuleVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6901901012445956974L;

	private Integer id;
	
	/**
	 * 组id
	 */
	private Integer ruleGroupId;
	
	/**
	 * 组名称
	 */
	private String ruleGroupName;
	
	/**
	 * 名称
	 */
	private String name;
	
	public Integer getId() {
    	return id;
    }

	public void setId(Integer id) {
    	this.id = id;
    }

	public Integer getRuleGroupId() {
    	return ruleGroupId;
    }

	public void setRuleGroupId(Integer ruleGroupId) {
    	this.ruleGroupId = ruleGroupId;
    }

	public String getRuleGroupName() {
    	return ruleGroupName;
    }

	public void setRuleGroupName(String ruleGroupName) {
    	this.ruleGroupName = ruleGroupName;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }
}
