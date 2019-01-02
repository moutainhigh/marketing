package com.oristartech.rule.core.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.template.dao.IFieldGroupDao;
import com.oristartech.rule.core.template.model.FieldGroup;
import com.oristartech.rule.core.template.service.IFieldGroupService;
import com.oristartech.rule.vos.template.vo.FieldGroupVO;

@Component
@Transactional
public class FieldGroupServiceImpl extends RuleBaseServiceImpl implements IFieldGroupService {

	@Autowired
	private IFieldGroupDao ruleFieldGroupDao;
	
//	public Integer saveOrUpdate(FieldGroupVO groupVO) {
//		validate(groupVO);
//		FieldGroup group = getMapper().map(groupVO, FieldGroup.class);
//		group = ruleFieldGroupDao.saveOrUpdate(group);
//		return group.getId();
//	}
//
//	public List<FieldGroupVO> getAllFieldGroupInTest() {
//		List<FieldGroup> fieldGroups = ruleFieldGroupDao.getAllFieldGroupInTest();
//		return convertDo2VO(fieldGroups);
//	}
	
	public List<Integer> getAllFieldGroupIdsInTest() {
		List<Integer> ids = ruleFieldGroupDao.getAllFieldGroupIdsInTest();
		return ids;
	}
	
	public List<FieldGroupVO> convertDo2VO(List<FieldGroup> fieldGroups) {
		if(!BlankUtil.isBlank(fieldGroups)) {
			List<FieldGroupVO> vos = new ArrayList<FieldGroupVO>();
			for(FieldGroup group : fieldGroups) {
				vos.add(getMapper().map(group, FieldGroupVO.class));
			}
			return vos;
		}
		return null;
	}
	
//	private void validate(FieldGroupVO groupVO) {
//		if(groupVO == null) {
//			throw new ServiceRuntimeException("groupvo.required");
//		}
//		if(BlankUtil.isBlank(groupVO.getCnName())) {
//			throw new ServiceRuntimeException("field.group.cnName.required");
//		}
//		if(BlankUtil.isBlank(groupVO.getGroupElementTypeId())) {
//			throw new ServiceRuntimeException("field.group.type.id.required");
//		}
//	}
}
