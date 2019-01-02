package com.oristartech.rule.core.template.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.template.model.FieldGroup;

/**
 * 属性分组dao
 * @author chenjunfei
 *
 */
public interface IFieldGroupDao extends IRuleBaseDao<FieldGroup, Integer> {

	/**
	 * 根据分组类别名称和管理编号获取分组列表
	 * @param groupTypeName
	 * @param fieldManagerNums
	 */
	List<FieldGroup> searchByTypeAndNums(String groupTypeName, String[] fieldManagerNums);
	
	/**
	 * 查找模板下的所有fieldGroup
	 * @param templateId
	 * @param isCommon 
	 * @param 排除的FieldGroup id list
	 * @return
	 */
	List<FieldGroup> searchFieldGroups(Integer templateId, Boolean isCommon, List<Integer> excludeGroupIds);

	/**
	 * 根据id数组查找fieldGroup
	 * @param gids
	 * @return
	 */
	List<FieldGroup> getByIds(List<Integer> gids);

	/**
	 * 获取所有可以在在测试页面出现的fieldgroup
	 * @return
	 */
	List<FieldGroup> getAllFieldGroupInTest();

	/**
	 * 获取所有可以在在测试页面出现的fieldgroup ids
	 * @return
	 */
	List<Integer> getAllFieldGroupIdsInTest();

}
