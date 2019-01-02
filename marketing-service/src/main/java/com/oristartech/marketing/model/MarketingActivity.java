package com.oristartech.marketing.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.oristartech.marketing.enums.ActivityState;
import com.oristartech.marketing.enums.ApprovalResult;

/**
 * 营销活动DO
 */
@Entity
@Table(name="MARKETING_ACTIVITY")
public class MarketingActivity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	/**
	 * 活动模块编号
	 * 01:商品特价促销,02：加价换购,03：买赠/买购促销,04：商品组合促销
	 * 05:会员充值促销,06：整单打折促销,07：会员消费返积分促销,08：会员消费返券促销
	 * 09：会员消费返现促销,10:套餐
	 */
	@Column(name = "ACTIVITY_TYPE")
	private String activityType;
	
	/**
	 * 活动编号
	 * MAT+yyyymmdd+4位序列号
	 */
	@Column(name = "ACTIVITY_CODE")
	private String activityCode;
	
	/**
	 * 营销活动名称
	 */
	@Column(name = "ACTIVITY_NAME")
	private String activityName;
	
	/**
	 * 营销活动内容描述
	 */
	@Column(name = "ACTIVITY_DESC")
	private String activityDesc;
	
	/**
	 * 有效期 - 开始
	 */
	@Column(name = "VALID_DATE_START")
	@Temporal(TemporalType.DATE)
	private Date validDateStart;
	
	/**
	 * 有效期 - 结束
	 */
	@Column(name = "VALID_DATE_END")
	@Temporal(TemporalType.DATE)
	private Date validDateEnd;
	
	/**
	 * 活动状态
	 * 1:新建(审批不通过),2：锁定,3：(审批通过)执行中,4：暂停,5：作废
	 */
	@Column(name = "ACTIVITY_STATE")
	private ActivityState activityState;
	
	/**
	 * 创建人
	 */
	@Column(name="CREATER_ID",nullable=false)
	private Long creater;
	
	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.DATE)
	private Date createTime;
	
	/**
	 * 修订的前营销活动ID
	 */
	@Column(name = "REVISON_ID")
	private Long revisonId; 
	
	/**
	 * 审批人
	 */
	@Column(name="APPROVALMAN_ID")
	private Long approvalman;
	
	/**
	 * 审批任务编号
	 */
	@Column(name = "APPROVAL_TASK_CODE")
	private String approvalTaskCode;
	
	/**
	 * 审批结果
	 * 1-未提交，2-未审批，3-审批通过，4-审批不通过
	 */
	@Column(name = "APPROVAL_RESULT")
	private ApprovalResult approvalResult;
	
	/**
	 * 审批完成时间
	 */
	@Column(name = "APPROVAL_COMPLETE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvalCompleteTime;
	
	/**
	 * 执行方法
	 */
	@Column(name = "EXECUTE_MODE")
	private String executeMode;
	
	/**
	 * 作废标志   1:作废  0：未作废
	 */
	@Column(name="IS_INVALID", nullable=false)
	private boolean isInvalid;
	
	@Column(name="RULE_GROUP_ID", nullable=false)
	private Integer ruleGroupId;
	
	@Column(name="RULE_TEMPLATE_ID", nullable=false)
	private Integer ruleTemplateId;
	
	/**
	 * 租户ID
	 */
	@Column(name="TENANT_ID", nullable=false)
	private Long tenantId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Date getValidDateStart() {
		return validDateStart;
	}

	public void setValidDateStart(Date validDateStart) {
		this.validDateStart = validDateStart;
	}

	public Date getValidDateEnd() {
		return validDateEnd;
	}

	public void setValidDateEnd(Date validDateEnd) {
		this.validDateEnd = validDateEnd;
	}

	public ActivityState getActivityState() {
		return activityState;
	}

	public void setActivityState(ActivityState activityState) {
		this.activityState = activityState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getRevisonId() {
		return revisonId;
	}

	public void setRevisonId(Long revisonId) {
		this.revisonId = revisonId;
	}

	public String getApprovalTaskCode() {
		return approvalTaskCode;
	}

	public void setApprovalTaskCode(String approvalTaskCode) {
		this.approvalTaskCode = approvalTaskCode;
	}

	public ApprovalResult getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(ApprovalResult approvalResult) {
		this.approvalResult = approvalResult;
	}

	public Date getApprovalCompleteTime() {
		return approvalCompleteTime;
	}

	public void setApprovalCompleteTime(Date approvalCompleteTime) {
		this.approvalCompleteTime = approvalCompleteTime;
	}

	public boolean isInvalid() {
		return isInvalid;
	}

	public void setInvalid(boolean isInvalid) {
		this.isInvalid = isInvalid;
	}

	public Integer getRuleGroupId() {
    	return ruleGroupId;
    }

	public void setRuleGroupId(Integer ruleGroupId) {
    	this.ruleGroupId = ruleGroupId;
    }

	public Integer getRuleTemplateId() {
    	return ruleTemplateId;
    }

	public void setRuleTemplateId(Integer ruleTemplateId) {
    	this.ruleTemplateId = ruleTemplateId;
    }

	public String getExecuteMode() {
		return executeMode;
	}

	public void setExecuteMode(String executeMode) {
		this.executeMode = executeMode;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Long getApprovalman() {
		return approvalman;
	}

	public void setApprovalman(Long approvalman) {
		this.approvalman = approvalman;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	
}