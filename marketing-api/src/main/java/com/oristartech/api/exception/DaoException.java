/*
 *  文件创建时间： 2013-6-5
 *  文件创建者: wuzhixiong
 *  所属工程: settlement
 *  CopyRights Received Dept. BIOSTIME
 *
 *  备注: 
 */
package com.oristartech.api.exception;


/**
 * 
 * 类功能描述：持久层异常
 *
 * <p> 版权所有：BIOSTIME.com
 * <p> 未经本公司许可，不得以任何方式复制或使用本程序任何部分 <p>
 * 
 * @author <a href="mailto:wuzhixiong@biostime.com">wuzhixiong</a>
 * @version 1.0
 * @since 2013-6-5 
 *
 */
public class DaoException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2275517316879838933L;

	public DaoException(){
		super();
	}
	
	public DaoException(String msg){
		super(msg);
	}
	private Integer code;

    private String message;
    
	public DaoException(BizExceptionEnum serviceExceptionEnum) {
		code = serviceExceptionEnum.getCode();
        message = serviceExceptionEnum.getMessage();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
