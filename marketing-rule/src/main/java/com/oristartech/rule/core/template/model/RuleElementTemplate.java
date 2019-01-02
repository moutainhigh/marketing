package com.oristartech.rule.core.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 规则元素模板
 * 
 * @author chenjunfei
 * @version 1.0
 * @created 21-十一月-2013 09:56:19
 */
@Entity
@Table(name="RULE_TPL_RULE_ELEMENT_TEMPLATE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,  region="com-oristartech-rule-basic-model-region")
public class RuleElementTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 中文名称
	 */
	@Column(length = 50, nullable = false, name="CN_NAME")
	private String cnName;
	/**
	 * 英文名称
	 */
	@Column(length = 50, nullable = false, name="NAME", unique=true)
	private String name;
	/**
	 * 类型
	 */
	@Column(length = 50, nullable = false, name="TYPE")
	private String type;
	
	/**
	 * 类型中文名称
	 */
	@Column(length = 50, nullable = false, name="CN_TYPE")
	private String cnType;
	
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	
	/**
	 * 是否启用
	 * 
	 */
	@Column(name="IS_ENABLE")
	private Boolean isEnable;
	
	/**
	 * 关联的规则类型名称
	 */
	@Column(name="RULE_TYPE", length=50)
	private String ruleType;

	public String getRuleType() {
    	return ruleType;
    }

	public void setRuleType(String ruleType) {
    	this.ruleType = ruleType;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public String getCnType() {
    	return cnType;
    }

	public void setCnType(String cnType) {
    	this.cnType = cnType;
    }

	public Boolean getIsEnable() {
    	return isEnable;
    }

	public void setIsEnable(Boolean isEnable) {
    	this.isEnable = isEnable;
    }
}