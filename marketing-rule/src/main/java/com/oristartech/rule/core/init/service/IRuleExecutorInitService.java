package com.oristartech.rule.core.init.service;

import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

/**
 * 执行模块初始化，把rule文件load进内存中
 * @author chenjunfei
 */
public interface IRuleExecutorInitService {

	/**
	 * 初始化是否完成
	 * @return
	 */
	public boolean isInitCompleted();
	
	public void setInitCompleted(boolean initCompleted);
	
	/**
	 * 查询可以使用中的规则
	 * @param searchCondition
	 * @return
	 */
	public Page<RuleGroupVO> findEngineExeRuleVOs(RuleSearchCondition searchCondition);
}
