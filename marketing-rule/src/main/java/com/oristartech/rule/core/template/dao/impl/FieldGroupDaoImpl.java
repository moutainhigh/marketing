package com.oristartech.rule.core.template.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.template.dao.IFieldGroupDao;
import com.oristartech.rule.core.template.model.FieldGroup;

@Repository
public class FieldGroupDaoImpl extends RuleBaseDaoImpl<FieldGroup, Integer> implements IFieldGroupDao {
	public List<FieldGroup> searchByTypeAndNums(String groupTypeName, String[] fieldManagerNums) {
		String hql = "select fg from FieldGroup fg where fg.groupElementType.name = :groupTypeName and fg.managerNum in(:fieldManagerNums)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupTypeName", groupTypeName);
		params.put("fieldManagerNums", fieldManagerNums);
	    return (List<FieldGroup>) findByNamedParam(hql, params);
	}
	
	public List<FieldGroup> searchFieldGroups(Integer templateId, Boolean isCommon, List<Integer> excludeGroupIds) {
		StringBuilder sb = new StringBuilder("SELECT fg FROM FieldGroupAndTemplateRelation fgt " +
				                      " join fgt.fieldGroup fg " +
				                      " join fetch fg.groupElementType get " +
				                      " WHERE ( fg.isEnable = true or fg.isEnable is null) ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(templateId != null) {
			sb.append(" AND fgt.ruleElementTemplate.id = :templateId ");
			params.put("templateId", templateId);
		}
		if(isCommon != null) {
			sb.append(" AND fgt.isCommon = :isCommon ");
			params.put("isCommon", isCommon);
		}
		if(!BlankUtil.isBlank(excludeGroupIds)) {
			sb.append(" AND fg.id NOT IN(:excludeGroupIds) ");
			params.put("excludeGroupIds", excludeGroupIds);
		}
		sb.append("ORDER BY get.seqNum, fg.seqNum, fgt.seqNum ");
		return (List<FieldGroup>)super.findByNamedParam(sb.toString(), params);
	}
	
	public List<FieldGroup> getByIds(List<Integer> gids) {
		String hql = "SELECT fg FROM FieldGroup fg WHERE fg.id IN(:ids)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", gids);
	    return (List<FieldGroup>) findByNamedParam(hql, params);
	}
	
	public List<FieldGroup> getAllFieldGroupInTest() {
		String hql = "SELECT fg FROM FieldGroup fg WHERE fg.isDisplayInTest = true AND ( fg.isEnable = true or fg.isEnable is null)";
	    return (List<FieldGroup>)findByNamedParam(hql, null);
	}
	
	public List<Integer> getAllFieldGroupIdsInTest() {
		String hql = "SELECT fg.id FROM FieldGroup fg WHERE fg.isDisplayInTest = true AND ( fg.isEnable = true or fg.isEnable is null)";
	    return (List<Integer>)findByNamedParam(hql, null);
	}
}
