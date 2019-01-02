package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.oristartech.rule.common.util.HashCodeUtil;

public class RuleActionParameterVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5071936109514163466L;

	private Long id;
	
	/**
	 * 关联的函数参数id
	 */
	private Integer fnParameterId;
	
	/**
	 * 关联的函数参数name
	 */
	private String fnParameterName;
	
	/**
	 * 关联的函数参数中文name
	 */
	private String fnParameterCnName;
	
	/**
	 * 关联的ruleAction id
	 */
	private Long ruleActionId;
	
	/**
	 * 参数值
	 */
	private String value;
	
	/**
	 * 参数显示label
	 */
	private String label;
	
	/**
	 * 是否文件参数
	 */
	private Boolean isFile;
	
	/**
	 * 有些参数是大参数,例如邮件,短信内容, 文件,在生成规则文件时不应该加载这些参数. 
	 */
	private Boolean isLazy;
	
	/**
	 * 是否配置型参数， 这些参数不会渲染在界面
	 */
	private Boolean isConfig;
	
	/**
	 * 顺序号
	 */
	private Integer seqNum ;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		RuleActionParameterVO other = (RuleActionParameterVO)obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals(); 
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodeUtil.initialNonZeroOddNumber, HashCodeUtil.multiplierNonZeroOddNumber)
		                        .append(id).toHashCode();
	}
	
	
	public Boolean getIsConfig() {
    	return isConfig;
    }

	public void setIsConfig(Boolean isConfig) {
    	this.isConfig = isConfig;
    }

	public Boolean getIsLazy() {
    	return isLazy;
    }

	public void setIsLazy(Boolean isLazy) {
    	this.isLazy = isLazy;
    }

	public Boolean getIsFile() {
    	return isFile;
    }

	public void setIsFile(Boolean isFile) {
    	this.isFile = isFile;
    }

	public String getFnParameterCnName() {
    	return fnParameterCnName;
    }

	public void setFnParameterCnName(String fnParameterCnName) {
    	this.fnParameterCnName = fnParameterCnName;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFnParameterId() {
		return fnParameterId;
	}

	public void setFnParameterId(Integer fnParameterId) {
		this.fnParameterId = fnParameterId;
	}

	public String getFnParameterName() {
		return fnParameterName;
	}

	public void setFnParameterName(String fnParameterName) {
		this.fnParameterName = fnParameterName;
	}

	public Long getRuleActionId() {
		return ruleActionId;
	}

	public void setRuleActionId(Long ruleActionId) {
		this.ruleActionId = ruleActionId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }
	
	
}
