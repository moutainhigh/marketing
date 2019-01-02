package com.oristartech.marketing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.marketing.dao.IMarketingActivityBusinessManagenMentDao;
import com.oristartech.marketing.model.MarketingActivityBusinessManagenMent;
import com.oristartech.marketing.service.IMarketingActivityBusinessManagenMentService;
import com.oristartech.marketing.vo.MarketingActivityBusinessManagenMentVO;

@Component
public class MarketingActivityBusinessManagenMentServiceImpl extends RuleBaseServiceImpl implements IMarketingActivityBusinessManagenMentService {

	@Autowired
	private IMarketingActivityBusinessManagenMentDao marketingActivityBusinessManagenMentDao;

	/**
	 * 根据营销活动ID删除交易商户记录
	 */
	public void deleteByActivityId(Long activityId) {
		this.marketingActivityBusinessManagenMentDao.deleteByActivityId(activityId);
	}

	public void save(MarketingActivityBusinessManagenMentVO activityBusMent) {
		MarketingActivityBusinessManagenMent entity = getMapper().map(activityBusMent,MarketingActivityBusinessManagenMent.class);
		this.marketingActivityBusinessManagenMentDao.saveOrUpdate(entity);
	}

}
