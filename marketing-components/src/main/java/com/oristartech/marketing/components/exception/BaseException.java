package com.oristartech.marketing.components.exception;

public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2502010563432360597L;

	public BaseException() {
			
	}
    public BaseException(Throwable cause) {
		super(cause);
	}
	public BaseException(String msg ) {
		super(msg);
	}
	
	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
	}	
}
