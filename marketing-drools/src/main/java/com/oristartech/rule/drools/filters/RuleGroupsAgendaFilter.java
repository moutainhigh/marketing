package com.oristartech.rule.drools.filters;

import java.util.List;

import org.drools.core.spi.Activation;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import com.oristartech.rule.drools.util.RuleUtil;

/**
 * 过滤若干group 匹配的规则, 匹配前缀
 * @author chenjunfei
 *
 */
public class RuleGroupsAgendaFilter implements AgendaFilter  {
	private final List<Integer> groupIds;
	private final boolean accept;
	
	public RuleGroupsAgendaFilter(List<Integer> groupIds) {
		this(groupIds, true);
	}

	public RuleGroupsAgendaFilter(List<Integer> groupIds, boolean accept) {
		this.groupIds = groupIds;
		this.accept = accept;
	}

	public boolean accept(Activation activation) {
		String ruleName = activation.getRule().getName();
		
		if(startWithGroupId(ruleName)) {
			return accept;
		} else {
			return !accept;
		}
	}

	public boolean accept(Match match) {
		String ruleName = match.getRule().getName();
		if(startWithGroupId(ruleName)) {
			 return accept;
		} else {
			 return !accept;
		}
	}
	
	private boolean startWithGroupId(String ruleName) {
		boolean result = false;
		for(Integer id : groupIds) {
			result = ruleName.startsWith(RuleUtil.getRuleGroupPrefix(id));
			if(result) {
				return result;
			}
		}
		return result;
	}
}
