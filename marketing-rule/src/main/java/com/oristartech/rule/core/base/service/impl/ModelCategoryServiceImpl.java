package com.oristartech.rule.core.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.base.dao.IModelCategoryDao;
import com.oristartech.rule.core.base.model.ModelCategory;
import com.oristartech.rule.core.base.service.IModelCategoryService;
import com.oristartech.rule.vos.base.enums.ModelCategoryType;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;

@Component
@Transactional
public class ModelCategoryServiceImpl extends RuleBaseServiceImpl implements IModelCategoryService {

	@Autowired
	private IModelCategoryDao ruleModelCategoryDao;
	
	public void setRuleModelCategoryDao(IModelCategoryDao ruleModelCategoryDao) {
    	this.ruleModelCategoryDao = ruleModelCategoryDao;
    }

	public ModelCategoryVO getByName(String categoryName) {
		if(!BlankUtil.isBlank(categoryName)) {
			ModelCategory cat = ruleModelCategoryDao.getByName(categoryName);
			if(cat != null) {
				return getMapper().map(cat,  ModelCategoryVO.class);
			}
		}
	    return null;
	}
	
//	public boolean hasDynamicField(String categoryName) {
//		if(!BlankUtil.isBlank(categoryName)) {
//			return ruleModelCategoryDao.hasDynamicField(categoryName);
//		}
//		return false;
//	}
	
	public List<ModelCategoryVO> getAllCanMergeFieldCon() {
		List<ModelCategory> list = ruleModelCategoryDao.findALlCanMergeFieldCon();
		if(!BlankUtil.isBlank(list)) {
			List<ModelCategoryVO> vos = new ArrayList<ModelCategoryVO>();
			for(ModelCategory cat : list) {
				vos.add(getMapper().map(cat, ModelCategoryVO.class));
			}
			return vos;
		}
		return null;
	}
	
	public List<ModelCategoryVO> getCategorys(ModelCategoryType type) {
		if(type != null) {
			List<ModelCategory> list = ruleModelCategoryDao.getCategorys(type);
			if(!BlankUtil.isBlank(list)) {
				List<ModelCategoryVO> vos = new ArrayList<ModelCategoryVO>();
				for(ModelCategory cat : list) {
					vos.add(getMapper().map(cat, ModelCategoryVO.class));
				}
				return vos;
			}
		}
		return null;
	}
	
//	/**
//	 * category中是否包含有需要外部查询加载的属性， 该属性必须是ModelField的queryKeyFieldNames不为空，isInDynamicCondition为false或空
//	 * 并且loadServiceName不为空
//	 */
//	public boolean hasExternLoadField(String categoryName) {
//		if(!BlankUtil.isBlank(categoryName)) {
//			return ruleModelCategoryDao.hasExternLoadField(categoryName);
//		}
//		return false;
//	}
}
