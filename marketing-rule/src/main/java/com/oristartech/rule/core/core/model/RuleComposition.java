package com.oristartech.rule.core.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 规则最终内容，例如drools，则合成文件内容
 * @author chenjunfei
 * @version 1.0
 * @updated 26-十月-2013 14:15:39
 */
@Entity
@Table(name="RULE_CORE_RULE_COMPOSITION")
public class RuleComposition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 规则合成内容
	 */
	@Lob
	@Column(length=255, name="CONTENT")
	private String content;

	/**
	 * 如，若是drools，则是drl
	 */
	@Column(length = 30, name="FORMAT")
	private String format;

	/**
	 * 所属规则
	 */
	@OneToOne
	@JoinColumn(name="RULE_ID")
	private Rule rule;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Rule getRule() {
    	return rule;
    }

	public void setRule(Rule rule) {
    	this.rule = rule;
    }

}