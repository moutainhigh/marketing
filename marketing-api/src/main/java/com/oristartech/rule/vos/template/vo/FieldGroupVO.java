package com.oristartech.rule.vos.template.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 属性分组vo
 * @author chenjunfei
 *
 */
public class FieldGroupVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7513403215288646466L;
	
	private Integer id;
	/**
	 * 中文名称
	 */
	private String cnName;
	
	/**
	 * 顺序号
	 */
	private Integer seqNum;
	
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
	 * 管理编号(不同的分组类别下,编号可相同)
	 */
	private String managerNum;
	
	/**
	 * 是否启用
	 * 
	 */
	private Boolean isEnable;
	
	private String remark;
	
	/**
	 * 在页面添加或测试页面,是否允许显示添加多个按钮.
	 */
	private Boolean isMultiInTest;
	
	/**
	 * 当前fieldGroup下所有的field
	 */
	private List<FieldGroupAndFieldRelationVO> groupAndFields = new ArrayList<FieldGroupAndFieldRelationVO>();
	
	public Boolean getIsMultiInTest() {
    	return isMultiInTest;
    }

	public void setIsMultiInTest(Boolean isMultiInTest) {
    	this.isMultiInTest = isMultiInTest;
    }

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

	public Integer getGroupElementTypeId() {
    	return groupElementTypeId;
    }


	public void setGroupElementTypeId(Integer groupElementTypeId) {
    	this.groupElementTypeId = groupElementTypeId;
    }


	public String getManagerNum() {
    	return managerNum;
    }


	public void setManagerNum(String managerNum) {
    	this.managerNum = managerNum;
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

	public List<FieldGroupAndFieldRelationVO> getGroupAndFields() {
    	return groupAndFields;
    }

	public void setGroupAndFields(List<FieldGroupAndFieldRelationVO> groupAndFields) {
    	this.groupAndFields = groupAndFields;
    }

	public Boolean getIsEnable() {
    	return isEnable;
    }

	public void setIsEnable(Boolean isEnable) {
    	this.isEnable = isEnable;
    }
}
