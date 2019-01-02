package com.oristartech.marketing.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.BaseTestCase;
import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.common.util.Page;

public class MarketingServiceTest extends BaseTestCase {

	@Autowired
	IMarketingService marketingService;
	
	@Test
	public void pageListMarketingActivityTest() throws ServiceException {
		MarketingSearchVO searchVo = new MarketingSearchVO();
		int pageNo = 1;
		int pageSize = 20;
		long accountId = 123;
		Page page = marketingService.pageListMarketingActivity(searchVo, pageNo, pageSize, accountId);
		System.out.println(JsonUtil.objToJson(page));
	}
}
