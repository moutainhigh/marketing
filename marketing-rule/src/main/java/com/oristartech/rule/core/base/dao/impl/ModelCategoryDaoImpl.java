package com.oristartech.rule.core.base.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.base.dao.IModelCategoryDao;
import com.oristartech.rule.core.base.model.ModelCategory;
import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.vos.base.enums.ModelCategoryType;

@Repository
public class ModelCategoryDaoImpl extends RuleBaseDaoImpl<ModelCategory, Integer> implements IModelCategoryDao {
	
	public ModelCategory getByName(String name) {
		getHibernateTemplate().setCacheQueries(true);
		String hql = "select mc from ModelCategory mc where mc.name = ?";
		List<ModelCategory> categorys = (List<ModelCategory>)super.findByNamedParam(hql, name);
		getHibernateTemplate().setCacheQueries(false);
		if(!BlankUtil.isBlank(categorys)) {
			return categorys.get(0);
		}
		return null;
	}
	
	public List<ModelCategory> getCategorys(ModelCategoryType type) {
		String hql = "select mc from ModelCategory mc where mc.type = ?";
		return (List<ModelCategory>)super.findByNamedParam(hql, type);
	}
	
	public boolean hasDynamicField(String categoryName) {
		String queryCatName = MYSQL_LIKE_SPLITER + ModelField.composeQueryCategoryPrefix(categoryName) + MYSQL_LIKE_SPLITER;
		String hql = "SELECT count(mf) from ModelField mf WHERE mf.isInDynamicCondition = true" +
				         " AND mf.queryKeyFieldNames like ? " +
				         " AND (mf.isDelete is null or mf.isDelete = false )";
		super.setCacheQueries(true);
		Long c = (Long)super.uniqueResult(hql, queryCatName);
		super.setCacheQueries(false);
		return (c != null && c > 0);
	}
	
	public List<ModelCategory> findALlCanMergeFieldCon() {
		super.setCacheQueries(true);
		String hql = "select mc from ModelCategory mc where mc.type = ? AND mc.isMergeNearFieldCon= ?";
		List<ModelCategory> results = (List<ModelCategory>)super.findByNamedParam(hql, new Object[]{ModelCategoryType.DROOLS, true});
		super.setCacheQueries(false);
		return results;
	}
	
	/**
	 * category中是否包含有需要外部查询加载的属性， 该属性必须是ModelField的queryKeyFieldNames不为空，isInDynamicCondition为false或空
	 * 并且loadServiceName不为空
	 */
	public boolean hasExternLoadField(String categoryName) {
		String hql = " SELECT count(mf) from ModelField mf join mf.modelCategory mc " +
				     " WHERE (mf.isInDynamicCondition = false or mf.isInDynamicCondition is null)" +
        			 " AND mf.queryKeyFieldNames is not null " +
        			 " AND mf.loadServiceName is not null " + 
                     " AND (mf.isDelete is null or mf.isDelete = false )" +
                     " AND mc.name = ? ";
		super.setCacheQueries(true);
		Long c = (Long)super.uniqueResult(hql, categoryName);
		super.setCacheQueries(false);
		return (c != null && c > 0);
	}
	
//	/**
//	 * 把字符串id变为整数id
//	 * @param ids
//	 * @return
//	 */
//	private List<Integer> getModelQueryIds(List<String> ids) {
//		if(!BlankUtil.isBlank(ids)) {
//			List<Integer> modelIds = new ArrayList<Integer>();
//			for(String strIds : ids) {
//				String[] fields = strIds.split(QUERY_ID_SPLITER);
//				for(String field : fields) {
//					if(!BlankUtil.isBlank(field)) {
//						modelIds.add(Integer.valueOf(field));
//					}
//				}
//			}
//			return modelIds;
//		}
//		return null;
//	}
}
