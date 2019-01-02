package com.oristartech.rule.core.template.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.template.model.FunctionGroup;
import com.oristartech.rule.core.template.model.FunctionGroupAndFunctionRelation;

public interface IFunctionGroupDao extends IRuleBaseDao<FunctionGroup, Integer>  {

	/**
	 * 根据分类及管理编号查找方法组
	 * @param groupTypeName
	 * @param functionManagerNums
	 * @return
	 */
	List<FunctionGroup> searchByTypeAndNums(String groupTypeName, String[] functionManagerNums);

	/**
	 * 根据模板id查找所有方法组
	 * @param templateId
	 * @param excludeGroupIds排除的FunctionGroup id list
	 * @return List<FunctionGroup>
	 */
	List<FunctionGroup> searchFunctionGroups(Integer templateId, List<Integer> excludeGroupIds);

	
	/**
	 * 找出group id列表下的所有function和functionGroup的关系, 会把function中的参数及参数select列表全部查出来
	 * @param fgids functionGroup id 列表
	 * @return List<FunctionGroupAndFunctionRelation>
	 */
	List<FunctionGroupAndFunctionRelation> findGroupAndFunctions(List<Integer> fgids);

}
