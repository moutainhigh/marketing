package com.oristartech.marketing.service;

import java.util.List;

import com.oristartech.rule.search.RuleExeStatisCondition;
import com.oristartech.rule.vos.core.vo.RuleExeTimeResultVO;

public interface IRuleExeStatisService {

	/**
	 * 获取指定规则指定约束的执行次数
	 * @param RuleExeStatisCondition condition
	 * @return
	 */
	public long sumExeTimes(RuleExeStatisCondition condition) ;

	/**
	 * 指定规则在指定时间是否小于指定次数
	 * @param RuleExeStatisCondition condition
	 * @return
	 */
	public boolean lessThanConstraintAmount(RuleExeStatisCondition condition) ;
	
	/**
	 * 指定规则列表,当前日期增加执行次数 id的list必须和amount的list一一对应
	 * 处理过程
	 * 1.根据规则id把规则查出来，统计规则执行次数的实效与规则总次数
	 * 2.根据规则实效区间与执行总次数在RuleExeStatis中查询此规则执行的次数
	 * 3.在总次数内的进行更新执行次数操作，超过执行总次数的返回实体
	 * @param ruleIds
	 * @param amounts 要添加的数量 list 
	 * @return 
	 */
	public RuleExeTimeResultVO addExeTime(List<Integer> ruleIds, List<Integer> amounts);
	
	/**
	 * 指定规则列表,当前日期增加执行次数 id的list必须和amount的list一一对应
	 * @param ruleIds ruleid列表, 用","号隔开的字符串
	 * @param amounts 数量列表, 用","号隔开的字符串
	 * @return 
	 */
	public RuleExeTimeResultVO addExeTime(String ruleIds, String amounts);
	
	/**
	 * 指定规则列表,当前日期增加执行次数 id, 和次数
	 * @param ruleId
	 * @param amount 
	 */
	public RuleExeTimeResultVO addExeTime(int ruleId, int amount);
}
