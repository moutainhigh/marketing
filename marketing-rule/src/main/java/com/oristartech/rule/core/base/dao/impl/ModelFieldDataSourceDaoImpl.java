package com.oristartech.rule.core.base.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.base.dao.IModelFieldDataSourceDao;
import com.oristartech.rule.core.base.model.ModelFieldDataSource;
import com.oristartech.rule.vos.base.vo.ModelFieldDataSourceVO;

@Repository
public class ModelFieldDataSourceDaoImpl extends RuleBaseDaoImpl<ModelFieldDataSource, Integer> implements IModelFieldDataSourceDao {

	public List<ModelFieldDataSourceVO> getDatasByModelFieldId(Integer fieldId) {
		String hql = "select id as id,label as label, value as value , seqNum as seqNum ,modelField.id as modelFieldId" +
				" from ModelFieldDataSource mfds where mfds.modelField.id = ?";
		return (List<ModelFieldDataSourceVO>)findByNamedParam(hql, fieldId, ModelFieldDataSourceVO.class);
	}
}
