package com.oristartech.rule.core.template.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.template.dao.IFunctionGroupAndTemplateRDao;
import com.oristartech.rule.core.template.model.FunctionGroupAndTemplateRelation;

@Repository
public class FunctionGroupAndTemplateRDaoImpl extends RuleBaseDaoImpl<FunctionGroupAndTemplateRelation, Integer>  implements IFunctionGroupAndTemplateRDao {

}
