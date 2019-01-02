package com.oristartech.rule.core.result.utils;

import java.util.Map;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.constants.FactConstants;

public class RuleResultUtil {
	/**
	 * 清空anction结果中的和规则引擎相关的属性，不会把这些属性返回客户端
	 * @param anResult
	 */
	public static void removeEngineRelateField(Object anResult) {
		if(anResult instanceof Map) {
			((Map) anResult).remove(FactConstants.IS_FACT_KEY);
			((Map) anResult).remove(FactConstants.CATEGORY_LOAD_KEY);
			((Map) anResult).remove(FactConstants.CATEGORY_NUM_KEY);
			((Map) anResult).remove(FactConstants.CATEGORY_FACT_ACTION_TYPE);
			((Map) anResult).remove(FactConstants.CATEGORY_TYPE_KEY);
		} else {
			BeanUtils.setProperty(anResult, FactConstants.IS_FACT_KEY, null);
			BeanUtils.setProperty(anResult, FactConstants.CATEGORY_LOAD_KEY, null);
			BeanUtils.setProperty(anResult, FactConstants.CATEGORY_NUM_KEY, null);
			BeanUtils.setProperty(anResult, FactConstants.CATEGORY_FACT_ACTION_TYPE, null);
			BeanUtils.setProperty(anResult, FactConstants.CATEGORY_TYPE_KEY, null);
		}
	}
}
