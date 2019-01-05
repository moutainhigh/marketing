package com.oristartech.rule.core.init.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.marketing.core.exception.DaoRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.core.dao.IRuleTypeDao;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.init.dao.IRuleInitDao;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.enums.RuleRunMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;

@Repository
public class RuleInitDaoImpl extends RuleBaseDaoImpl<RuleGroup, Long> implements IRuleInitDao{

	@Autowired
	private IRuleTypeDao ruleTypeDao;
	
	@Override
	public Page findEngineExeRuleVOs(RuleSearchCondition searchCondition) {
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(" WHERE 1=1 ");
		sqlWhere.append("  AND ((rg.VALID_DATE_END is not null) AND rg.VALID_DATE_END >= NOW() ) ");
		sqlWhere.append("  AND rg.status= " + RuleStatus.RUNNING.ordinal());
		
		List<String> types = ruleTypeDao.getNamesByRunMode(RuleRunMode.DROOLS);
		if(!BlankUtil.isBlank(types)){
			String type = "'"+ types.stream().collect(Collectors.joining("','")) + "'";
			sqlWhere.append(" AND rg.RULE_TYPE IN ("+type+") ");
		}
		StringBuffer sqlTableMA = new StringBuffer();
		sqlTableMA.append(" RIGHT JOIN marketing_activity activity ON activity.RULE_GROUP_ID = rg.ID ");
		StringBuffer sqlTableMMA = new StringBuffer();
		sqlTableMMA.append(" RIGHT JOIN member_marketing_activity activity ON activity.RULE_GROUP_ID = rg.ID ");
		StringBuffer sqlTableCAA = new StringBuffer();
		sqlTableCAA.append(" RIGHT JOIN coupon_active_apply activity ON activity.RULEID = rg.ID ");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT ");
		sql.append(" 		activity.TENANT_ID rg_tenantId, ");
		sql.append(" 		rg.id rg_id, ");
		sql.append(" 		rg.CREATE_DATE rg_createDate, ");
		sql.append(" 		rg.EXECUTE_MODE rg_executeMode, ");
		sql.append(" 		rg.MODIFY_DATE rg_modifyDate, ");
		sql.append(" 		rg.NAME rg_name, ");
		sql.append(" 		rg.PRIORITY rg_priority, ");
		sql.append(" 		rg.RELATE_BUSINESS_CODE rg_bizOrderCode, ");
		sql.append(" 		rg.REMARK rg_remark, ");
		sql.append(" 		rg.RULE_TYPE rg_ruleType, ");
		sql.append(" 		rg.STATUS rg_status ,");
		sql.append(" 		rg.TEMPLATE_ID rg_templateId, ");
		sql.append(" 		rg.VALID_DATE_END rg_validDateEnd, ");
		sql.append(" 		rg.VALID_DATE_START rg_validDateStart ");
		sql.append(" FROM rule_core_rule_group rg ");
		
		StringBuffer sqlTotal = new StringBuffer();
		sqlTotal.append(sql);
		sqlTotal.append(sqlTableMA);
		sqlTotal.append(sqlWhere);
		sqlTotal.append(" UNION ALL ");
		sqlTotal.append(sql);
		sqlTotal.append(sqlTableMMA);
		sqlTotal.append(sqlWhere);
		sqlTotal.append(" UNION ALL ");
		sqlTotal.append(sql);
		sqlTotal.append(sqlTableCAA);
		sqlTotal.append(sqlWhere);
		
		if(searchCondition != null) {
			int pageNo = (searchCondition.getStart() / searchCondition.getLimit()) + 1;
			
			try {
				return queryLabelMapPageBySql(sqlTotal.toString(), pageNo, searchCondition.getLimit(), null);
				
			} catch (Exception e) {
				throw new DaoRuntimeException(e.getMessage());
			}
//			if(page != null && !BlankUtil.isBlank(page.getResult())) {
//				List<RuleGroupVO> groupVos = convertRuleGroupVO(page.getResult());
//				Page<RuleGroupVO> pageVo = new Page<RuleGroupVO>(page.getStartIndex(), page.getTotalCount(), page.getPageSize(), groupVos);
//				return pageVo;
//			}
		}
		
	    return null;
	}

}
