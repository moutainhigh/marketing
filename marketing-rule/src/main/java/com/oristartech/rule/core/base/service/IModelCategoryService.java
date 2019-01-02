package com.oristartech.rule.core.base.service;

import java.util.List;

import com.oristartech.rule.vos.base.enums.ModelCategoryType;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;

/**
 * 业务分类service
 * @author chenjunfei
 *
 */
public interface IModelCategoryService {
	
	/**
	 * 获取指定类型的业务分类
	 * @param type
	 * @return
	 */
	public List<ModelCategoryVO> getCategorys(ModelCategoryType type);

	/**
	 * 获取category
	 * @param categoryName
	 * @return
	 */
	public ModelCategoryVO getByName(String categoryName);

//	/**
//	 * 是否包含动态field
//	 * @param categoryName
//	 * @return
//	 */
//	public boolean hasDynamicField(String categoryName);
	

	/**
	 * 获取所有允许进行条件融合的分类
	 * @return
	 */
	public List<ModelCategoryVO> getAllCanMergeFieldCon();

//	/**
//	 * category中是否包含有需要外部查询加载的属性， 该属性必须是ModelField的queryKeyFieldNames不为空，isInDynamicCondition为false或空
//	 * 并且loadServiceName不为空
//	 */
//	public boolean hasExternLoadField(String categoryName);
}
