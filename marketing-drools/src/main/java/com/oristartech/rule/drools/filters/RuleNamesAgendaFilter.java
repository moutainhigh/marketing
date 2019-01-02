package com.oristartech.rule.drools.filters;

import java.util.List;

import org.drools.core.spi.Activation;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import com.oristartech.rule.drools.util.RuleUtil;

/**
 * 多个规则中缀名称匹配
 * @author chenjunfei
 *
 */
public class RuleNamesAgendaFilter implements AgendaFilter{
	private final List<Integer> ruleIds;
	private final boolean accept;
	
	public RuleNamesAgendaFilter(List<Integer> ruleIds) {
		this(ruleIds, true);
	}

	public RuleNamesAgendaFilter(List<Integer> ruleIds, boolean accept) {
		this.ruleIds = ruleIds;
		this.accept = accept;
	}

	public boolean accept(Activation activation) {
		//ruleName 是drl文件中的name
		String ruleName = activation.getRule().getName();
		
		if(hasRuleId(ruleName)) {
			return accept;
		} else {
			return !accept;
		}
	}

	public boolean accept(Match match) {
		String ruleName = match.getRule().getName();
		if(hasRuleId(ruleName)) {
			 return accept;
		} else {
			 return !accept;
		}
	}
	
	private boolean hasRuleId(String ruleName) {
		for(Integer id : ruleIds) {
			if(ruleName.indexOf(RuleUtil.getRuleMidName(id)) >= 0) {
				return true;
			}
		}
		return false;
	}
}
