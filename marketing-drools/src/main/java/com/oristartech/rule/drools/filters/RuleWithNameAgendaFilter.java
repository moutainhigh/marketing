package com.oristartech.rule.drools.filters;

import org.drools.core.spi.Activation;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import com.oristartech.rule.drools.util.RuleUtil;

/**
 * 匹配规则部分名称
 * @author chenjunfei
 *
 */
public class RuleWithNameAgendaFilter implements AgendaFilter {

	private final Integer ruleId;
	private final String name;
	private final boolean accept;

	public RuleWithNameAgendaFilter(Integer ruleId) {
		this(ruleId, true);
	}

	public RuleWithNameAgendaFilter(Integer ruleId, boolean accept) {
		this.ruleId = ruleId;
		this.accept = accept;
		this.name = RuleUtil.getRuleMidName(ruleId);
	}

	public boolean accept(Activation activation) {
		 if(activation.getRule().getName().indexOf(name) >= 0) {
			 return accept;
		 } else {
			 return !accept;
		 }
	}

	public boolean accept(Match match) {
		if(match.getRule().getName().indexOf(name) >=0 ) {
			 return accept;
		} else {
			 return !accept;
		}
	}
}
