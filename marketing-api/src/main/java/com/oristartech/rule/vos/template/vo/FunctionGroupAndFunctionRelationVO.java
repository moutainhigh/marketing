package com.oristartech.rule.vos.template.vo;

import java.io.Serializable;

import com.oristartech.rule.vos.base.vo.ActionFunctionVO;


public class FunctionGroupAndFunctionRelationVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7984545103257866609L;
	private Integer id;
	/**
	 * 执行方法
	 */
	private ActionFunctionVO actionFunction;
	/**
	 * 方法组
	 */
	private Integer functionGroupId;

	/**
	 * 顺序号
	 */
	private Integer seqNum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ActionFunctionVO getActionFunction() {
		return actionFunction;
	}

	public void setActionFunction(ActionFunctionVO actionFunction) {
		this.actionFunction = actionFunction;
	}

	public Integer getFunctionGroupId() {
		return functionGroupId;
	}

	public void setFunctionGroupId(Integer functionGroupId) {
		this.functionGroupId = functionGroupId;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
}
