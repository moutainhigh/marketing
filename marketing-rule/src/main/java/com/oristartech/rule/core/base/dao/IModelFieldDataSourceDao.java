package com.oristartech.rule.core.base.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.base.model.ModelFieldDataSource;
import com.oristartech.rule.vos.base.vo.ModelFieldDataSourceVO;

public interface IModelFieldDataSourceDao extends IRuleBaseDao<ModelFieldDataSource, Integer> {
	/**
	 * 根据业务属性id查询可选的值列表
	 * @param fieldId
	 * @return
	 */
	List<ModelFieldDataSourceVO> getDatasByModelFieldId(Integer fieldId) ;
}
