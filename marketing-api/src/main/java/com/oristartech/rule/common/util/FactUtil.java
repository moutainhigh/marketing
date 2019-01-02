package com.oristartech.rule.common.util;

import java.util.ArrayList;
import java.util.List;

import com.oristartech.rule.constants.FactConstants;


/**
 * 事实相关工具类
 * @author chenjunfei
 *
 */
public class FactUtil {
	public static boolean isMatchFactType(Object fact, String type) {
		if(fact != null && !BlankUtil.isBlank(type)) {
			String srcType = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
			if(type.equals(srcType)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 在事实中获取指定的类型的事实，若有多个同类型，只返回一个
	 * @param facts
	 * @param needType
	 * @return
	 */
	public static Object getFactByType (List<Object> facts, String needType) {
		if(!BlankUtil.isBlank(facts)) {
			for(Object fact : facts) {
				String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
				if(needType.equals(type)) {
					return fact;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取事实中指定类型的所有事实
	 * @param facts
	 * @param needType
	 * @return
	 */
	public static List<Object> collectFactsByType(List<Object> facts, String needType) {
		if(!BlankUtil.isBlank(facts)) {
			List<Object> result = new ArrayList<Object>();
			for(Object fact : facts) {
				String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
				if(needType.equals(type)) {
					result.add(fact);
				}
			}
			return result;
		}
		return null;
	}
}
