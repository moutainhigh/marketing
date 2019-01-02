package com.oristartech.rule.core.core.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.marketing.core.exception.DaoRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.core.dao.IRuleConditionDao;
import com.oristartech.rule.core.core.dao.IRuleTypeDao;
import com.oristartech.rule.core.core.model.RuleCondition;
import com.oristartech.rule.core.core.model.RuleConditionElement;
import com.oristartech.rule.vos.core.enums.RuleRunMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;

@Repository
public class RuleConditionDaoImpl extends RuleBaseDaoImpl<RuleCondition, Long> implements IRuleConditionDao {
	
	@Autowired
	private IRuleTypeDao ruleTypeDao;
	
	public void setRuleTypeDao(IRuleTypeDao ruleTypeDao) {
    	this.ruleTypeDao = ruleTypeDao;
    }

	/**
	 * 加载所有包含动态加载属性的规则条件分类. 只查询又规则引擎执行的,并且执行中的规则条件
	 * map的key是 modelfield service name
	 */
	public Map<String, List<RuleCondition>> searchAllDynamicConditions(String conditionCategory) {
		List<RuleCondition> allConditions = new ArrayList<RuleCondition>();
		List<RuleCondition> inCommon = searchDynamicConInCommonSection(conditionCategory);
		List<RuleCondition> inRule = searchDynamicConInRuleSection(conditionCategory);
		Map<String, List<RuleCondition>> conditionMaps = new HashMap<String, List<RuleCondition>>();
		if(!BlankUtil.isBlank(inCommon)) {
			allConditions.addAll(inCommon);
		}
		if(!BlankUtil.isBlank(inRule)) {
			allConditions.addAll(inRule);
		}
		if(!BlankUtil.isBlank(allConditions)) {
			for(RuleCondition con : allConditions) {
				String key = getDynamicServiceName(con);
				List<RuleCondition> sourceList = conditionMaps.get(key);
				if(sourceList == null) {
					conditionMaps.put(key, new ArrayList<RuleCondition>());
				}
				if(!hasSameCondition(con, conditionMaps.get(key))) {
					conditionMaps.get(key).add(con);
				}
			}
		}
		return conditionMaps;
	}
	
	/**
	 * 是否有相同的条件
	 * @param con
	 * @param exists
	 * @return
	 */
	private boolean hasSameCondition(RuleCondition con, List<RuleCondition> exists) {
		if(!BlankUtil.isBlank(exists)) {
			for(RuleCondition exist : exists ) {
				if(con.getModelCategory().getName().equals(exist.getModelCategory().getName())) {
					List<RuleConditionElement> sourceEles = exist.getConditionElements();
					List<RuleConditionElement> destEles = con.getConditionElements();
					if(isSameConEles(sourceEles, destEles) == true) {
						return true;
					}
				}
			}
		}
		return false;
	}
	 
	private boolean isSameConEles(List<RuleConditionElement> sourceEles, List<RuleConditionElement> destEles) {
		if(sourceEles.size() != destEles.size()) {
			return false;
		}
		for(int i = 0; i < sourceEles.size(); i++) {
			ModelField sourceField = sourceEles.get(i).getModelField();
			ModelField destField = destEles.get(i).getModelField();
			String sourceValue = sourceEles.get(i).getOperand();
			String destValue = destEles.get(i).getOperand();
			//
			if(sourceField.getIsInDynamicCondition() != null && sourceField.getIsInDynamicCondition() == true ) {
				continue;
			}
			if(!sourceField.getName().equals(destField.getName()) 
			  || (sourceValue != null && !sourceValue.equals(destValue))
			  || (destValue != null && !destValue.equals(sourceValue))) {
				return false;
			}
		}
		return true;
	}
	
	private String getDynamicServiceName(RuleCondition con) {
		if(con != null) {
			List<RuleConditionElement> destEles = con.getConditionElements();
			if(!BlankUtil.isBlank(destEles)) {
				for(RuleConditionElement ele : destEles) {
					if(ele.getModelField().getIsInDynamicCondition() != null 
					   && ele.getModelField().getIsInDynamicCondition() == true) {
						if(BlankUtil.isBlank(ele.getModelField().getLoadServiceName())) {
							throw new DaoRuntimeException("please set dynamic load data service name in model field!");
						}
						return ele.getModelField().getLoadServiceName();
					}
				}
			}
		}
		return null;
	}
	//优化为一条sql, 直接查询出条件
	/**
	 * 查询公共条件中的动态条件
	 * @return
	 */
	private List<RuleCondition> searchDynamicConInCommonSection(String conditionCategory) {
		StringBuilder sb = new StringBuilder(" select DISTINCT rc " +
		        " from RuleCondition rc " +
		        " JOIN rc.ruleSection rsection " +
		        " JOIN rsection.ruleGroup rg " +
		        " JOIN rc.conditionElements rccon " +
		        " JOIN rccon.modelField mf " +
		        " WHERE rg.ruleType in (:ruleType) " + 
		        " AND rg.status = :status " +
		        " AND rg.validDateStart <= :now " +
		        " AND rg.validDateEnd >= :now " +
		        " AND mf.isInDynamicCondition = true " +
		        " AND (mf.isDelete IS NULL or mf.isDelete = false) " +
		        " AND mf.queryKeyFieldNames like :queryCatName"
		        );
		
		Map<String, Object> params = getSearchDyConParams(conditionCategory);
		super.setCacheQueries(true);
		List<RuleCondition> cons = (List<RuleCondition>) super.findByNamedParam(sb.toString(), params);
		super.setCacheQueries(false);
		return cons;
		
	}
	
	/**
	 * 查询规则条件中的动态条件
	 * @return
	 */
	private List<RuleCondition> searchDynamicConInRuleSection(String conditionCategory) {
		
		StringBuilder sb = new StringBuilder(" select DISTINCT rc " +
		        " from RuleCondition rc " +
		        " JOIN rc.ruleSection rsection " +
		        " JOIN rsection.rule rule" +
		        " JOIN rc.conditionElements rccon " +
		        " JOIN rccon.modelField mf " +
		        " where rule.ruleType in (:ruleType) " +
		        " AND rule.status = :status" +
		        " AND rule.validDateStart <= :now " +
		        " AND rule.validDateEnd >= :now " +
		        " AND mf.isInDynamicCondition = true " +
		        " AND (mf.isDelete IS NULL or mf.isDelete = false) " +
		        " AND mf.queryKeyFieldNames like :queryCatName" );
		
		Map<String, Object> params = getSearchDyConParams(conditionCategory);
		super.setCacheQueries(true);
		List<RuleCondition> result =  (List<RuleCondition>) super.findByNamedParam(sb.toString(), params);
		super.setCacheQueries(false);
		return result;
	}
	
	private Map<String, Object> getSearchDyConParams(String conditionCategory) {
		String queryCatName = MYSQL_LIKE_SPLITER + ModelField.composeQueryCategoryPrefix(conditionCategory) + MYSQL_LIKE_SPLITER;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ruleType", ruleTypeDao.getNamesByRunMode(RuleRunMode.DROOLS));
		String dateStr = DateUtil.convertDateToStr(new Date(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		//只需要当天的年月日
		Date nowDate = DateUtil.convertStrToDate(dateStr, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		params.put("now", nowDate);
		params.put("status", RuleStatus.RUNNING);
		params.put("queryCatName", queryCatName);
		return params;
	}
}
