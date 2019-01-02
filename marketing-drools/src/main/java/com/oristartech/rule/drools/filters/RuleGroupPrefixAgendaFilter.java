package com.oristartech.rule.drools.filters;

import org.drools.core.spi.Activation;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import com.oristartech.rule.drools.util.RuleUtil;

/**
 * 规则组前缀过滤器
 * @author chenjunfei
 *
 */
public class RuleGroupPrefixAgendaFilter implements AgendaFilter {

	private final Integer groupId;
	private final boolean accept;
	private final String groupPrefixName;
	
	public RuleGroupPrefixAgendaFilter(Integer groupId) {
		this(groupId, true);
	}

	public RuleGroupPrefixAgendaFilter(Integer groupId, boolean accept) {
		this.groupId = groupId;
		this.accept = accept;
		this.groupPrefixName = RuleUtil.getRuleGroupPrefix(groupId);
	}

	public boolean accept(Activation activation) {
		 if(activation.getRule().getName().startsWith(groupPrefixName)) {
			 return accept;
		 } else {
			 return !accept;
		 }
	}

	public boolean accept(Match match) {
		if(match.getRule().getName().startsWith(groupPrefixName)) {
			 return accept;
		} else {
			 return !accept;
		}
	}
}
