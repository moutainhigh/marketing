package com.oristartech.rule.core.template.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.base.model.ModelFieldDataSource;
import com.oristartech.rule.core.template.model.FieldGroupAndFieldRelation;

public interface IFieldGroupAndFieldRDao extends IRuleBaseDao<FieldGroupAndFieldRelation, Integer> {
	/**
	 * 找出group id列表下的所有modelField和fieldGroup的关系
	 * @param fgids FieldGroup id 列表
	 * @return List<FieldGroupAndFieldRelation>
	 */
	List<FieldGroupAndFieldRelation> findRelationByGroupIds(List<Integer> groupIds);
	
	/**
	 * 找出group id列表下的所有modelField下的datasource
	 * @return List<ModelFieldDataSource>
	 */
	List<ModelFieldDataSource> findModelFieldDSByGroupIds(List<Integer> groupIds);
}
