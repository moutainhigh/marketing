package com.oristartech.rule.core.base.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.base.model.ModelCategory;
import com.oristartech.rule.vos.base.enums.ModelCategoryType;

public interface IModelCategoryDao extends IRuleBaseDao<ModelCategory, Integer> {

	/**
	 * 根据name获取实体
	 * @param name
	 * @return
	 */
	ModelCategory getByName(String name);
	
	/**
	 * 获取指定类型的业务分类
	 * @param type
	 * @return
	 */
	public List<ModelCategory> getCategorys(ModelCategoryType type);

	/**
	 * 是否包含动态field
	 * @param categoryName
	 * @return
	 */
	boolean hasDynamicField(String categoryName);

	/**
	 * 获取所有允许进行条件融合的分类
	 * @return
	 */
	List<ModelCategory> findALlCanMergeFieldCon();

	/**
	 * category中是否包含有需要外部查询加载的属性， 该属性必须是ModelField的queryKeyFieldNames不为空，isInDynamicCondition为false或空
	 * 并且loadServiceName不为空
	 */
	boolean hasExternLoadField(String categoryName);
	
}
