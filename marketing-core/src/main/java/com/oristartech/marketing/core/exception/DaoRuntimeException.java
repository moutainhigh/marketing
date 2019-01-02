package com.oristartech.marketing.core.exception;

public class DaoRuntimeException extends RuleRuntimeBaseException {
	public DaoRuntimeException() {
		
	}
	public DaoRuntimeException(String msg ) {
		super(msg);
	}
	
	public DaoRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
