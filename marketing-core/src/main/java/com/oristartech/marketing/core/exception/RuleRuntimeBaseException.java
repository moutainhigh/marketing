package com.oristartech.marketing.core.exception;

/**
 * 规则组件异常基类
 * @author chenjunfei
 *
 */
public class RuleRuntimeBaseException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3759579235718560547L;

	public RuleRuntimeBaseException() {
		
	}
    public RuleRuntimeBaseException(Throwable cause) {
		super(cause);
	}
	public RuleRuntimeBaseException(String msg ) {
		super(msg);
	}
	
	public RuleRuntimeBaseException(String msg, Throwable cause) {
		super(msg, cause);
	}	
}
