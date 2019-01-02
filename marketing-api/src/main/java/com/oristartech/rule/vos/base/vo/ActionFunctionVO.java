package com.oristartech.rule.vos.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.oristartech.rule.vos.base.enums.ActionFunctionType;
import com.oristartech.rule.vos.base.enums.FunctionResultType;


public class ActionFunctionVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1479617708656112665L;

	private Integer id;

	/**
	 * 函数名称
	 */
	private String clsMethod;

	/**
	 * 中文名称
	 */
	private String cnName;

	/**
	 * 函数类名, 包含包名称
	 */
	private String clzName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否删除, true删除，false未删除
	 */
	private boolean isDelete;
	/**
	 * 方法内部类型
	 */
	private ActionFunctionType type;
	
	/**
	 * 业务方法分类
	 */
	private Integer fnCategoryId;
	
	private String fnCategoryName;
	/**
	 * 唯一名称
	 */
	private String uniqueName;
	
	/**
	 * 方法效果. 例如添加商品, 修改商品, 客户端可以根据本属性直接调用相关方法(必须是符合java命名的英文名)
	 */
	private String fnEffect;
	
	private List<ActionFunctionParameterVO> actionFunctionParameters = new ArrayList<ActionFunctionParameterVO>();
	
	/**
	 * 若是由引擎执行,在一次匹配过程中的同一条规则里是否做多次.
	 */
	private Boolean isExeMultiTime;
	
	/**
	 * 方法产生的结果类型。若方法需要返回map结果，需要设置本值
	 */
	private FunctionResultType resultType;
	
	/**
	 * @return the resultType
	 */
	public FunctionResultType getResultType() {
		return resultType;
	}

	/**
	 * @param resultType the resultType to set
	 */
	public void setResultType(FunctionResultType resultType) {
		this.resultType = resultType;
	}

	public Boolean getIsExeMultiTime() {
    	return isExeMultiTime;
    }

	public void setIsExeMultiTime(Boolean isExeMultiTime) {
    	this.isExeMultiTime = isExeMultiTime;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClsMethod() {
		return clsMethod;
	}

	public void setClsMethod(String clsMethod) {
		this.clsMethod = clsMethod;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getClzName() {
		return clzName;
	}

	public void setClzName(String clzName) {
		this.clzName = clzName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public ActionFunctionType getType() {
		return type;
	}

	public void setType(ActionFunctionType type) {
		this.type = type;
	}

	public Integer getFnCategoryId() {
		return fnCategoryId;
	}

	public void setFnCategoryId(Integer fnCategoryId) {
		this.fnCategoryId = fnCategoryId;
	}

	public String getFnCategoryName() {
		return fnCategoryName;
	}

	public void setFnCategoryName(String fnCategoryName) {
		this.fnCategoryName = fnCategoryName;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getFnEffect() {
    	return fnEffect;
    }

	public void setFnEffect(String fnEffect) {
    	this.fnEffect = fnEffect;
    }

	public List<ActionFunctionParameterVO> getActionFunctionParameters() {
		return actionFunctionParameters;
	}

	public void setActionFunctionParameters(List<ActionFunctionParameterVO> actionFunctionParameters) {
		this.actionFunctionParameters = actionFunctionParameters;
	}
}
