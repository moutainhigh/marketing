package com.oristartech.rule.core.base.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.base.dao.IModelFieldDao;
import com.oristartech.rule.core.base.model.ModelField;

@Repository
public class ModelFieldDaoImpl extends RuleBaseDaoImpl<ModelField, Integer> implements IModelFieldDao {

	public List<ModelField> getByCategoryId(Integer catId) {
		String hql = "select mf from ModelField mf where mf.modelCategory.id = ?";
		return (List<ModelField>)findByNamedParam(hql, catId);
	}
	
	public ModelField getByCatNameAndFieldName(String categoryName, String fieldName) {
	    String hql = "select mf from ModelField mf where mf.modelCategory.name = ? and mf.name = ?";
	    return (ModelField)super.uniqueResult(hql, new Object[]{categoryName, fieldName} );
	}
	
	public List<ModelField> getAllExternFields() {
		String hql = "select mf from ModelField mf where mf.isExtern = true";
	    return (List<ModelField>)findByNamedParam(hql, null);
	}
	
	public List<ModelField> getExternLoadFields(String categoryName) {
		String hql = " SELECT mf from ModelField mf join mf.modelCategory mc " +
	     		" WHERE (mf.isInDynamicCondition = false or mf.isInDynamicCondition is null)" +
				" AND mf.queryKeyFieldNames is not null " +
				" AND mf.loadServiceName is not null " + 
		        " AND (mf.isDelete is null or mf.isDelete = false )" +
		        " AND mc.name = ? ";
		super.setCacheQueries(true);
		List result = super.findByNamedParam(hql, categoryName);
		super.setCacheQueries(false);
	    return result;
	}
}
