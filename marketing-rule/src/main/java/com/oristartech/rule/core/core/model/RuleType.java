package com.oristartech.rule.core.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.oristartech.rule.vos.core.enums.RuleRunMode;

/**
 * 规则类型，表示内置的类型，不能让用户添加。主要为了处理不同的规则类型，有不同的优先级策略，及结果过滤策略。
 * 
 * @author chenjunfei
 * @version 1.0
 * @created 18-十月-2013 15:05:56
 */
@Entity
@Table(name="RULE_CORE_RULE_TYPE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-core-model-region")
public class RuleType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 英文名称，名字唯一
	 */
	@Column(length = 50, nullable = false, name="NAME", unique = true)
	private String name;
	/**
	 * 中文名称
	 */
	@Column(length = 50, name="CN_NAME")
	private String cnName;

	@Column(name="IS_DEFAULT")
	private Boolean isDefault;
	
	/**
	 * 是否匹配多次（这里是drools框架会组合事实造成的多次匹配。若是，则可以多次运行规则，否则若已经存在一个结果，直接返回）
	 */
	@Column(name="IS_MATCH_MULTI")
	private Boolean isMatchMulti;
	
	/**
	 * 是否在一次匹配中，执行方法需要执行多次。 即不需要经过多次匹配，是否一次匹配就要计算出执行方法要运行多次
	 */
	@Column(name="IS_ACTION_RUN_MULTI")
	private Boolean isActionRunMulti;
	
	/**
	 * 规则运行方式. 例如用drools,sql,等等
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name="RUN_MODE")
	private RuleRunMode runMode;
	
	/**
	 * 不同规则类型的执行executor service， 该executor必须实现IRuleExecutorService, 可空
	 */
	@Column(name="EXECUTOR", length=50)
	private String executor;
	
	/**
	 * 本类型的规则,运行后需要执行的IResultFilter实现类, 保存格式是IResultFilter spring bean名称json数组, 
	 * json数组, 并按过滤器执行的顺序依次列出. 例如["fileter1","filter2"];
	 */
	@Column(name = "RESULT_FILTERS", length=255)
	private String resultFilters;
	
	/**
	 * 本类型的规则,匹配前需要执行的IFactFilter实现类, 保存格式是IFactFilter spring bean名称, 
	 * json数组, 并按过滤器执行的顺序依次列出. 例如["fileter1","filter2"];
	 */
	@Column(name = "FACT_FILTERS", length=255)
	private String factFilters;
	
	/**
	 * @return the factFilters
	 */
	public String getFactFilters() {
		return factFilters;
	}

	/**
	 * @param factFilters the factFilters to set
	 */
	public void setFactFilters(String factFilters) {
		this.factFilters = factFilters;
	}

	/**
	 * @return the executor
	 */
	public String getExecutor() {
		return executor;
	}

	/**
	 * @param executor the executor to set
	 */
	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public RuleRunMode getRunMode() {
    	return runMode;
    }

	public void setRunMode(RuleRunMode runMode) {
    	this.runMode = runMode;
    }

	public Boolean getIsActionRunMulti() {
    	return isActionRunMulti;
    }

	public void setIsActionRunMulti(Boolean isActionRunMulti) {
    	this.isActionRunMulti = isActionRunMulti;
    }

	public Boolean getIsMatchMulti() {
    	return isMatchMulti;
    }

	public void setIsMatchMulti(Boolean isMatchMulti) {
    	this.isMatchMulti = isMatchMulti;
    }

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

	public Boolean getIsDefault() {
    	return isDefault;
    }

	public void setIsDefault(Boolean isDefault) {
    	this.isDefault = isDefault;
    }

	public String getResultFilters() {
    	return resultFilters;
    }

	public void setResultFilters(String resultFilters) {
    	this.resultFilters = resultFilters;
    }
}