package com.oristartech.rule.vos.ws.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oristartech.rule.common.util.JsonUtil;

/**
 * 外部数据查询结果
 * 
 * @author chenjunfei
 * 
 */
public class RuleWSResultVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2990775438733007383L;
	public final static String FAIL = "fail";
	public final static String OK = "ok";
	public final static String OK_0 = "0";
	public final static String FAIL_1 = "1";
	
	private String code;
	private String status;
	private String message;
	private Object data;

	public RuleWSResultVO() {

	}

	public RuleWSResultVO(String status, String code, String message, Object data) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getCode() {
    	return code;
    }

	public void setCode(String code) {
    	this.code = code;
    }

	@JsonIgnore
	public boolean isOk() {
		return OK.equalsIgnoreCase(this.status) || OK_0.equals(this.status);
	}
	
	@JsonIgnore
	public void setFail() {
		this.status = FAIL;
	}

	@JsonIgnore
	public void setOk() {
		this.status = OK;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static RuleWSResultVO createFailResultVo(String msg) {
		RuleWSResultVO resultVO = new RuleWSResultVO();
		resultVO.setFail();
		resultVO.setMessage(msg);
		return resultVO;
	}
	
	public static String createFailVoStr(String msg) {
		return createFailResultVo(msg).toString();
	}
	
	public static RuleWSResultVO createOKResultVo(Object data){
		RuleWSResultVO resultVO = new RuleWSResultVO();
		resultVO.setOk();
		resultVO.setData(data);
		return resultVO;
	}
	
	/**
	 * 返回结果data的json格式
	 * @return
	 */
	@JsonIgnore
	public String getDataJson() {
		if(this.data != null) {
			if(this.data instanceof String) {
				return (String)this.data;
			} else {
				return JsonUtil.objToJson(this.data);
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		status = status == null ? "" : status;
		message = message == null ? "" : message;
	    return JsonUtil.objToJsonIgnoreNull(this);
	}
}
