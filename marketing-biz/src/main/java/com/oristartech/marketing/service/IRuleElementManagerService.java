package com.oristartech.marketing.service;

import java.util.List;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.template.vo.FieldGroupVO;
import com.oristartech.rule.vos.template.vo.GroupElementTypeVO;
import com.oristartech.rule.vos.template.vo.RuleElementTemplateVO;

/**
 * 规则元素查询管理类
 * @author chenjunfei
 *
 */
public interface IRuleElementManagerService  {


	/**
	 * 根据templateid获取template
	 * @param templateId
	 * @return
	 */
	RuleElementTemplateVO getTemplateById(Integer templateId);
	
	/**
	 * 获取所有可以在在测试页面出现的fieldgroup
	 * @return
	 */
	List<FieldGroupVO> getAllFieldGroupInTest();

	/**
	 * 获取所有category
	 * @return
	 */
	List<ModelCategoryVO> getAllCategory(); 
	
	/**
	 * 根据类型名称获取下面的模板
	 * @return
	 * @param type 类型名称 
	 */
	List<RuleElementTemplateVO> getTemplateByType(String type) throws ServiceException;
	
	/**
	 * 根据templateid获取fieldtemplate下的fieldGroup
	 * @param templateId
	 * @param isCommon 是否只查找公共条件
	 * @param excludeFgIds 排除的FieldGroup id字符串， 用逗号隔开
	 */
	List<GroupElementTypeVO> getGroupEleWithFieldGroups(Integer templateId, Boolean isCommon, String excludeGroupIds);
	
	/**
	 * 根据fieldGroup id组，找出用于定义规则条件的fieldGroup，并同时找出下面的ModelField， 及管理的operate等
	 * @param ids 逗号隔开的id
	 * @return 返回json
	 */
	String findConditionFieldGroupsJson(String ids);
	
	/**
	 * 根据templateid获取template下的functionGroup
	 * @param templateId
	 * @param excludeFgIds 排除的FunctionGroup id字符串， 用逗号隔开
	 * @return
	 */
	List<GroupElementTypeVO> getGroupEleWithFunctionGroups(Integer templateId, String excludeFgIds);
	
	/**
	 * 根据FunctionGroup id组，找出用于定义规则条件的FunctionGroup，并同时找出下面的参数等
	 * @param ids 逗号隔开的id
	 * @return 返回 FunctionGroupVO list 的json字符串
	 */
	String findFuncGroupJson(String ids);
}
