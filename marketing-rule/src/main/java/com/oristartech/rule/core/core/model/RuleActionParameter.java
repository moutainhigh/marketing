package com.oristartech.rule.core.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.oristartech.rule.core.base.model.RuleActionFunctionParameter;

/**
 * rule action 关联的参数
 * @author user
 *
 */
@Entity
@Table(name="RULE_CORE_RULE_ACTION_PARAMETER")
public class RuleActionParameter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	/**
	 * 关联的参数
	 */
	@ManyToOne
	@ForeignKey(name = "FK_RAP_RULE_ACTION_FUNCTION_PARAMETER")
	@JoinColumn(name = "FN_PARAMETER_ID")
	private RuleActionFunctionParameter fnParameter;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@ForeignKey(name = "FK_RAP_RULE_ACTION")
	@JoinColumn(name = "RULE_ACTION_ID")
	private RuleAction ruleAction;
	
	/**
	 * 参数值, 可以是json字符串, 因为可能有文件, 要长一点.
	 * 
	 */
	@Lob
	@Column(name = "VALUE", length=655350)
	private String value;
	
	/**
	 * 若参数是文件, 或是其他属性, 可以用label保存显示值, 例如文件名
	 */
	@Column(name = "LABEL", length=50)
	private String label;
	
	//现在用value同时保存文件base64编码
	
//	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
//	@JoinColumn(name="RULE_ACCESSORY_FILE_ID")
//	private RuleAccessoryFile fileValue;
	
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum ;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RuleActionFunctionParameter getFnParameter() {
		return fnParameter;
	}

	public void setFnParameter(RuleActionFunctionParameter fnParameter) {
		this.fnParameter = fnParameter;
	}

	public RuleAction getRuleAction() {
		return ruleAction;
	}

	public void setRuleAction(RuleAction ruleAction) {
		this.ruleAction = ruleAction;
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

	public String getLabel() {
    	return label;
    }

	public void setLabel(String label) {
    	this.label = label;
    }
}
