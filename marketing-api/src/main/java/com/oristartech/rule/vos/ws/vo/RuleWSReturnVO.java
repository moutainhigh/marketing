package com.oristartech.rule.vos.ws.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 规则webservice返回结果结构
 * @author chenjunfei
 *
 */

public class RuleWSReturnVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6772162086415270741L;
	public static final String FAIL = "1";
	public static final String OK = "0";

	private String status;
	private String code;
	private String message;
	private Object data;
	
	public Object getData() {
    	return data;
    }

	public void setData(Object data) {
    	this.data = data;
    }
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonIgnore
	public boolean isFail() {
		return FAIL.equalsIgnoreCase(status);
	}
	
	@JsonIgnore
	public boolean isOK() {
		return OK.equalsIgnoreCase(status);
	}
	
	public void setFail() {
		this.status = FAIL;
	}
	
	public void setOK() {
		this.status = OK;
	}

	public String getStatus() {
		return status;
	}
}
