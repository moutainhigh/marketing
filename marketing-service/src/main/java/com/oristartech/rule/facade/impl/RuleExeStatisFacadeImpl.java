package com.oristartech.rule.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.oristartech.rule.core.core.service.IRuleExeStatisService;
import com.oristartech.rule.facade.IRuleExeStatisFacade;
import com.oristartech.rule.search.RuleExeStatisCondition;
import com.oristartech.rule.vos.core.vo.RuleExeTimeResultVO;

@Service(version = "1.0.0", interfaceClass = IRuleExeStatisFacade.class)
public class RuleExeStatisFacadeImpl implements IRuleExeStatisFacade {

	@Autowired
	IRuleExeStatisService ruleExeStatisService;
	
	@Override
	public long sumExeTimes(RuleExeStatisCondition condition) {
		return ruleExeStatisService.sumExeTimes(condition);
	}

	@Override
	public boolean lessThanConstraintAmount(RuleExeStatisCondition condition) {
		return ruleExeStatisService.lessThanConstraintAmount(condition);
	}

	@Override
	public RuleExeTimeResultVO addExeTime(List<Integer> ruleIds, List<Integer> amounts) {
		return ruleExeStatisService.addExeTime(ruleIds, amounts);
	}

	@Override
	public RuleExeTimeResultVO addExeTime(String ruleIds, String amounts) {
		return ruleExeStatisService.addExeTime(ruleIds, amounts);
	}

	@Override
	public RuleExeTimeResultVO addExeTime(int ruleId, int amount) {
		return ruleExeStatisService.addExeTime(ruleId, amount);
	}

}
