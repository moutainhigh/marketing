package com.oristartech.marketing.core.http;

import com.oristartech.rule.common.util.BlankUtil;

public class HttpResult {
	private String httpStatus;
	private String content;
	private static String HTTP_OK_PREFIX = "2";

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isOk() {
		if (!BlankUtil.isBlank(httpStatus) && httpStatus.startsWith(HTTP_OK_PREFIX)) {
			return true;
		}
		return false;
	}
}
