package com.oristartech.marketing.core.exception;

public class ServiceRuntimeException extends RuleRuntimeBaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6341790302021660744L;

	public ServiceRuntimeException() {

	}

	public ServiceRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public ServiceRuntimeException(String msg) {
		super(msg);
	}

	public ServiceRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
