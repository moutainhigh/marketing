package com.oristartech.rule.core.core.dao;

import java.util.Date;
import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.core.model.RuleExeStatis;
import com.oristartech.rule.vos.common.vo.DateRange;

/**
 * 规则执行统计dao
 * @author chenjunfei
 *
 */
public interface IRuleExeStatisDao extends IRuleBaseDao<RuleExeStatis, Long> {
	/**
	 * 统计指定规则指定范围内执行次数
	 * @param ruleId
	 * @param dateRanges 日期组 list, 每组有start, end
	 * @return
	 */
	public long sumExeTimes(Integer ruleId, List<DateRange> dateRanges);

	/**
	 * 批量查询规则执行次数
	 * @param ruleIds
	 * @param date
	 * @return
	 */
	public List<RuleExeStatis> getRuleExeStatis(List<Integer> ruleIds, Date date);
	
	/**
	 * 指定规则,指定日期,添加指定次数
	 * @param amount
	 * @param List<Integer> ruleIds
	 * @param date
	 */
	public void addExeTimes(int amount, List<Integer> ruleIds, Date date);
	
	/**
	 * 指定规则,指定日期,添加指定次数
	 * @param amount
	 * @param List<Integer> ruleIds
	 * @param date
	 */
	public Integer addExeTimes(Integer amount,Integer totalamount,Integer ruleId, Date date,List<DateRange> dateRanges,boolean inTimeRange);
	
	/**
	 * 根据规则id,日期获取统计类
	 * @param ruleId
	 * @param date
	 * @return
	 */
	public RuleExeStatis getByRuleAndDate(Integer ruleId, Date date);
}	
