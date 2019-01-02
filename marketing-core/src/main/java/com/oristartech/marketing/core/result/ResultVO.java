package com.oristartech.marketing.core.result;

public class ResultVO {
	public static final String FAIL = "fail";
	public static final String OK = "ok";

	private String status;
	private String code;
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public static String getFail() {
		return FAIL;
	}

	public static String getOk() {
		return OK;
	}
	
	public boolean isFail() {
		return FAIL.equals(getStatus());
	}
	
	public boolean isOK() {
		return OK.equals(getStatus());
	}
	
	public void setFail() {
		this.status = FAIL;
	}
	
	public void setOK() {
		this.status = OK;
	}
}
