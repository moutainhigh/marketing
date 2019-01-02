package com.oristartech.marketing.dao.impl;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.marketing.dao.IMarketingActivityBusinessManagenMentDao;
import com.oristartech.marketing.model.MarketingActivityBusinessManagenMent;

@Repository
public class MarketingActivityBusinessManagenMentDaoImpl extends RuleBaseDaoImpl<MarketingActivityBusinessManagenMent, Long> implements IMarketingActivityBusinessManagenMentDao{

	public Integer deleteByActivityId(Long activityId){
		String hql="delete from MarketingActivityBusinessManagenMent M WHERE M.activityId=?";
		return this.executeSaveOrUpdate(hql, activityId);
	}

}
