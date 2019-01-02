package com.oristartech.rule.functions.dto;

/**
 * 获取影票数量限制相关属性
 */
public class RuleTicketLimitInfo {
	/**
	 * 是否数量忽略限制
	 */
	private Boolean ignoreTicketAmount;
	
	/**
	 * 是否忽略会员卡类型数量限制；
	 */
	private Boolean ignoreCardTicketAmount;

	/**
	 * 会员类型打折的允许的数量
	 */
	private Integer cardTypeTicketCountLimit;
	
	/**
	 * 当前会员当前规则按方式的设置的参数，每日，每周，每月维度，查询出的
	 * 当前已经使用过的影票数量
	 */
	private Integer curRuleMemExistTicketAmount ;
	
	/**
	 * 本活动方法对会员剩余可以优惠票数， 通过统计会员活动对应时间的已经使用票数，和当前方法设置的会员可以使用数差得出。
	 */
	private Integer ruleFnMemTicketAmountLimit;
	
	/**
	 * 获取当前卡类型限制，和活动本身会员统计中 剩余最小优惠票数值
	 * cardTypeTicketCountLimit, ruleFnMemTicketAmountLimit必须已经设置过
	 * @param actionVO
	 * @param limitInfo
	 * @return
	 */
	public Integer calcCurTicketLimit() {
		Integer cardTypeAmountLimit = this.getCardTypeTicketCountLimit();
		Integer perRuleUseAmountLimit = this.getRuleFnMemTicketAmountLimit();
		
		if(cardTypeAmountLimit == null && perRuleUseAmountLimit == null) {
			return null;
		}
		cardTypeAmountLimit = cardTypeAmountLimit == null ? Integer.MAX_VALUE : cardTypeAmountLimit;
		perRuleUseAmountLimit = perRuleUseAmountLimit == null ? Integer.MAX_VALUE : perRuleUseAmountLimit;
		//返回最小值那个
		return cardTypeAmountLimit > perRuleUseAmountLimit ? perRuleUseAmountLimit : cardTypeAmountLimit;
	}
	
	/**
	 * @return the ruleFnMemTicketAmountLimit
	 */
	public Integer getRuleFnMemTicketAmountLimit() {
		return ruleFnMemTicketAmountLimit;
	}

	/**
	 * @param ruleFnMemTicketAmountLimit the ruleFnMemTicketAmountLimit to set
	 */
	public void setRuleFnMemTicketAmountLimit(Integer ruleFnMemTicketAmountLimit) {
		this.ruleFnMemTicketAmountLimit = ruleFnMemTicketAmountLimit;
	}

	/**
	 * @return the curRuleMemExistTicketAmount
	 */
//	public Integer getCurRuleMemExistTicketAmount() {
//		return curRuleMemExistTicketAmount;
//	}

	/**
	 * @param curRuleMemExistTicketAmount the curRuleMemExistTicketAmount to set
	 */
//	public void setCurRuleMemExistTicketAmount(Integer curRuleMemExistTicketAmount) {
//		this.curRuleMemExistTicketAmount = curRuleMemExistTicketAmount;
//	}

	/**
	 * @return the cardTypeTicketCountLimit
	 */
	public Integer getCardTypeTicketCountLimit() {
		return cardTypeTicketCountLimit;
	}

	/**
	 * @param cardTypeTicketCountLimit the cardTypeTicketCountLimit to set
	 */
	public void setCardTypeTicketCountLimit(Integer cardTypeTicketCountLimit) {
		this.cardTypeTicketCountLimit = cardTypeTicketCountLimit;
	}

	/**
	 * @return the ignoreTicketAmount
	 */
	public Boolean getIgnoreTicketAmount() {
		return ignoreTicketAmount;
	}

	/**
	 * @param ignoreTicketAmount the ignoreTicketAmount to set
	 */
	public void setIgnoreTicketAmount(Boolean ignoreTicketAmount) {
		this.ignoreTicketAmount = ignoreTicketAmount;
	}

	/**
	 * @return the ignoreCardTicketAmount
	 */
	public Boolean getIgnoreCardTicketAmount() {
		return ignoreCardTicketAmount;
	}

	/**
	 * @param ignoreCardTicketAmount the ignoreCardTicketAmount to set
	 */
	public void setIgnoreCardTicketAmount(Boolean ignoreCardTicketAmount) {
		this.ignoreCardTicketAmount = ignoreCardTicketAmount;
	}
	
}
