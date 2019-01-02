package com.oristartech.rule.core.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.template.dao.IFunctionGroupAndTemplateRDao;
import com.oristartech.rule.core.template.dao.IFunctionGroupDao;
import com.oristartech.rule.core.template.dao.IRuleElementTemplateDao;
import com.oristartech.rule.core.template.model.FunctionGroup;
import com.oristartech.rule.core.template.model.FunctionGroupAndTemplateRelation;
import com.oristartech.rule.core.template.model.RuleElementTemplate;
import com.oristartech.rule.core.template.service.IFunctionGroupAndTemplateRService;

@Component
@Transactional
public class FunctionGroupAndTemplateRServiceImpl implements IFunctionGroupAndTemplateRService {

//	@Autowired
//	private IRuleElementTemplateDao ruleElementTemplateDao;
//	@Autowired
//	private IFunctionGroupDao ruleFunctionGroupDao;
//	@Autowired
//	private IFunctionGroupAndTemplateRDao ruleFunctionGroupAndTemplateRDao;
	
//	public void connectGroupAndTemplate(String templateName, String groupTypeName, String[] functionManagerNums) {
//		if(!BlankUtil.isBlank(templateName) && !BlankUtil.isBlank(groupTypeName) && !BlankUtil.isBlank(functionManagerNums)) {
//			RuleElementTemplate template = ruleElementTemplateDao.getByName(templateName);
//			if(template == null) {
//				throw new ServiceRuntimeException("rule.element.template.not.exist");
//			}
//			List<FunctionGroup> fieldGroups = ruleFunctionGroupDao.searchByTypeAndNums(groupTypeName, functionManagerNums);
//			if(!BlankUtil.isBlank(fieldGroups)) {
//				List<FunctionGroupAndTemplateRelation> fgts = new ArrayList<FunctionGroupAndTemplateRelation>();
//				for(FunctionGroup fg : fieldGroups) {
//					FunctionGroupAndTemplateRelation fgr = new FunctionGroupAndTemplateRelation();
//					fgr.setFunctionGroup(fg);
//					fgr.setRuleElementTemplate(template);
//					fgts.add(fgr);
//				}
//				ruleFunctionGroupAndTemplateRDao.batchSaveOrUpdate(fgts);
//			}
//		}
//	}

}
