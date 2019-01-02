package com.oristartech.rule.constants;

/**
 * 匹配事实常量
 * @author chenjunfei
 *
 */
public class FactConstants {
	//-------------------客户端需要使用时-----------
	//分类类别标识
	public static String CATEGORY_TYPE_KEY = "_type";
	
	//对象是否是售卖中条目
//	public static String CATEGORY_FOR_SALE = "_sale";
	
	//是否客户端传过来的fact,而不是方法添加的实体
	public static String IS_FACT_KEY = "_fact" ;
	
	//分类对象主动加载标识
	public static String CATEGORY_LOAD_KEY = "_load";
	
	//分类对象号码标识
	public static String CATEGORY_NUM_KEY = "_num";
	
	//分类对象中用于操作分类标识
	public static String CATEGORY_FACT_ACTION_TYPE = "_actionType";
	
	public static String MATCH_FACT_NUM_SPLITER = "_";
	
	public static String FACT_PARENT = "_parent";
	
	public static String TRADE_TYPE = "tradeType";
}
