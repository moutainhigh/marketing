package com.oristartech.rule.core.template.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.template.dao.IFunctionGroupDao;
import com.oristartech.rule.core.template.model.FunctionGroup;
import com.oristartech.rule.core.template.service.IFunctionGroupService;
import com.oristartech.rule.vos.template.vo.FunctionGroupVO;

@Component
@Transactional
public class FunctionGroupServiceImpl extends RuleBaseServiceImpl implements IFunctionGroupService {

//	@Autowired
//	private IFunctionGroupDao ruleFunctionGroupDao;
//	
//	public Integer saveOrUpdate(FunctionGroupVO groupVO) {
//		validate(groupVO);
//		FunctionGroup fnGroup = getMapper().map(groupVO, FunctionGroup.class);
//		fnGroup = ruleFunctionGroupDao.saveOrUpdate(fnGroup);
//		return fnGroup.getId();
//	}
//	
//	private void validate(FunctionGroupVO groupVO) {
//		if(groupVO == null) {
//			throw new ServiceRuntimeException("groupvo.required");
//		}
//		if(BlankUtil.isBlank(groupVO.getCnName())) {
//			throw new ServiceRuntimeException("function.group.cnName.required");
//		}
//		if(BlankUtil.isBlank(groupVO.getGroupElementTypeId())) {
//			throw new ServiceRuntimeException("function.group.type.id.required");
//		}
//	}

}
