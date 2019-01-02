package com.oristartech.rule.core.core.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.oristartech.rule.vos.core.enums.RuleExecuteMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;

/**
 * 规则组
 * @author chenjunfei
 * @version 1.0
 * @updated 26-十一月-2013 15:00:33
 */
@Entity
@Table(name="RULE_CORE_RULE_GROUP")
public class RuleGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * 组名称(通常是活动名称)
	 */
	@Column(name = "NAME", length=50)
	private String name;
	
	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;
	
	/**
	 * 组中共同的规则块
	 */
	@OneToOne(mappedBy="ruleGroup",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private RuleSection ruleSection;

	/**
	 * 优先级
	 */
	@Column(name = "PRIORITY")
	private Integer priority;
	/**
	 * 关联的业务编码. 例如活动编号
	 */
	@Column(name = "RELATE_BUSINESS_CODE", length=50)
	private String relateBusinessCode;
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;
	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE")
	private Date modifyDate;
	
	/**
	 * 执行模式
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "EXECUTE_MODE")
	private RuleExecuteMode executeMode;
	/**
	 * 包含的规则
	 */
	@OneToMany(mappedBy="ruleGroup",fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH  })
	private List<Rule> rules;
	/**
	 * 组中规则状态
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS")
	private RuleStatus status;
	
	/**
	 * 有效开始时间, 若条件里的有效时间是分段的, 则要用最开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_DATE_START")
	private Date validDateStart;
	
	/**
	 * 结束时间, 若条件里的有效时间是分段的, 则要用最后时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_DATE_END")
	private Date validDateEnd;

	/**
	 * 关联的模板id, 若不是通过模板建立, 可空
	 */
	@Column(name="TEMPLATE_ID")
	private Integer templateId;
	
	/**
	 * 规则类型, RuleType 中的名称
	 */
	@Column(length = 50, name="RULE_TYPE")
	private String ruleType;
	
	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RuleSection getRuleSection() {
    	return ruleSection;
    }

	public void setRuleSection(RuleSection ruleSection) {
    	this.ruleSection = ruleSection;
    }

	public Integer getPriority() {
    	return priority;
    }

	public void setPriority(Integer priority) {
    	this.priority = priority;
    }

	public String getRelateBusinessCode() {
		return relateBusinessCode;
	}

	public void setRelateBusinessCode(String relateBusinessCode) {
		this.relateBusinessCode = relateBusinessCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public RuleExecuteMode getExecuteMode() {
    	return executeMode;
    }

	public void setExecuteMode(RuleExecuteMode executeMode) {
    	this.executeMode = executeMode;
    }

	public List<Rule> getRules() {
    	return rules;
    }

	public void setRules(List<Rule> rules) {
    	this.rules = rules;
    }

	public RuleStatus getStatus() {
    	return status;
    }

	public void setStatus(RuleStatus status) {
    	this.status = status;
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

	public Integer getTemplateId() {
    	return templateId;
    }

	public void setTemplateId(Integer templateId) {
    	this.templateId = templateId;
    }

	public String getRuleType() {
    	return ruleType;
    }

	public void setRuleType(String ruleType) {
    	this.ruleType = ruleType;
    }
}