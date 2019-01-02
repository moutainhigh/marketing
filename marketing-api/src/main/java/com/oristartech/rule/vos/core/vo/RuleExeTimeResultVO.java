package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RuleExeTimeResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3627640840676351123L;
	private Integer totalRule = 0;
	private Integer successAmount = 0;
	private Integer falureAmount = 0;
	private List<RuleExeTimeVO> successRuleVO = new ArrayList<RuleExeTimeVO>();
	private List<RuleExeTimeVO> falureRuleVO = new ArrayList<RuleExeTimeVO>();
	
	public Integer getTotalRule() {
		return totalRule;
	}
	public void setTotalRule(Integer totalRule) {
		this.totalRule = totalRule;
	}
	public Integer getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(Integer successAmount) {
		this.successAmount = successAmount;
	}
	public Integer getFalureAmount() {
		return falureAmount;
	}
	public void setFalureAmount(Integer falureAmount) {
		this.falureAmount = falureAmount;
	}
	public List<RuleExeTimeVO> getSuccessRuleVO() {
		return successRuleVO;
	}
	public void setSuccessRuleVO(List<RuleExeTimeVO> successRuleVO) {
		this.successRuleVO = successRuleVO;
	}
	public List<RuleExeTimeVO> getFalureRuleVO() {
		return falureRuleVO;
	}
	public void setFalureRuleVO(List<RuleExeTimeVO> falureRuleVO) {
		this.falureRuleVO = falureRuleVO;
	}
	
	public void addSuccessAmount(){
		successAmount++;
	}
	
	public void addFalureAmount(){
		falureAmount++;
	}
	
	public void addSuccessRuleVO(RuleExeTimeVO vo){
		successRuleVO.add(vo);
	}
	
	public void addFalureRuleVO(RuleExeTimeVO vo){
		falureRuleVO.add(vo);
	}
}
