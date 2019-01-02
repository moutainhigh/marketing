/**
 * 
 */
package com.oristartech.marketing.core.exception;

/**
 * 数据访问异常封装类，封装数据访问过程中出现的故障性异常
 *	
 * @see IBaseRepository
 * @author 梁志彬 新增日期：2008-4-1
 * @since CE Common 1.0
 */
public class DataAccessRuntimeException extends RuleRuntimeBaseException {

	private static final long serialVersionUID = 1L;

	public DataAccessRuntimeException() {
		super();
	}

	public DataAccessRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessRuntimeException(String message) {
		super(message);
	}

	public DataAccessRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
