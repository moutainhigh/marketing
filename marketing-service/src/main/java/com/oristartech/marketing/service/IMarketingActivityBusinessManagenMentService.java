package com.oristartech.marketing.service;

import com.oristartech.marketing.vo.MarketingActivityBusinessManagenMentVO;

/**
 * 营销活动交易商户关联 Service
 *
 */
public interface IMarketingActivityBusinessManagenMentService {

	
	/**
	 * 根据活动id删除记录
	 * @param id
	 */
	public void deleteByActivityId(Long activityId);
	
	public void save(MarketingActivityBusinessManagenMentVO activityBusMent);
	
	
}