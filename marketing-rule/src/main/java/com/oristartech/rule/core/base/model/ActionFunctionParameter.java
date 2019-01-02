package com.oristartech.rule.core.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 * 函数参数
 * @author chenjunfei
 * @version 1.0
 * @updated 05-十二月-2013 14:59:54
 */
@Entity
@Table(name="RULE_BASE_ACTION_FUNCTION_PARAMETER")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
@DiscriminatorColumn(name="INHERIT_TYPE")  
@DiscriminatorValue("fnParam") 
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class ActionFunctionParameter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	/**
	 * 英文名称，java变量名称
	 */
	@Column(length = 50, nullable = false,name="NAME")
	private String name;
	/**
	 * 中文名称
	 */
	@Column(length = 50, name="CN_NAME")
	private String cnName;
	
	/**
	 * 参数类型
	 */
	@Column(length = 100, name="TYPE")
	private String type;
	
	/**
	 * 默认值
	 */
	@Column(length = 100, name="DEFAULT_VALUE")
	private String defaultValue;
	
	/**
	 * 顺序号
	 */
	@Column(name="SEQ_NUM")
	private Integer seqNum;
	
	/**
	 * 所属函数
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@ForeignKey(name = "FK_FUNCTION_PARAMETER_ACTION_FUNCTION")
	@JoinColumn(name="ACTION_FUNCTION_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ActionFunction actionFunction;
	
	@OneToMany(mappedBy="functionParameter", fetch=FetchType.LAZY)
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
	private List<FunctionParameterDataSource> funcParamDataSource;

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

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getType() {
    	return type;
    }

	public void setType(String type) {
    	this.type = type;
    }

	public ActionFunction getActionFunction() {
    	return actionFunction;
    }

	public void setActionFunction(ActionFunction actionFunction) {
    	this.actionFunction = actionFunction;
    }

	public String getDefaultValue() {
    	return defaultValue;
    }

	public void setDefaultValue(String defaultValue) {
    	this.defaultValue = defaultValue;
    }

	public List<FunctionParameterDataSource> getFuncParamDataSource() {
    	return funcParamDataSource;
    }

	public void setFuncParamDataSource(List<FunctionParameterDataSource> funcParamDataSource) {
    	this.funcParamDataSource = funcParamDataSource;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }
}