	package com.oristartech.rule.core.base.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.base.model.ModelField;

public interface IModelFieldDao extends IRuleBaseDao<ModelField, Integer> {
	/**
	 * 查询所有分类的业务属性
	 * @param catId
	 * @return
	 */
	List<ModelField> getByCategoryId(Integer catId) ;
	
	/**
	 * 根据分类名称和属性名称,获取业务属性
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	ModelField getByCatNameAndFieldName(String categoryName, String fieldName);
	
	/**
	 * 获取所有需要外部加载数据的属性
	 * @return
	 */
	List<ModelField> getAllExternFields();

	/**
	 * 获取指定category下需要动态加载的所有属性 。
	 * 该属性必须是ModelField的queryKeyFieldNames不为空，isInDynamicCondition为false或空，并且loadServiceName不为空
	 * @param categoryName
	 * @return
	 */
	List<ModelField> getExternLoadFields(String categoryName);
}
