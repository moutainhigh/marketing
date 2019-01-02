package com.oristartech.rule.core.template.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.template.dao.IRuleElementTemplateDao;
import com.oristartech.rule.core.template.model.RuleElementTemplate;

@Repository
public class RuleElementTemplateDaoImpl extends RuleBaseDaoImpl<RuleElementTemplate, Integer> implements IRuleElementTemplateDao {
	
	public List<RuleElementTemplate> findByType(String type) {
	    String hql  = "select rt from RuleElementTemplate rt where rt.type = ? and (rt.isEnable = true or rt.isEnable is null) ";
	    return (List<RuleElementTemplate>)super.findByNamedParam(hql, type);
	}
	
	public RuleElementTemplate getByName(String temName) {
		String hql  = "select rt from RuleElementTemplate rt where rt.name = ? ";
	    return (RuleElementTemplate)super.uniqueResult(hql, temName);
	}
}
