package com.oristartech.rule.common.util;


import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * list工具类
 * @author chenjunfei
 *
 */
public class ListUtil  {
	private final static String SPLITER = ",";
	/**
	 * 把","分隔字符串变为指定类 list, 该类必须要有以字符串为参数的构造函数.
	 * @param list
	 * @return
	 */
	public static <T> List<T> str2List(String strs, Class<T> clz) {
		if(!BlankUtil.isBlank(strs)) {
			String[] strArr = strs.split(SPLITER);
			List<T> result = new ArrayList<T>();
			try {
				for(String str : strArr) {
					Constructor<T> con = clz.getConstructor(String.class);
					result.add(con.newInstance(str));
				}
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
			return result;
		}
		return null;
	}
	
}
