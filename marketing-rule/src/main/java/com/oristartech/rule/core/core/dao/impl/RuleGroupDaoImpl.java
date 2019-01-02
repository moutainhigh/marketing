package com.oristartech.rule.core.core.dao.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.core.core.dao.IRuleGroupDao;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.core.model.RuleType;
import com.oristartech.rule.vos.core.enums.RuleStatus;

@Repository
public class RuleGroupDaoImpl extends RuleBaseDaoImpl<RuleGroup, Integer> implements IRuleGroupDao {
	private Logger log = LoggerFactory.getLogger(RuleGroupDaoImpl.class);
	
	public Integer updateStatus(Integer id, RuleStatus statusEm) {
	    String hql = "update RuleGroup rg set rg.status = ? , rg.modifyDate=? where rg.id = ?";
	    int g = super.executeSaveOrUpdate(hql, statusEm, new Date(), id);
	    if(g <= 0) {
	    	log.warn("无法更新规则组, 可能不存在, id=" + id);
	    	return 0;
	    }
	    hql = "update Rule r set r.status = ? , r.modifyDate = ? where r.ruleGroup.id = ?";
	    int r = super.executeSaveOrUpdate(hql, statusEm, new Date(), id);
	    if(r <= 0) {
	    	log.warn("无法更新规则组中规则, groud id=" + id);
	    }
	    return g;
	}
	
	public void deleteGroup(Integer groupId) {
		String hql = "DELETE Rule r where r.ruleGroup.id  = ?";
		Integer count = super.executeSaveOrUpdate(hql, groupId);
		if(count > 0) {
			hql = "DELETE RuleSection rs where rs.ruleGroup.id =? ";
			super.executeSaveOrUpdate(hql, groupId);
			hql = "DELETE RuleGroup rg where rg.id = ? ";
			super.executeSaveOrUpdate(hql, groupId);
		}
	}
	
	public String getRuleTypeName(Integer groupId) {
		super.setCacheQueries(true);
	    String hql = "SELECT rg.ruleType FROM RuleGroup rg where rg.id = ?";
	    String result = (String)super.uniqueResult(hql, groupId);
	    super.setCacheQueries(false);
	    return result;
	}
	
	public RuleType getRuleType(Integer groupId) {
		super.setCacheQueries(true);
		
		String hql = "SELECT rt FROM RuleType rt, RuleGroup rg where rt.name = rg.ruleType and rg.id = ?";
		RuleType result = (RuleType)super.uniqueResult(hql, groupId);
	    
		super.setCacheQueries(false);
		return result;
	}
	
	public boolean existRuleGroup(Integer groupId) {
		 String hql = "select count(rg.id) FROM RuleGroup rg where rg.id= ? ";
		 Long count = (Long)super.uniqueResult(hql, groupId);
		 return count != null && count > 0;
	}
	
	public void modifyValidDate(Integer groupId, Date start, Date end) {
	    String hql = "update RuleGroup rg set rg.validDateStart= ?, rg.validDateEnd= ? where rg.id=? ";
	    int g = super.executeSaveOrUpdate(hql, start, end, groupId);
	    if(g <= 0) {
	    	log.warn("无法更新规则组, 可能不存在, groud id=" + groupId);
	    }
	    hql = "update Rule r set r.validDateStart = ? , r.validDateEnd = ? where r.ruleGroup.id = ?";
	    int r = super.executeSaveOrUpdate(hql, start, end, groupId);
	    if(r <= 0) {
	    	log.warn("无法更新规则组中规则, groud id=" + groupId);
	    }
	}
	
	public void updateBizCode(Integer groupId, String relateBizCode) {
		String hql = "update RuleGroup rg set rg.relateBusinessCode = ? where rg.id = ? ";
		super.executeSaveOrUpdate(hql, new Object[]{ relateBizCode, groupId});
		hql = "update Rule r set r.businessCode = ? where r.ruleGroup.id = ? ";
		super.executeSaveOrUpdate(hql, new Object[]{relateBizCode, groupId});
	}
	
	public RuleStatus getRuleGroupStatus(Integer groupId) {
	    String hql = "select rg.status from  RuleGroup rg where rg.id = ?" ;
	    RuleStatus status = (RuleStatus)super.uniqueResult(hql, groupId);
	    return status;
	}
}	
