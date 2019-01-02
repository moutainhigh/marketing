package com.oristartech.marketing.model;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.oristartech.marketing.enums.ActivityState;
import com.oristartech.marketing.enums.ApprovalResult;


@Entity
@Table(name="MEMBER_MARKETING_ACTIVITY")
public class MemberMarketingActivity {   

	@Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="ID", unique=true, nullable=false)
	private Long id;
    
    /**
     * 活动模块编号
     */
    @Column(name="ACTIVITY_TYPE", nullable=false, length=5)
	private String activityType;
    
    /**
     * 活动编号
     */
    @Column(name="ACTIVITY_CODE")
	private String activityCode;
    
    /**
     * 活动名称
     */
    @Column(name="ACTIVITY_NAME", nullable=false)
	private String activityName;
    
    /**
     * 活动内容描述
     */
    @Column(name="ACTIVITY_DESC", length=300)
	private String activityDesc;
	
    /**
     * 活动执行时间
     */
    @Column(name="ACTIVITY_DATE", length=19)
	private Date activityDate;
    
    /**
     * 活动执行影院编号
     */
    @Column(name="BUSINESS_CODE")
	private String businessCode;
    
    /**
     * 活动执行影院名称
     */
    @Column(name="BUSINESS_NAME")
	private String businessName;
    
    /**
     * 活动状态
     */
    @Column(name="ACTIVITY_STATE")
	private ActivityState activityState;
	
    /**
     * 创建人
     */
    @Column(name="CREATER_ID")
	private Long creater;
	
	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.DATE)
    @Column(name="CREATE_TIME", length=10)
	private Date createTime;
	
	/**
	 * 创建单位
	 */
	@Column(name="CREATE_UNIT")
	private Long createUnit;
	
	/**
	 * 修订的前营销活动ID
	 */
	@Column(name="REVISON_ID")
	private Long revisonId;
	
	/**
	 * 审批人
	 */
	@Column(name="APPROVALMAN_ID")
	private Long approvalman;
	
	/**
	 * 审批任务编号
	 */
	@Column(name="APPROVAL_TASK_CODE")
	private String approvalTaskCode;
	
	/**
	 * 审批结果1-未提交，2-未审批，3-审批通过，4-审批不通过
	 */
	@Column(name="APPROVAL_RESULT")
	private ApprovalResult approvalResult;
	
	/**
	 * 审批完成时间
	 */
	@Column(name="APPROVAL_COMPLETE_TIME", length=19)
	private Date approvalCompleteTime;
	
	/**
	 * 作废标志
	 */
	@Column(name="IS_INVALID", nullable=false)
	private boolean isInvalid;
	
	@Column(name="RULE_GROUP_ID")
	private Integer ruleGroupId;
	
	@Column(name="RULE_TEMPLATE_ID")
	private Integer ruleTemplateId;
	
	/**
	 * 标签ID
	 */
	@Column(name="CARD_LABEL_ID")
	private Long cardLabelId;
	
	/**
	 * 执行类型，1-马上执行，2-定时执行，3-每天执行一次
	 */
	@Column(name="EXECUTE_TYPE")
	private int executeType;
	
	/**
	 * 每天执行开始时间
	 */
	@Column(name="ACTIVITY_START_DATE", length=19)
	private Date activityStartDate;
	
	/**
	 * 每天执行结束时间
	 */
	@Column(name="ACTIVITY_END_DATE", length=19)
	private Date activityEndDate;

	/**
	 * 租户ID
	 */
	@Column(name="TENANT_ID", nullable=false)
	private Long tenantId;

	
    public Long getTenantId() {
		return tenantId;
	}


	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
   

    public String getActivityType() {
        return this.activityType;
    }
    
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    
    

    public String getActivityCode() {
        return this.activityCode;
    }
    
    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }
    
    

    public String getActivityName() {
        return this.activityName;
    }
    
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    
    

    public String getActivityDesc() {
        return this.activityDesc;
    }
    
    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }
    
   
    public Date getActivityDate() {
        return this.activityDate;
    }
    
    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }
    
    
    public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	
	
    public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	

    public ActivityState getActivityState() {
        return this.activityState;
    }
    
    

	public void setActivityState(ActivityState activityState) {
        this.activityState = activityState;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    

    public Long getCreateUnit() {
        return this.createUnit;
    }
    
    public void setCreateUnit(Long createUnit) {
        this.createUnit = createUnit;
    }
    
    

    public Long getRevisonId() {
        return this.revisonId;
    }
    
    public void setRevisonId(Long revisonId) {
        this.revisonId = revisonId;
    }

    public String getApprovalTaskCode() {
        return this.approvalTaskCode;
    }
    
	public void setApprovalTaskCode(String approvalTaskCode) {
        this.approvalTaskCode = approvalTaskCode;
    }

    public ApprovalResult getApprovalResult() {
        return this.approvalResult;
    }
    
    public void setApprovalResult(ApprovalResult approvalResult) {
        this.approvalResult = approvalResult;
    }
    
    

    public Date getApprovalCompleteTime() {
        return this.approvalCompleteTime;
    }
    
    public void setApprovalCompleteTime(Date approvalCompleteTime) {
        this.approvalCompleteTime = approvalCompleteTime;
    }
    
    
    public boolean getIsInvalid() {
        return this.isInvalid;
    }
    
    public void setIsInvalid(boolean isInvalid) {
        this.isInvalid = isInvalid;
    }
    
    

    public Integer getRuleGroupId() {
        return this.ruleGroupId;
    }
    
    public void setRuleGroupId(Integer ruleGroupId) {
        this.ruleGroupId = ruleGroupId;
    }
    
    

    public Integer getRuleTemplateId() {
        return this.ruleTemplateId;
    }
    
    public void setRuleTemplateId(Integer ruleTemplateId) {
        this.ruleTemplateId = ruleTemplateId;
    }
    
    

    public Long getCardLabelId() {
        return this.cardLabelId;
    }
    
    public void setCardLabelId(Long cardLabelId) {
        this.cardLabelId = cardLabelId;
    }
    
    
	public int getExecuteType() {
		return executeType;
	}

	public void setExecuteType(int executeType) {
		this.executeType = executeType;
	}

	
	public Date getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	
	public Date getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(Date activityEndDate) {
		this.activityEndDate = activityEndDate;
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


	public void setInvalid(boolean isInvalid) {
		this.isInvalid = isInvalid;
	}
   

}