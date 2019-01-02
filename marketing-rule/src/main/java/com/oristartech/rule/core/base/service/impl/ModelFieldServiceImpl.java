package com.oristartech.rule.core.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.base.dao.IModelFieldDao;
import com.oristartech.rule.core.base.dao.IModelFieldDataSourceDao;
import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.base.service.IModelFieldService;
import com.oristartech.rule.vos.base.vo.ModelFieldDataSourceVO;
import com.oristartech.rule.vos.base.vo.ModelFieldVO;

/**
 * 
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class ModelFieldServiceImpl extends RuleBaseServiceImpl  implements IModelFieldService {
	@Autowired
	private IModelFieldDataSourceDao ruleModelFieldDataSourceDao;
	@Autowired
	private IModelFieldDao ruleModelFieldDao;
	
//	public List<ModelFieldDataSourceVO> getDataSourceByFieldId(Integer fieldId) {
//		if(fieldId != null) {
//			return ruleModelFieldDataSourceDao.getDatasByModelFieldId(fieldId);
//		}
//		return null;
//	}
	
//	public List<ModelFieldVO> getAllExternFields() {
//		List<ModelField> modelFields = ruleModelFieldDao.getAllExternFields();
//		return convert2VO(modelFields);
//	}
	
	public List<ModelFieldVO> getExternLoadFields(String categoryName) {
		if(!BlankUtil.isBlank(categoryName)) {
			List<ModelField> modelFields = ruleModelFieldDao.getExternLoadFields(categoryName);
			return convert2VO(modelFields);
		}
		
	    return null;
	}
	
	private List<ModelFieldVO> convert2VO(List<ModelField> modelFields) {
		if(!BlankUtil.isBlank(modelFields)) {
			List<ModelFieldVO> vos = new ArrayList<ModelFieldVO>();
			for(ModelField f : modelFields) {
				vos.add(getMapper().map(f, ModelFieldVO.class));
			}
			return vos;
		}
		return null;
	}

}
