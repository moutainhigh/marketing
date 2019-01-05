package com.oristartech.coupon.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 票券销售申请单延期记录
 */
@Entity
@Table(name = "COUPON_ACTIVE_APPLY_VALIDITY")
public class CouponActiveApplyValidity {

	private Long id;
	
	private Long applyId;
	
	private Date createDate;
	
	private Date validDateStart;
	
	private Date validDateEnd;
	
	private Long userId;
	
	private Integer flag;
	
	private String applyCode;
	
	public CouponActiveApplyValidity() {
	}

	public CouponActiveApplyValidity(Long id, Long applyId, Date createDate,
			Date validDateStart, Date validDateEnd, Long userId) {
		this.id = id;
		this.applyId = applyId;
		this.createDate = createDate;
		this.validDateStart = validDateStart;
		this.validDateEnd = validDateEnd;
		this.userId = userId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="APPLY_ID")
	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	@Column(name="CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="VALID_DATE_START")
	public Date getValidDateStart() {
		return validDateStart;
	}

	public void setValidDateStart(Date validDateStart) {
		this.validDateStart = validDateStart;
	}

	@Column(name="VALID_DATE_END")
	public Date getValidDateEnd() {
		return validDateEnd;
	}

	public void setValidDateEnd(Date validDateEnd) {
		this.validDateEnd = validDateEnd;
	}

	@Column(name="USER_ID")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="FLAG")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name="APPLY_CODE")
	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	
	
	
}
