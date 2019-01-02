package com.oristartech.rule.core.functions.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.vos.base.enums.FunctionResultType;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

public class FunctionResultUtil {
	
	private static final String ACTION_ID = "actionId";
	private static final String ACTION_FN_CATEGORY = "actionFnCategory";
	
	/**
	 * 把相关结果转为map result
	 * @param ruleResult
	 * @param anResult
	 */
	public static void convertActionToMapResult(RuleResult ruleResult, RuleActionResult anResult) {
		putFnCateogry(ruleResult, anResult);
		putResultInMap(ruleResult, anResult);
	}
	
	/**
	 * 添加结果的方法类别
	 * @param ruleResult
	 * @param anResult
	 */
	public static void putFnCateogry(RuleResult ruleResult, RuleActionResult anResult) {
		if(anResult != null && !BlankUtil.isBlank(anResult.getFnResults())) {
			if(ruleResult.getFnCategories() == null) {
				ruleResult.setFnCategories(new HashSet<String>());
			}
			ruleResult.getFnCategories().add(anResult.getFnCategoryName());
		}
	}
	
	/**
	 * 
	 * 为方便解析结果， RuleResult 提供map形式的result，这个方法统一把结果进该map中，
	 * 例如value，可以是对象， 多个时，可以是数组，或直接是一个简单值, map的key是方法的fnEffect值。
	 * 方法会根据ActionFunction 的resulttype值作为map中的结果形式。 
	 * 
	 * 若有特殊情况， 可以在子类重写本方法。
	 * 
	 * @param context
	 * @param acContext
	 * @param resultRuleVO
	 * @param actionVO
	 * @param curActionResult 当前run方法执行的结果
	 */
	public static void putResultInMap(RuleResult ruleResult, RuleActionResult actionResult) {
		if(actionResult == null || actionResult.getFnResultType() == null || BlankUtil.isBlank(actionResult.getFnResults()) ) {
			return;
		}
		Map<String, Object> ruleResultMap = ruleResult.getResultMap();
		if(ruleResultMap == null) {
			ruleResultMap = new HashMap<String, Object>();
			ruleResult.setResultMap(ruleResultMap);
		} 
		FunctionResultType fnResultType = FunctionResultType.getByName(actionResult.getFnResultType());
		if(fnResultType != null) {
			switch(fnResultType) {
			case SINGLE_RESULT_VALUE :  putSingleResultValue(ruleResultMap, actionResult); break;
			case SINGLE_RESULT_OBJ : putSingleResultObj(ruleResultMap, actionResult); break;
			case SINGLE_RESULT_ACTION_OBJ : putSingleResultActionObj(ruleResultMap,actionResult); break;
			case MULTI_RESULT_OBJ : putMultiResultObj(ruleResultMap,  actionResult); break;
			case MULTI_RESULT_ACTION_OBJ : putMultiResultActionObj(ruleResultMap,  actionResult); break;
			}
		}
		
	}
	
	/**
	 * 方法结果可以直接返回一个值。 若规则已经有同样fnEffect的方法值，会被覆盖。 所以有本形式结果的执行方法应该只在规则中出现一次。
	 * 并且方法结果FnResults只包含一个map
	 * @param ruleResultMap
	 * @param curActionResult
	 */
	private static void putSingleResultValue(Map<String, Object> ruleResultMap, RuleActionResult curActionResult) {
		List<Map<String, Object>> fnResults = curActionResult.getFnResults();
		Map<String, Object> fnResult = fnResults.get(0);
		if(!BlankUtil.isBlank(fnResult)) {
			for(String key : fnResult.keySet()) {
				//单值，map中只有一个属性
				ruleResultMap.put(curActionResult.getFnEffect(), fnResult.get(key));
				return;
			}
		}
	}
	
	
	/**
	 * 方法结果可以返回一个map对象，对象中包含若干属性值。 若规则已经有同样fnEffect的方法结果，会被覆盖。 
	 * 所以有本形式结果的执行方法应该只在规则中出现一次。并且方法结果FnResults只包含一个map。
	 * @param ruleResultMap
	 * @param curActionResult
	 */
	private static void putSingleResultObj(Map<String, Object> ruleResultMap,   RuleActionResult curActionResult) {
		List<Map<String, Object>> fnResults = curActionResult.getFnResults();
		Map<String, Object> fnResult = fnResults.get(0);
		if(!BlankUtil.isBlank(fnResult)) {
			//直接把map结果放进去
			ruleResultMap.put(curActionResult.getFnEffect(), fnResult);
		}
	}
	
	/**
	 * 方法结果返回一个map对象，但同时把action 信息添加进去， 例如action id， 方法 category。所以方法结果map中不能包含相关属性。
	 * 若规则已经有同样fnEffect的方法值，会被覆盖。 所以有本形式结果的执行方法应该只在规则中出现一次，
	 * 并且方法结果FnResults只包含一个map.
	 * @param ruleResultMap
	 * @param curActionResult
	 */
	private static void putSingleResultActionObj(Map<String, Object> ruleResultMap,  RuleActionResult curActionResult) {
		List<Map<String, Object>> fnResults = curActionResult.getFnResults();
		Map<String, Object> fnResult = fnResults.get(0);
		if(!BlankUtil.isBlank(fnResult)) {
			//直接把action id， 方法 category放进去
			putActionInfoInResult(curActionResult, fnResult);
			ruleResultMap.put(curActionResult.getFnEffect(), fnResult);
		}
	}
	
	/**
	 * 方法结果是一个map组成的list。 适合同一条规则有多个同样fnEffect方法的形式，或该方法本身的结果是个list。
	 * @param ruleResultMap
	 * @param curActionResult
	 */
	private static void putMultiResultObj(Map<String, Object> ruleResultMap, RuleActionResult curActionResult) {
		List<Map<String, Object>> fnResults = curActionResult.getFnResults();
		if(!BlankUtil.isBlank(fnResults)) {
			//结果是list
			List<Map<String, Object>> results = (List<Map<String, Object>> )ruleResultMap.get(curActionResult.getFnEffect());
			if(results == null) {
				results = new ArrayList<Map<String, Object>>();
				ruleResultMap.put(curActionResult.getFnEffect(), results);
				
			} 
			
			results.addAll(fnResults);
		}
	}
	
	/**
	 * 方法结果是一个map组成的list。 适合同一条规则有多个同样fnEffect方法的形式，或该方法本身的结果是个list。
	 * 并且把action信息放到没一个map结果中
	 * @param ruleResultMap
	 * @param actionResult
	 * @param curActionResult
	 */
	private static void putMultiResultActionObj(Map<String, Object> ruleResultMap, RuleActionResult curActionResult) {
		List<Map<String, Object>> fnResults = curActionResult.getFnResults();
		if(!BlankUtil.isBlank(fnResults)) {
			List<Map<String, Object>> results = (List<Map<String, Object>> )ruleResultMap.get(curActionResult.getFnEffect());
			if(results == null) {
				results = new ArrayList<Map<String, Object>>();
				ruleResultMap.put(curActionResult.getFnEffect(), results);
			}
			for(Map<String, Object> fnResult : fnResults) {
				putActionInfoInResult(curActionResult, fnResult);
			}
			results.addAll(fnResults);
		}
	}
	
	private static void putActionInfoInResult(RuleActionResult actionResult, Map<String, Object> fnResult) {
		//直接把action id， 方法 category放进去
		fnResult.put(ACTION_ID, actionResult.getRuleActionId());
		fnResult.put(ACTION_FN_CATEGORY, actionResult.getFnCategoryName());
	}
	
}
