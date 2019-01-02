package com.oristartech.rule.core.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.base.model.ActionFunctionParameter;
import com.oristartech.rule.core.base.model.RuleActionFunctionParameter;
import com.oristartech.rule.core.core.dao.IRuleActionDao;
import com.oristartech.rule.core.core.model.RuleAction;
import com.oristartech.rule.core.core.service.IRuleActionService;
import com.oristartech.rule.vos.base.vo.RuleActionFunctionParameterVO;
import com.oristartech.rule.vos.core.vo.RuleActionVO;

@Component
@Transactional
public class RuleActionServiceImpl extends RuleBaseServiceImpl implements IRuleActionService {
	@Autowired
	private IRuleActionDao ruleActionDao ;
	//dozer中的map id
	private static final String ACTION_FILTER_MAPPING_NAME = "action-filter-lazy-proper";
	private static final String FN_PARAM_SIMPLE_MAPPER = "fn-param-simple-mapper";
	
	public List<RuleActionVO> getActionVOsForInvoke(Integer ruleId, List<Long> actionIds) {
		if(ruleId != null && !BlankUtil.isBlank(actionIds)) {
			List<RuleAction> actions = ruleActionDao.findActionVOs(ruleId, actionIds);
			return toVOs(actions);
		}
		
	    return null;
	}
	
	public List<RuleActionVO> getActionVOsForInvoke(List<Long> actionIds) {
		if(!BlankUtil.isBlank(actionIds)) {
			List<RuleAction> actions = ruleActionDao.findActionVOs(actionIds);
			return toVOs(actions);
		}
	    return null;
	}
	
	private List<RuleActionVO> toVOs(List<RuleAction> actions ) {
		if(!BlankUtil.isBlank(actions)) {
			List<RuleActionVO> actionVOs = new ArrayList<RuleActionVO>();
			for(RuleAction action : actions) {
				RuleActionVO anVO = getMapper().map(action, RuleActionVO.class, ACTION_FILTER_MAPPING_NAME);
				anVO.setFnParameters(createConfigParameter(action));
				actionVOs.add(anVO);
			}
			return actionVOs;
		}
		return null;
	}
	
	/**
	 * 因为function 的 config参数没有在页面存在的, 因而没有保存在action的参数中, 需要从function提取出来, 并封装为RuleActionParameterVO 方便使用
	 * @param action
	 * @return
	 */
	private List<RuleActionFunctionParameterVO> createConfigParameter(RuleAction action) {
		if(action.getActionFunction() != null) {
			List<ActionFunctionParameter> fnParams = (List<ActionFunctionParameter>)action.getActionFunction().getActionFunctionParameters();
			if(!BlankUtil.isBlank(fnParams)) {
				List<RuleActionFunctionParameterVO> fnParamVOs = new ArrayList<RuleActionFunctionParameterVO>();
				for(ActionFunctionParameter param : fnParams) {
					RuleActionFunctionParameter ruleParam = (RuleActionFunctionParameter)param;
					if(ruleParam.getIsConfig() != null && ruleParam.getIsConfig()) {
						fnParamVOs.add(getMapper().map(ruleParam, RuleActionFunctionParameterVO.class, FN_PARAM_SIMPLE_MAPPER));
					}
				}
				return fnParamVOs;
			}
		}
		return null;
	}
	
}
