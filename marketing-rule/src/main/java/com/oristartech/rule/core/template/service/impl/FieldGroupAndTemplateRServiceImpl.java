package com.oristartech.rule.core.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.template.dao.IFieldGroupAndTemplateRDao;
import com.oristartech.rule.core.template.dao.IFieldGroupDao;
import com.oristartech.rule.core.template.dao.IRuleElementTemplateDao;
import com.oristartech.rule.core.template.model.FieldGroup;
import com.oristartech.rule.core.template.model.FieldGroupAndTemplateRelation;
import com.oristartech.rule.core.template.model.RuleElementTemplate;
import com.oristartech.rule.core.template.service.IFieldGroupAndTemplateRService;

@Component
@Transactional
public class FieldGroupAndTemplateRServiceImpl extends RuleBaseServiceImpl implements IFieldGroupAndTemplateRService {

//	@Autowired
//	private IRuleElementTemplateDao ruleElementTemplateDao;
//	@Autowired
//	private IFieldGroupDao ruleFieldGroupDao;
//	@Autowired
//	private IFieldGroupAndTemplateRDao ruleFieldGroupAndTemplateRDao;
	
//	public void connectGroupAndTemplate(String templateName, String groupTypeName , String[] fieldManagerNums, Boolean isCommon, Boolean isFix) {
//		if(!BlankUtil.isBlank(templateName) && !BlankUtil.isBlank(groupTypeName) && !BlankUtil.isBlank(fieldManagerNums)) {
//			RuleElementTemplate template = ruleElementTemplateDao.getByName(templateName);
//			if(template == null) {
//				throw new ServiceRuntimeException("rule.element.template.not.exist");
//			}
//			List<FieldGroup> fieldGroups = ruleFieldGroupDao.searchByTypeAndNums(groupTypeName, fieldManagerNums);
//			if(!BlankUtil.isBlank(fieldGroups)) {
//				List<FieldGroupAndTemplateRelation> fgts = new ArrayList<FieldGroupAndTemplateRelation>();
//				for(FieldGroup fg : fieldGroups) {
//					FieldGroupAndTemplateRelation fgr = new FieldGroupAndTemplateRelation();
//					fgr.setIsCommon(isCommon);
//					fgr.setFieldGroup(fg);
//					fgr.setRuleElementTemplate(template);
//					fgr.setIsFix(isFix);
//					fgts.add(fgr);
//				}
//				ruleFieldGroupAndTemplateRDao.batchSaveOrUpdate(fgts);
//			}
//		}
//	}

}
