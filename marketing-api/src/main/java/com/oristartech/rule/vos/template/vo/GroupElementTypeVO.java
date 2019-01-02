package com.oristartech.rule.vos.template.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 规则元素类别vo
 * @author chenjunfei
 *
 */
public class GroupElementTypeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8929469116095686386L;

	private Integer id;

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 中文名称
	 */
	private String cnName;
	/**
	 * 顺序号
	 */
	private Integer seqNum;

	/**
	 * 备注
	 */
	private String remark;

	private List<FieldGroupVO> fieldGroups = new ArrayList<FieldGroupVO> ();
	
	private List<FunctionGroupVO> funcGroups = new ArrayList<FunctionGroupVO>();
	
	public Integer getId() {
    	return id;
    }

	public void setId(Integer id) {
    	this.id = id;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
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

	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }

	public List<FieldGroupVO> getFieldGroups() {
    	return fieldGroups;
    }

	public void setFieldGroups(List<FieldGroupVO> fieldGroups) {
    	this.fieldGroups = fieldGroups;
    }

	public List<FunctionGroupVO> getFuncGroups() {
    	return funcGroups;
    }

	public void setFuncGroups(List<FunctionGroupVO> funcGroups) {
    	this.funcGroups = funcGroups;
    }
}
