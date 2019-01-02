package com.oristartech.marketing.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.facade.IMarketingFacade;
import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.marketing.vo.MarketingActivityVO;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.Page;

@Service(version = "1.0.0", interfaceClass = IMarketingFacade.class)
public class MarketingFacadeImpl implements IMarketingFacade {

	@Autowired
	IMarketingService marketingService;
	
	@Override
	public Page pageListMarketingActivity(MarketingSearchVO searchVo, int pageNo, int pageSize,Long accountId)
			throws ServiceException {
		return marketingService.pageListMarketingActivityByAccountAuth(searchVo, pageNo, pageSize, accountId);
	}

	@Override
	public MarketingActivityVO getMarketingById(Long tenantId,Long id, boolean isLoadGroupJson) {
		return marketingService.getMarketingById(tenantId,id, isLoadGroupJson);
	}

	@Override
	public Long saveActivity(MarketingActivityVO marketingActivityVO, String ruleGroup, String deleteIds,
			Long accountId) throws ServiceException {
		return marketingService.saveActivity(marketingActivityVO, ruleGroup, deleteIds, accountId);
	}

	@Override
	public String getActivityCode(Long id) throws ServiceException {
		return marketingService.getActivityCode(id);
	}
	
	public void delete(Long id,Long tenantId) throws ServiceException{
		marketingService.delete(id, tenantId);
	}

	@Override
	public void enableOrDisable(Long id, Long tenantId, Long accountId, String remark) throws ServiceException {
		marketingService.enableOrDisable(id, tenantId, accountId, remark);
	}

	@Override
	public String testRule(String ruleGroup, List<Object> facts) {
		return marketingService.testRule(ruleGroup, facts);
	}

	@Override
	public boolean validateName(String name, Long id, String flag, Long tenantId) {
		return marketingService.validateName(name, id, flag, tenantId);
	}

}
