package com.oristartech.rule.core.template.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.template.dao.IFieldGroupAndTemplateRDao;
import com.oristartech.rule.core.template.model.FieldGroupAndTemplateRelation;

@Repository
public class FieldGroupAndTemplateRDaoImpl extends RuleBaseDaoImpl<FieldGroupAndTemplateRelation, Integer>  implements IFieldGroupAndTemplateRDao {
	
}
