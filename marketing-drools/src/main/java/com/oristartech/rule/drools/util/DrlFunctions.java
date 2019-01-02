package com.oristartech.rule.drools.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.vos.core.vo.RuleVO;
/**
 * 建立drl中的动作工具方法
 * @author chenjunfei
 *
 */
public class DrlFunctions {
	protected static final Logger log = LoggerFactory.getLogger(DrlFunctions.class);
	
	/**
	 * 获取业务需要返回的匹配fact 事实
	 * @param facts
	 * @return
	 */
	public static List<Object> getRuleBizMatchFacts(List<Object> facts) {
		if(!BlankUtil.isBlank(facts)) {
			List<Object> results = new ArrayList<Object>();
			for(Object fact : facts) {
				Object obj = convertRuleMatchFacts(fact);
				if(obj !=  null) {
					results.add(obj);
				}
			}
			return results;
		}
		return null;
	}
	
	/**
	 * 规则需要返回现在匹配的事实数据. 但事实可能包含很多信息, 只返回需要返回的信息.
	 * @param fact
	 * @return
	 */
	public static Map<String, Object> convertRuleMatchFacts(Object fact) {
		if(fact != null) {
			Map<String, Object> result = new HashMap<String, Object>();
			String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
			if(isSaleInfo(fact)) {
				result.put(FactConstants.CATEGORY_TYPE_KEY, type);
				result.put(BizFactConstants.SALE_ITEM_TYPE, BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_TYPE));
				result.put(BizFactConstants.MER_KEY, BeanUtils.getPropertyStr(fact, BizFactConstants.MER_KEY));
				result.put(FactConstants.CATEGORY_NUM_KEY, BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY));
				return result;
			}
		}
		return null;
	}
	
	/**
	 * 是否卖品
	 * @param type
	 * @return
	 */
	public static boolean isSaleInfo(Object fact) {
		String type = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY);
		if(BizFactConstants.FILM_INFO.equals(type) || BizFactConstants.MER_INFO.equals(type)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 榨取规则项中的条件,格式同fact格式, 而且不包含比较符信息
	 * @param id
	 * @return
	 */
	public static List<Map<String, Object>> extractSectionConditions(RuleVO ruleVO, Long sectionId) {
		if(ruleVO != null && sectionId != null) {
			return ruleVO.extractSectionConditions(sectionId);
		}
		return null;
	}
	
}
