package com.oristartech.rule.vos.result.action;

/**
 * 每个方法结果设置消耗信息的结果
 * @author Administrator
 *
 */
public class RuleConsumeActionResult extends RuleActionResult  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4892057355383629209L;

	//消耗任意数量标识
	public static final int  ANY_CONSUME_FACT_AMOUNT_TAG = -1;
		
	/**
	 * 本方法需要消耗事实的数量，若是-1，表示可以消耗任意
	 * 
	 */
	private Integer consumeAmount ;
	
	/**
	 * 可以使用fnResults中的多少个结果对象；
	 */
	private Integer useAmountInResult;
	
	/**
	 * 全局所有规则的相同方法可使用结果数量限制
	 */
	private Integer globalAmountLimit;
	
	/**
	 * @return the consumeAmount
	 */
	public Integer getConsumeAmount() {
		return consumeAmount;
	}

	/**
	 * @param consumeAmount the consumeAmount to set
	 */
	public void setConsumeAmount(Integer consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	/**
	 * @return the globalAmountLimit
	 */
	public Integer getGlobalAmountLimit() {
		return globalAmountLimit;
	}

	/**
	 * @param globalAmountLimit the globalAmountLimit to set
	 */
	public void setGlobalAmountLimit(Integer globalAmountLimit) {
		this.globalAmountLimit = globalAmountLimit;
	}

	/**
	 * @return the useAmountInResult
	 */
	public Integer getUseAmountInResult() {
		return useAmountInResult;
	}

	/**
	 * @param useAmountInResult the useAmountInResult to set
	 */
	public void setUseAmountInResult(Integer useAmountInResult) {
		this.useAmountInResult = useAmountInResult;
	}
}
