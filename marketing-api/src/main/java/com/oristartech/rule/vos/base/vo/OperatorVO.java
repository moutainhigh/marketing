package com.oristartech.rule.vos.base.vo;

import java.io.Serializable;

import com.oristartech.rule.vos.base.enums.OperatorType;

public class OperatorVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2129543670497774973L;

	private Integer id;

	private String cnName;
	/**
	 * 如+,-等，若是自定义函数，请写完整类路径加方法，并把函数实现为static方法。
	 */
	private String opCode;
	/**
	 * 操作符需要的操作数量，负数表示可以多组值, 数量表示每组包含多少个值.
	 * -1 表示可以输入多个值
	 * -2 表示可以输入多组值, 而且每组2个值
	 */
	private Integer opNum;
	/**
	 * 逻辑运算，算术运算，自定义等
	 */
	private OperatorType type;
	/**
	 * 是否自定义
	 */
	private boolean isCustom;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 唯一名称
	 */
	private String uniqueName;
	/**
	 * 默认操作数, 由于有些自定义操作符号, 可以带有预定义的操作数,虽然可以不设, 但在和业务系统交互, 或显示规则时需要把这种预设的概念表达出来.
	 * 多个值用","号隔开
	 */
	private String defaultOperand;

	/**
	 * 同等性标记, 相同类型相同tag的操作符号认为是相同的. 
	 */
	private Integer identityTag;
	
	/**
	 * 若是true, 不会根据opnum 生成操作数, 用于模板中
	 */
	private Boolean isPlainOp;
	
	/**
	 * 操作符的操作数是否不能为null, 若是true,同时判空
	 */
	private Boolean isNotNullOp;
	
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

	public boolean isCustom() {
		return isCustom;
	}

	public void setCustom(boolean isCustom) {
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
