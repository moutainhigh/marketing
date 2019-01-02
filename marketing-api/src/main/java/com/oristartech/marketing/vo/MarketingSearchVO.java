package com.oristartech.marketing.vo;

import java.io.Serializable;

/**
 * 营销活动查询VO
 */
public class MarketingSearchVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743763355922071256L;

	/**
	 * 租户ID
	 */
	public String  tenantId;
	/**
	 * 名称
	 */
	public String searchActivityName;
	
	/**
	 * 状态
	 */
	public String searchState;
	
	/**
	 * 有效期 - 开始
	 */
	public String searchValidDateStart;
	
	/**
	 * 有效期 - 结束
	 */
	public String searchValidDateEnd;
	
	/**
	 * 创建人
	 */
	public String searchCreaterId;
	
	/**
	 * 创建单位
	 */
	public String searchCreaterArea;
	
	/**
	 * 审批人
	 */
	public String searchApprovalmanId;
	/**
	 * 执行方法
	 */
	public String executeMode;
	
	/**
	 * 适用商户
	 */
	public String searchBusinessCode;
	/**
	 * 适用商户(不知道会不会影响以前程序 故未删除searchBusinessCode)
	 */
	public String businessCode;
	
	/**
	 * 交易影院
	 */
	public String cinemaCode;
	
	private String autoShow = "1";
	
	/**
	 * 交易渠道
	 */
	private String consumeWayCode;
	
	/**
	 * 交易类型
	 * @return
	 */
	private String tradeType;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getConsumeWayCode() {
		return consumeWayCode;
	}

	public void setConsumeWayCode(String consumeWayCode) {
		this.consumeWayCode = consumeWayCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getAutoShow() {
		return autoShow;
	}

	public void setAutoShow(String autoShow) {
		this.autoShow = autoShow;
	}
	
	
	private Integer pageNo=1;
	private Integer pageSize=10;
	public String label;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getSearchActivityName() {
		return searchActivityName;
	}

	public void setSearchActivityName(String searchActivityName) {
		this.searchActivityName = searchActivityName;
	}

	public String getSearchState() {
		return searchState;
	}

	public void setSearchState(String searchState) {
		this.searchState = searchState;
	}

	public String getSearchValidDateStart() {
		return searchValidDateStart;
	}

	public void setSearchValidDateStart(String searchValidDateStart) {
		this.searchValidDateStart = searchValidDateStart;
	}

	public String getSearchValidDateEnd() {
		return searchValidDateEnd;
	}

	public void setSearchValidDateEnd(String searchValidDateEnd) {
		this.searchValidDateEnd = searchValidDateEnd;
	}

	public String getSearchCreaterArea() {
		return searchCreaterArea;
	}

	public void setSearchCreaterArea(String searchCreaterArea) {
		this.searchCreaterArea = searchCreaterArea;
	}

	public String getSearchCreaterId() {
		return searchCreaterId;
	}

	public void setSearchCreaterId(String searchCreaterId) {
		this.searchCreaterId = searchCreaterId;
	}

	public String getSearchApprovalmanId() {
		return searchApprovalmanId;
	}

	public void setSearchApprovalmanId(String searchApprovalmanId) {
		this.searchApprovalmanId = searchApprovalmanId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getExecuteMode() {
		return executeMode;
	}

	public void setExecuteMode(String executeMode) {
		this.executeMode = executeMode;
	}

	public String getSearchBusinessCode() {
		return searchBusinessCode;
	}

	public void setSearchBusinessCode(String searchBusinessCode) {
		this.searchBusinessCode = searchBusinessCode;
	}
	
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getCinemaCode() {
		return cinemaCode;
	}

	public void setCinemaCode(String cinemaCode) {
		this.cinemaCode = cinemaCode;
	}
}
