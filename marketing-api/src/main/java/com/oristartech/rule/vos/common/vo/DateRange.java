package com.oristartech.rule.vos.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期范围类
 * @author chenjunfei
 *
 */
public class DateRange implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -545315051892641260L;
	private Date start;
	private Date end;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
