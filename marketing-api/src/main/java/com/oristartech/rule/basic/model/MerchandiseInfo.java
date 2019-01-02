package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

/**
 * 小卖 "_" 开头的属性是内部使用的属性.
 */
public class MerchandiseInfo extends SaleItemInfo implements Serializable {
	/**
	 * 类型标识, 方便转为json加上类型标识
	 */
	private String _type = MerchandiseInfo.class.getSimpleName();
	// 分类对象号码标识
	@JsonIgnore
	private Integer _num;

	// 标识是否是事实
	@JsonIgnore
	private Boolean _fact = true;

	// 父类
	private String _parent;

	// 是否需要主动加载
	private Boolean _load;

	/**
	 * 国际条码
	 * 
	 */
	private String merBarCode;
	/**
	 * 店内码
	 * 
	 */
	private String merCode;
	/**
	 * 数量 小卖数量
	 */
	private BigDecimal amount;
	/**
	 * 商品类别
	 * 
	 */
	private String classCode;
	/**
	 * 品牌
	 * 
	 */
	private String brandId;
	/**
	 * 供应商 供应商编码
	 */
	private String supCode;
	/**
	 * 规格
	 * 
	 */
	private String merSpec;
	/**
	 * 货架
	 * 
	 */
	private String storeRack;
	/**
	 * 商品名称 小卖商品key
	 */
	private String merKey;
	/**
	 * 商品原数量 商品修改前数量
	 */
	private BigDecimal oldAmount;
	/**
	 * 卖品类型 服务商品, 套餐,普通卖品等
	 */
	private String merType;

	/**
	 * 卖品单价
	 * 
	 */
	private BigDecimal merPrice;

	/**
	 * 商品类型
	 * 
	 */
	private String saleItemType = "1";

	public String get_typeList() {
		StringBuilder sb = new StringBuilder();

		Class clz = this.getClass();
		while (!clz.getSimpleName().equals(Object.class.getSimpleName())) {
			sb.append(clz.getSimpleName());
			sb.append(",");
			clz = clz.getSuperclass();
		}
		return sb.replace(sb.length() - 1, sb.length(), "").toString();
	}

	public void set_typeList(String typeList) {
		// 为了方便用工具setting, 例如DozerMapper, 提供了个冗余方法.
	}

	public String get_parent() {
		return this.getClass().getSuperclass().getSimpleName();
	}

	public void set_parent(String parent) {
		// 为了方便用工具setting, 例如DozerMapper, 提供了个冗余方法.
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String type) {
		this._type = type;
	}

	public Integer get_num() {
		return _num;
	}

	public void set_num(Integer num) {
		this._num = num;
	}

	public Boolean get_fact() {
		return _fact;
	}

	public void set_fact(Boolean fact) {
		this._fact = fact;
	}

	public Boolean get_load() {
		return _load;
	}

	public void set_load(Boolean load) {
		this._load = load;
	}

	public String getMerBarCode() {
		return merBarCode;
	}

	public void setMerBarCode(String merBarCode) {
		this.merBarCode = merBarCode;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getSupCode() {
		return supCode;
	}

	public void setSupCode(String supCode) {
		this.supCode = supCode;
	}

	public String getMerSpec() {
		return merSpec;
	}

	public void setMerSpec(String merSpec) {
		this.merSpec = merSpec;
	}

	public String getStoreRack() {
		return storeRack;
	}

	public void setStoreRack(String storeRack) {
		this.storeRack = storeRack;
	}

	public String getMerKey() {
		return merKey;
	}

	public void setMerKey(String merKey) {
		this.merKey = merKey;
	}

	public BigDecimal getOldAmount() {
		return oldAmount;
	}

	public void setOldAmount(BigDecimal oldAmount) {
		this.oldAmount = oldAmount;
	}

	public String getMerType() {
		return merType;
	}

	public void setMerType(String merType) {
		this.merType = merType;
	}

	public String getSaleItemType() {
		return this.saleItemType;
	}

	public void setSaleItemType(String saleItemType) {
		this.saleItemType = "1"; // 卖品固定1;
	}

	public BigDecimal getMerPrice() {
		return merPrice;
	}

	public void setMerPrice(BigDecimal merPrice) {
		this.merPrice = merPrice;
	}

}
