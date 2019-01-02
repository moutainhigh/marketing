package com.oristartech.rule.core.base.model;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 * 规则方法参数
 * @author chenjunfei
 * @version 1.0
 * @created 28-一月-2014 14:48:26
 */
@Entity
@DiscriminatorValue("ruleFnParam") 
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class RuleActionFunctionParameter extends ActionFunctionParameter {

	/**
	 * 参数值输入显示类型, input,select, radio, checkbox等
	 */
	@Column(length = 30, name="FORM_CTRL")
	private String formCtrl;
	
	/**
	 * 当类型是modelfield时,对应的业务属性
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@ForeignKey(name = "FK_FUNCTION_PARAMETER_MODEL_FIELD")
	@JoinColumn(name="MODEL_FIELD_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ModelField modelField;
	
	/**
	 * 是否显示, 因为有些是隐含参数
	 */
	@Column(name="IS_SHOW")
	private Boolean isShow;
	
	/**
	 * 是否可编辑
	 */
	@Column(name="IS_EDITABLE")
	private Boolean isEditable;
	
	/**
	 * 是否配置型参数， 这些参数不会渲染在界面
	 */
	@Column(name="IS_CONFIG")
	private Boolean isConfig;
	
	
	/**
	 * 结构同validator组件中的, rules方法中的add模式中的参数. 即为json对象.
	 */
	@Column(name="VALIDATE_RULE")
	private String validateRule;

	/**
	 * 分组号码. 同一组的参数, 而且isMulti为true, 同组号码的参数同时动态添加
	 */
	@Column(name="GROUP_NUM")
	private Integer groupNum;
	
	/**
	 * 是否可以多个组, 即是否可动态添加多个组输入控件
	 */
	@Column(name="IS_MULTI_GROUP")
	private Boolean isMultiGroup;
	
	/**
	 * 参数可以输入的数量，负数表示可以多组值, 数量表示每组包含多少个值.
	 * -1 表示可以输入多个值
	 * -2 表示可以输入多组值, 而且每组2个值
	 * 本规则同Operator中的opNum
	 */
	@Column(name="VALUE_NUM")
	private Integer valueNum;
	
	/**
	 * 值Format, 有些参数需要指定显示格式,例如日期
	 */
	@Column(name="VALUE_FORMAT", length=30)
	private String valueFormat;
	
	/**
	 * 参数占用行数,显示时用到, 若空则为1
	 */
	@Column(name="ROW_SPAN")
	private Integer rowspan;
	
	/**
	 * 参数占用列数,显示时用到, 若空则为1
	 */
	@Column(name="COL_SPAN")
	private Integer colspan;
	
	/**
	 * 是否文件参数
	 */
	@Column(name = "IS_FILE")
	private Boolean isFile;
	
	/**
	 * 有些参数是大参数,例如邮件,短信内容, 文件,在生成规则文件时不应该加载这些参数. 
	 */
	@Column(name = "IS_LAZY")
	private Boolean isLazy;
	
	/**
	 * 是否是条件.主要为了规则测算时,需要添加到条件中.通常是关联modelfield时才会true.
	 */
	@Column(name = "IS_CONDITION")
	private Boolean isCondition;
	
	public Boolean getIsCondition() {
    	return isCondition;
    }


	public void setIsCondition(Boolean isCondition) {
    	this.isCondition = isCondition;
    }


	public String getFormCtrl() {
		return formCtrl;
	}


	public void setFormCtrl(String formCtrl) {
		this.formCtrl = formCtrl;
	}


	public ModelField getModelField() {
    	return modelField;
    }


	public void setModelField(ModelField modelField) {
    	this.modelField = modelField;
    }

	public Boolean getIsShow() {
    	return isShow;
    }

	public void setIsShow(Boolean isShow) {
    	this.isShow = isShow;
    }

	public String getValidateRule() {
    	return validateRule;
    }

	public void setValidateRule(String validateRule) {
    	this.validateRule = validateRule;
    }

	public Integer getGroupNum() {
    	return groupNum;
    }

	public void setGroupNum(Integer groupNum) {
    	this.groupNum = groupNum;
    }

	public Boolean getIsMultiGroup() {
    	return isMultiGroup;
    }

	public void setIsMultiGroup(Boolean isMultiGroup) {
    	this.isMultiGroup = isMultiGroup;
    }

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Boolean getIsConfig() {
		return isConfig;
	}

	public void setIsConfig(Boolean isConfig) {
		this.isConfig = isConfig;
	}

	public Integer getValueNum() {
    	return valueNum;
    }

	public void setValueNum(Integer valueNum) {
    	this.valueNum = valueNum;
    }


	public String getValueFormat() {
    	return valueFormat;
    }

	public void setValueFormat(String valueFormat) {
    	this.valueFormat = valueFormat;
    }


	public Integer getRowspan() {
    	return rowspan;
    }


	public void setRowspan(Integer rowspan) {
    	this.rowspan = rowspan;
    }


	public Integer getColspan() {
    	return colspan;
    }

	public void setColspan(Integer colspan) {
    	this.colspan = colspan;
    }


	public Boolean getIsFile() {
    	return isFile;
    }


	public void setIsFile(Boolean isFile) {
    	this.isFile = isFile;
    }


	public Boolean getIsLazy() {
    	return isLazy;
    }


	public void setIsLazy(Boolean isLazy) {
    	this.isLazy = isLazy;
    }
	
}