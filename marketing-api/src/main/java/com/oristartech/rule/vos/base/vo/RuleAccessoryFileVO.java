package com.oristartech.rule.vos.base.vo;

import java.io.Serializable;


/**
 * 规则定义中需要保存的文件
 * @author user
 *
 */
public class RuleAccessoryFileVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5892543207481799645L;

	private Long id;
	
	private String name;

	private String mime ;
	
	private String content;

	public Long getId() {
    	return id;
    }

	public void setId(Long id) {
    	this.id = id;
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

	public String getContent() {
    	return content;
    }

	public void setContent(String content) {
    	this.content = content;
    }
}
