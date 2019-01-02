package com.oristartech.rule.core.template.service;

import java.util.List;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.rule.vos.template.vo.RuleElementTemplateVO;

/**
 * 模板service类
 * @author user
 *
 */
public interface IRuleElementTemplateService {
//	/**
//	 * 或其所有的template
//	 * @return
//	 */
//	List<RuleElementTemplateVO> getAllTemplates();
	
	
	/**
	 * 根据类型或其模板
	 * @param type
	 * @return
	 */
	List<RuleElementTemplateVO> getByType(String type) throws ServiceException;
	
	/**
	 * 
	 * @param templateId
	 * @return
	 */
	RuleElementTemplateVO getById(Integer templateId) ;
	
	/**
	 * 根据名称或其vo
	 * @param templateName
	 * @return
	 */
	RuleElementTemplateVO getByName(String templateName) ;
}
