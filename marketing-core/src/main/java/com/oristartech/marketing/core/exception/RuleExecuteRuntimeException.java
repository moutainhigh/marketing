package com.oristartech.marketing.core.exception;

public class RuleExecuteRuntimeException extends RuleRuntimeBaseException {
	public RuleExecuteRuntimeException() {

	}
	public RuleExecuteRuntimeException(String msg) {
		super(msg);
	}
	public RuleExecuteRuntimeException(Throwable cause) {
		super(cause);
	}

	public RuleExecuteRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
