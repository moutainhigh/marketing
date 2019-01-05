package com.oristartech.marketing.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.api.exception.DaoException;
import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.marketing.core.exception.DaoRuntimeException;
import com.oristartech.marketing.dao.IMarketingDao;
import com.oristartech.marketing.model.MarketingActivity;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.core.dao.IRuleTypeDao;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.enums.RuleRunMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;

@Repository
public class MarketingDaoImpl extends RuleBaseDaoImpl<MarketingActivity, Long> implements IMarketingDao {

	/**
	 * 分页查询营销活动(根据个人权限查询)
	 * @throws Exception 
	 */
	public Page pageListMarketingActivityByAccountAuth(MarketingSearchVO searchVo, int pageNo, int pageSize) throws DaoException{
		try{	
			List<Object> params = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT DISTINCT ");
			sql.append(" 		activity.ID id, ");
			sql.append(" 		activity.ACTIVITY_CODE activityCode, ");
			sql.append(" 		activity.ACTIVITY_NAME activityName, ");
			sql.append(" 		activity.ACTIVITY_STATE activityState, ");
			sql.append(" 		activity.APPROVAL_RESULT approvalResult, ");
			sql.append(" 		activity.CREATER_ID createrName, ");
//			sql.append(" 		area.AREA_NAME createrAreaName, ");
			sql.append(" 		activity.CREATE_TIME createTime, ");
			sql.append(" 		activity.APPROVALMAN_ID approvalmanName, ");
			sql.append(" 		activity.VALID_DATE_START validDateStart, ");
			sql.append(" 		activity.VALID_DATE_END validDateEnd ");
			sql.append(" FROM MARKETING_ACTIVITY activity ");
//			sql.append(" LEFT JOIN COM_ACCT_ACCOUNT creator ON activity.CREATER_ID = creator.ID ");
//			sql.append(" LEFT JOIN ACCOUNT_AREA aarls ON activity.CREATER_ID = aarls.ACCOUNT_ID ");
//			sql.append(" LEFT JOIN CINAREA area ON aarls.PARENT_ID = area.AREA_ID ");
//			sql.append(" LEFT JOIN COM_ACCT_ACCOUNT apploval ON activity.APPROVALMAN_ID = apploval.ID ");
			sql.append(" LEFT JOIN MARKETING_ACTIVITY_BUSINESS_MANAGENMENT ab ON activity.ID = ab.ACTIVITY_ID ");
			sql.append(" WHERE 1=1 ");
		
//		//判断是否首次加载页面，是的话过滤作废订单
//		if(searchVo.getAutoShow()==null || "1".equals(searchVo.getAutoShow())){
//			sql.append(" and activity.ACTIVITY_STATE != '5' ");
//		}
//			if(!BlankUtil.isBlank(accountId)){
//				sql.append(" AND (ab.ID IS NULL OR ab.BUSINESS_CODE IN (SELECT c.CINEMA_CODE FROM COM_AUTH2_ACCOUNT_CIN_RLS acr LEFT JOIN CINEMA_CINEMA c ON acr.CIN_ID = c.ID WHERE acr.ACCOUNT_ID = ?)) ");
//				params.add(accountId);
//			}
			if(!BlankUtil.isBlank(searchVo.getSearchBusinessCode())){
				String businessCode = "'"+ Arrays.asList(searchVo.getSearchBusinessCode().split(",")).stream().collect(Collectors.joining("','")) + "'";
				sql.append(" AND ab.BUSINESS_CODE IN ("+businessCode+") ");
			}
			if(!BlankUtil.isBlank(searchVo.getSearchActivityName())){
				sql.append(" AND activity.ACTIVITY_NAME LIKE ? ");
				params.add("%" + filterSign(searchVo.getSearchActivityName()) + "%");
			}
			if(!BlankUtil.isBlank(searchVo.getSearchValidDateStart())){
				sql.append(" AND activity.VALID_DATE_START >= ? ");
				params.add(DateUtil.parseStrToDate(searchVo.getSearchValidDateStart()));
			}
			if(!BlankUtil.isBlank(searchVo.getSearchValidDateEnd())){
				sql.append(" AND activity.VALID_DATE_END <= ? ");
				params.add(DateUtil.parseStrToDate(searchVo.getSearchValidDateEnd()));
			}
			if(!BlankUtil.isBlank(searchVo.getSearchState())){
				sql.append(" AND activity.ACTIVITY_STATE IN("+searchVo.getSearchState()+") ");
			}
			if(!BlankUtil.isBlank(searchVo.getSearchCreaterId())){
				sql.append(" AND activity.CREATER_ID = ? ");
				params.add(searchVo.getSearchCreaterId());
			}
//			if(!BlankUtil.isBlank(searchVo.getSearchCreaterArea())){
//				sql.append(" AND area.AREA_NAME = ? ");
//				params.add(filterSign(searchVo.getSearchCreaterArea()) );
//			}
			if(!BlankUtil.isBlank(searchVo.getSearchApprovalmanId())){
				sql.append(" AND activity.APPROVALMAN_ID = ? ");
				params.add(searchVo.getSearchApprovalmanId());
			}
			if(!BlankUtil.isBlank(searchVo.getTenantId())){
				sql.append(" AND activity.TENANT_ID = ? ");
				params.add(searchVo.getTenantId());
			}
			
			sql.append(" ORDER BY activity.ID DESC ");

		
			return queryLabelMapPageBySql(sql.toString(), pageNo, pageSize, params.toArray());
		} catch (Exception e) {
			throw new DaoRuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 转换模糊查询的百分号
	 * @param param
	 * @return
	 */
	public String filterSign(String param){
		if(param != null){
			return param.replaceAll("%", "||%");
		}
		return null;
	}
	
	/**
	 * 验证活动名称是否存在
	 */
	public List<MarketingActivity> validateName(String name, Long id,Long tenantId){
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder();
		hql.append(" from MarketingActivity m where 1=1 AND m.activityState!='5' ");	
		if (id != null){
			hql.append(" and m.id != ? ");
			params.add(id);
		}
		if (StringUtils.isNotBlank(name)){
			hql.append(" and m.activityName = ? ");
			params.add(name);

		}
		if (tenantId != null){
			hql.append(" and m.tenantId = ? ");
			params.add(tenantId);

		}
		return executeFindHql(hql.toString(), params);
	}
	
	public MarketingActivity getActivity(Long tenantId, Long id){
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder();
		hql.append(" from MarketingActivity m where 1=1 ");	
		if (id != null){
			hql.append(" and m.id = ? ");
			params.add(id);
		}
		if (tenantId != null){
			hql.append(" and m.tenantId = ? ");
			params.add(tenantId);

		}
		return executeFindHql(hql.toString(), params).get(0);
	}
	
	public String getActivityCode(Long id) throws DaoException{
		if(BlankUtil.isBlank(id))
			throw new DaoException(BizExceptionEnum.MKT_MISSING_ID);
		return get(id).getActivityCode();
	}
}
