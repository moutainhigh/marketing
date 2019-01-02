package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

/**
 * 影票 "_" 开头的属性是内部使用的属性.
 */
public class FilmTicketInfo extends SaleItemInfo implements Serializable {
	/**
	 * 类型标识, 方便转为json加上类型标识
	 */
	private String _type = FilmTicketInfo.class.getSimpleName();
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
	 * 影片类型 影片类型编码
	 */
	private String filmTypeKey;
	/**
	 * 放映效果
	 * 
	 */
	private String showEffect;
	/**
	 * 影厅类型
	 * 
	 */
	private String hallTypeKey;
	/**
	 * 放映厅座位数
	 * 
	 */
	private Integer hallSeatAmout;
	/**
	 * 最低发行价
	 * 
	 */
	private BigDecimal lowestPrice;
	/**
	 * 影片编码
	 * 
	 */
	private String uniformCode;
	/**
	 * 座位销售区域 座位销售区域
	 */
	private String seatRegionKey;
	/**
	 * 影票标准价
	 * 
	 */
	private BigDecimal filmStandardPrice;
	/**
	 * 影票是否是连场
	 * 
	 */
	private Boolean isConnectTicket;
	/**
	 * 影票key 影票key
	 */
	private String merKey;
	/**
	 * 影票数量 影票数量(本属性固定为1, 因为即使同一场次的票,也是不同的. 不要用本属性做条件, 可以用SaleInfo中的影票总数量做条件)
	 */
	private BigDecimal amount;
	/**
	 * 电影放映时间
	 * 
	 */
	private String planStartTime;
	/**
	 * 放映场次Key 场次uid
	 */
	private String filmPlanKey;
	/**
	 * 放映计划编码
	 * 
	 */
	private String filmPlanCode;
	/**
	 * 商品类型
	 * 
	 */
	private String saleItemType = "0";

	/**
	 * 座位等级
	 * 
	 */
	private Integer seatGrade;
	/**
	 * 影票单价
	 * 
	 */
	private BigDecimal filmPrice;
	/**
	 * 放映前N天
	 * 
	 */
	private Integer filmStartDayBefore;
	/**
	 * 放映前N小时
	 * 
	 */
	private Integer filmStartHourBefore;

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

	public String getFilmTypeKey() {
		return filmTypeKey;
	}

	public void setFilmTypeKey(String filmTypeKey) {
		this.filmTypeKey = filmTypeKey;
	}

	public String getShowEffect() {
		return showEffect;
	}

	public void setShowEffect(String showEffect) {
		this.showEffect = showEffect;
	}

	public String getHallTypeKey() {
		return hallTypeKey;
	}

	public void setHallTypeKey(String hallTypeKey) {
		this.hallTypeKey = hallTypeKey;
	}

	public Integer getHallSeatAmout() {
		return hallSeatAmout;
	}

	public void setHallSeatAmout(Integer hallSeatAmout) {
		this.hallSeatAmout = hallSeatAmout;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public String getUniformCode() {
		return uniformCode;
	}

	public void setUniformCode(String uniformCode) {
		this.uniformCode = uniformCode;
	}

	public String getSeatRegionKey() {
		return seatRegionKey;
	}

	public void setSeatRegionKey(String seatRegionKey) {
		this.seatRegionKey = seatRegionKey;
	}

	public BigDecimal getFilmStandardPrice() {
		return filmStandardPrice;
	}

	public void setFilmStandardPrice(BigDecimal filmStandardPrice) {
		this.filmStandardPrice = filmStandardPrice;
	}

	public Boolean getIsConnectTicket() {
		return isConnectTicket;
	}

	public void setIsConnectTicket(Boolean isConnectTicket) {
		this.isConnectTicket = isConnectTicket;
	}

	public String getMerKey() {
		return merKey;
	}

	public void setMerKey(String merKey) {
		this.merKey = merKey;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}

	public String getFilmPlanKey() {
		return filmPlanKey;
	}

	public void setFilmPlanKey(String filmPlanKey) {
		this.filmPlanKey = filmPlanKey;
	}

	public String getFilmPlanCode() {
		return filmPlanCode;
	}

	public void setFilmPlanCode(String filmPlanCode) {
		this.filmPlanCode = filmPlanCode;
	}

	public String getSaleItemType() {
		return this.saleItemType;
	}

	public void setSaleItemType(String saleItemType) {
		this.saleItemType = "0"; // 影票固定0;
	}

	public Integer getSeatGrade() {
		return seatGrade;
	}

	public void setSeatGrade(Integer seatGrade) {
		this.seatGrade = seatGrade;
	}

	public BigDecimal getFilmPrice() {
		return filmPrice;
	}

	public void setFilmPrice(BigDecimal filmPrice) {
		this.filmPrice = filmPrice;
	}

	public Integer getFilmStartDayBefore() {
		return filmStartDayBefore;
	}

	public void setFilmStartDayBefore(Integer filmStartDayBefore) {
		this.filmStartDayBefore = filmStartDayBefore;
	}

	public Integer getFilmStartHourBefore() {
		return filmStartHourBefore;
	}

	public void setFilmStartHourBefore(Integer filmStartHourBefore) {
		this.filmStartHourBefore = filmStartHourBefore;
	}

}
