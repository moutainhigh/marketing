package com.oristartech.marketing.facade;

import java.util.List;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.vo.MarketingActivityVO;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.Page;

public interface IMarketingFacade {

	/**
	 * 分页查询营销活动
	 * @throws Exception 
	 */
	public Page pageListMarketingActivity(MarketingSearchVO searchVo, int pageNo, int pageSize,Long accountId) throws ServiceException;
	
	/**
	 * 根据ID查询营销活动信息
	 * 
	 * @param id
	 * @param isLoadGroup 是否加载规则组json数据
	 * @return
	 */
	public MarketingActivityVO getMarketingById(Long tenantId,Long id,boolean isLoadGroupJson);
	
	/**
	 * 保存或更新活动
	 * @param marketingActivityVO
	 * @param ruleGroup 组信息
	 * @param deleteIds  要删除的ids
	 * @param accountId 编辑的账户
	 * @return 
	 */
	public Long saveActivity(MarketingActivityVO marketingActivityVO, String ruleGroup, String deleteIds,Long accountId) throws ServiceException;
	
	/**
	 * 获取活动的活动编码
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public String getActivityCode(Long id) throws ServiceException;
	
	/**
	 * 删除营销活动
	 * @param id
	 * @throws ServiceException 
	 */
	public void delete(Long id,Long tenantId) throws ServiceException;
	
	/**
	 * 启用/停用 营销活动
	 * @param id 营销活动id
	 * @param tenantId 租户id
	 * @param accountId 账号id
	 * @param remark
	 * @throws ServiceException
	 */
	public void enableOrDisable(Long id, Long tenantId, Long accountId, String remark) throws ServiceException;
	
	/**
	 * 规则测算
	 * @param ruleGroup
	 * @param facts
	 * @return
	 */
	String testRule(String ruleGroup, List<Object> facts);
	
	/**
	 * 验证活动名称是否存在
	 */
	public boolean validateName(String name, Long id,String flag,Long tenantId);
}
