package com.oristartech.marketing.dao;

import com.oristartech.marketing.model.MarketingActivityLog;

public interface IMarketingLogDao extends IMarketingBaseDao<MarketingActivityLog, Long> {

	public Long save(MarketingActivityLog marketingActivityLog);
}
