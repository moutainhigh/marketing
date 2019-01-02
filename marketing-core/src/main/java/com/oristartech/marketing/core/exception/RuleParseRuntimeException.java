package com.oristartech.marketing.core.exception;

public class RuleParseRuntimeException extends RuleRuntimeBaseException {
	public RuleParseRuntimeException() {
		
	}
	public RuleParseRuntimeException(String msg ) {
		super(msg);
	}
	
	public RuleParseRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
