package com.oristartech.rule.vos.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ActionFunctionParameterVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 979854320634225599L;
	private Integer id;
	/**
	 * 英文名称，java变量名称
	 */
	private String name;
	/**
	 * 中文名称
	 */
	private String cnName;
	
	/**
	 * 参数类型
	 */
	private String type;
	
	/**
	 * 默认值
	 */
	private String defaultValue;
	
	/**
	 * 所属函数
	 */
	private Integer actionFunctionId;

	/**
	 * 顺序号
	 */
	private Integer seqNum;
	
	private List<FunctionParameterDataSourceVO> funcParamDataSource = new ArrayList<FunctionParameterDataSourceVO>();
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getActionFunctionId() {
		return actionFunctionId;
	}

	public void setActionFunctionId(Integer actionFunctionId) {
		this.actionFunctionId = actionFunctionId;
	}

	public List<FunctionParameterDataSourceVO> getFuncParamDataSource() {
    	return funcParamDataSource;
    }

	public void setFuncParamDataSource(List<FunctionParameterDataSourceVO> funcParamDataSource) {
    	this.funcParamDataSource = funcParamDataSource;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }
}
