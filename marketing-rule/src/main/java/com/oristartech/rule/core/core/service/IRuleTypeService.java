package com.oristartech.rule.core.core.service;

import java.util.Map;

import com.oristartech.rule.vos.core.vo.RuleTypeVO;

/**
 * rule type service
 * @author chenjunfei
 *
 */
public interface IRuleTypeService {
	/**
	 * 获取所有规则类型，并用返回map，key是rule type name， value是RuleTypeVO对象
	 * @param ruleId
	 * @param actionIds
	 * @return
	 */
	public Map<String, RuleTypeVO> getAllRuleTypeVOsMap() ;
	
	/**
	 * 根据名称获取规则类型
	 * @param name
	 * @return
	 */
	public RuleTypeVO getByName(String name);

}
