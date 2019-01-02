package com.oristartech.rule.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json 工具类
 * @author chenjunfei
 *
 */
public class JsonUtil {
	//cache ObjectMapper
	private static ObjectMapper objectMapper;
	
	/**
	 * 把json对象字符串转化为java对象
	 * @param jsonObj
	 * @return
	 */
	public static <T> T jsonToObject(String jsonStr, Class<T> cls) {
		if(BlankUtil.isBlank(jsonStr) || !jsonStr.trim().startsWith("{")) {
			return null;
		}
		ObjectMapper jsonMapper = getMapper();
		jsonMapper.registerSubtypes(cls);
		try {
			return jsonMapper.readValue(jsonStr, cls);
		} catch(Exception e) {
			throw new RuntimeException("转换json出错：" + jsonStr, e);
		}
	}
	
	/**
	 * json数组变为list数组
	 * @param <T>
	 * @param jsonArray
	 * @param clz
	 * @return
	 */
	public static <T> List<T> jsonArrayToList (String jsonArray, Class<T> clz) {
		if(BlankUtil.isBlank(jsonArray) || !jsonArray.trim().startsWith("[")) {
			return null;
		}
		
		ObjectMapper jsonMapper = getMapper();
		try {
			return jsonMapper.readValue(jsonArray, jsonMapper.getTypeFactory().constructParametricType(ArrayList.class, clz));
		} catch(Exception e) {
			throw new RuntimeException("转换json出错：" + jsonArray, e);
		}
	}
	
	/**
	 * 把json字符串变为对应的object, 可用是普通object, 或list
	 * @param <T>
	 * @param jsonStr
	 * @param clz 目标对象, 若不知道具体类型, 可以是Map
	 * @return
	 */
	public static <T> Object jsonToCommonObject(String jsonStr, Class<T> clz) {
		if(BlankUtil.isBlank(jsonStr)) {
			return null;
		}
		if(jsonStr.trim().startsWith("[")) {
			return jsonArrayToList(jsonStr, clz);
		}
		return jsonToObject(jsonStr, clz);
	}
	
	/**
	 * java对象变为json字符串, 忽略null值的属性, 建议调用本方法
	 * @param obj
	 * @return
	 */
	public static String objToJsonIgnoreNull(Object obj) {
		try {
			if(obj != null) {
				if(obj instanceof String) {
					return (String)obj;
				}
				ObjectMapper jsonMapper = getMapper();
				return jsonMapper.writeValueAsString(obj);
			} 
		} catch(Exception e) {
			throw new RuntimeException("转换json出错：" + obj, e);
		}
	
		return null;
	}
	
	
	/**
	 * 把java对象（包含普通对象，数组，map，list等），序列化为json字符串, 包含null值
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj) {
		try {
			if(obj != null) {
				if(obj instanceof String) {
					return (String)obj;
				}
				ObjectMapper jsonMapper = getMapper();
				return jsonMapper.writeValueAsString(obj);
			} 
			
		} catch(Exception e) {
			throw new RuntimeException("转换json出错：" + obj, e);
		}
		return null;
	}
	
	private static ObjectMapper getMapper() {
		if(objectMapper == null) {
			objectMapper = new ObjectMapper();
			initCondig(objectMapper);
		}
		
		return objectMapper;
	}
	/**
	 * 配置转换行为
	 * @param mapper
	 */
	private static void initCondig(ObjectMapper mapper) {
		 DateFormat df = new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT); 
		 //默认的日期格式
         mapper.setDateFormat(df);
         mapper.setSerializationInclusion(Include.NON_NULL);
         mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
         mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
}
