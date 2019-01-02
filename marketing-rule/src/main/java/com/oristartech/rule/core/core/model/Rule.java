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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.oristartech.rule.vos.core.enums.RuleExecuteMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;

/**
 * 规则
 * @author chenjunfei
 * @version 1.0
 * @updated 14-十二月-2013 15:17:37
 */
@Entity
@Table(name="RULE_CORE_RULE")
public class Rule {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	/**
	 * 名称
	 */
	@Column(length = 50, name="NAME")
	private String name;
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	/**
	 * 优先级,值越大,越优先.
	 */
	@Column(name = "PRIORITY")
	private Integer priority;
	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;
	/**
	 * 规则状态
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS")
	private RuleStatus status;
	/**
	 * 规则类型, RuleType 中的名称
	 */
	@Column(length = 50, nullable=false, name="RULE_TYPE")
	private String ruleType;
	
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
	@Column(name = "MODITY_DATE")
	private Date modifyDate;
	
	/**
	 * 规则组
	 */
	@ManyToOne
	@ForeignKey(name = "FK_RULE_RULE_GROUP")
	@JoinColumn(name="RULE_GROUP_ID")
	private RuleGroup ruleGroup;
	
	/**
	 * 规则块集合
	 */
	@OneToMany(mappedBy="rule",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private List<RuleSection> ruleSections;
	
	/**
	 * 版本号
	 */
	@Column(name="VERSION")
	private Integer version;

	/**
	 * 若条件里的有效时间是分段的, 则要用最开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_DATE_START")
	private Date validDateStart;
	
	/**
	 * 若条件里的有效时间是分段的, 则要用最后时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_DATE_END")
	private Date validDateEnd;
	/**
	 * 执行模式
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "EXECUTE_MODE")
	private RuleExecuteMode executeMode;
	
	/**
	 * 关联的业务编码. 例如活动编号
	 */
	@Column(name = "BUSINESS_CODE", length=50)
	private String businessCode;
	
	/**
	 * 额外业务属性. 额外的业务系统需要保存的规则属性. json字符串对象
	 */
	@Column(name="BIZ_PROPERTIES") 
	private String bizProperties;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public Integer getPriority() {
    	return priority;
    }

	public void setPriority(Integer priority) {
    	this.priority = priority;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public RuleStatus getStatus() {
		return status;
	}

	public void setStatus(RuleStatus status) {
		this.status = status;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public RuleGroup getRuleGroup() {
    	return ruleGroup;
    }

	public void setRuleGroup(RuleGroup ruleGroup) {
    	this.ruleGroup = ruleGroup;
    }

	public List<RuleSection> getRuleSections() {
    	return ruleSections;
    }

	public void setRuleSections(List<RuleSection> ruleSections) {
    	this.ruleSections = ruleSections;
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

	public Integer getVersion() {
    	return version;
    }

	public void setVersion(Integer version) {
    	this.version = version;
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

	public RuleExecuteMode getExecuteMode() {
    	return executeMode;
    }

	public void setExecuteMode(RuleExecuteMode executeMode) {
    	this.executeMode = executeMode;
    }

	public String getBusinessCode() {
    	return businessCode;
    }

	public void setBusinessCode(String businessCode) {
    	this.businessCode = businessCode;
    }

	public String getBizProperties() {
    	return bizProperties;
    }

	public void setBizProperties(String bizProperties) {
    	this.bizProperties = bizProperties;
    }
}