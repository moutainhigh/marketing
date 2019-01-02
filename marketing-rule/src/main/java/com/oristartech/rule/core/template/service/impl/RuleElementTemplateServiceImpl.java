package com.oristartech.rule.core.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.template.dao.IRuleElementTemplateDao;
import com.oristartech.rule.core.template.model.RuleElementTemplate;
import com.oristartech.rule.core.template.service.IRuleElementTemplateService;
import com.oristartech.rule.vos.template.vo.RuleElementTemplateVO;

@Component
@Transactional
public class RuleElementTemplateServiceImpl extends RuleBaseServiceImpl implements IRuleElementTemplateService {
	@Autowired
	private IRuleElementTemplateDao ruleElementTemplateDao;
	
//	public List<RuleElementTemplateVO> getAllTemplates() {
//		List<RuleElementTemplate> tems = ruleElementTemplateDao.getAll();
//		return dO2VO(tems);
//	}
	
	public List<RuleElementTemplateVO> getByType(String type) throws ServiceException {
		if(BlankUtil.isBlank(type)) {
			throw new ServiceException(BizExceptionEnum.MKT_MISSING_RULE_TEMPLATE_TYPE);
		}
		List<RuleElementTemplate> tems = ruleElementTemplateDao.findByType(type);
		return dO2VO(tems);
	}
	
	public RuleElementTemplateVO getById(Integer templateId) {
	    if(templateId != null) {
	    	RuleElementTemplate tem = ruleElementTemplateDao.get(templateId);
	    	if(tem != null) {
	    		return getMapper().map(tem, RuleElementTemplateVO.class);
	    	}
	    }
	    return null;
	}
	
	private List<RuleElementTemplateVO> dO2VO (List<RuleElementTemplate> tems) {
		List<RuleElementTemplateVO> result = null;
		if(!BlankUtil.isBlank(tems)) {
			result = new ArrayList<RuleElementTemplateVO>();
			for(RuleElementTemplate tem : tems) {
				result.add(getMapper().map(tem, RuleElementTemplateVO.class));
			}
		}
		return result;
	}
	
	public RuleElementTemplateVO getByName(String templateName) {
		if(!BlankUtil.isBlank(templateName)) {
	    	RuleElementTemplate tem = ruleElementTemplateDao.getByName(templateName);
	    	if(tem != null) {
	    		return getMapper().map(tem, RuleElementTemplateVO.class);
	    	}
	    }
	    return null;
	}
}
