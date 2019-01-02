package com.oristartech.rule.core.core.dao;

/**
 * 条件元素dao
 * @author chenjunfei
 *
 */
public interface IRuleConditionElementDao {

	/**
	 * 获取在规则子项中出现的第一个条件值
	 * @param sectionId
	 * @param categoryName
	 * @param modelFieldName
	 * @return
	 */
	String findFirstValueInSection(Long sectionId, String categoryName, String modelFieldName);
	
	/**
	 * 更新指定section下所有指定的条件为指定值.
	 * @param sectionId
	 * @param categoryName
	 * @param modelFieldName
	 */
	void updateFieldValue(Long sectionId, String categoryName, String modelFieldName, String value);

}
