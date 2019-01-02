package com.oristartech.rule.vos.result.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 单个操作的结果
 * @author chenjunfei
 * 
 */
public class RuleActionResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6328198673534287063L;

	private Long ruleActionId;
	
	/**
	 * 方法id
	 */
	private Integer fnId;
	
	/**
	 * 是否只是方法参数
	 */
	private Boolean isParam;
	
	/**
	 * 业务方法分类名称
	 */
	private String fnCategoryName;
	
	/**
	 * 业务方法唯一名称
	 */
	@JsonIgnore
	private String fnUniqueName;
	
	/**
	 * 方法效果. 例如添加商品, 修改商品, 客户端可以根据本属性直接调用相关方法 
	 */
	private String fnEffect;
	
	/**
	 * 方法名称
	 */
	private String cnName;
	
	/**
	 * 第几次运行的结果
	 */
	private Integer runIndex;
	
	/**
	 * 方法结果, 通常就是传递过来的事实数据. 若是执行型方法, 就是方法参数
	 */
	private List<Map<String, Object>> fnResults = new ArrayList<Map<String, Object>> ();
	
	/**
	 * 方法产生的结果类型。若方法需要返回map结果，需要设置本值
	 */
	@JsonIgnore
	private String fnResultType;
	
	/**
	 * 结果中修改事实的数量, 某些业务方法才设置本属性, 某些不会, 具体看业务.
	 */
	@JsonIgnore
	private int modifyFactAmount = 0;
	
	
	public int getModifyFactAmount() {
    	return modifyFactAmount;
    }

	public void setModifyFactAmount(int modifyFactAmount) {
    	this.modifyFactAmount = modifyFactAmount;
    }

	public String getFnResultType() {
    	return fnResultType;
    }

	public void setFnResultType(String fnResultType) {
    	this.fnResultType = fnResultType;
    }

	public Integer getRunIndex() {
    	return runIndex;
    }

	public void setRunIndex(Integer runIndex) {
    	this.runIndex = runIndex;
    }

	public Boolean getIsParam() {
    	return isParam;
    }

	public void setIsParam(Boolean isParam) {
    	this.isParam = isParam;
    }

	public Integer getFnId() {
    	return fnId;
    }

	public void setFnId(Integer fnId) {
    	this.fnId = fnId;
    }

	public String getCnName() {
    	return cnName;
    }

	public void setCnName(String cnName) {
    	this.cnName = cnName;
    }

	public String getFnCategoryName() {
    	return fnCategoryName;
    }

	public void setFnCategoryName(String fnCategoryName) {
    	this.fnCategoryName = fnCategoryName;
    }

	public String getFnUniqueName() {
    	return fnUniqueName;
    }

	public void setFnUniqueName(String fnUniqueName) {
    	this.fnUniqueName = fnUniqueName;
    }

	public String getFnEffect() {
    	return fnEffect;
    }

	public void setFnEffect(String fnEffect) {
    	this.fnEffect = fnEffect;
    }

	public Long getRuleActionId() {
    	return ruleActionId;
    }

	public void setRuleActionId(Long ruleActionId) {
    	this.ruleActionId = ruleActionId;
    }

	public List<Map<String, Object>> getFnResults() {
    	return fnResults;
    }

	public void setFnResults(List<Map<String, Object>> fnResults) {
    	this.fnResults = fnResults;
    }

	public void addFnResult(Map<String, Object> fnResult) {
		if(this.fnResults == null) {
			fnResults = new ArrayList<Map<String, Object>> ();
		}
		fnResults.add(fnResult);
	} 
}
