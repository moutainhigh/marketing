package com.oristartech.rule.core.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.core.core.dao.IRuleDao;
import com.oristartech.rule.core.core.dao.IRuleGroupDao;
import com.oristartech.rule.core.core.model.Rule;
import com.oristartech.rule.core.core.model.RuleGroup;
import com.oristartech.rule.core.core.service.IRuleActionService;
import com.oristartech.rule.core.core.service.IRuleGroupFinderService;
import com.oristartech.rule.core.core.service.IRuleGroupSaverService;
import com.oristartech.rule.core.core.service.IRuleGroupService;
import com.oristartech.rule.core.core.service.IRuleManagerService;
import com.oristartech.rule.core.core.service.IRuleTestService;
import com.oristartech.rule.core.executor.IRuleLoaderService;
import com.oristartech.rule.vos.core.enums.RuleStatus;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleSectionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 规则的CRUD管理类, 具体方法注释看接口
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class RuleManagerServiceImpl extends RuleBaseServiceImpl implements IRuleManagerService {
	protected static final Logger log = LoggerFactory.getLogger(RuleManagerServiceImpl.class);
	
	@Autowired
	private IRuleDao ruleDao;
	@Autowired
	private IRuleGroupService ruleGroupService;
	@Autowired
	private IRuleGroupDao ruleGroupDao;
	@Autowired
	private IRuleGroupFinderService ruleGroupFinderService;
	@Autowired
	private IRuleGroupSaverService ruleGroupSaverService;
	@Autowired
	private IRuleTestService ruleTestService;
	@Autowired
	private IRuleLoaderService ruleLoaderService;
	
	public RuleGroupVO getRuleGroupById(Integer id) {
	    return getRuleGroupNoInitExternal(id);
	}
	
	/**
	 * 客户端启用规则
	 * @param vo
	 */
	public void startRule(Integer groupId,Integer tenantId){
		if(groupId != null){
			log.info("------启动刚下发的规则------group id=" + groupId);
			RuleGroupVO groupVo = getRuleGroupById(groupId);
			groupVo.setTenantId(tenantId);
//				if(vo.getIsSave()){
//					boolean exist = existRuleGroup(groupId);
//					log.info("------保存新下发规则。------");
//					if(!exist){
//						saveRuleGroup(groupVo,null);
//					}else{
//						updateRuleGroupStatus(groupVo.getId(), RuleStatus.RUNNING);
//					}
//				}
			ruleLoaderService.loadRuleGroup(groupVo);
		}
	}
	
	/**
	 * 客户端停用规则
	 * @param vo
	 */
	public void endRuleForMemory(Integer groupId,Integer tenantId){
		if(groupId != null){
//			if(!BlankUtil.isBlank(vo.getTaskContent())){
			RuleGroupVO groupVo = getRuleGroupById(groupId);
			groupVo.setTenantId(tenantId);
			log.info("------停用下发的规则------group id=" + groupVo.getId() );
//				if(vo.getIsSave()){
//					boolean exist = existRuleGroup(groupVo.getId());
//					if(exist){
//						updateRuleGroupStatus(groupVo.getId(), RuleStatus.STOP);
//					}
//				}
			ruleLoaderService.removeRuleGroup(groupVo);
		}
	}
	
	/**
	 * 更新rulegroup组,及下面的所有规则的关联的业务编码
	 */
	public void updateRuleGroupBizCode(Integer groupId, String relateBizCode) {
		ruleGroupService.updateRuleGroupBizCode(groupId, relateBizCode);
	}
	
//	public RuleGroupVO getRuleGroupInitExternal(Integer id) {
//		return ruleGroupFinderService.getRuleGroupInitExternal(id);
//	}
	
	public RuleGroupVO getRuleGroupNoInitExternal(Integer id) {
		return ruleGroupFinderService.getRuleGroupNoInitExternal(id);
	}
	
//	public List<RuleVO> getRulesByGroupId(Integer groupId) {
//		List<RuleVO> ruleVos = null;
//	    if(groupId != null) {
//	    	RuleGroup group = ruleGroupDao.get(groupId);
//	    	RuleSectionVO commonSectionVO = getMapper().map(group.getRuleSection(), RuleSectionVO.class); 
//	    	
//	    	List<Rule> rules = ruleDao.getRulesByGroup(groupId);
//	    	if(!BlankUtil.isBlank(rules)) {
//	    		ruleVos = new ArrayList<RuleVO>();
//	    		for(Rule rule : rules) {
//	    			RuleVO ruleVO = getMapper().map(rule, RuleVO.class);
//	    			ruleVO.setCommonSection(commonSectionVO);
//	    			ruleVos.add(ruleVO);
//	    		}
//	    	}
//	    }
//	    return ruleVos;
//	}
	
//	public List<RuleVO> getSimpleRulesByGroupId(Integer groupId) {
//		List<RuleVO> ruleVos = null;
//		
//		if(groupId != null) {
//	    	List<Rule> rules = ruleDao.getSimpleRulesByGroup(groupId);
//	    	if(!BlankUtil.isBlank(rules)) {
//	    		ruleVos = new ArrayList<RuleVO>();
//	    		for(Rule rule : rules) {
//	    			RuleVO ruleVO = getMapper().map(rule, RuleVO.class);
//	    			ruleVos.add(ruleVO);
//	    		}
//	    	}
//	    }
//		
//		return ruleVos;
//	}
	
	public List<RuleVO> getSimpleRulesByGroupIds(List<Integer> groupIds) {
		List<RuleVO> ruleVos = null;
		if(!BlankUtil.isBlank(groupIds)) {
	    	return ruleDao.getSimpleRulesByGroupIds(groupIds);
	    }
		return ruleVos;
	}
	
//	public String getRuleGroupJsonById(Integer id, Boolean escapeJs) {
//	    RuleGroupVO groupVO = getRuleGroupById(id);
//	    if(groupVO != null) {
//	    	String result = JsonUtil.objToJsonIgnoreNull(groupVO);
//	    	if(escapeJs != null && escapeJs == true) {
//	    		result = StringEscapeUtils.escapeJavaScript(result);
//	    	}
//	    	return result;
//	    }
//	    return null;
//	}
	
	public RuleGroupVO getRGWithDefinData(Integer id) {
		return ruleGroupFinderService.getRGWithDefinData(id);
	}
	
	public String getRGJsonWithDefineData(Integer id, Boolean escapeJs) {
		RuleGroupVO groupVO = getRGWithDefinData(id);
	    if(groupVO != null) {
	    	String result = JsonUtil.objToJsonIgnoreNull(groupVO);
	    	if(escapeJs != null && escapeJs == true) {
	    		result = StringEscapeUtils.escapeJavaScript(result);
	    	}
	    	return result;
	    }
	    return null;
	}
	
	public Integer saveRuleGroup(String groupVoStr, String deleteRuleStrs, String bizCode) {
		return ruleGroupSaverService.saveRuleGroup(groupVoStr, deleteRuleStrs, bizCode);
	}
	
	public Integer saveRuleGroup(RuleGroupVO groupVO, List<Integer> deleteRules) {
		return ruleGroupSaverService.saveRuleGroup(groupVO, deleteRules);
	}
	
	public Integer updateRuleGroupStatus(Integer id, RuleStatus status) {
		return ruleGroupService.updateRuleGroupStatus(id, status);
	}
	
	public void removeRuleGroupLogical(Integer groupId) {
		ruleGroupService.updateRuleGroupStatus(groupId, RuleStatus.DELETE);
	}
	
	public void removeRuleGroup(Integer groupId) {
		ruleGroupService.removeRuleGroup(groupId);
	}
	
	public RuleGroupVO copyRuleGroupById(Integer id) {
		return ruleGroupFinderService.copyRuleGroupById(id);
	}
	
	public String copyRuleGroupJsonById(Integer id, Boolean escapeJs) {
	    return ruleGroupFinderService.copyRuleGroupJsonById(id, escapeJs);
	}
	
	public String copyRuleGroupJsonWithDefineData(Integer id, Boolean escapeJS) {
		return ruleGroupFinderService.copyRuleGroupJsonWithDefineData(id, escapeJS);
	}
	
	public String testRuleGroup(String ruleGroup, String facts) {
		return ruleTestService.testRuleGroup(ruleGroup, facts);
	}
	
	public String testRuleGroup(String ruleGroupStr, List<Object> facts) {
		return ruleTestService.testRuleGroup(ruleGroupStr, facts);
	}
	
	public RuleGroupVO getTempGroupForTest(String ruleGroupStr, boolean needFakeId) {
		return ruleTestService.getTempGroupForTest(ruleGroupStr, needFakeId);
	}
	
	public RuleVO getRuleById(Integer id) {
		if(id != null) {
			Rule rule = ruleDao.get(id);
			if(rule != null) {
				return getMapper().map(rule, RuleVO.class);
			}
		}
		
	    return null;
	}
	
	public boolean existRuleGroup(Integer groupId) {
	    return ruleGroupDao.existRuleGroup(groupId);
	}

	@Override
	public List<RuleVO> getRuleByIds(List<Integer> ids) {
		if(ids.size() != 0) {
			List<Rule> rules = ruleDao.getRulesByIds(ids);
			List<RuleVO> vos = new ArrayList<RuleVO>();
			for(Rule r : rules){
				RuleVO vo = new RuleVO();
				BeanUtils.copyBeanToBean(vo, r);
				vos.add(vo);
			}
			return vos;
		}
		
	    return null;
	}
}
