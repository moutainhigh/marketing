package com.oristartech.marketing.vo;

import java.io.Serializable;

import com.oristartech.rule.vos.template.vo.RuleElementTemplateVO;

public class MarketingViewVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8088388756746000803L;

	private MarketingActivityVO marketingActivityVO;
	
	private RuleElementTemplateVO ruleElementTemplateVO;
	
	private String allCategory;
	
	private String allFieldGroup;
	
	public String getAllCategory() {
		return allCategory;
	}

	public void setAllCategory(String allCategory) {
		this.allCategory = allCategory;
	}

	public String getAllFieldGroup() {
		return allFieldGroup;
	}

	public void setAllFieldGroup(String allFieldGroup) {
		this.allFieldGroup = allFieldGroup;
	}

	public MarketingActivityVO getMarketingActivityVO() {
		return marketingActivityVO;
	}

	public void setMarketingActivityVO(MarketingActivityVO marketingActivityVO) {
		this.marketingActivityVO = marketingActivityVO;
	}

	public RuleElementTemplateVO getRuleElementTemplateVO() {
		return ruleElementTemplateVO;
	}

	public void setRuleElementTemplateVO(RuleElementTemplateVO ruleElementTemplateVO) {
		this.ruleElementTemplateVO = ruleElementTemplateVO;
	}
	
}
