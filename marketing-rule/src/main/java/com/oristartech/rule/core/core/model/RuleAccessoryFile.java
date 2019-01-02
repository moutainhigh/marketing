package com.oristartech.rule.core.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 规则定义中需要保存的文件
 * @author user
 *
 */
@Entity
@Table(name="RULE_CORE_RULE_ACCESSORY_FILE")
public class RuleAccessoryFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	/**
	 * 文件名称
	 */
	@Column(name="NAME", length=50)
	private String name;
	
	/**
	 * 文件mime类型
	 */
	@Column(name="MIME", length=50)
	private String mime ;
	
	/**
	 * 内容
	 */
	@Column(name="CONTENT", length=65535)
	private byte[] content;

	public Long getId() {
    	return id;
    }

	public void setId(Long id) {
    	this.id = id;
    }

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getMime() {
    	return mime;
    }

	public void setMime(String mime) {
    	this.mime = mime;
    }
	
	
}
