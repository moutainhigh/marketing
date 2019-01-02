package com.oristartech.rule.drools.executor.context;

import java.util.List;

import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.RuleSectionResult;

/**
 * 当前action 执行的context;
 * 
 * @author chenjunfei
 * 
 */
public class ActionExecuteContext {
	/**
	 * 进入section执行的match fact
	 */
	private List<Object> curMatchFacts;
	/**
	 * 当前的rule result
	 */
	private RuleResult curRuleResult;

	/**
	 * 当前sectionResult
	 */
	private RuleSectionResult curSectionResult;

	/**
	 * 动作在一次匹配中运行次数
	 */
	private Integer runTimes;
	
	/**
	 * 当前运行次数
	 */
	private Integer runIndex;
	
	private RuleVO curRuleVO ; 
	
	public RuleVO getCurRuleVO() {
    	return curRuleVO;
    }

	public void setCurRuleVO(RuleVO curRuleVO) {
    	this.curRuleVO = curRuleVO;
    }

	public Integer getRunIndex() {
		return runIndex;
	}

	public void setRunIndex(Integer runIndex) {
		this.runIndex = runIndex;
	}

	public Integer getRunTimes() {
    	return runTimes;
    }

	public void setRunTimes(Integer runTimes) {
    	this.runTimes = runTimes;
    }

	public List<Object> getCurMatchFacts() {
		return curMatchFacts;
	}
	
	public void setCurMatchFacts(List<Object> curMatchFacts) {
		this.curMatchFacts = curMatchFacts;
	}

	public RuleResult getCurRuleResult() {
		return curRuleResult;
	}

	public void setCurRuleResult(RuleResult curRuleResult) {
		this.curRuleResult = curRuleResult;
	}

	public RuleSectionResult getCurSectionResult() {
		return curSectionResult;
	}

	public void setCurSectionResult(RuleSectionResult curSectionResult) {
		this.curSectionResult = curSectionResult;
	}
}
