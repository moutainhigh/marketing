package com.oristartech.rule.core.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.components.exception.RuleServerException;
import com.oristartech.marketing.core.exception.DataAccessRuntimeException;
import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateTimeUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.common.util.ListUtil;
import com.oristartech.rule.core.core.dao.IRuleExeStatisDao;
import com.oristartech.rule.core.core.service.IRuleExeStatisService;
import com.oristartech.rule.core.core.service.IRuleManagerService;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.search.RuleExeStatisCondition;
import com.oristartech.rule.vos.common.vo.DateRange;
import com.oristartech.rule.vos.core.enums.RuleExeStatisConType;
import com.oristartech.rule.vos.core.vo.RuleExeTimeResultVO;
import com.oristartech.rule.vos.core.vo.RuleExeTimeVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

@Component
@Transactional
public class RuleExeStatisServiceImpl extends RuleBaseServiceImpl implements IRuleExeStatisService {
	
	@Autowired
	private IRuleManagerService ruleManagerService;
	@Autowired
	private IRuleExeStatisDao ruleExeStatisDao ;

	public boolean lessThanConstraintAmount(RuleExeStatisCondition condition) {
		validateStatisCondition(condition);
		if(condition.getConstraintAmount() == null) {
			throw new ServiceRuntimeException("需要提供数量");
		}
		if(!isInTimeRange(getDateRanges(condition))){
			return false;
		}
		return sumExeTimes(condition) < condition.getConstraintAmount();
	}
	
	/**
	 * 统计执行次数
	 */
	public long sumExeTimes(RuleExeStatisCondition condition) {
		validateStatisCondition(condition);
		
		List<DateRange> dateRanges = getDateRanges(condition);
		if(!BlankUtil.isBlank(dateRanges)) {
			return ruleExeStatisDao.sumExeTimes(condition.getRuleId(), dateRanges);
		}
	    return 0;
	}
	
	public RuleExeTimeResultVO addExeTime(String ruleIdStrs, String amountStrs){
		validateAddParams(ruleIdStrs, amountStrs);
		List<Integer> ruleIds = ListUtil.str2List(ruleIdStrs, Integer.class);
		List<Integer> amounts = ListUtil.str2List(amountStrs, Integer.class);
		return addExeTime(ruleIds, amounts);
	}
	
	@Override
	public RuleExeTimeResultVO addExeTime(int ruleId, int amount){
		List<Integer> ruleIds = new ArrayList<Integer>();
		List<Integer> amounts = new ArrayList<Integer>();
		ruleIds.add(ruleId);
		amounts.add(amount);
		return this.addExeTime(ruleIds, amounts);
	}
	
	private boolean isInTimeRange(List<DateRange> dateRanges){
		Date start = dateRanges.get(0).getStart();
		Date end = dateRanges.get(0).getEnd();
		Date today = new Date();
        if((DateUtil.compareDateIgnoreTime(today,start) == 1 || DateUtil.compareDateIgnoreTime(today,start) == 0) 
        		&& (DateUtil.compareDateIgnoreTime(today,end) == -1 || DateUtil.compareDateIgnoreTime(today,end) == 0)){
        	return true;
        }else{
        	return false;
        }
	}
	
	public RuleExeTimeResultVO addExeTime(List<Integer> ruleIds, List<Integer> amounts){
		validateAddParams(ruleIds, amounts);
		
		RuleExeTimeResultVO result = new RuleExeTimeResultVO();
		result.setTotalRule(ruleIds.size());
		
		List<RuleVO> rules = ruleManagerService.getRuleByIds(ruleIds);
		
		if(ruleIds.size() != rules.size()){
			throw new ServiceRuntimeException("营销活动不存在");
		}
		
		Map<Integer, RuleExeTimeVO> idMap = new HashMap<Integer, RuleExeTimeVO>();
		for(int i=0 ; i < ruleIds.size() ; i++) {
			RuleExeTimeVO ruleExeTimeVo = new RuleExeTimeVO();
			//id 和amount是一一配对的
			Integer amount = amounts.get(i);
			Integer ruleId = ruleIds.get(i);
			ruleExeTimeVo.setRuleId(ruleId);
			ruleExeTimeVo.setCurrentAmount(amount);
			idMap.put(ruleId, ruleExeTimeVo);
		}
		
		for(Integer ruleId : idMap.keySet()) {
			for(RuleVO rule : rules){
				if(rule.getId().intValue() == ruleId.intValue()){
					//获取当前rule执行次数信息，并组装好匹配条件
					RuleExeTimeVO vo = idMap.get(ruleId);
					RuleExeStatisCondition condition = BizFunctionUtil.createStatCondition(rule);
					
					Integer totalAmount = condition == null ? 99999:condition.getConstraintAmount();
//					vo.setRuleVO(rule);
					vo.setTotalAmount(totalAmount);
					
					boolean inTimeRange = true;
					List<DateRange> dateRanges = new ArrayList<DateRange>();
					if(condition != null){
						dateRanges = getDateRanges(condition);
						vo.setStartTime(dateRanges.get(0).getStart());
						vo.setEndTime(dateRanges.get(0).getEnd());
						
			        	inTimeRange = isInTimeRange(dateRanges);
					}else{
						DateRange dr = new DateRange();
						dr.setStart(new Date());
						dr.setEnd(new Date());
						vo.setStartTime(dr.getStart());
						vo.setEndTime(dr.getEnd());
						dateRanges.add(dr);
					}
					int updateAmount = 0;
					try{
						//组装好时间后，进行更新操作，失败的返回
						updateAmount = ruleExeStatisDao.addExeTimes(vo.getCurrentAmount(), vo.getTotalAmount(), ruleId, new Date(), dateRanges,inTimeRange);
					
						vo.setUsedAmount((int)ruleExeStatisDao.sumExeTimes(ruleId, dateRanges));
					
					}catch(DataAccessRuntimeException e){
						vo.setReason(e.getMessage());
						result.addFalureAmount();
						result.addFalureRuleVO(vo);
						continue;
					}
					if(updateAmount == 0){
						vo.setRuleVO(null);
						throw new ServiceRuntimeException(JsonUtil.objToJson(vo));
//						vo.setReason("超过次数限制或超出活动时间范围");
//						result.addFalureAmount();
//						result.addFalureRuleVO(vo);
//						continue;
					}
					result.addSuccessAmount();
					result.addSuccessRuleVO(vo);
				}
			}
		}
		
		return result;
	}
	
	
	private List<DateRange> getDateRanges(RuleExeStatisCondition condition) {
		List<DateRange> dateRanges = new ArrayList<DateRange>();
		switch(condition.getConstraintDateType()) {
		//每天
		case perDay : DateRange range = new DateRange();
		              range.setStart(new Date());
		              range.setEnd(new Date());
		              dateRanges.add(range);
		              break;
		//每周
		case perWeek :  range = new DateRange();
						range.setStart(DateTimeUtil.getBeginDateOfWeek());
						range.setEnd(DateTimeUtil.getEndDateOfWeek());
				        dateRanges.add(range);
				        break;
	    //每月
		case perMonth : range = new DateRange();
						range.setStart(DateTimeUtil.getBeginDayOfMonth());
						range.setEnd(DateTimeUtil.getEndDayOfMonth());
				        dateRanges.add(range);
				        break;
	    //每年
		case perYear :  range = new DateRange();
						range.setStart(DateTimeUtil.getBeginDayOfYear());
						range.setEnd(DateTimeUtil.getEndDayOfYear());
				        dateRanges.add(range);
				        break;
	    //指定周期
		case appointTimeRange :
			            if(!BlankUtil.isBlank(condition.getAppointRunRestrainRange())) {
			            	List<List> appoints = JsonUtil.jsonArrayToList(condition.getAppointRunRestrainRange(), List.class);
			            	for(List list : appoints) {
			            		if(list.size() != 2) {
			            			throw new ServiceRuntimeException("指定日期范围格式不正确");
			            		}
			            		range = new DateRange();
			            		range.setStart(DateUtil.convertStrToDate(String.valueOf(list.get(0)), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
			            		range.setEnd(DateUtil.convertStrToDate(String.valueOf(list.get(1)), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
			            		dateRanges.add(range);
			            	}
			            }
			            break;
		}
		return dateRanges;
	}
	
	private void validateAddParams(String ruleIds, String amounts) {
		if(BlankUtil.isBlank(ruleIds)) {
			throw new ServiceRuntimeException("需要提供活动id");
		}
		if(BlankUtil.isBlank(amounts)) {
			throw new ServiceRuntimeException("需要提供执行次数");
		}
	}
	
	private void validateAddParams(List<Integer> ruleIds, List<Integer> amounts) {
		if(BlankUtil.isBlank(ruleIds)) {
			throw new ServiceRuntimeException("需要提供活动id");
		}
		if(BlankUtil.isBlank(amounts)) {
			throw new ServiceRuntimeException("需要提供执行次数");
		}
		if(ruleIds.size() != amounts.size() ) {
			throw new ServiceRuntimeException("活动id数量和次数数量必须一致");
		}
	}
	
	private void validateStatisCondition(RuleExeStatisCondition condition) {
		if(condition == null) {
			throw new ServiceRuntimeException("需要提供查询条件");
		}
		if(condition.getRuleId() == null) {
			throw new ServiceRuntimeException("需要提供活动id");
		}
		if(condition.getConstraintDateType() == null 
			|| (condition.getConstraintDateType() != null 
			   && condition.getConstraintDateType().equals(RuleExeStatisConType.appointTimeRange) 
			   && BlankUtil.isBlank(condition.getAppointRunRestrainRange()))) {
			throw new ServiceRuntimeException("需要提供约束类型");
		}
	}
	
}
