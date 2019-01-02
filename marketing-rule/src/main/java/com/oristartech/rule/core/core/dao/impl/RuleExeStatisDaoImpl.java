package com.oristartech.rule.core.core.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.core.dao.IRuleExeStatisDao;
import com.oristartech.rule.core.core.model.RuleExeStatis;
import com.oristartech.rule.vos.common.vo.DateRange;

@Repository
public class RuleExeStatisDaoImpl extends RuleBaseDaoImpl<RuleExeStatis, Long> implements IRuleExeStatisDao {
	public long sumExeTimes(Integer ruleId, List<DateRange> dateRanges) {
		StringBuilder hql = new StringBuilder("SELECT sum(res.amount) FROM RuleExeStatis res WHERE res.ruleId = ? AND ( ");
		List<Object> params = new ArrayList<Object>();
		List<String> betweens = new ArrayList<String>();
		params.add(ruleId);
		for(DateRange dateRange : dateRanges) {
			betweens.add(" res.exeDate BETWEEN Date(?) AND Date(?) ");
			params.add(dateRange.getStart());
			params.add(dateRange.getEnd());
		}
		hql.append(StringUtils.join(betweens, " OR "));
		hql.append(" ) "); 
		Long amount = (Long)uniqueResult(hql.toString(), params);
	    return amount == null ? 0 : amount;
	}
	
	public List<RuleExeStatis> getRuleExeStatis(List<Integer> ruleIds, Date date){
		String hql = "SELECT res FROM RuleExeStatis res WHERE res.ruleId in (:ruleIds) AND res.exeDate = DATE(:date) ORDER BY res.id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ruleIds", ruleIds);
		params.put("date", date);
		return (List<RuleExeStatis> )findByNamedParam(hql, params);
	}
	
	public Integer addExeTimes(Integer amount,Integer totalamount,Integer ruleId, Date date,List<DateRange> dateRanges,boolean inTimeRange){
		List<Integer> ruleIds = new ArrayList<Integer>();
		ruleIds.add(ruleId);
		List<RuleExeStatis> statis = getRuleExeStatis(ruleIds,date);
		
		//已经存在,则修改, 否则新增
		List<Long> existIds = new ArrayList<Long>();
		
		if(!BlankUtil.isBlank(statis)) {
			//只更新第一条数据
			RuleExeStatis stat = statis.get(0);
			existIds.add(stat.getId());
			if(ruleIds.contains(stat.getRuleId())) {
				ruleIds.remove(stat.getRuleId());
			}
			//如果有多条，删除除了第一条之外的数据,并且把计算第一条之外的数据调用次数总和并把次数加给第一条数据
			if(statis.size() > 1){
				statis.remove(stat);
				int rest = 0;
				for(RuleExeStatis st:statis){
					deleteById(st.getId());
					rest = rest + st.getAmount();
				}
				String hql = "UPDATE RULE_CORE_EXE_STATIS T SET T.AMOUNT = T.AMOUNT + ? WHERE T.ID = ?  ";
				List<Object> params = new ArrayList<Object>();
				params.add(rest);
				params.add(stat.getId());
				executeSqlUpdate(hql, params);
			}
		}
				
		if(!BlankUtil.isBlank(existIds)) {
			return updateExeTimes(amount, totalamount, ruleId, date, dateRanges, existIds);
		}
		
		if(!BlankUtil.isBlank(ruleIds) && inTimeRange) {
			//ruleIds 永远只有一条数据。
			//数据库没有数据时候，先insert一条次数为0的的数据，再进行update
			RuleExeStatis stat = new RuleExeStatis();
			stat.setAmount(0);
			stat.setExeDate(date);
			stat.setRuleId(ruleId);
			RuleExeStatis res = saveOrUpdate(stat);
			
			existIds.add(res.getId());
			return updateExeTimes(amount, totalamount, ruleId, date, dateRanges, existIds);
		}
		return 0;
	}
	
	private Integer updateExeTimes(Integer amount,Integer totalamount,Integer ruleId, Date date,List<DateRange> dateRanges,List<Long> existIds){
		String hql = "UPDATE RULE_CORE_EXE_STATIS T SET T.AMOUNT = T.AMOUNT + ? "
				+ " WHERE T.ID = ? AND (SELECT AT FROM ("
				+ " SELECT SUM(RES.AMOUNT) AS AT FROM RULE_CORE_EXE_STATIS RES "
				+ " WHERE RES.RULE_ID = ? AND ( RES.EXE_DATE BETWEEN DATE(?) AND DATE(?) ) ) TEMP"
				+ " ) + ? <= ? AND ( DATE(?) BETWEEN DATE(?) AND DATE(?))";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<Object> params = new ArrayList<Object>();
		params.add(amount);
		params.add(existIds.get(0));
		params.add(ruleId);
		params.add(formatter.format(dateRanges.get(0).getStart()));
		params.add(formatter.format(dateRanges.get(0).getEnd()));
		params.add(amount);
		params.add(totalamount);
		params.add(formatter.format(date));
		params.add(formatter.format(dateRanges.get(0).getStart()));
		params.add(formatter.format(dateRanges.get(0).getEnd()));
		return executeSqlUpdate(hql, params);
	}
	
	public void addExeTimes(int amount, List<Integer> ruleIds, Date date) {
		List<RuleExeStatis> statis = getRuleExeStatis(ruleIds,date);
		//已经存在,则修改, 否则新增
		List<Long> existIds = new ArrayList<Long>();
		
		if(!BlankUtil.isBlank(statis)) {
			for(RuleExeStatis stat : statis) {
				existIds.add(stat.getId());
				if(ruleIds.contains(stat.getRuleId())) {
					ruleIds.remove(stat.getRuleId());
				}
			}
		}
		
		if(!BlankUtil.isBlank(existIds)) {
			String hql = "UPDATE RuleExeStatis res SET res.amount = res.amount + :amount WHERE res.id in (:existIds) ";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amount", amount);
			params.put("existIds", existIds);
			executeSaveOrUpdate(hql, params);
		}
		
		if(!BlankUtil.isBlank(ruleIds)) {
			List<RuleExeStatis> saves = new ArrayList<RuleExeStatis>();
			for(Integer ruleId : ruleIds) {
				RuleExeStatis stat = new RuleExeStatis();
				stat.setAmount(amount);
				stat.setExeDate(date);
				stat.setRuleId(ruleId);
				saves.add(stat);
			}
			batchSaveOrUpdate(saves);
		}
		
	}
	
	public RuleExeStatis getByRuleAndDate(Integer ruleId, Date date) {
		String hql = "SELECT res FROM RuleExeStatis res WHERE res.ruleId = ? AND res.exeDate = date(?)";
	    return (RuleExeStatis)uniqueResult(hql, new Object[]{ruleId, date});
	}
}
