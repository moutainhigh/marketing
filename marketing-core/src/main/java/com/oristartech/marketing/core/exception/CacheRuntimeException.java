package com.oristartech.marketing.core.exception;

public class CacheRuntimeException extends RuleRuntimeBaseException {
	public CacheRuntimeException() {

	}

	public CacheRuntimeException(String msg) {
		super(msg);
	}

	public CacheRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
