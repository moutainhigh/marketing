package com.oristartech.rule.drools.executor.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.FactConstants;

/**
 * 事实容器，方便管理来匹配的事实。
 * @author Administrator
 *
 */
public class FactsContainer {
	
	private List<Object> facts = new ArrayList<Object>();
	
	/**
	 * 包含fact的map， key是fact的num，value是fact
	 */
	private Map<String, Object> factMap = new HashMap<String, Object>();
	
	/**
	 * 把fact根据它的type归类好，放在map中。map的value是同类的fact 的list
	 */
	private Map<String, List<Object>> factTypeMap = new HashMap<String, List<Object>> ();
	
	/**
	 * 获取container中所有fact
	 * @return the facts
	 */
	public List<Object> getFacts() {
		return facts;
	}
	
	/**
	 * clone 一份所有fact。
	 * @return
	 */
	public List<Object> getCloneFacts() {
		if(!BlankUtil.isBlank(facts)) {
			List<Object> result = new ArrayList<Object>();
			for(Object fact : facts) {
				result.add(BeanUtils.cloneBean(fact));
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 添加事实到container
	 * @param fact
	 */
	public void addFact(Object fact) {
		String key = getBeanValue(fact, FactConstants.CATEGORY_NUM_KEY);
		if(!BlankUtil.isBlank(key) && !factMap.containsKey(key) ) {
			facts.add(fact);
			factMap.put(key, fact);
			addToTypeMap(fact);
		}
	}
	
	/**
	 * 添加事实到container
	 * @param fact
	 */
	public void addFacts(List<Object> facts) {
		if(!BlankUtil.isBlank(facts)) {
			for(Object fact : facts) {
				addFact(fact);
			}
		}
	}
	
	/**
	 * 由指定key获取fact。现在的key固定为fact的num属性。该属性在RuleFactPrepareServiceImpl中初始化。
	 * @param key
	 * @return
	 */
	public Object getFactByKey(String key) {
		if(!BlankUtil.isBlank(key) && this.factMap != null) {
			return this.factMap.get(key);
		}
		return null;
	}
	
	/**
	 * 根据type，获取相关的fact
	 */
	public List<Object> getFactsByType(String type) {
		if(!BlankUtil.isBlank(type) && factTypeMap != null) {
			return factTypeMap.get(type);
		}
		return null;
	}
	
	/**
	 * 复制指定类型的fact
	 * @param type
	 * @return
	 */
	public List<Object> cloneFactsByType(String type) {
		if(BlankUtil.isBlank(type) || factTypeMap == null) {
			return null;
		}
		
		List<Object> facts = getFactsByType(type);
		if(!BlankUtil.isBlank(facts)) {
			List<Object> result = new ArrayList<Object>();
			for(Object fact : facts) {
				result.add(BeanUtils.cloneBean(fact));
			}
			return result;
		}
		return null;
	}
	
	/**
	 * @return the factTypeMap
	 */
	public Map<String, List<Object>> getFactTypeMap() {
		return factTypeMap;
	}
	
	/**
	 * 添加到type map中
	 * @param fact
	 */
	private void addToTypeMap(Object fact) {
		String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
		if(BlankUtil.isBlank(type)) {
			return;
		}
		if(this.factTypeMap == null) {
			factTypeMap = new HashMap<String, List<Object>>();
		}
		if(factTypeMap.get(type) == null) {
			factTypeMap.put(type, new ArrayList<Object>());
		}
		factTypeMap.get(type).add(fact);
	}
	
	private String getBeanValue(Object obj ,String property) {
		if(obj != null && !BlankUtil.isBlank(property)) {
			return BeanUtils.getPropertyStr(obj, property);
		}
		return null;
	}
	
}
