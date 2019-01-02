package com.oristartech.rule.core.template.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.core.base.model.ModelFieldDataSource;
import com.oristartech.rule.core.base.service.IModelCategoryService;
import com.oristartech.rule.core.template.dao.IFieldAndOperatorRDao;
import com.oristartech.rule.core.template.dao.IFieldGroupAndFieldRDao;
import com.oristartech.rule.core.template.dao.IFieldGroupDao;
import com.oristartech.rule.core.template.dao.IFunctionGroupDao;
import com.oristartech.rule.core.template.model.FieldAndOperatorRelation;
import com.oristartech.rule.core.template.model.FieldGroup;
import com.oristartech.rule.core.template.model.FieldGroupAndFieldRelation;
import com.oristartech.rule.core.template.model.FunctionGroup;
import com.oristartech.rule.core.template.model.FunctionGroupAndFunctionRelation;
import com.oristartech.rule.core.template.service.IFieldGroupService;
import com.oristartech.rule.core.template.service.IRuleElementManagerService;
import com.oristartech.rule.core.template.service.IRuleElementTemplateService;
import com.oristartech.rule.vos.base.enums.ModelCategoryType;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.base.vo.ModelFieldDataSourceVO;
import com.oristartech.rule.vos.base.vo.ModelFieldVO;
import com.oristartech.rule.vos.template.vo.FieldAndOperatorRelationVO;
import com.oristartech.rule.vos.template.vo.FieldGroupAndFieldRelationVO;
import com.oristartech.rule.vos.template.vo.FieldGroupVO;
import com.oristartech.rule.vos.template.vo.FunctionGroupAndFunctionRelationVO;
import com.oristartech.rule.vos.template.vo.FunctionGroupVO;
import com.oristartech.rule.vos.template.vo.GroupElementTypeVO;
import com.oristartech.rule.vos.template.vo.RuleElementTemplateVO;

@Component
@Transactional
public class RuleElementManagerServiceImpl extends RuleBaseServiceImpl implements IRuleElementManagerService {
	
	@Autowired
	private IRuleElementTemplateService ruleElementTemplateService;
	@Autowired
	private IFieldGroupDao ruleFieldGroupDao;
	@Autowired
	private IFieldGroupService ruleFieldGroupService;
	@Autowired
	private IModelCategoryService ruleModelCategoryService;
	@Autowired
	private IFieldGroupAndFieldRDao ruleFieldGroupAndFieldRDao;
	@Autowired
	private IFieldAndOperatorRDao ruleFieldAndOperatorRDao;
	@Autowired
	private IFunctionGroupDao ruleFunctionGroupDao;
	
	private final static String FIELD_GROUP_SPLITER = ",";
	
//	public List<RuleElementTemplateVO> getAllTemplates() {
//		return ruleElementTemplateService.getAllTemplates();
//	}
	public RuleElementTemplateVO getTemplateById(Integer templateId) {
		return ruleElementTemplateService.getById(templateId);
	}
	
	
	public RuleElementTemplateVO getTemplateByName(String templateName) {
		return ruleElementTemplateService.getByName(templateName);
	}
	
	public List<RuleElementTemplateVO> getTemplateByType(String type) throws ServiceException {
		return ruleElementTemplateService.getByType(type);
	}
	
	public List<GroupElementTypeVO> getGroupEleWithFieldGroups(Integer templateId, Boolean isCommon, String excludeGroupIds) {
		List<GroupElementTypeVO> result = null;
		if(templateId != null) {
			List<Integer> exIds = processStrIds(excludeGroupIds);
			List<FieldGroup> groups = ruleFieldGroupDao.searchFieldGroups(templateId, isCommon, exIds);
			Map<Integer, GroupElementTypeVO> map = new LinkedHashMap<Integer, GroupElementTypeVO>();
			if(!BlankUtil.isBlank(groups)) {
				result = new ArrayList<GroupElementTypeVO>();
				for(FieldGroup group : groups) {
					FieldGroupVO gvo = getMapper().map(group, FieldGroupVO.class);
					
					if(map.get(gvo.getGroupElementTypeId()) == null) {
						GroupElementTypeVO gtvo = new GroupElementTypeVO();
						result.add(gtvo);
						BeanUtils.copyProperties(group.getGroupElementType(), gtvo, new String[]{"fieldGroups", "funcGroups"});
						if(gtvo.getFieldGroups() == null) {
							gtvo.setFieldGroups(new ArrayList<FieldGroupVO>());
						}
						map.put(gvo.getGroupElementTypeId(), gtvo);
					} 
					map.get(gvo.getGroupElementTypeId()).getFieldGroups().add(gvo);
				}
			}
			
		}
	    return result;
	}
	
	
	public String getGroupEleWithFieldGroupsJson(Integer templateId, Boolean isCommon,
			String excludeGroupIds) {
		return JsonUtil.objToJsonIgnoreNull(getGroupEleWithFieldGroups(templateId, isCommon, excludeGroupIds));
	}
	
	
	public List<GroupElementTypeVO> getGroupEleWithFunctionGroups(Integer templateId, String excludeFgIds) {
		List<GroupElementTypeVO> result = null;
		if(templateId != null) {
			List<Integer> exIds = processStrIds(excludeFgIds);
			List<FunctionGroup> groups = ruleFunctionGroupDao.searchFunctionGroups(templateId, exIds);
			Map<Integer, GroupElementTypeVO> map = new LinkedHashMap<Integer, GroupElementTypeVO>();
			if(!BlankUtil.isBlank(groups)) {
				result = new ArrayList<GroupElementTypeVO>();
				for(FunctionGroup group : groups) {
					FunctionGroupVO gvo = getMapper().map(group, FunctionGroupVO.class);
					
					if(map.get(gvo.getGroupElementTypeId()) == null) {
						GroupElementTypeVO gtvo = new GroupElementTypeVO();
						result.add(gtvo);
						BeanUtils.copyProperties(group.getGroupElementType(), gtvo, new String[]{"fieldGroups", "funcGroups"});
						if(gtvo.getFieldGroups() == null) {
							gtvo.setFuncGroups(new ArrayList<FunctionGroupVO>());
						}
						map.put(gvo.getGroupElementTypeId(), gtvo);
					} 
					map.get(gvo.getGroupElementTypeId()).getFuncGroups().add(gvo);
				}
			}
			
		}
	    return result;
	}
	
	
	public String getGroupEleWithFunctionGroupsJson(Integer templateId, String excludeFgIds) {
		return JsonUtil.objToJsonIgnoreNull(getGroupEleWithFunctionGroups(templateId, excludeFgIds));
	}
	
	
	public List<FieldGroupVO> findConditionFieldGroups(String ids) {
		if(!BlankUtil.isBlank(ids)) {
			List<Integer> groupIds = processStrIds(ids);
			return findConditionFieldGroups(groupIds);
		}
		return null;
	}
	
	public List<FieldGroupVO> findConditionFieldGroups(List<Integer> groupIds) {
		if(BlankUtil.isBlank(groupIds)) {
			return null;
		}
		List<FieldGroupVO> result = new ArrayList<FieldGroupVO>();
		Map<Integer, FieldGroupVO> map = new LinkedHashMap<Integer, FieldGroupVO>();
		List<FieldGroupAndFieldRelation> groupAndFields =  ruleFieldGroupAndFieldRDao.findRelationByGroupIds(groupIds);
		//避免多次sql
		List<ModelFieldDataSourceVO> fieldDsVos = findModelFieldDSVOs(groupIds);
		//为了避免关联太多，手动查询实体，组装实体
		if(!BlankUtil.isBlank(groupAndFields)) {
			List<FieldAndOperatorRelation> fieldOperators = ruleFieldAndOperatorRDao.findFieldAndOpByGroupIds(groupIds);
			for(FieldGroupAndFieldRelation groupField : groupAndFields) {
				FieldGroupAndFieldRelationVO relationVO = getMapper().map(groupField, FieldGroupAndFieldRelationVO.class);
				initModelFieldVODS(relationVO.getModelField(), fieldDsVos);
				FieldGroupVO groupVO = map.get(relationVO.getFieldGroupId());
				if(groupVO == null) {
					groupVO = getMapper().map(groupField.getFieldGroup(), FieldGroupVO.class);
					map.put(groupVO.getId(), groupVO);
					result.add(groupVO);
				}
				groupVO.getGroupAndFields().add(relationVO);
				initFieldOperators(relationVO, groupVO, fieldOperators);
			}
		}
		return result;
	}
	
	public String findFuncGroupJson(String ids) {
		return JsonUtil.objToJsonIgnoreNull(findFuncGroup(ids));
	}
	
	public List<FunctionGroupVO> findFuncGroup(String ids) {
		if(!BlankUtil.isBlank(ids)) {
			List<Integer> fgids = processStrIds(ids);
			return findFuncGroup(fgids);
		}
		return null;
	}
	
	public List<FunctionGroupVO> findFuncGroup(List<Integer> ids) {
		if(BlankUtil.isBlank(ids)) {
			return null;
		}
		List<FunctionGroupVO> result = new ArrayList<FunctionGroupVO>();
		Map<Integer, FunctionGroupVO> map = new HashMap<Integer, FunctionGroupVO>();
		List<FunctionGroupAndFunctionRelation> groupAndFuncs =  ruleFunctionGroupDao.findGroupAndFunctions(ids);
		//为了避免关联太多，手动查询实体，组装实体
		if(!BlankUtil.isBlank(groupAndFuncs)) {
			for(FunctionGroupAndFunctionRelation groupFunc : groupAndFuncs) {
				FunctionGroupAndFunctionRelationVO grvo = getMapper().map(groupFunc, FunctionGroupAndFunctionRelationVO.class);
				FunctionGroupVO fvo = map.get(grvo.getFunctionGroupId());
				if(fvo == null) {
					fvo = getMapper().map(groupFunc.getFunctionGroup(), FunctionGroupVO.class);
					map.put(fvo.getId(), fvo);
					result.add(fvo);
				}
				fvo.getGroupAndFuncs().add(grvo);
			}
		}
	    return result;
	}
	
	public String findConditionFieldGroupsJson(String ids) {
		return JsonUtil.objToJsonIgnoreNull(findConditionFieldGroups(ids));
	}
	
	public List<FieldGroupVO> getAllFieldGroupInTest() {
		List<Integer> groupIds = ruleFieldGroupService.getAllFieldGroupIdsInTest();
		return findConditionFieldGroups(groupIds);
	}
	
	public List<ModelCategoryVO> getAllCategory() {
		return ruleModelCategoryService.getCategorys(ModelCategoryType.DROOLS);
	}
	
	/**
	 * 查找fieldgroup id下的field的datasource vo
	 * @param groupIds
	 * @return
	 */
	private List<ModelFieldDataSourceVO> findModelFieldDSVOs(List<Integer> groupIds) {
		List<ModelFieldDataSource> dsList = ruleFieldGroupAndFieldRDao.findModelFieldDSByGroupIds(groupIds);
		List<ModelFieldDataSourceVO> result = null;
		if(!BlankUtil.isBlank(dsList)) {
			result = new ArrayList<ModelFieldDataSourceVO>();
			for(ModelFieldDataSource ds : dsList) {
				result.add(getMapper().map(ds, ModelFieldDataSourceVO.class));
			}
		}
		return result;
	}
	
	/**
	 * 把field和operator关联
	 * @param relationVO
	 * @param groupVO
	 * @param fieldOperators
	 */
	private void initFieldOperators(FieldGroupAndFieldRelationVO relationVO, FieldGroupVO groupVO, List<FieldAndOperatorRelation> fieldOperators) {
		if(!BlankUtil.isBlank(fieldOperators) ) {
			Iterator<FieldAndOperatorRelation> it = fieldOperators.iterator();
			while(it.hasNext()) {
				FieldAndOperatorRelation fieldAndOP = it.next();
				if(fieldAndOP.getModelField().getId().equals(relationVO.getModelField().getId()) && 
						fieldAndOP.getFieldGroup().getId().equals(groupVO.getId()) ) {
					FieldAndOperatorRelationVO fopVO = getMapper().map(fieldAndOP, FieldAndOperatorRelationVO.class);
					relationVO.getModelField().getFieldOperators().add(fopVO);
					it.remove();
				}
			}
		}
	}
	/**
	 * 把datasource设进modelfield
	 * @param fieldVO
	 * @param dsList
	 */
	private void initModelFieldVODS(ModelFieldVO fieldVO, List<ModelFieldDataSourceVO> dsList) {
		if(!BlankUtil.isBlank(dsList)) {
			if(fieldVO.getModelFieldDataSources() == null) {
				fieldVO.setModelFieldDataSources(new ArrayList<ModelFieldDataSourceVO>());
			} else if(fieldVO.getModelFieldDataSources().size() > 0) {
				//datasource已经初始化
				return;
			}
			for(ModelFieldDataSourceVO ds : dsList) {
				if(ds.getModelFieldId().equals(fieldVO.getId())) {
					fieldVO.getModelFieldDataSources().add(ds);
				}
			}
			 
		}
	}
	/**
	 * 把字符串，并又逗号分隔的id变为整数id List
	 * @param strIds
	 * @return
	 */
	private List<Integer> processStrIds(String strIds) {
		List<Integer> intIds = null;
		if(!BlankUtil.isBlank(strIds)) {
			String[] ids = strIds.split(FIELD_GROUP_SPLITER);
			intIds = new ArrayList<Integer>();
			for(int i=0; i < ids.length; i++) {
				intIds.add(Integer.parseInt(ids[i]));
			}
		}
		return intIds;
	}
}
