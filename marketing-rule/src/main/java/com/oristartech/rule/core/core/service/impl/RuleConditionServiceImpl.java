package com.oristartech.rule.core.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.core.dao.IRuleConditionDao;
import com.oristartech.rule.core.core.model.RuleCondition;
import com.oristartech.rule.core.core.service.IRuleConditionService;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;

@Component
@Transactional
public class RuleConditionServiceImpl extends RuleBaseServiceImpl implements IRuleConditionService {

//	@Autowired
//	private IRuleConditionDao ruleConditionDao;
	
//	/**
//	 * 加载所有包含动态加载属性的规则条件分类, map的key是 modelfield service name
//	 * @param conditionCategory ModelField 中queryKeyFieldNames 对应的category 
//	 */
//	public Map<String, List<RuleConditionVO>> searchAllDynamicConditions(String conditionCategory) {
//		if(BlankUtil.isBlank(conditionCategory)) {
//			return null;
//		}
//		Map<String, List<RuleCondition>> conMaps = ruleConditionDao.searchAllDynamicConditions(conditionCategory);
//		if(!BlankUtil.isBlank(conMaps)) {
//			Map<String, List<RuleConditionVO>> conMapVos = new HashMap<String, List<RuleConditionVO>>();
//			
//			for(String key : conMaps.keySet()) {
//				List<RuleCondition> cons = conMaps.get(key);
//				if(!BlankUtil.isBlank(cons)) {
//					List<RuleConditionVO> vos = new ArrayList<RuleConditionVO>();
//					for(RuleCondition con : cons) {
//						vos.add(getMapper().map(con, RuleConditionVO.class));
//					}
//					conMapVos.put(key, vos);
//				}
//			}
//			return conMapVos;
//		}
//		return null;
//	}

}
