package com.oristartech.rule.vos.template.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FunctionGroupVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3700429578952119626L;

	private Integer id;

	private String cnName;

	/**
	 * 顺序号
	 */
	private Integer seqNum;

	/**
	 * 管理编号(不同的分组类别下,编号可相同)
	 */
	private String managerNum;
	
	/**
	 * 分组元素类别id
	 */
	private Integer groupElementTypeId;
	
	/**
	 * 分组元素类别英文名称
	 */
	private String groupElementTypeName;
	
	/**
	 * 分组元素类别中文名称
	 */
	private String groupElementTypeCnName;
	
	/**
	 * 是否启用
	 * 
	 */
	private Boolean isEnable;
	
	private String remark;
	
	/**
	 * 是否在界面只能添加单个
	 */
	private Boolean isSingle;

	private List<FunctionGroupAndFunctionRelationVO> groupAndFuncs = new ArrayList<FunctionGroupAndFunctionRelationVO>();
	
	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
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

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public String getManagerNum() {
    	return managerNum;
    }

	public void setManagerNum(String managerNum) {
    	this.managerNum = managerNum;
    }

	public Integer getGroupElementTypeId() {
    	return groupElementTypeId;
    }

	public void setGroupElementTypeId(Integer groupElementTypeId) {
    	this.groupElementTypeId = groupElementTypeId;
    }

	public String getGroupElementTypeName() {
		return groupElementTypeName;
	}

	public void setGroupElementTypeName(String groupElementTypeName) {
		this.groupElementTypeName = groupElementTypeName;
	}

	public String getGroupElementTypeCnName() {
		return groupElementTypeCnName;
	}

	public void setGroupElementTypeCnName(String groupElementTypeCnName) {
		this.groupElementTypeCnName = groupElementTypeCnName;
	}

	public List<FunctionGroupAndFunctionRelationVO> getGroupAndFuncs() {
    	return groupAndFuncs;
    }

	public void setGroupAndFuncs(List<FunctionGroupAndFunctionRelationVO> groupAndFuncs) {
    	this.groupAndFuncs = groupAndFuncs;
    }

	public Boolean getIsEnable() {
    	return isEnable;
    }

	public void setIsEnable(Boolean isEnable) {
    	this.isEnable = isEnable;
    }

	public Boolean getIsSingle() {
    	return isSingle;
    }

	public void setIsSingle(Boolean isSingle) {
    	this.isSingle = isSingle;
    }
}
