package com.oristartech.rule.core.core.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.core.dao.IRuleFinderDao;
import com.oristartech.rule.core.core.service.IRuleFinderService;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.base.vo.ModelFieldVO;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleSectionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
 
/**
 * 专门用于执行时的规则查找
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class RuleFinderServiceImpl extends RuleBaseServiceImpl implements IRuleFinderService {

	@Autowired
	private IRuleFinderDao ruleFinderDao;
	
	public void setRuleFinderDao(IRuleFinderDao ruleFinderDao) {
    	this.ruleFinderDao = ruleFinderDao;
    }

	/**
	 * 多租户模式此方法过期
	 */
	@Deprecated
	public Page<RuleGroupVO> searchEngineExeRules(RuleSearchCondition searchCondition) {
		if(searchCondition == null) {
			return null;
		}
		Page<RuleGroupVO> pages = ruleFinderDao.findEngineExeRuleVOs(searchCondition);
		
		return pages;
	}
	
	public RuleGroupVO getRuleGroupForExe(Integer ruleGroupId) {
		if(ruleGroupId == null) {
			return null;
		}
		return ruleFinderDao.getRuleGroupForExe(ruleGroupId);
	}

	@Override
	@Deprecated
	public List<RuleGroupVO> assembleRuleGroupVO(List<RuleGroupVO> groupVOs) {
		return ruleFinderDao.assembleRuleGroupVO(groupVOs);
	}
	
	
	@Override
	public List<RuleGroupVO> assembleRuleGroup(List<RuleGroupVO> groupVOs) throws Exception {
		Map<Integer, RuleGroupVO> ruleGroupMap = new HashMap<Integer, RuleGroupVO>();
		
		List<String> groupIds = new ArrayList<>();
		
		for(RuleGroupVO groupVO : groupVOs) {
			if(groupVO != null) {
				groupVO.setRuleSectionVO(null);
				groupVO.setRules(null);
			}
			if(ruleGroupMap.get(groupVO.getId()) == null) {
				ruleGroupMap.put(groupVO.getId(), groupVO);
			}
			groupIds.add(String.valueOf(groupVO.getId()));
		}
		
		assembleRuleGroupSection(ruleGroupMap, initCommonSection(ruleFinderDao.getGroupSectionInfo(groupIds)));
//		assembleRuleGroupSection(ruleGroupMap, initRuleInGroups(groupIds));
		assembleRuleInGroup(ruleGroupMap, initRuleInGroups(ruleFinderDao.getRuleConditionInfo(groupIds),
				ruleFinderDao.getRuleActionInfo(groupIds)));
		return groupVOs;
	}
	
	/**
	 * 1,把查询到的action和condition的rule和section分别组装成list
	 * 2,去重rule和section
	 * 3,根据rulevo的数据结构进行组装
	 * @param conditionList
	 * @param actionList
	 * @return
	 */
	public List<RuleVO> initRuleInGroups(List<Map<String,Object>> conditionList,List<Map<String,Object>> actionList){
//		把查询到的action和condition的rule和section分别组装成list
		List<RuleVO> ruleVOs = new ArrayList<>();
		for(Map<String,Object> rule:conditionList){
			
			RuleVO newRuleVO = getMapper().map(rule, RuleVO.class);
			ruleVOs.add(newRuleVO);
		}
		
		List<RuleSectionVO> sectionVOs = new ArrayList<>();
		for(Map<String,Object> section:conditionList){
			
			RuleSectionVO newSectionVO = getMapper().map(section, RuleSectionVO.class);
			sectionVOs.add(newSectionVO);
		}
		
		for(Map<String,Object> rule:actionList){
			
			RuleVO newRuleVO = getMapper().map(rule, RuleVO.class);
			ruleVOs.add(newRuleVO);
		}

		for(Map<String,Object> section:actionList){
			
			RuleSectionVO newSectionVO = getMapper().map(section, RuleSectionVO.class);
			sectionVOs.add(newSectionVO);
		}
		
//		根据sectionid和rugroupid去重section
		sectionVOs = sectionVOs.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(o -> o.getRuleId() + o.getId()))),ArrayList::new));
//		组装section里面的action属性
		combineSectionAndAction(sectionVOs, assembleRuleAction(actionList));
//		组装section里面的condition属性
		combineSectionAndCondition(sectionVOs, assembleRuleCondition(conditionList));

//		根据ruleid和rugroupid去重rule
		ruleVOs = ruleVOs.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(o -> o.getRuleGroupId() + o.getId()))),ArrayList::new));
		combineRuleAndSection(ruleVOs, sectionVOs);
		
		return ruleVOs;
	}
	
	/**
	 * 组装公共section vo
	 * @param ruleGroupMap
	 * @param sectionVOs
	 */
	private void assembleRuleGroupSection(Map<Integer, RuleGroupVO> ruleGroupMap, List<RuleSectionVO> sectionVOs) {
		if(!BlankUtil.isBlank(ruleGroupMap) && !BlankUtil.isBlank(sectionVOs)) {
			for(RuleSectionVO rs : sectionVOs) {
				if(ruleGroupMap.get(rs.getRuleGroupId()) != null) {
					ruleGroupMap.get(rs.getRuleGroupId()).setRuleSectionVO(rs);
				}
			}
		}
	}
	
	private List<RuleActionVO> assembleRuleAction(List<Map<String,Object>> list){
		List<RuleActionVO> all = new ArrayList<>();
//		所有的condition加入list
		for(Map<String,Object> map : list){
			RuleActionVO newActionVO = getMapper().map(map, RuleActionVO.class);
			all.add(newActionVO);
		}
		
		return all;
	}
	
	private List<RuleSectionVO> combineSectionAndAction(List<RuleSectionVO> secs,List<RuleActionVO> actions){
		for(RuleActionVO action : actions){
			for(RuleSectionVO sec : secs){
				if(action.getRuleSectionId().longValue() == sec.getId().longValue()){
					if(BlankUtil.isBlank(sec.getRuleActions())){
						sec.setRuleActions(new ArrayList<>());
						sec.getRuleActions().add(action);
					}else{
						sec.getRuleActions().add(action);
					}
				}
			}
		}
		
		return secs;
	}
	
	private List<RuleVO> combineRuleAndSection(List<RuleVO> rules,List<RuleSectionVO> ruleSections){
		for(RuleSectionVO section : ruleSections){
			for(RuleVO rule : rules){
				if(section.getRuleId().longValue() == rule.getId().longValue()){
					if(BlankUtil.isBlank(rule.getRuleSections())){
						rule.setRuleSections(new ArrayList<>());
						rule.getRuleSections().add(section);
					}else{
						rule.getRuleSections().add(section);
					}
				}
			}
		}
		
		return rules;
	}
	
	private List<RuleSectionVO> initCommonSection(List<Map<String,Object>> list){
		List<RuleSectionVO> sectionVOs = new ArrayList<>();
		for(Map<String,Object> section:list){
			
			RuleSectionVO newSectionVO = getMapper().map(section, RuleSectionVO.class);
			sectionVOs.add(newSectionVO);
		}
//			组装section里面的condition属性
		combineSectionAndCondition(sectionVOs, assembleRuleCondition(list));
//			根据sectionid和rugroupid去重section
		sectionVOs = sectionVOs.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(o -> o.getRuleGroupId() + o.getId()))),ArrayList::new));
		
		return sectionVOs;
	}
	
	private List<RuleConditionVO> assembleRuleCondition(List<Map<String,Object>> list){
		List<RuleConditionVO> all = new ArrayList<>();
//		所有的condition加入list
		for(Map<String,Object> map : list){
			RuleConditionVO newConditionVO = getMapper().map(map, RuleConditionVO.class);
			all.add(newConditionVO);
		}
		
//		根据sectionid和conditionid去重
		all = all.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(o -> o.getRuleSectionId() + o.getId()))),ArrayList::new));

		//组装好的RuleConditionElementVO放入RuleConditionVO中
		combineConditionAndElement(all, assembleRuleConditionElement(list));
		
		return all;
	}
	
	private List<RuleSectionVO> combineSectionAndCondition(List<RuleSectionVO> secs,List<RuleConditionVO> cons){
		for(RuleConditionVO con : cons){
			for(RuleSectionVO sec : secs){
				if(con.getRuleSectionId().longValue() == sec.getId().longValue()){
					if(BlankUtil.isBlank(sec.getRuleConditions())){
						sec.setRuleConditions(new ArrayList<>());
						sec.getRuleConditions().add(con);
					}else{
						sec.getRuleConditions().add(con);
					}
				}
			}
		}
		
		return secs;
	}
	
	private List<RuleConditionVO> combineConditionAndElement(List<RuleConditionVO> cons,List<RuleConditionElementVO> conEles){
		for(RuleConditionElementVO conEle : conEles){
			for(RuleConditionVO con : cons){
				if(conEle.getRuleConditionId().longValue() == con.getId().longValue()){
					if(BlankUtil.isBlank(con.getConditionElements())){
						con.setConditionElements(new ArrayList<>());
						con.getConditionElements().add(conEle);
					}else{
						con.getConditionElements().add(conEle);
					}
				}
			}
		}
		
		return cons;
	}
	
	private List<RuleConditionElementVO> assembleRuleConditionElement(List<Map<String,Object>> list){
		List<RuleConditionElementVO> all = new ArrayList<>();
		for(Map<String,Object> map : list){
			RuleConditionElementVO newConditionEleVO = getMapper().map(map, RuleConditionElementVO.class);
			ModelFieldVO newMFvo = getMapper().map(map, ModelFieldVO.class);
			newConditionEleVO.setModelField(newMFvo);
			all.add(newConditionEleVO);
		}
		return all;
	}
	
	/**
	 * 组装公共section vo
	 * @param ruleGroupMap
	 * @param sectionVOs
	 */
	private void assembleRuleInGroup(Map<Integer, RuleGroupVO> ruleGroupMap, List<RuleVO> rules) {
		if(BlankUtil.isBlank(ruleGroupMap) || BlankUtil.isBlank(rules)) {
			return;
		}
		
		for(RuleVO ruleVO : rules) {
			if(ruleGroupMap.get(ruleVO.getRuleGroupId()) != null) {
				List<RuleVO> ruleVOs = ruleGroupMap.get(ruleVO.getRuleGroupId()).getRules();
				RuleGroupVO groupVO = ruleGroupMap.get(ruleVO.getRuleGroupId());
				if(ruleVOs == null) {
					groupVO.setRules(new ArrayList<RuleVO>());
				}
				if(!groupVO.getRules().contains(ruleVO)) {
					groupVO.getRules().add(ruleVO);
				}
			}
		}
	}
}
