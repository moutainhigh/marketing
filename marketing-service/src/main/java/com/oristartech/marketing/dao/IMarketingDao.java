package com.oristartech.marketing.dao;

import java.util.List;

import com.oristartech.api.exception.DaoException;
import com.oristartech.marketing.model.MarketingActivity;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;


public interface IMarketingDao extends IMarketingBaseDao<MarketingActivity, Long> {
	
	/**
	 * 根据租户ID查询真正执行中的规则，初始化规则用
	 * @param searchCondition
	 * @return
	 */
	public Page<RuleGroupVO> findEngineExeRuleVOs(RuleSearchCondition searchCondition);

	public Page pageListMarketingActivityByAccountAuth(MarketingSearchVO searchVo, 
			int pageNo, int pageSize) throws DaoException;
	
	/**
	 * 验证活动名称是否存在
	 */
	public List<MarketingActivity> validateName(String name, Long id,Long tenantId);
	
	public MarketingActivity getActivity(Long tenantId, Long id);
	
	/**
	 * 获取活动的活动编码
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public String getActivityCode(Long id) throws DaoException;
}
