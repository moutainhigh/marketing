package com.oristartech.marketing.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.oristartech.marketing.service.IRuleExeStatisService;
import com.oristartech.rule.facade.IRuleExeStatisFacade;
import com.oristartech.rule.search.RuleExeStatisCondition;
import com.oristartech.rule.vos.core.vo.RuleExeTimeResultVO;

@Service
public class RuleExeStatisServiceImpl implements IRuleExeStatisService {

	@Reference(version = "1.0.0")
	IRuleExeStatisFacade ruleExeStatisFacade;
	
	@Override
	public long sumExeTimes(RuleExeStatisCondition condition) {
		return ruleExeStatisFacade.sumExeTimes(condition);
	}

	@Override
	public boolean lessThanConstraintAmount(RuleExeStatisCondition condition) {
		return ruleExeStatisFacade.lessThanConstraintAmount(condition);
	}

	@Override
	public RuleExeTimeResultVO addExeTime(List<Integer> ruleIds, List<Integer> amounts) {
		return ruleExeStatisFacade.addExeTime(ruleIds, amounts);
	}

	@Override
	public RuleExeTimeResultVO addExeTime(String ruleIds, String amounts) {
		return ruleExeStatisFacade.addExeTime(ruleIds, amounts);
	}

	@Override
	public RuleExeTimeResultVO addExeTime(int ruleId, int amount) {
		return ruleExeStatisFacade.addExeTime(ruleId, amount);
	}

}
