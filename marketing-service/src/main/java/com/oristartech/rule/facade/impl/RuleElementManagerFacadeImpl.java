package com.oristartech.rule.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.rule.core.template.service.IRuleElementManagerService;
import com.oristartech.rule.facade.IRuleElementManagerFacade;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.template.vo.FieldGroupVO;
import com.oristartech.rule.vos.template.vo.GroupElementTypeVO;
import com.oristartech.rule.vos.template.vo.RuleElementTemplateVO;

@Service(version = "1.0.0", interfaceClass = IRuleElementManagerFacade.class)
public class RuleElementManagerFacadeImpl implements IRuleElementManagerFacade {
	
	@Autowired
	IRuleElementManagerService ruleElementManagerService;

	@Override
	public RuleElementTemplateVO getTemplateById(Integer templateId) {
		return ruleElementManagerService.getTemplateById(templateId);
	}

	@Override
	public List<FieldGroupVO> getAllFieldGroupInTest() {
		return ruleElementManagerService.getAllFieldGroupInTest();
	}

	@Override
	public List<ModelCategoryVO> getAllCategory() {
		return ruleElementManagerService.getAllCategory();
	}

	@Override
	public List<RuleElementTemplateVO> getTemplateByType(String type) throws ServiceException {
		return ruleElementManagerService.getTemplateByType(type);
	}

	@Override
	public List<GroupElementTypeVO> getGroupEleWithFieldGroups(Integer templateId, Boolean isCommon,
			String excludeGroupIds) {
		return ruleElementManagerService.getGroupEleWithFieldGroups(templateId, isCommon, excludeGroupIds);
	}

	@Override
	public String findConditionFieldGroupsJson(String ids) {
		return ruleElementManagerService.findConditionFieldGroupsJson(ids);
	}

	@Override
	public List<GroupElementTypeVO> getGroupEleWithFunctionGroups(Integer templateId, String excludeFgIds) {
		return ruleElementManagerService.getGroupEleWithFunctionGroups(templateId, excludeFgIds);
	}

	@Override
	public String findFuncGroupJson(String ids) {
		return ruleElementManagerService.findFuncGroupJson(ids);
	}

}
