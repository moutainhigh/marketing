package com.oristartech.rule.core.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 * 方法参数备选列表
 * @author chenjunfei
 * @version 1.0
 * @created 04-十二月-2013 17:58:52
 */
@Entity
@Table(name = "RULE_BASE_FUNCTION_PARAM_DATA_SOURCE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class FunctionParameterDataSource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 显示值
	 */
	@Column(name = "LABEL", length=50, nullable=false)
	private String label;
	/**
	 * 值
	 */
	@Column(name = "VALUE", length=50, nullable=false)
	private String value;
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	
	/**
	 * 归属参数
	 */
	@ManyToOne
	@ForeignKey(name = "FK_ACTION_FUNCTION_PARAM_DATA_SOURCE")
	@JoinColumn(name = "FUNCTION_PARAMTER_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ActionFunctionParameter functionParameter;

	/**
	 * 联动的属性(即本值选中,则相关属性显示, 否则隐藏)， 最好少用本属性控制。若控制多个联动，用|号隔开多个id
	 */
	@Column(name = "LINKAGE_FN_PARAM_ID")
	private String  linkageId;
	/**
	 * 联动的属性验证规则, 几个联动的参数都指向一个参数时有用. 多个时，用|号隔开多个校验规则对象
	 * 否则应该用回RuleActionFunctionParameter中的validateRule属性指定验证规则
	 */
	@Column(name = "LINKAGE_VALIDATE_RULE")
	private String linkageValidateRule;
	
	public String getLinkageValidateRule() {
    	return linkageValidateRule;
    }

	public void setLinkageValidateRule(String linkageValidateRule) {
    	this.linkageValidateRule = linkageValidateRule;
    }

	public Integer getId() {
    	return id;
    }

	public void setId(Integer id) {
    	this.id = id;
    }

	public String getLabel() {
    	return label;
    }

	public void setLabel(String label) {
    	this.label = label;
    }

	public String getValue() {
    	return value;
    }

	public void setValue(String value) {
    	this.value = value;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public ActionFunctionParameter getFunctionParameter() {
    	return functionParameter;
    }

	public void setFunctionParameter(ActionFunctionParameter functionParameter) {
    	this.functionParameter = functionParameter;
    }

	public String getLinkageId() {
		return linkageId;
	}

	public void setLinkageId(String linkageId) {
		this.linkageId = linkageId;
	}
}