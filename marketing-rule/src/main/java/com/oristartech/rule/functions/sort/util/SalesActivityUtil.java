package com.oristartech.rule.functions.sort.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.result.RuleResult;

public class SalesActivityUtil {
	/**
	 * 获取最优惠规则价格组合. 可以组合的是消耗商品没有交叉事实的规则. 而且是只包含修改价格信息的活动
	 * 方法是先把所有
	 * @param results
	 * @param facts
	 * @return
	 */
	public static List<RuleResult> chooseMostPriceFavorables(RuleExecuteContext context) {
		List<RuleResult> results = context.getRuleResults();
		if(BlankUtil.isBlank(results)) {
			return null;
		}
		List<ResultKeyPair> resultPairs = initRuleResult(results);		
		List<ResultsConsumeFactGroup> groups = generateGroups(resultPairs);
		
		if(groups.size() > 1) {
			List<Object> saleItems = SortRuleResultUtil.getSaleItems(context);
			groups = SortRuleResultUtil.sortLowestPriceInGroup(groups, saleItems);
		}
		return getFirstPriceGroupResults(groups);
	}
	
	/**
	 * 获取第一个有打折结果的group
	 * @param groups
	 * @return
	 */
	private static List<RuleResult> getFirstPriceGroupResults(List<ResultsConsumeFactGroup> groups) {
		for(ResultsConsumeFactGroup group : groups) {
			List<RuleResult> results = group.getResults();
			Iterator<RuleResult> it = results.iterator();
			while(it.hasNext()) {
				RuleResult r = it.next();
				if(!BlankUtil.isBlank(r.getResultMap())) {
					r = PriceRuleUtil.cleanActionResultInMapStyle(r);
				} else {
					r = PriceRuleUtil.cleanActionResultInListStyle(r);
				}
				if(r == null) {
					it.remove();
				}
			}
			if(results.size() > 0) {
				return results;
			} 
		}
		return null;
	}
	
	private static List<ResultKeyPair> initRuleResult(List<RuleResult> results) {
		List<ResultKeyPair> resultPair = new ArrayList<ResultKeyPair>();
		for(RuleResult result : results) {
			resultPair.add(new ResultKeyPair(result));
		}
		return resultPair;
	}
	
	private static List<ResultsConsumeFactGroup> generateGroups(List<ResultKeyPair> resultPairs) {
		List<ResultsConsumeFactGroup> groups = new ArrayList<ResultsConsumeFactGroup>();
		for(int i=0 ; i < resultPairs.size(); i++) {
			List<ResultKeyPair> candidateResults = removeConfiltPair(resultPairs.subList(i + 1, resultPairs.size()), resultPairs.get(i));
			if(!BlankUtil.isBlank(candidateResults) && candidateResults.size() > 1) {
				List<ResultsConsumeFactGroup> candidateGroups = generateGroups(candidateResults);
				removeInExistGroup(groups, candidateGroups);
				if(!BlankUtil.isBlank(candidateGroups)) {
					for(ResultsConsumeFactGroup g : candidateGroups) {
						g.addRuleResult(resultPairs.get(i));
						groups.add(g);
					}
				}
			} else if(!BlankUtil.isBlank(candidateResults) && candidateResults.size() == 1){
				ResultsConsumeFactGroup group = new ResultsConsumeFactGroup();
				group.addRuleResult(resultPairs.get(i));
				group.addRuleResult(candidateResults.get(0));
				if(!existGroup(groups, group)) {
					groups.add(group);
				}
			} else {
				ResultsConsumeFactGroup group = new ResultsConsumeFactGroup();
				group.addRuleResult(resultPairs.get(i));
				if(!existGroup(groups, group)) {
					groups.add(group);
				}
			}
//			removeInExistGroup(exists, groups);
//			if(!BlankUtil.isBlank(groups)) {
//				exists.addAll(groups);
//			}
		}
		return groups;
	}
	
	private static List<ResultsConsumeFactGroup> generateGroups(List<ResultKeyPair> resultPairs, List<ResultsConsumeFactGroup> exists) {
		List<ResultsConsumeFactGroup> groups = new ArrayList<ResultsConsumeFactGroup>();
		for(int i=0 ; i < resultPairs.size(); i++) {
			List<ResultKeyPair> candidateResults = removeConfiltPair(resultPairs.subList(i + 1, resultPairs.size()), resultPairs.get(i));
			if(!BlankUtil.isBlank(candidateResults) && candidateResults.size() > 1) {
				List<ResultsConsumeFactGroup> candidateGroups = generateGroups(candidateResults, exists);
				if(!BlankUtil.isBlank(candidateGroups)) {
					for(ResultsConsumeFactGroup g : candidateGroups) {
						g.addRuleResult(resultPairs.get(i));
						groups.add(g);
					}
				}
			} else if(!BlankUtil.isBlank(candidateResults) && candidateResults.size() == 1){
				ResultsConsumeFactGroup group = new ResultsConsumeFactGroup();
				group.addRuleResult(resultPairs.get(i));
				group.addRuleResult(candidateResults.get(0));
				
				groups.add(group);
				
			} else {
				ResultsConsumeFactGroup group = new ResultsConsumeFactGroup();
				group.addRuleResult(resultPairs.get(i));
				groups.add(group);
			}
			removeInExistGroup(exists, groups);
			if(!BlankUtil.isBlank(groups)) {
				exists.addAll(groups);
			}
		}
		return exists;
	}
	
	private static void removeInExistGroup(List<ResultsConsumeFactGroup> existGroups, List<ResultsConsumeFactGroup> curGroups) {
		if(!BlankUtil.isBlank(existGroups) && !BlankUtil.isBlank(curGroups)) {
			Iterator<ResultsConsumeFactGroup> gpIt = curGroups.iterator();
			while(gpIt.hasNext()) {
				ResultsConsumeFactGroup gp = gpIt.next();
				for(ResultsConsumeFactGroup cg : existGroups) {
					if(cg.containGroup(gp)) {
						gpIt.remove();
					}
				}
			}
		}
	}
	
	private static boolean existGroup(List<ResultsConsumeFactGroup> existGroups, ResultsConsumeFactGroup curGroup) {
		if(!BlankUtil.isBlank(existGroups) && curGroup != null) {
			for(ResultsConsumeFactGroup cg : existGroups) {
				if(cg.containGroup(curGroup)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static List<ResultKeyPair> removeConfiltPair(List<ResultKeyPair> paris, ResultKeyPair cur) {
		if(!BlankUtil.isBlank(paris)) {
			List<ResultKeyPair> newParis = new ArrayList<ResultKeyPair>();
			for(ResultKeyPair pair : paris) {
				if(pair != cur && !twoResultContainSameFact(pair, cur)) {
					newParis.add(pair);
				}
			}
			return newParis;
		}
		return null;
	}
	
	private static boolean twoResultContainSameFact(ResultKeyPair pair, ResultKeyPair other) {
		for(String key : pair.getFactKeys()) {
			if(other.containFactKey(key) == true) {
				return true;
			}
		}
		return false;
	}
}
