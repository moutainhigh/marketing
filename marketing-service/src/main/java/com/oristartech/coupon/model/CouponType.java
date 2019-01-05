package com.oristartech.coupon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 票劵/折扣卡分类设置
 * @author zhan
 * created on :2013-11-11
 */
@Entity
@Table(name="COUPON_TYPE")
public class CouponType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	/**
	 * 票劵类型编码
	 */
	@Column(name="COUPON_TYPE_CODE", nullable=false)
	private String couponTypeCode;
	
	/**
	 * 票劵类型名称
	 *  
	 */
	@Column(name="COUPON_NAME", nullable=false)
	private String couponName;
	
	/**
	 * 票劵类型 
	 * 0：兑换券  1：代金卷  2：优惠卷  3：礼品卡
	 */
	@Column(name="COUPON_TYPE", nullable=false)
	private String couponType;
	
	/**
	 * 票劵类型状态
	 * 0：有效  1：无效
	 */
	@Column(name="COUPON_TYPE_STATUS", nullable=false)
	private String couponTypeStatus;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCouponTypeCode() {
		return couponTypeCode;
	}

	public void setCouponTypeCode(String couponTypeCode) {
		this.couponTypeCode = couponTypeCode;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponTypeStatus() {
		return couponTypeStatus;
	}

	public void setCouponTypeStatus(String couponTypeStatus) {
		this.couponTypeStatus = couponTypeStatus;
	}
	
}
