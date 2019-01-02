package com.oristartech.rule.core.base.service;

import java.util.List;

import com.oristartech.rule.vos.base.vo.ModelFieldDataSourceVO;
import com.oristartech.rule.vos.base.vo.ModelFieldVO;

/**
 * ModelField 属性管理接口
 * @author chenjunfei
 * @date 2013-11-28
 */
public interface IModelFieldService {
	
//	/**
//	 * 根据 fieldId 获取ModelField的可选值列表
//	 * @param fieldId
//	 * @return
//	 */
//	List<ModelFieldDataSourceVO> getDataSourceByFieldId(Integer fieldId);
	
//	/**
//	 * 
//	 * @return
//	 */
//	List<ModelFieldVO> getAllExternFields();

	/**
	 * 获取指定category下需要动态加载的所有属性 。
	 * 该属性必须是ModelField的queryKeyFieldNames不为空，isInDynamicCondition为false或空，并且loadServiceName不为空
	 * @param categoryName
	 * @return
	 */
	List<ModelFieldVO> getExternLoadFields(String categoryName);
	
}
