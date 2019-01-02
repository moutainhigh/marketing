package com.oristartech.marketing.model;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MemberMarketingActivityLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMBER_MARKETING_ACTIVITY_LOG")
public class MemberMarketingActivityLog {

	// Fields
	private Long id;
	private String flowCode;
	private Long activityId;
	private Integer ruleGroupId;
	private Integer ruleTemplateId;
	private Long memberId;
	private String activityType;
	private String activityPerformContent;
	private Date createDate;

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ACTIVITY_ID")
	public Long getActivityId() {
		return this.activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	
	@Column(name = "FLOW_CODE")
	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	@Column(name = "RULE_GROUP_ID")
	public Integer getRuleGroupId() {
		return this.ruleGroupId;
	}

	public void setRuleGroupId(Integer ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

	@Column(name = "RULE_TEMPLATE_ID")
	public Integer getRuleTemplateId() {
		return this.ruleTemplateId;
	}

	public void setRuleTemplateId(Integer ruleTemplateId) {
		this.ruleTemplateId = ruleTemplateId;
	}

	@Column(name = "MEMBER_ID")
	public Long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "ACTIVITY_TYPE", length = 50)
	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	@Column(name = "ACTIVITY_PERFORM_CONTENT", length = 500)
	public String getActivityPerformContent() {
		return this.activityPerformContent;
	}

	public void setActivityPerformContent(String activityPerformContent) {
		this.activityPerformContent = activityPerformContent;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
}