package com.oristartech.rule.base.op.model;

import com.oristartech.rule.drools.executor.context.RuleMatchContext;

/**
 * 自定义条件比较方法中的参数对象
 * @author chenjunfei
 *
 */
public class CustomOpParameter {
	/**
	 * 静态创建方法
	 * @param ruleId
	 * @param conditionId
	 * @param conditionEleId
	 * @param modelFieldName
	 * @param conditionParams
	 * @param fact
	 * @param fieldValue
	 * @return
	 */
	public static CustomOpParameter create(
			RuleMatchContext context,
			Integer ruleId,
			Long conditionId,
			Long conditionEleId,
			String modelCategoryName,
			String modelFieldName,
			String conditionParams,
			Object fact,
			Object fieldValue) {
		CustomOpParameter param = new CustomOpParameter();
		param.ruleId = ruleId;
		param.conditionId = conditionId;
		param.conditionEleId = conditionEleId;
		param.modelCategoryName = modelCategoryName;
		param.modelFieldName = modelFieldName;
		param.conditionParams = conditionParams;
		param.fact = fact;
		param.fieldValue = fieldValue;
		param.context = context;
		return param;
	}
	
	/**
	 * 当前规则id
	 */
	private Integer ruleId;
	
	/**
	 * 当前的条件分类Id
	 */
	private Long conditionId;
	
	/**
	 * 当前条件元素id
	 */
	private Long conditionEleId;
	
	/**
	 * 当前条件元素对应的分类名称
	 */
	private String modelCategoryName;
	
	/**
	 * 当前条件元素对应的属性名称
	 */
	private String modelFieldName;
	
	/**
	 * 当前条件在规则中设置的要比较的参数值
	 */
	private String conditionParams;
	
	/**
	 * 当前来匹配的事实对象
	 */
	private Object fact;
	
	/**
	 * 当前事实对应的要比较的属性值
	 */
	private Object fieldValue;
	
	/**
	 * 当前匹配context
	 */
	private RuleMatchContext context;
	
	public String getModelCategoryName() {
    	return modelCategoryName;
    }

	public void setModelCategoryName(String modelCategoryName) {
    	this.modelCategoryName = modelCategoryName;
    }

	/**
	 * @return the context
	 */
	public RuleMatchContext getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(RuleMatchContext context) {
		this.context = context;
	}

	/**
	 * @return the conditionId
	 */
	public Long getConditionId() {
		return conditionId;
	}
	
	/**
	 * @param conditionId the conditionId to set
	 */
	public void setConditionId(Long conditionId) {
		this.conditionId = conditionId;
	}
	/**
	 * @return the conditionEleId
	 */
	public Long getConditionEleId() {
		return conditionEleId;
	}
	/**
	 * @param conditionEleId the conditionEleId to set
	 */
	public void setConditionEleId(Long conditionEleId) {
		this.conditionEleId = conditionEleId;
	}
	/**
	 * @return the ruleId
	 */
	public Integer getRuleId() {
		return ruleId;
	}
	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	/**
	 * @return the modelFieldName
	 */
	public String getModelFieldName() {
		return modelFieldName;
	}
	/**
	 * @param modelFieldName the modelFieldName to set
	 */
	public void setModelFieldName(String modelFieldName) {
		this.modelFieldName = modelFieldName;
	}
	
	
	/**
	 * @return the fact
	 */
	public Object getFact() {
		return fact;
	}
	/**
	 * @param fact the fact to set
	 */
	public void setFact(Object fact) {
		this.fact = fact;
	}
	/**
	 * @return the conditionParams
	 */
	public String getConditionParams() {
		return conditionParams;
	}
	/**
	 * @param conditionParams the conditionParams to set
	 */
	public void setConditionParams(String conditionParams) {
		this.conditionParams = conditionParams;
	}
	/**
	 * @return the fieldValue
	 */
	public Object getFieldValue() {
		return fieldValue;
	}
	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	
}
