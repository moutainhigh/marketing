package com.oristartech.rule.core.template.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.template.dao.IFieldAndOperatorRDao;
import com.oristartech.rule.core.template.model.FieldAndOperatorRelation;
import com.oristartech.rule.core.template.service.IFieldAndGroupAndOpRService;
import com.oristartech.rule.vos.template.vo.FieldAndOperatorRelationVO;

@Component
@Transactional
public class FieldAndGroupAndOpRServiceImpl extends RuleBaseServiceImpl implements IFieldAndGroupAndOpRService {

//	@Autowired
//	private IFieldAndOperatorRDao ruleFieldAndOperatorRDao;
	
//	public void saveRelation(FieldAndOperatorRelationVO vo) {
//		validate(vo);
//		FieldAndOperatorRelation relation = getMapper().map(vo, FieldAndOperatorRelation.class);
//		ruleFieldAndOperatorRDao.saveOrUpdate(relation);
//	}
//	
//	private void validate(FieldAndOperatorRelationVO vo) {
//		if(vo == null) {
//			throw new ServiceRuntimeException("field.operator.relation.required");
//		}
//		if(BlankUtil.isBlank(vo.getFieldGroupId())) {
//			throw new ServiceRuntimeException("field.group.id.required");
//		}
//		if(vo.getModelFieldId() == null) {
//			throw new ServiceRuntimeException("field.id.required");
//		}
//		if(vo.getOperator() == null || vo.getOperator().getId() == null) {
//			throw new ServiceRuntimeException("operator.id.required");
//		}
//	}

}
