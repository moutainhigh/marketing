package com.oristartech.rule.core.executor;

import java.util.List;

import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * 规则加载器, 不同的类型可能使用不同的加载方式.
 * @author chenjunfei
 *
 */
public interface IRuleLoaderService {
	public void loadRuleGroup(RuleGroupVO groupVo);
	
	public void loadRuleGroups(List<RuleGroupVO> groupVOs);
	
	public void removeRuleGroup(RuleGroupVO groupVo);
}
