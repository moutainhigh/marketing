package com.oristartech.marketing.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.facade.IMarketingFacade;
import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.marketing.service.IRuleService;
import com.oristartech.marketing.vo.MarketingActivityVO;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.facade.IRuleFacade;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;

@Service
public class MarketingServiceImpl implements IMarketingService {

	private Logger logger = LoggerFactory.getLogger(MarketingServiceImpl.class);
	
	@Reference(version = "1.0.0")
	IMarketingFacade marketingFacade;

	@Override
	public Page pageListMarketingActivity(MarketingSearchVO searchVo, int pageNo, int pageSize,
			Long accountId) throws ServiceException {
		return marketingFacade.pageListMarketingActivity(searchVo, pageNo, pageSize, accountId);
	}

	@Override
	public MarketingActivityVO getMarketingById(Long tenantId,Long id, boolean isLoadGroupJson) {
		return marketingFacade.getMarketingById(tenantId,id, isLoadGroupJson);
	}

	@Override
	public Long saveActivity(MarketingActivityVO marketingActivityVO, String ruleGroup, String deleteIds,
			Long accountId) throws ServiceException {
		return marketingFacade.saveActivity(marketingActivityVO, ruleGroup, deleteIds, accountId);
	}

	@Override
	public String getActivityCode(Long id) throws ServiceException {
		return marketingFacade.getActivityCode(id);
	}

	@Override
	public void delete(Long id, Long tenantId) throws ServiceException {
		marketingFacade.delete(id, tenantId);
	}

	@Override
	public void enableOrDisable(Long id, Long tenantId, Long accountId, String remark) throws ServiceException {
		marketingFacade.enableOrDisable(id, tenantId, accountId, remark);
	}

	@Override
	public String testRule(String ruleGroup, List<Object> facts) {
		return marketingFacade.testRule(ruleGroup, facts);
	}

	@Override
	public boolean validateName(String name, Long id, String flag, Long tenantId) {
		return marketingFacade.validateName(name, id, flag, tenantId);
	}

	@Override
	public void audit() throws ServiceException {
		
	}
	

}
