package com.oristartech.rule.vos.ws.vo;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 外部数据查询结果
 * 
 * @author chenjunfei
 * 
 */
public class CMSWSResultVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -594694715313880186L;
	public final static int OK = 0;
	public final static int FAIL = -1;
	public final static String TYPE = "type";
	public final static String OBJECT_TYPE = "object";
	public final static String ARRAY_TYPE = "array";
	public final static String ARRAY_RESULT_KEY = "result";
	
	private int status;
	private String msg;
	private Map<String,Object> data;

	public CMSWSResultVO() {

	}

	public boolean isOk() {
		return OK == status;
	}
	
	public void setFail() {
		this.status = FAIL;
	}

	@JsonIgnore
	public void setOk() {
		this.status = OK;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
    	return msg;
    }

	public void setMsg(String msg) {
    	this.msg = msg;
    }

	
	public Map<String, Object> getData() {
    	return data;
    }

	public void setData(Map<String, Object> data) {
    	this.data = data;
    }

	public RuleWSResultVO convert2WSResult() {
		RuleWSResultVO vo = new RuleWSResultVO();
		if(this.isOk()) {
			vo.setOk();
		} else {
			vo.setFail();
		}
		vo.setMessage(this.getMsg());
		if(this.data != null) {
			if(OBJECT_TYPE.equals(this.data.get(TYPE))) {
				vo.setData(this.data);
			} else if(ARRAY_TYPE.equals(this.data.get(TYPE))) {
				vo.setData(this.data.get(ARRAY_RESULT_KEY));
			} else {
				vo.setData(this.data);
			}
		}
		return vo;
	}
}
