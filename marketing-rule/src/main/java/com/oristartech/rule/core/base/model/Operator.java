package com.oristartech.rule.core.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.oristartech.rule.vos.base.enums.OperatorType;

/**
 * @author chenjunfei
 * @version 1.0
 * @created 21-十月-2013 10:17:57
 */
@Entity
@Table(name="RULE_BASE_OPERATOR")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class Operator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(length = 50, name = "CN_NAME")
	private String cnName;
	/**
	 * 如+,-等，若是自定义函数，请写完整类路径加方法，并把函数实现为static方法。
	 */
	@Column(length = 100,name = "OP_CODE")
	private String opCode;
	/**
	 * 操作符需要的操作数量，-1是表示不定，例如in（...）
	 */
	@Column(length = 100,name = "OP_NUM")
	private Integer opNum;
	/**
	 * 逻辑运算，算术运算，自定义等
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TYPE")
	private OperatorType type;
	/**
	 * 是否自定义
	 */
	@Column(name = "IS_CUSTOM")
	private Boolean isCustom;
	
	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;
	/**
	 * 唯一名称
	 */
	@Column(name = "UNIQUE_NAME", length= 150, unique=true)
	private String uniqueName;
	/**
	 * 默认操作数, 由于有些自定义操作符号, 可以带有预定义的操作数,虽然可以不设, 但在和业务系统交互, 
	 * 或显示规则时需要把这种预设的概念表达出来.
	 * 多个值用","号隔开
	 */
	@Column(name="DEFAULT_OPERAND")
	private String defaultOperand;
	
	/**
	 * 同等性标记, 相同类型相同tag的操作符号认为是相同的. 
	 */
	@Column(name="IDENTITY_TAG")
	private Integer identityTag;

	/**
	 * 若是true, 不会根据opnum 生成操作数, 用于模板中
	 */
	@Column(name="IS_PLAIN_OP")
	private Boolean isPlainOp;
	
	/**
	 * 操作符的操作数是否不能为null, 若是true,同时判空
	 */
	@Column(name = "IS_NOT_NULL_OP")
	private Boolean isNotNullOp;
	
	/**
	 * 条件外面的修饰判定符号。例如exists， not等等。 通常用在单个条件中，方便在条件外设置判定符号。
	 */
	@Column(name = "CONDITION_MODIFIER")
	private String conditionModifier ;
	
	public String getConditionModifier() {
    	return conditionModifier;
    }

	public void setConditionModifier(String conditionModifier) {
    	this.conditionModifier = conditionModifier;
    }

	public Boolean getIsNotNullOp() {
    	return isNotNullOp;
    }

	public void setIsNotNullOp(Boolean isNotNullOp) {
    	this.isNotNullOp = isNotNullOp;
    }

	public Boolean getIsPlainOp() {
    	return isPlainOp;
    }

	public void setIsPlainOp(Boolean isPlainOp) {
    	this.isPlainOp = isPlainOp;
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

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public Integer getOpNum() {
		return opNum;
	}

	public void setOpNum(Integer opNum) {
		this.opNum = opNum;
	}

	public OperatorType getType() {
		return type;
	}

	public void setType(OperatorType type) {
		this.type = type;
	}

	public Boolean getIsCustom() {
    	return isCustom;
    }

	public void setIsCustom(Boolean isCustom) {
    	this.isCustom = isCustom;
    }

	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }

	public String getUniqueName() {
    	return uniqueName;
    }

	public void setUniqueName(String uniqueName) {
    	this.uniqueName = uniqueName;
    }

	public String getDefaultOperand() {
    	return defaultOperand;
    }

	public void setDefaultOperand(String defaultOperand) {
    	this.defaultOperand = defaultOperand;
    }

	public Integer getIdentityTag() {
    	return identityTag;
    }

	public void setIdentityTag(Integer identityTag) {
    	this.identityTag = identityTag;
    }
}