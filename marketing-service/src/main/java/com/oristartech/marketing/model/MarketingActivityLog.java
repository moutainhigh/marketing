package com.oristartech.marketing.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 营销活动日志
 */
@Entity
@Table(name="MARKETING_ACTIVITY_LOG")
public class MarketingActivityLog{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
//	/**
//	 * 营销活动
//	 */
//	@ManyToOne    
//	@JoinColumn(name="MARKETING_ACTIVITY_ID")
//	private MarketingActivity marketingActivity;
	
	/**
	 * 营销活动
	 */
	@Column(name="MARKETING_ACTIVITY_ID")
	private Long marketingActivity;
	
	/**
	 * 操作类型 0:启用 1:停用
	 */
	@Column(name="OPERATE_TYPE")
	private Integer opetateType;
	
	/**
	 * 操作时间
	 */
	@Column(name="OPERATE_TIME")
	private Date operateTime;
	
	/**
	 * 操作人ID
	 */
	@Column(name="OPERATOR_ID")
	private Long operatorId;
	
	/**
	 * 操作人姓名
	 */
	@Column(name="OPERATOR_NAME")
	private String operatorName;
	
	/**
	 * 备注
	 */
	@Column(name="REMARK")
	private String remark;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public MarketingActivity getMarketingActivity() {
//		return marketingActivity;
//	}
//
//	public void setMarketingActivity(MarketingActivity marketingActivity) {
//		this.marketingActivity = marketingActivity;
//	}

	public Integer getOpetateType() {
		return opetateType;
	}

	public void setOpetateType(Integer opetateType) {
		this.opetateType = opetateType;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getMarketingActivity() {
		return marketingActivity;
	}

	public void setMarketingActivity(Long marketingActivity) {
		this.marketingActivity = marketingActivity;
	}
	
	
	
}