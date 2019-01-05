package com.oristartech.coupon.model;
// default package

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 合同附件表
 */
@Entity
@Table(name = "COUPON_APPLY_ATTACH")
public class CouponApplyAttach   {

	private Integer id;
	
	//附件对应申请单号
	private String applycode;
	
	// 合同附件
	private Blob applyAttach;
	
	//外部导入excel文件
	private Blob importApplyAttach;
	
	private Blob checkApplyAttach;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	/** default constructor */
	public CouponApplyAttach() {
	}
	@Column(name="APPLY_CODE")
	public String getApplycode() {
		return applycode;
	}

	public void setApplycode(String applycode) {
		this.applycode = applycode;
	}
	@Column(name="APPLY_ATTACH")
	public Blob getApplyAttach() {
		return applyAttach;
	}

	public void setApplyAttach(Blob applyAttach) {
		this.applyAttach = applyAttach;
	}
	
	@Column(name="IMPORT_APPLY_ATTACH")
	public Blob getImportApplyAttach() {
		return importApplyAttach;
	}
	public void setImportApplyAttach(Blob importApplyAttach) {
		this.importApplyAttach = importApplyAttach;
	}
	
	@Column(name="CHECK_APPLY_ATTACH")
	public Blob getCheckApplyAttach() {
		return checkApplyAttach;
	}
	public void setCheckApplyAttach(Blob checkApplyAttach) {
		this.checkApplyAttach = checkApplyAttach;
	}
	
	
	
}