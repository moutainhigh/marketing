package com.oristartech.rule.core.core.util;

import com.oristartech.rule.core.base.model.ModelField;

public class RuleGroupUtil {
	/**
	 * 判断是否要加载数据
	 * @param field
	 * @return
	 */
	public static boolean isNeedLoadExternData(ModelField field) {
		if(field != null && field.getIsExtern() != null && field.getIsExtern() == true
		  && ((field.getKeyFieldName() != null && !field.getName().equals(field.getKeyFieldName()))
			  || ( field.getLabelFieldName() != null && !field.getName().equals(field.getLabelFieldName()))
		  )) {
			return true;
		}
		return false;
	}
}
