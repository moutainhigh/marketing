package com.oristartech.marketing.dao;

import com.oristartech.marketing.model.MarketingActivityBusinessManagenMent;

/**
 * 营销活动 Dao
 *
 */
public interface IMarketingActivityBusinessManagenMentDao extends IMarketingBaseDao<MarketingActivityBusinessManagenMent, Long> {

	public Integer deleteByActivityId(Long activityId);
}
