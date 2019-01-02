package com.oristartech.rule.core.template.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.base.model.ActionFunction;
import com.oristartech.rule.core.base.model.ActionFunctionParameter;
import com.oristartech.rule.core.template.dao.IFunctionGroupDao;
import com.oristartech.rule.core.template.model.FunctionGroup;
import com.oristartech.rule.core.template.model.FunctionGroupAndFunctionRelation;

@Repository
public class FunctionGroupDaoImpl extends RuleBaseDaoImpl<FunctionGroup, Integer> implements IFunctionGroupDao {

	public List<FunctionGroup> searchByTypeAndNums(String groupTypeName, String[] functionManagerNums) {
		String hql = "select fg from FunctionGroup fg where fg.groupElementType.name = :groupTypeName and fg.managerNum in(:functionManagerNums)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupTypeName", groupTypeName);
		params.put("functionManagerNums", functionManagerNums);
	    return (List<FunctionGroup>) findByNamedParam(hql, params);
	}
	
	public List<FunctionGroup> searchFunctionGroups(Integer templateId , List<Integer> excludeGroupIds) {
		StringBuilder sb = new StringBuilder("SELECT fg FROM FunctionGroupAndTemplateRelation fgt " +
				                              " join fgt.functionGroup fg " +
				                              " join fetch fg.groupElementType get " +
				                              " WHERE (fg.isEnable = true or fg.isEnable is null ) ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(templateId != null) {
			sb.append(" AND fgt.ruleElementTemplate.id = :templateId ");
			params.put("templateId", templateId);
		}
		
		if(!BlankUtil.isBlank(excludeGroupIds)) {
			sb.append(" AND fg.id NOT IN( :excludeGroupIds) ");
			params.put("excludeGroupIds", excludeGroupIds);
		}
		sb.append("ORDER BY get.seqNum, fg.seqNum ,fgt.seqNum");
		return (List<FunctionGroup>)super.findByNamedParam(sb.toString(), params);
	}
	
	public List<FunctionGroupAndFunctionRelation> findGroupAndFunctions(List<Integer> groupFuncIds) {
		String  hql = "SELECT DISTINCT fgf FROM FunctionGroupAndFunctionRelation fgf " +
											 " JOIN FETCH fgf.functionGroup fg " +
											 " JOIN FETCH fgf.actionFunction af " +
											 " WHERE fg.id in( :fgIds ) " +
											 " ORDER BY fgf.seqNum ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fgIds", groupFuncIds);
		List<FunctionGroupAndFunctionRelation> groupAndFuncs = (List<FunctionGroupAndFunctionRelation>) findByNamedParam(hql, params);
		if(!BlankUtil.isBlank(groupAndFuncs)) {
			//用sql,把function parameter 中的可选列表查出来,即datasource
			//避免在mapper时,每个datasource都用一条sql查出
			List<ActionFunctionParameter> paramList = getFunctionParameters(groupFuncIds);
			if(!BlankUtil.isBlank(paramList)) {
				setParamToFunc(groupAndFuncs, paramList);
			}
		}
		return groupAndFuncs;
	}
	
	/**
	 * @param groupFuncIds
	 * @return
	 */
	private List<ActionFunctionParameter> getFunctionParameters(List<Integer> groupFuncIds) {
		String  hql = "SELECT DISTINCT afParam FROM FunctionGroupAndFunctionRelation fgf " +
					 " JOIN fgf.functionGroup fg " +
					 " JOIN fgf.actionFunction af " +
					 " LEFT JOIN af.actionFunctionParameters afParam " +
					 " LEFT JOIN FETCH afParam.funcParamDataSource paramDs " +
					 " WHERE fg.id in( :fgIds ) AND (afParam.isConfig != true OR afParam.isConfig IS NULL ) " +
					 " ORDER BY afParam.seqNum, paramDs.seqNum ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fgIds", groupFuncIds);
		List<ActionFunctionParameter> paramList = (List<ActionFunctionParameter>) findByNamedParam(hql, params);
		
		return paramList;
	}
	
	private void setParamToFunc(List<FunctionGroupAndFunctionRelation> groupAndFuncs, List<ActionFunctionParameter> paramList) {
		Map<Integer , ActionFunction> map = new HashMap<Integer , ActionFunction>();
		for(FunctionGroupAndFunctionRelation funcGroupRla : groupAndFuncs) {
			if(map.get(funcGroupRla.getActionFunction().getId()) == null) {
				map.put(funcGroupRla.getActionFunction().getId(), funcGroupRla.getActionFunction());
				funcGroupRla.getActionFunction().setActionFunctionParameters(new ArrayList<ActionFunctionParameter>());
			}
		}
		for(ActionFunctionParameter param : paramList) {
			map.get(param.getActionFunction().getId()).getActionFunctionParameters().add(param);
		}
	}
}
