package com.oristartech.rule.constants;

/**
 * 业务事实一些属性命名常量值
 * @author chenjunfei
 *
 */
public class BizFactConstants {
	//票券规则类型
	public static final String COUPON_RULE_TYPE = "CouponSale";
	//订单类
	public static final String SALE_INFO = "SaleInfo";
	//商户编码
	public static final String SI_BUSINESS_CODE = "businessCode";
	//影院编码
	public static final String SI_CINEMA_CODE = "cinemaCode";
	//影票总金额
	public static final String SALE_INFO_FILM_SUM_PRICE = "filmSumPrice";
	
	//影票总金额
	public static final String SALE_INFO_MER_SUM_PRICE = "merSumPrice";
	
	
	//商品基类
	public static final String SALE_ITEM_INFO = "SaleItemInfo";
	//期望的执行模式
	public static final String SI_RULE_EXECUTE_MODE = "ruleExecuteMode";
	//交易总额
	public static final String SI_SUM_PRICE = "sumPrice";
	
	//影票
	public static final String FILM_INFO = "FilmTicketInfo";
	//影票场次
	public static final String FILM_PLAN_KEY = "filmPlanKey";
	public static final String FILM_PLAN_CODE = "filmPlanCode";
	//放映时间
	public static final String FILM_PLAN_START_TIME = "planStartTime";
	//放映前n天
	public static final String FILM_START_DAY_BEFORE = "filmStartDayBefore";
	public static final String FILM_START_HOUR_BEFORE = "filmStartHourBefore";
	//座位等级
	public static final String FILM_SEAT_GRADE = "seatGrade";
	//影票单价
	public static final String FILM_FILM_PRICE = "filmPrice";
	//小卖
	public static final String MER_INFO = "MerchandiseInfo";
	
	//卖品单价
	public static final String MER_MER_PRICE = "merPrice";
	
	//小卖统计类
	public static final String MER_STATIS_INFO = "SaleItemStatis";
	//统计数量
	public static final String MER_SATTIS_SUM_AMOUNT = "sumAmount";
	//统计金额
	public static final String MER_SATTIS_SUM_PRICE = "sumPrice";
	//小卖类别
	public static final String MER_CLASS_CODE = "classCode";
	//小卖品牌
	public static final String MER_BRAND_ID = "brandId";
	
	//票券
	public static final String COUPON_INFO = "CouponInfo";
	
	//商品类型
	public static final String SALE_ITEM_TYPE = "saleItemType";
	
	//影票总数量
	public static final String FILM_TICKET_AMOUNT = "filmTicketAmount";
	
	//商品价格
	public static final String PRICE = "price";
	public static final String OLD_PRICE = "oldPrice";
	//其余影票价格
	public static final String OTHER_PRICE = "othersPrice";
	//影票标准价
	public static final String FILM_STANDARD_PRICE = "filmStandardPrice";
	
	//影票
	public static final String FILM_ITEM = "0";
	//卖品
	public static final String MER_ITEM = "1";
	//最低发行价 
	public static final String LOW_PRICE = "lowestPrice";
	
	//商品key
	public static final String MER_KEY = "merKey";
	//订单项id
	public static final String SALE_ITEM_KEY = "saleItemKey";
	
	//订单项多个id分隔符号
	public static final String SALE_ITEM_KEY_SPLITER = ",";
	
	//是否连场
	public static final String IS_CONNECT_TICKET = "isConnectTicket";
	
	//商品数量
	public static final String AMOUNT = "amount";
	//商品原数量
	public static final String OLD_AMOUNT = "oldAmount";
	
	//抵用对象(包含抵用面额, 影院补贴)
	public static final String USE_FORM_MONEY_INFO = "UseForMoneyInfo";
	
	//抵用面额值
	public static final String USE_FORM_MONEY_AMOUNT = "useAsMoneyAmount";
	//影院补贴值
	public static final String CINEMA_PAY_AMOUNT = "cinemaPayAmount";
	
	//会员类
	public static final String MEMBER_INFO = "MemberInfo";
	
	//会员卡号或手机号
	public static final String MEMBER_CARD_NUM = "cardNum";
	
	//票券申请单号
	public static final String COUPON_APPLY_CODE = "couponApplyCode";
	
	//控制一些规则执行行为
	public static final String RULE_CONTEXT_INFO = "RuleContextInfo";
	
	//忽略票数限制
	public static final String RCI_IGNORE_TICKET_AMOUNT = "ignoreFnTicketAmountLimit";
	
	//忽略卡票数
	public static final String RCI_IGNORE_CARD_TICKET_AMOUNT  = "ignoreCardTicketAmountLimit";
	
	//是否输出消耗事实
	public static final String RCI_IS_OUTPUT_CONSUME_FACTS = "isOutputConsumeFacts";
	
	//是否map结果
	public static final String RCI_IS_MAP_RESULT = "isMapResult";
	
	//忽略比较属性
	public static final String RCI_IGNORE_CATEGORY_FIELDS = "ignoreCategoryFields";
	
	public static final String RCI_IS_OUT_GROUP_PRICE_RESULT = "isOutGroupPriceResult";
	
	//是否使用新影票消耗属性
	public static final String RCI_IS_USE_NEW_CONSUME_ITEM = "isUseNewConsumeItem";
	
	public final static String SYSTEM_CODE = "systemCode";
	
}
