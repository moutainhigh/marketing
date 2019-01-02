package com.oristartech.marketing.service;


import java.util.Date;
import java.util.List;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.enums.ActivityState;
import com.oristartech.marketing.vo.MarketingActivityVO;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * 营销活动 Service
 *
 */
public interface IMarketingService {
	
	/**
	 * 审批完成
	 * @param id 活动ID
	 * @param auditStatus 审批状态 1审批通过，2驳回审批
	 * @param auditTime 审批时间
	 * @param auditTaskCode 审批任务节点
	 * @param approvalMan 审批人
	 * @throws ServiceException
	 */
	public void audit(Long id,Integer auditStatus,Date auditTime,String auditTaskCode,Long approvalMan) throws ServiceException;

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
	 * 删除营销活动
	 * @param id
	 * @throws ServiceException 
	 */
	public void delete(Long id,Long tenantId) throws ServiceException;
	
	/**
	 * 获取活动的活动编码
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public String getActivityCode(Long id) throws ServiceException;
	
	/**
	 * 查询可以使用中的规则
	 * @param searchCondition
	 * @return
	 */
	public Page<RuleGroupVO> findEngineExeRuleVOs(RuleSearchCondition searchCondition);
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
	 * @param marketingActivityVo 
	 * @param ruleGroup 组信息
	 * @param deleteIds  要删除的ids
	 * @param editRules 要编辑的规则
	 * @return 
	 */
	public Long saveActivity(MarketingActivityVO marketingActivityVO, String ruleGroup, String deleteIds,Long accountId) throws ServiceException;

	/**
	 * 分页查询营销活动
	 * @throws Exception 
	 */
	public Page pageListMarketingActivity(MarketingSearchVO searchVo, int pageNo, int pageSize) throws ServiceException;
	
	/**
	 * 分页查询营销活动(根据个人权限查询)
	 * @throws Exception 
	 */
	public Page pageListMarketingActivityByAccountAuth(MarketingSearchVO searchVo, int pageNo, int pageSize,Long accountId) throws ServiceException;
	
	/**
	 * 根据ID查询营销活动信息
	 * @param id
	 * @return
	 */
	public MarketingActivityVO getMarketingByIdForCopy(Long id);
	
//	/**
//	 * 启用/停用 营销活动
//	 */
//	public void enableOrDisable(Long id, ComAcctAccountVO account, String remark);
	
	/**
	 * 验证活动名称是否存在
	 */
	public boolean validateName(String name, Long id,String flag,Long tenantId);
	
	/**
	 * 修改活动
	 * @param id
	 */
	public void update(MarketingActivityVO marketingActivityVO);
	
	/**
	 * 根据CODE查询营销活动信息
	 */
	public MarketingActivityVO getMarketingByNo(String serNo);

	/**
	 * 规则测算
	 * @param ruleGroup
	 * @param facts
	 * @return
	 */
	String testRule(String ruleGroup, List<Object> facts);
	
	/**
	 * 根据活动状态查询类别
	 * @param status
	 * @return
	 */
	public List<MarketingActivityVO> queryByStatus(ActivityState activityState);
	
	/**
	 * 定时过期营销活动
	 */
	public void expiredMarketings();
	
}