package com.oristartech.rule.core.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 * 属性分组
 * @author chenjunfei
 * @version 1.0
 * @updated 25-十一月-2013 17:22:27
 */
@Entity
@Table(name="RULE_TPL_FIELD_GROUP")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class FieldGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 中文名称
	 */
	@Column(length = 50, nullable = false, name="CN_NAME")
	private String cnName;
	
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	/**
	 * 分组元素类别
	 */
	@ManyToOne
	@ForeignKey(name = "FK_FIELD_GROUP_ELEMENT_TYPE")
	@JoinColumn(name="GROUP_EMELEMT_TYPE_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private GroupElementType groupElementType;

	/**
	 * 管理编号(不同的分组类别下,编号可相同)
	 */
	@Column(name="MANAGER_NUM", length=30)
	private String managerNum;
	
	/**
	 * 是否启用
	 * 
	 */
	@Column(name="IS_ENABLE")
	private Boolean isEnable;
	
	/**
	 * 备注
	 */
	@Column(name="REMARK")
	private String remark;
	
	/**
	 * 在页面添加或测试页面,是否允许显示添加多个按钮.
	 */
	@Column(name="IS_MULTI_IN_TEST")
	private Boolean isMultiInTest;
	
	/**
	 * 是否在测试页面出现. 若是true,并且要测试的规则条件和方法存在就可以显示输入.
	 */
	@Column(name="IS_DISPLAY_IN_TEST")
	private Boolean isDisplayInTest;
	
	public Boolean getIsMultiInTest() {
    	return isMultiInTest;
    }

	public void setIsMultiInTest(Boolean isMultiInTest) {
    	this.isMultiInTest = isMultiInTest;
    }

	public Boolean getIsDisplayInTest() {
    	return isDisplayInTest;
    }

	public void setIsDisplayInTest(Boolean isDisplayInTest) {
    	this.isDisplayInTest = isDisplayInTest;
    }


	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }

	public Boolean getIsEnable() {
    	return isEnable;
    }

	public void setIsEnable(Boolean isEnable) {
    	this.isEnable = isEnable;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public GroupElementType getGroupElementType() {
    	return groupElementType;
    }

	public void setGroupElementType(GroupElementType groupElementType) {
    	this.groupElementType = groupElementType;
    }

	public String getManagerNum() {
    	return managerNum;
    }

	public void setManagerNum(String managerNum) {
    	this.managerNum = managerNum;
    }
}