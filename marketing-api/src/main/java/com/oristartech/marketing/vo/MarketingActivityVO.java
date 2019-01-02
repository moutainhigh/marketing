package com.oristartech.marketing.vo;

import java.io.Serializable;
import java.util.Date;

import com.oristartech.marketing.enums.ApprovalResult;

/**
 * 营销活动vo
 */
public class MarketingActivityVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7445055425534129451L;

	private Long id;
	
	/**
	 * 活动模块编号
	 * 01:商品特价促销,02：加价换购,03：买赠/买购促销,04：商品组合促销
	 * 05:会员充值促销,06：整单打折促销,07：会员消费返积分促销,08：会员消费返券促销
	 * 09：会员消费返现促销,10:套餐
	 */
	private String activityType;
	
	/**
	 * 活动模块名称
	 */
	private String activityTypeName;
	
	/**
	 * 活动编号
	 * MAT+yyyymmdd+4位序列号
	 */
	private String activityCode;
	
	/**
	 * 营销活动名称
	 */
	private String activityName;
	
	/**
	 * 营销活动内容描述
	 */
	private String activityDesc;
	
	/**
	 * 有效期 - 开始
	 */
	private String validDateStart;
	
	/**
	 * 有效期 - 结束
	 */
	private String validDateEnd;
	
	/**
	 * 活动状态
	 * 1:新建,2：锁定,3：执行中,4：暂停,5：作废
	 */
	private int activityState;
	
	/**
	 * 创建人ID
	 */
	private Long createrId;
	
	/**
	 * 创建人
	 */
	private String createrName;
	
	/**
	 * 创建人单位ID
	 */
	private Long createrAreaId;
	
	/**
	 * 创建人单位
	 */
	private String createrAreaName;
	
	/**
	 * 创建日期
	 */
	private Date createTime;
	
	/**
	 * 修订的前营销活动ID
	 */
	private Long revisonId; 
	
	/**
	 * 审批人ID
	 */
	private Long approvalmanId;
	
	/**
	 * 审批人
	 */
	private String approvalmanName;
	
	/**
	 * 审批人ID
	 */
	private Long approvalmanAreaId;
	
	/**
	 * 审批人单位
	 */
	private String approvalmanAreaName;
	
	/**
	 * 审批任务编号
	 */
	private String approvalTaskCode;
	
	/**
	 * 审批结果
	 * 
	 */
	private ApprovalResult approvalResult;
	
	/**
	 * 审批完成时间
	 */
	private Date approvalCompleteTime;
	
	/**
	 * 规则json数据
	 */
	private String ruleGroup;
	
	/**
	 * 规则组id
	 */
	private Integer ruleGroupId;
	
	/**
	 * 活动模板id
	 */
	private Integer ruleTemplateId;
	
	/**
	 * 标签列表
	 */
	private String labelList;
	
	/**
	 * 修改修订状态1-修订，2-修改
	 */
	private String flag;
	
	/**
	 * 执行方法
	 */
	private String executeMode;
	
	private boolean isInvalid;
	
	/**
	 * 租户Id
	 */
	private Long tenantId;
	
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getLabelList() {
		return labelList;
	}

	public void setLabelList(String labelList) {
		this.labelList = labelList;
	}

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

	public String getActivityTypeName() {
		return activityTypeName;
	}

	public void setActivityTypeName(String activityTypeName) {
		this.activityTypeName = activityTypeName;
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

	public String getValidDateStart() {
		return validDateStart;
	}

	public void setValidDateStart(String validDateStart) {
		this.validDateStart = validDateStart;
	}

	public String getValidDateEnd() {
		return validDateEnd;
	}

	public void setValidDateEnd(String validDateEnd) {
		this.validDateEnd = validDateEnd;
	}

	public int getActivityState() {
		return activityState;
	}

	public void setActivityState(int activityState) {
		this.activityState = activityState;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCreaterAreaName() {
		return createrAreaName;
	}

	public void setCreaterAreaName(String createrAreaName) {
		this.createrAreaName = createrAreaName;
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

	public String getApprovalmanName() {
		return approvalmanName;
	}

	public void setApprovalmanName(String approvalmanName) {
		this.approvalmanName = approvalmanName;
	}

	public String getApprovalmanAreaName() {
		return approvalmanAreaName;
	}

	public void setApprovalmanAreaName(String approvalmanAreaName) {
		this.approvalmanAreaName = approvalmanAreaName;
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

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Long getCreaterAreaId() {
		return createrAreaId;
	}

	public void setCreaterAreaId(Long createrAreaId) {
		this.createrAreaId = createrAreaId;
	}

	public Long getApprovalmanId() {
		return approvalmanId;
	}

	public void setApprovalmanId(Long approvalmanId) {
		this.approvalmanId = approvalmanId;
	}

	public Long getApprovalmanAreaId() {
		return approvalmanAreaId;
	}

	public void setApprovalmanAreaId(Long approvalmanAreaId) {
		this.approvalmanAreaId = approvalmanAreaId;
	}

	public String getRuleGroup() {
    	return ruleGroup;
    }

	public void setRuleGroup(String ruleGroup) {
    	this.ruleGroup = ruleGroup;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getExecuteMode() {
		return executeMode;
	}

	public void setExecuteMode(String executeMode) {
		this.executeMode = executeMode;
	}

	public boolean isInvalid() {
		return isInvalid;
	}

	public void setInvalid(boolean isInvalid) {
		this.isInvalid = isInvalid;
	}
}
