package com.oristartech.rule.core.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.core.dao.IRuleCompositionDao;
import com.oristartech.rule.core.core.model.RuleComposition;

@Repository
public class RuleCompositionDaoImpl extends RuleBaseDaoImpl<RuleComposition, Integer> implements IRuleCompositionDao {
	public Page loadValidRuleComposition(int start, int limit, String format) {
		return super.searchPagedQuery("from RuleComposition", start, limit);
	}
}
