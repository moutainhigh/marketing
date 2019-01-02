package com.oristartech.rule.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.reflectasm.MethodAccess;

public class BeanUtils {
	private static Logger log = LoggerFactory.getLogger(BeanUtils.class);
	
	private static Map<Class, MethodAccess> methodMap = new HashMap<Class, MethodAccess>();
	private static Map<String, Integer> methodIndexMap = new HashMap<String, Integer>();

	private static Map<Class, List<String>> fieldMap = new HashMap<Class, List<String>>();

	public static void copyProperties(Object dest, Object source) {
		if(dest == null || source == null) {
			return;
		}
		if(!(dest instanceof Map) && !(source instanceof Map)) {
//			copyBeanToBean(dest, source);
			try {
				org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source);
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
			
			return;
		} else if(dest instanceof Map && source instanceof Map) {
			((Map)dest).putAll((Map)source);
		}
	}

	public static Object cloneBean(Object source) {
		if(source == null) {
			return null;
		}
		try {
			Object newBean = null;
			if(source instanceof Map) {
				newBean = new HashMap();
			} else {
				newBean = source.getClass().newInstance();
			}
			
			copyProperties(newBean, source);
			return newBean;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void copyBeanToBean(Object dest, Object source) {
		MethodAccess destMethodAccess = methodMap.get(dest.getClass());
		if (destMethodAccess == null) {
			destMethodAccess = cache(dest);
		}
		MethodAccess sourceMethodAccess = methodMap.get(source.getClass());
		if (sourceMethodAccess == null) {
			sourceMethodAccess = cache(source);
		}

		List<String> fieldList = fieldMap.get(source.getClass());
		for (String field : fieldList) {
			String getKey = getPropertyMapKey(source,  getGetKey(field));
			String setkey = getPropertyMapKey(dest, getSetKey(field));
			Integer setIndex = methodIndexMap.get(setkey);
			Integer getIndex = methodIndexMap.get(getKey);
			if (setIndex != null && getIndex != null) {
				destMethodAccess.invoke(dest, setIndex.intValue(), sourceMethodAccess.invoke(source, getIndex));
			}
		}
	}
	
	public static Object getProperty(Object source, String name) {
		if(source == null || BlankUtil.isBlank(name)) {
			return null;
		}
		
		if(source instanceof Map) {
			return ((Map)source).get(name);
		}
		MethodAccess sourceMethodAccess = methodMap.get(source.getClass());
		if (sourceMethodAccess == null) {
			sourceMethodAccess = cache(source);
		}

		List<String> fieldList = fieldMap.get(source.getClass());
		for (String field : fieldList) {
			if(name.equals(field)) {
				String getKey = getPropertyMapKey(source,  getGetKey(field));
				int getIndex = methodIndexMap.get(getKey);
				return sourceMethodAccess.invoke(source, getIndex);
			}
		}
		return null;
	}
	
	public static String getPropertyStr(Object source, String name) {
		Object value = getProperty(source, name);
		if(value != null) {
			return value.toString();
		}
		return null;
	}
	
	public static void setProperty(Object source, String property, Object value) {
		try {
			org.apache.commons.beanutils.BeanUtils.setProperty(source, property, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Map<String, Object> convertToMap(Object source) {
		Map<String, Object> map = null;
		if(source != null && !(source instanceof Map)) {
			map = new HashMap<String, Object>();
			MethodAccess sourceMethodAccess = methodMap.get(source.getClass());
			if (sourceMethodAccess == null) {
				sourceMethodAccess = cache(source);
			}
			List<String> fieldList = fieldMap.get(source.getClass());
			for (String field : fieldList) {
				String getKey = getPropertyMapKey(source,  getGetKey(field));
				Integer getIndex = methodIndexMap.get(getKey);
				if(getIndex != null) {
					map.put(field, sourceMethodAccess.invoke(source, getIndex));
				}
			    
			}
		} else if(source != null && source instanceof Map) {
			return convertMapToMap((Map<String, Object>)source);
		}
		return map;
	}
	
	public static Map<String, Object> convertMapToMap(Map<String, Object> source) {
		Map<String, Object> map = null;
		if(source != null && (source instanceof Map)) {
			map = new HashMap<String, Object>();
			map.putAll(source);
		}
		return map;
	}
//	
	private static MethodAccess cache(Object source) {
		synchronized (source.getClass()) {
			MethodAccess methodAccess = MethodAccess.get(source.getClass());
			Field[] fields = source.getClass().getDeclaredFields();
			List<String> fieldList = new ArrayList<String>(fields.length);
			for (Field field : fields) {
				try {
					String fieldName = field.getName();
					cacheMethodIndex(methodAccess, source, getGetKey(fieldName));
					cacheMethodIndex(methodAccess, source, getSetKey(fieldName));
					fieldList.add(fieldName);
				} catch(Exception e) {
					log.error("cache field value error :", e);
				}
			}
			fieldMap.put(source.getClass(), fieldList);
			methodMap.put(source.getClass(), methodAccess);
			return methodAccess;
		}
	}
	
	public static String getPropertyMapKey(Object source, String name) {
		return source.getClass().getName() + "." + name;
	}
	private static String getGetKey(String fieldName) {
		return "get" + StringUtils.capitalize(fieldName);
	}
	
	private static String getSetKey(String fieldName) {
		return "set" + StringUtils.capitalize(fieldName);
	}
	
	private static void cacheMethodIndex(MethodAccess methodAccess, Object source,  String name) {
		try {
			int index = methodAccess.getIndex(name);
			methodIndexMap.put(getPropertyMapKey(source, name), index);
		} catch(Exception e) {
			log.warn("cache field value error :" + e.getMessage());
		}
	}
}
