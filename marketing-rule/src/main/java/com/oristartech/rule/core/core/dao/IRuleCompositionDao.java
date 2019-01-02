package com.oristartech.rule.core.core.dao;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.core.model.RuleComposition;

public interface IRuleCompositionDao extends IRuleBaseDao<RuleComposition, Integer>{
	public Page loadValidRuleComposition(int start, int limit, String format);
}
