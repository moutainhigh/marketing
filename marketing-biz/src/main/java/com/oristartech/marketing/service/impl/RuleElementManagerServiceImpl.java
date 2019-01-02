package com.oristartech.marketing.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.service.IRuleElementManagerService;
import com.oristartech.rule.facade.IRuleElementManagerFacade;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.template.vo.FieldGroupVO;
import com.oristartech.rule.vos.template.vo.GroupElementTypeVO;
import com.oristartech.rule.vos.template.vo.RuleElementTemplateVO;

@Service
public class RuleElementManagerServiceImpl implements IRuleElementManagerService {

	@Reference(version = "1.0.0")
	IRuleElementManagerFacade ruleElementManagerFacade;
	
	@Override
	public RuleElementTemplateVO getTemplateById(Integer templateId) {
		return ruleElementManagerFacade.getTemplateById(templateId);
	}

	@Override
	public List<FieldGroupVO> getAllFieldGroupInTest() {
		return ruleElementManagerFacade.getAllFieldGroupInTest();
	}

	@Override
	public List<ModelCategoryVO> getAllCategory() {
		return ruleElementManagerFacade.getAllCategory();
	}

	@Override
	public List<RuleElementTemplateVO> getTemplateByType(String type) throws ServiceException {
		return ruleElementManagerFacade.getTemplateByType(type);
	}

	@Override
	public List<GroupElementTypeVO> getGroupEleWithFieldGroups(Integer templateId, Boolean isCommon,
			String excludeGroupIds) {
		return ruleElementManagerFacade.getGroupEleWithFieldGroups(templateId, isCommon, excludeGroupIds);
	}

	/**
	 * 根据fieldGroup id组，找出用于定义规则条件的fieldGroup，并同时找出下面的ModelField， 及管理的operate等
	 * @param ids 逗号隔开的id
	 * @return 返回json
	 */
	public String findConditionFieldGroupsJson(String ids){
		return ruleElementManagerFacade.findConditionFieldGroupsJson(ids);
	}

	@Override
	public List<GroupElementTypeVO> getGroupEleWithFunctionGroups(Integer templateId, String excludeFgIds) {
		return ruleElementManagerFacade.getGroupEleWithFunctionGroups(templateId, excludeFgIds);
	}

	@Override
	public String findFuncGroupJson(String ids) {
		return ruleElementManagerFacade.findFuncGroupJson(ids);
	}
}
