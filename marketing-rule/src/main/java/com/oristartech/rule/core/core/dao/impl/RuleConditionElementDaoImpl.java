package com.oristartech.rule.core.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.core.dao.IRuleConditionElementDao;
import com.oristartech.rule.core.core.model.RuleConditionElement;

@Repository
public class RuleConditionElementDaoImpl extends RuleBaseDaoImpl<RuleConditionElement, Long> implements IRuleConditionElementDao {
	public String findFirstValueInSection(Long sectionId, String categoryName, String modelFieldName) {
	    String hql = " SELECT rce.operand from RuleConditionElement rce JOIN rce.modelField mf JOIN rce.ruleCondition rc " +
	    		     " WHERE rc.ruleSection.id = ?" +
	    		     " AND mf.modelCategory.name = ? " +
	    		     " AND mf.name=? " +
	    		     " ORDER BY rce.seqNum ";
	    
	    List<String> result = (List<String>) super.findByNamedParam(hql, new Object[]{sectionId, categoryName, modelFieldName});
	    if(!BlankUtil.isBlank(result)) {
	    	return result.get(0);
	    }
	    return null;
	}
	
	public void updateFieldValue(Long sectionId, String categoryName, String modelFieldName, String value) {
		//因为条件中也会可能有这个业务编码
		String sql = " UPDATE RULE_CORE_RULE_CONDITION_ELEMENT rce " +
					" JOIN RULE_BASE_MODEL_FIELD mf ON (rce.MODEL_FIELD_ID = mf.ID) " + 
					" JOIN RULE_BASE_MODEL_CATEGORY mc ON (mf.MODEL_CATEGORY_ID = mc.ID) " + 
					" JOIN RULE_CORE_RULE_CONDITION rc ON rce.RULE_CONDITION_ID = rc.ID " + 
					" JOIN RULE_CORE_RULE_SECTION rs ON rs.ID = rc.RULE_SECTION_ID " + 
					" SET rce.OPERAND = ? " + 
					" WHERE" + 
					" mc.NAME = ? " + 
					" AND mf.NAME = ?  " + 
					" AND rs.ID = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(value);
		params.add(categoryName);
		params.add(modelFieldName);
		params.add(sectionId);
		super.executeSqlUpdate(sql, params);
	}
}
