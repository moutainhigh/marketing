package com.oristartech.rule.search;

import com.oristartech.rule.vos.core.enums.RuleStatus;

/**
 * 规则查询条件类
 * @author chenjunfei
 *
 */
public class RuleSearchCondition {
	private String name;
	private RuleStatus status;
	private String ruleType;
	private Boolean isAutoMatch;
	private Boolean isNotExecuteByEngine;
	private String fieldCategories;
	private String facts;
	/**
	 * 排序顺序（desc，asc）
	 */
	private String orderDirection;
	/**
	 * 排序字段名称
	 */
	private String orderName;
	/**
	 * 分页显示开始位置
	 */
	private int start = 0;
	/**
	 * 每页大小
	 */
	private int limit = 15;

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getFacts() {
    	return facts;
    }

	public void setFacts(String facts) {
    	this.facts = facts;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public RuleStatus getStatus() {
		return status;
	}

	public void setStatus(RuleStatus status) {
		this.status = status;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getFieldCategories() {
    	return fieldCategories;
    }

	public void setFieldCategories(String fieldCategories) {
    	this.fieldCategories = fieldCategories;
    }

	public Boolean getIsAutoMatch() {
    	return isAutoMatch;
    }

	public void setIsAutoMatch(Boolean isAutoMatch) {
    	this.isAutoMatch = isAutoMatch;
    }

	public Boolean getIsNotExecuteByEngine() {
    	return isNotExecuteByEngine;
    }

	public void setIsNotExecuteByEngine(Boolean isNotExecuteByEngine) {
    	this.isNotExecuteByEngine = isNotExecuteByEngine;
    }
}
