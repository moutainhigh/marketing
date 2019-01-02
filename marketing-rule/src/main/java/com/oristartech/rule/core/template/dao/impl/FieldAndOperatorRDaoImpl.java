package com.oristartech.rule.core.template.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.template.dao.IFieldAndOperatorRDao;
import com.oristartech.rule.core.template.model.FieldAndOperatorRelation;

@Repository
public class FieldAndOperatorRDaoImpl extends RuleBaseDaoImpl<FieldAndOperatorRelation, Integer>  implements IFieldAndOperatorRDao {
	public List<FieldAndOperatorRelation> findFieldAndOpByGroupIds(List<Integer> groupIds) {
		//由于同时查出modelfield的operator，所以结果中同样的FieldAndOperatorRelation会有多条；
		//需要DISTINCT去掉重复的 
		String hql = "SELECT DISTINCT fop FROM FieldAndOperatorRelation fop " +
				     " JOIN FETCH fop.modelField mf " +
				     " LEFT JOIN FETCH fop.operator " +
				     " JOIN FETCH fop.fieldGroup " +
				     " WHERE fop.fieldGroup.id IN(:ids) " +
				     " ORDER BY fop.seqNum";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", groupIds);
		return (List<FieldAndOperatorRelation>) findByNamedParam(hql, params);
	}
}
