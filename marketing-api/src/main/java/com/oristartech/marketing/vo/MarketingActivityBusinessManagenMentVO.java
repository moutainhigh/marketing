package com.oristartech.marketing.vo;

import java.io.Serializable;

public class MarketingActivityBusinessManagenMentVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2147479624245596427L;
	private Long id;
    private Long activityId;
    private String businessCode;
    private String flag;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
    
}
