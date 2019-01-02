package com.oristartech.rule.core.template.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.base.model.ModelFieldDataSource;
import com.oristartech.rule.core.template.dao.IFieldGroupAndFieldRDao;
import com.oristartech.rule.core.template.model.FieldGroupAndFieldRelation;

@Repository
public class FieldGroupAndFieldRDaoImpl extends RuleBaseDaoImpl<FieldGroupAndFieldRelation, Integer>  implements IFieldGroupAndFieldRDao {
	public List<ModelFieldDataSource> findModelFieldDSByGroupIds(List<Integer> groupIds) {
		String hql = "SELECT DISTINCT ds FROM FieldGroupAndFieldRelation fgfr" +
				     " JOIN fgfr.modelField mf " +
				     " JOIN mf.modelFieldDataSources ds" +
				     " JOIN fgfr.fieldGroup WHERE fgfr.fieldGroup.id IN(:ids)" +
				     " ORDER BY ds.seqNum ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", groupIds);
		List<ModelFieldDataSource> ds = (List<ModelFieldDataSource>) findByNamedParam(hql, params);
		
		return ds;	
		
	}
	
	public List<FieldGroupAndFieldRelation> findRelationByGroupIds(List<Integer> groupIds) {
		//需要DISTINCT去掉重复的 
		String hql = "SELECT DISTINCT fgfr FROM FieldGroupAndFieldRelation fgfr" +
				     " JOIN FETCH fgfr.modelField mf " +
				     " JOIN FETCH fgfr.fieldGroup fg WHERE fgfr.fieldGroup.id IN(:ids)" +
				     " ORDER BY fg.groupElementType.seqNum, fg.seqNum, fgfr.seqNum ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", groupIds);
		List<FieldGroupAndFieldRelation> groupFields = (List<FieldGroupAndFieldRelation>) findByNamedParam(hql, params);
		
		return groupFields;
	}
}
