package com.oristartech.rule.core.match.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.executor.IRuleExecutorDataService;
import com.oristartech.rule.core.match.service.IRuleFactPrepareService;
import com.oristartech.rule.core.result.filters.FilterUtil;
import com.oristartech.rule.drools.executor.context.FactsContainer;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;

/**
 * 为匹配，对fact进行准备的service
 * @author Administrator
 *
 */
@Component
@Transactional
public class RuleFactPrepareServiceImpl implements IRuleFactPrepareService {
	
	@Autowired
	private IRuleExecutorDataService ruleExecutorDataService;
	
	/**
	 * 匹配前对事实数据进行预处理, 设置相应的数据类型.
	 * @param isTest 是否测试
	 * @param srcFacts
	 * @return
	 */
	@Override
	public FactsContainer prepareFacts(String facts, boolean isTest, RuleTypeVO ruleType) {
		if(BlankUtil.isBlank(facts)) {
			return null;
		}
		
		List srcFacts = (List)JsonUtil.jsonArrayToList(facts, Map.class);
		
		return prepareFacts(srcFacts, isTest, ruleType);
	}
	
	/**
	 * 匹配前对事实数据进行预处理, 设置相应的数据类型.
	 * @param isTest 是否测试
	 * @param srcFacts
	 * @return
	 */
	@Override
	public FactsContainer prepareFacts(List<Object> facts,  boolean isTest , RuleTypeVO ruleType) {
		if(BlankUtil.isBlank(facts)) {
			return null;
		}
		if(!isTest) {
			//若果不是测试, 主动加载需要自动加载的category, 和属性
			ruleExecutorDataService.loadAndUpdateFactFields(facts);
			ruleExecutorDataService.loadAndUpdateFactDatas(facts);
			//若没必要,无须设置太多过滤器,影响性能
			FilterUtil.createFactFilterChain(ruleType).doFilter(facts);
		}
		List<Object> listFacts = new ArrayList<Object>();
		FactsContainer factsContainer = new FactsContainer();
		for(Object fact : facts) {
			composeFacts(fact,  listFacts, factsContainer);
		}
		
		return factsContainer;
	}
	
	/**
	 * 组装事实数据，加上号码标识
	 * @param data
	 * @param modelCategory
	 * @param index
	 * @return
	 */
	private void composeFacts(Object data, List<Object> listFacts, FactsContainer factsContainer) {
		if(data != null) {
			if(data instanceof Map ) {
				Map<String, Object> m = (Map<String, Object>) data;
				m.put(FactConstants.CATEGORY_NUM_KEY, listFacts.size());
				m.put(FactConstants.IS_FACT_KEY, true);
				
				//为了不影响别的规则逻辑, 本事实变为不可修改对象, 即在一条规则修改事实时，不会导致别的规则有影响。
				//规则方法需要clone一份事实进行修改。
				Map<String, Object> unmodifyMap = Collections.unmodifiableMap(m);
				listFacts.add(unmodifyMap);
				factsContainer.addFact(unmodifyMap);
				
//				for(String key : m.keySet()) {
//					//把属性中的复合对象抽出来.
//					Object obj = m.get(key);
//					if(obj != null && (obj.getClass().isArray() || obj instanceof Collection) ) {
//						composeFacts(obj, listFacts);
//					}
//				}
			} else if (data.getClass().isArray() ) {
				Object [] datas = (Object []) data; 
				if(datas.length > 0) {
					for(Object obj : datas) {
						composeFacts(obj, listFacts, factsContainer);
					}
				}
			} else if(data instanceof Collection) {
				Collection c = (Collection) data;
				if(!BlankUtil.isBlank(c)) {
					for(Object obj : c) {
						composeFacts(obj, listFacts, factsContainer);
					}
				}
			} else {
				//若是其他事实对象, 把他转为map. 因为现在的drl文件只处理map
				Map fact = BeanUtils.convertToMap(data);
				fact.put(FactConstants.CATEGORY_NUM_KEY, listFacts.size());
				fact.put(FactConstants.IS_FACT_KEY, true);
				//为了不影响别的规则逻辑, 本事实变为不可修改对象
				Map<String, Object> unmodifyMap = Collections.unmodifiableMap(fact);
				listFacts.add(unmodifyMap);
				factsContainer.addFact(unmodifyMap);
			}
		}
	}
	
//	//应该在自定义符号加载这些动态属性值,否则非常慢
//	private void loadDynamicConditionValues(List<Object> facts) {
//		List<Object> allDyFacts = new ArrayList<Object>();
//		
//		for(Object fact : facts ) {
//			List<Object> dynamicFacts = ruleExecutorDataService.loadDynamicConditionValues(fact);
//			if(!BlankUtil.isBlank(dynamicFacts)) {
//				allDyFacts.addAll(dynamicFacts);
//			}
//		}
//		facts.addAll(allDyFacts);
//	}
}
