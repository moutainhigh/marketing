package com.oristartech.marketing.components.exception;

public class RuleServerException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5060567652960960821L;

	public RuleServerException() {
		
	}
    public RuleServerException(Throwable cause) {
		super(cause);
	}
	public RuleServerException(String msg ) {
		super(msg);
	}
	
	public RuleServerException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
