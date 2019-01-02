package com.oristartech.rule.core.core.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oristartech.marketing.core.dao.hibernate5.impl.RuleBaseDaoImpl;
import com.oristartech.marketing.core.exception.RuleParseRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.core.dao.IRuleFinderDao;
import com.oristartech.rule.core.core.dao.IRuleTypeDao;
import com.oristartech.rule.core.core.model.Rule;
import com.oristartech.rule.core.core.model.RuleAction;
import com.oristartech.rule.core.core.model.RuleActionParameter;
import com.oristartech.rule.core.core.model.RuleConditionElement;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.core.model.RuleSection;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.enums.RuleRunMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;
import com.oristartech.rule.vos.core.vo.RuleActionParameterVO;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleSectionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

@Repository
public class RuleFinderDaoImpl extends RuleBaseDaoImpl<Rule, Integer> implements IRuleFinderDao {
	
	@Autowired
	private IRuleTypeDao ruleTypeDao;
	@Autowired
	Mapper ruleMapper;
	
	public Mapper getRuleMapper() {
    	return ruleMapper;
    }

	
	public void setRuleTypeDao(IRuleTypeDao ruleTypeDao) {
    	this.ruleTypeDao = ruleTypeDao;
    }

	public RuleGroupVO getRuleGroupForExe(Integer ruleGroupId) {
		StringBuilder builder = new StringBuilder(" select DISTINCT rg from RuleGroup rg left join FETCH rg.ruleSection rs where rg.id = :groupId ") ;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", ruleGroupId);
		
		RuleGroup group = (RuleGroup)super.uniqueResult(builder.toString(), params);
		if(group != null) {
			List<RuleGroup> groups = new ArrayList<RuleGroup>();
			groups.add(group);
			List<RuleGroupVO> groupVos = convertRuleGroupVO(groups);
			return groupVos.get(0);
		}
	    return null;
	}
	
	/**
	 * 分页查询方法，结果是包含可以用engine 执行的Rule的page。
	 * @param searchCondition
	 * @return
	 */
	public Page<RuleGroupVO> findEngineExeRuleVOs(RuleSearchCondition searchCondition) {
		StringBuilder builder = new StringBuilder(" select DISTINCT rg " +
				                             " from RuleGroup rg left join FETCH rg.ruleSection rs where 1=1 ");
		if(searchCondition != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			if(!BlankUtil.isBlank(searchCondition.getRuleType())) {
				builder.append(" AND rg.ruleType = :appointruleType ");
				params.put("appointruleType", searchCondition.getRuleType());
			}
			builder.append("  AND (rg.ruleType in (:ruleType) ) " +
					  "  AND ((rg.validDateEnd is not null) AND rg.validDateEnd >= :now ) " +
					  "  AND rg.status=:status " );
			
			params.put("ruleType", ruleTypeDao.getNamesByRunMode(RuleRunMode.DROOLS));
			String dateStr = DateUtil.convertDateToStr(new Date(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
			//只需要当天的年月日
			Date nowDate = DateUtil.convertStrToDate(dateStr, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
			
			params.put("now", nowDate);
			params.put("status", RuleStatus.RUNNING);
			Page<RuleGroup> page = super.searchPagedQuery(builder.toString(), searchCondition.getStart(), searchCondition.getLimit(), params, false);
			if(page != null && !BlankUtil.isBlank(page.getResult())) {
				List<RuleGroupVO> groupVos = convertRuleGroupVO(page.getResult());
				Page<RuleGroupVO> pageVo = new Page<RuleGroupVO>(page.getStartIndex(), page.getTotalCount(), page.getPageSize(), groupVos);
				return pageVo;
			}
		}
		
	    return null;
	}
	
	/**
	 * 为了控制hibernate产生过多的sql, 自己组装vo
	 * @param groups
	 * @return
	 */
	private List<RuleGroupVO> convertRuleGroupVO(List<RuleGroup> groups) {
		Map<Integer, RuleGroupVO> ruleGroupMap = new HashMap<Integer, RuleGroupVO>();
		List<RuleGroupVO> groupVos = new ArrayList<RuleGroupVO>();
		
		for(RuleGroup group : groups) {
			group.setRules(null);
			group.setRuleSection(null);
			RuleGroupVO groupVO = getRuleMapper().map(group, RuleGroupVO.class);
			clearGroup(groupVO);
			groupVos.add(groupVO);
			if(ruleGroupMap.get(groupVO.getId()) == null) {
				ruleGroupMap.put(groupVO.getId(), groupVO);
			}
		}
		initCommonSection(ruleGroupMap);
		initRuleInGroups(ruleGroupMap);
		return groupVos;
	}
	
	/**
	 * 为了控制hibernate产生过多的sql, 自己组装vo
	 * @param groups
	 * @return
	 */
	public List<RuleGroupVO> assembleRuleGroupVO(List<RuleGroupVO> groupVOs) {
		Map<Integer, RuleGroupVO> ruleGroupMap = new HashMap<Integer, RuleGroupVO>();
		
		for(RuleGroupVO groupVO : groupVOs) {
			clearGroup(groupVO);
			if(ruleGroupMap.get(groupVO.getId()) == null) {
				ruleGroupMap.put(groupVO.getId(), groupVO);
			}
		}
		initCommonSection(ruleGroupMap);
		initRuleInGroups(ruleGroupMap);
		return groupVOs;
	}
	
	
	/**
	 * 初始化
	 * @param ruleGroupMap
	 */
	private void initRuleInGroups(Map<Integer, RuleGroupVO> ruleGroupMap) {
		if(!BlankUtil.isBlank(ruleGroupMap)) {
			StringBuilder sb = new StringBuilder("SELECT DISTINCT r " +
            " FROM Rule r " +
            " WHERE r.ruleGroup.id in (:ruleGroupIds) " +
            " ORDER BY r.id " );
			
			Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("ruleGroupIds", ruleGroupMap.keySet());
	    	List<Rule> results = (List<Rule>)super.findByNamedParam(sb.toString(), params);
	    	assembleRuleInGroup(ruleGroupMap, results);
	    	initRuleSection(ruleGroupMap);
		}
	}
	
	/**
	 * 查询Rule 中的sectionVo
	 * @param ruleGroupMap
	 */
	private void initRuleSection(Map<Integer, RuleGroupVO> ruleGroupMap) {
		if(!BlankUtil.isBlank(ruleGroupMap)) {
			Map<Integer , RuleVO> ruleMap = new HashMap<Integer, RuleVO>();
			for(RuleGroupVO ruleGroup : ruleGroupMap.values()){
				List<RuleVO> rules = ruleGroup.getRules();
				if(!BlankUtil.isBlank(rules)) {
					for(RuleVO rule : rules) {
						ruleMap.put(rule.getId(), rule);
					}
				} else {
					throw new RuleParseRuntimeException("规则组下的规则内容为空.数据丢失");
				}
			}
			StringBuilder sb = new StringBuilder(" SELECT DISTINCT rs " +
		            " FROM RuleSection rs " +
		            " left JOIN FETCH rs.ruleConditions AS rc " +  //规则section可能没条件, 只有公共条件
		            " left JOIN FETCH rc.modelCategory " +
		            " WHERE rs.rule.id in (:ruleIds) " +
		            " ORDER BY rs.rule.id, rs.seqNum, rc.seqNum");
			
			Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("ruleIds", ruleMap.keySet());
	    	List<RuleSection> results = (List<RuleSection>)super.findByNamedParam(sb.toString(), params);
	    	List<RuleSectionVO> sectionVos = convertSectionVO(results);
	    	assembleRuleSection(ruleMap, sectionVos);
	    	initRuleConditionElements(sectionVos);
	    	initRuleActions(sectionVos);
		}
	}
	
	/**
	 * 查询rule actionVO
	 * @param sections
	 */
	private void initRuleActions(List<RuleSectionVO> sections ) {
		if(!BlankUtil.isBlank(sections)) {
			Map<Long, RuleSectionVO> sectionMap = new HashMap<Long, RuleSectionVO>();
			for(RuleSectionVO section : sections) {
				sectionMap.put(section.getId(), section);
			}
			
			StringBuilder sb = new StringBuilder("SELECT DISTINCT ra " +
		        " FROM  RuleAction AS ra " +
		        " JOIN FETCH ra.actionFunction AS rf " +
		        " JOIN FETCH rf.fnCategory" +
		        " JOIN FETCH ra.ruleSection AS rs " +
		        " left JOIN FETCH rf.actionFunctionParameters" +
		        " WHERE rs.id in (:sectionIds) " +
		        " ORDER BY ra.ruleSection.id, ra.ruleSection.seqNum ,ra.seqNum ");
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("sectionIds", sectionMap.keySet());
	    	
	    	List<RuleAction> results = (List<RuleAction>)super.findByNamedParam(sb.toString(), params);
	    	List<RuleActionVO> actionVOs = convertActionVO(results);
	    	assembleSectionAction(sectionMap, actionVOs);
	    	initActionParameter(actionVOs);
		}
	}
	
	private void initActionParameter (List<RuleActionVO> actions) {
		if(!BlankUtil.isBlank(actions)) {
			Map<Long, RuleActionVO> actionMap = new HashMap<Long, RuleActionVO>();
			for(RuleActionVO action : actions) {
				actionMap.put(action.getId(), action);
			}
			
			StringBuilder sb = new StringBuilder("SELECT DISTINCT rap " +
		        " FROM  RuleActionParameter AS rap " +
		        " JOIN FETCH rap.fnParameter fnp " +
		        " JOIN FETCH rap.ruleAction AS ra " +
		        " WHERE ra.id in (:actionIds) AND (fnp.isFile = false OR fnp.isFile is null) " +
		        " AND (fnp.isLazy = false OR fnp.isLazy is null )" +
		        " ORDER BY rap.id");
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("actionIds", actionMap.keySet());
	    	
	    	List<RuleActionParameter> results = (List<RuleActionParameter>)super.findByNamedParam(sb.toString(), params);
	    	assembleActionParameter(actionMap, results);
		}
	}

	/**
	 * 组装rule actionVO
	 * @param sectionMap
	 * @param actions
	 */
	private void assembleActionParameter(Map<Long, RuleActionVO> actionMap, List<RuleActionParameter> actionParams) {
		if(!BlankUtil.isBlank(actionMap) && !BlankUtil.isBlank(actionParams)) {
			for(RuleActionParameter actionParam : actionParams) {
				RuleActionParameterVO paramVO = getRuleMapper().map(actionParam, RuleActionParameterVO.class);
				RuleActionVO actionVO = actionMap.get(paramVO.getRuleActionId());
				if(actionVO != null) {
					if(actionVO.getParameters() == null) {
						actionVO.setParameters( new ArrayList<RuleActionParameterVO>());
					}
					if(!actionVO.getParameters().contains(paramVO)) {
						actionVO.getParameters().add(paramVO);
					}
				}
			}
		}
	}
	
	/**
	 * 组装rule actionVO
	 * @param sectionMap
	 * @param actions
	 */
	private void assembleSectionAction(Map<Long, RuleSectionVO> sectionMap, List<RuleActionVO> actions) {
		if(!BlankUtil.isBlank(sectionMap) && !BlankUtil.isBlank(actions)) {
			for(RuleActionVO action : actions) {
				RuleSectionVO sec = sectionMap.get(action.getRuleSectionId());
				if(sec != null) {
					if(sec.getRuleActions() == null) {
						sec.setRuleActions( new ArrayList<RuleActionVO>());
					}
					if(!sec.getRuleActions().contains(action)) {
						sec.getRuleActions().add(action);
					}
					
				}
			}
		}
	}

	private List<RuleActionVO> convertActionVO(List<RuleAction> actions) {
		List<RuleActionVO> results = new ArrayList<RuleActionVO>();
		if(!BlankUtil.isBlank(actions)) {
			for(RuleAction rs : actions) {
				results.add(getRuleMapper().map(rs, RuleActionVO.class));
			}
		}
		return results;
	}

	
	/**
	 * 组装rule section Vo
	 * @param ruleMap
	 * @param sections
	 */
	private void assembleRuleSection(Map<Integer , RuleVO> ruleMap, List<RuleSectionVO> sections) {
		if(!BlankUtil.isBlank(ruleMap) && !BlankUtil.isBlank(sections)) {
			for(RuleSectionVO rs : sections) {
				RuleVO rule = ruleMap.get(rs.getRuleId());
				if(rule != null) {
					if(rule.getRuleSections() == null) {
						rule.setRuleSections(new ArrayList<RuleSectionVO>());
					}
					if(!rule.getRuleSections().contains(rs)) {
						rule.getRuleSections().add(rs);
					}
				}
			}
		}
	}
	
	/**
	 * 查询公共sectionVo
	 */
	private void initCommonSection(Map<Integer, RuleGroupVO> ruleGroupMap) {
		if(!BlankUtil.isBlank(ruleGroupMap)) {
			StringBuilder sb = new StringBuilder("SELECT DISTINCT rs " +
            " FROM RuleSection rs " +
            " JOIN FETCH rs.ruleConditions AS rc " +
            " left JOIN FETCH rc.modelCategory rmc " +
            " WHERE rs.ruleGroup.id in (:ruleGroupIds) " +
            " ORDER BY rs.ruleGroup.id, rs.seqNum, rc.seqNum");
			
			Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("ruleGroupIds", ruleGroupMap.keySet());
	    	List<RuleSection> results = (List<RuleSection>)super.findByNamedParam(sb.toString(), params);
	    	List<RuleSectionVO> sectionVOs = convertSectionVO(results);
	    	assembleRuleGroupSection(ruleGroupMap, sectionVOs);
	    	initRuleConditionElements(sectionVOs);
	    	
		}
	}
	
	private void clearGroup( RuleGroupVO group) {
		if(group != null) {
			group.setRuleSectionVO(null);
			group.setRules(null);
		}
	}
	
	/**
	 * 把section DO转vo
	 * @param sections
	 * @return
	 */
	private List<RuleSectionVO> convertSectionVO(List<RuleSection> sections ) {
		List<RuleSectionVO> results = new ArrayList<RuleSectionVO>();
		if(!BlankUtil.isBlank(sections)) {
			for(RuleSection rs : sections) {
				results.add(getRuleMapper().map(rs, RuleSectionVO.class));
			}
		}
		return results;
	}
	/**
	 * 查询condition element vo
	 * @param sections
	 */
	private void initRuleConditionElements(List<RuleSectionVO> sections) {
		if(!BlankUtil.isBlank(sections)) {
			Map<Long, RuleConditionVO> conMap = new HashMap<Long, RuleConditionVO>();
			for(RuleSectionVO rs : sections) {
				List<RuleConditionVO> cons = rs.getRuleConditions(); 
				if(!BlankUtil.isBlank(cons)) {
					for(RuleConditionVO con : cons) {
						conMap.put(con.getId(), con);
					}
				}
			}
			//section 可以没有条件
			if(BlankUtil.isBlank(conMap)) {
				return;
			}
			StringBuilder sb = new StringBuilder("SELECT rcEle FROM RuleConditionElement rcEle " +
									" JOIN FETCH rcEle.operator op " +
									" JOIN FETCH rcEle.modelField mf " +
									" JOIN FETCH rcEle.ruleCondition rc " +
									" WHERE rc.id IN (:conIds) " +
									" ORDER BY rcEle.ruleCondition.id ,rcEle.ruleCondition.seqNum, rcEle.seqNum");
			Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("conIds", conMap.keySet());
	    	List<RuleConditionElement> results = (List<RuleConditionElement>)super.findByNamedParam(sb.toString(), params);
	    	assembleRuleConditionElement(conMap, results);
		}
	}
	
	/**
	 * 组装condition element vo
	 * @param conMap
	 * @param elements
	 */
	private void assembleRuleConditionElement(Map<Long, RuleConditionVO> conMap, List<RuleConditionElement> elements) {
		if(!BlankUtil.isBlank(conMap) && !BlankUtil.isBlank(elements)) {
			for(RuleConditionElement rcEle : elements) {
				RuleConditionElementVO eleVO = getRuleMapper().map(rcEle, RuleConditionElementVO.class);
				RuleConditionVO con = conMap.get(eleVO.getRuleConditionId());
				if(con != null) {
					if(con.getConditionElements() == null) {
						con.setConditionElements(new ArrayList<RuleConditionElementVO>());
					} 
					if(!con.getConditionElements().contains(eleVO)) {
						con.getConditionElements().add(eleVO);
					}
					
				}
			}
		}
	}
	
	/**
	 * 组装公共section vo
	 * @param ruleGroupMap
	 * @param sectionVOs
	 */
	private void assembleRuleInGroup(Map<Integer, RuleGroupVO> ruleGroupMap, List<Rule> rules) {
		if(BlankUtil.isBlank(ruleGroupMap) || BlankUtil.isBlank(rules)) {
			return;
		}
		
		for(Rule rule : rules) {
			RuleVO ruleVO = getRuleMapper().map(rule, RuleVO.class);
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

}
