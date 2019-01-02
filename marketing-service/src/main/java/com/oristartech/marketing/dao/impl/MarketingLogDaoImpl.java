package com.oristartech.marketing.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.marketing.dao.IMarketingLogDao;
import com.oristartech.marketing.model.MarketingActivityLog;

@Repository
public class MarketingLogDaoImpl extends RuleBaseDaoImpl<MarketingActivityLog, Long> implements IMarketingLogDao{

	public Long save(MarketingActivityLog marketingActivityLog) {
			return (Long) getHibernateTemplate().save(marketingActivityLog);

	}
}
