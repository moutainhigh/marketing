package com.oristartech.rule.core.match.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.core.cache.IRuleDataCache;
import com.oristartech.rule.core.core.dao.IRuleDao;
import com.oristartech.rule.core.core.model.Rule;
import com.oristartech.rule.core.core.service.IRuleActionService;
import com.oristartech.rule.core.executor.util.RuleExecutorDataHelper;
import com.oristartech.rule.core.match.factory.RuleExecutorFactory;
import com.oristartech.rule.core.match.service.IRuleFactPrepareService;
import com.oristartech.rule.core.match.service.IRuleService;
import com.oristartech.rule.core.result.filters.FilterUtil;
import com.oristartech.rule.core.ws.client.service.IRuleSystemWebServiceInvoker;
import com.oristartech.rule.drools.executor.IRuleExecutorService;
import com.oristartech.rule.drools.executor.context.FactsContainer;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleTypeVO;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

/**
 * 规则匹配service
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class RuleServiceImpl extends RuleBaseServiceImpl implements IRuleService {
	
	private static final String SERVICE_METHOD_NAME = "webserviceMethod";
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IRuleFactPrepareService ruleFactPrepareService;
	@Autowired
	private IRuleSystemWebServiceInvoker ruleSystemWebServiceInvoker;
	@Autowired
	private IRuleDataCache ruleDataCache;
	@Autowired
	private IRuleActionService ruleActionService;
	@Autowired
	private IRuleDao ruleDao;
	
	/**
	 * 匹配某规则组下的规则
	 * @param ruleId    规则id
	 * @param facts    业务属性数据和值
	 */
	public String matchRuleByGroupId(String tenantId,Integer ruleGroupId, String facts) {
		if(ruleGroupId == null || BlankUtil.isBlank(facts)) {
			return null;
		}
		List srcFacts = (List)JsonUtil.jsonArrayToList(facts, Map.class);
		return JsonUtil.objToJsonIgnoreNull(matchRuleByGroupId(tenantId,ruleGroupId, srcFacts));
	}
	
	public List<RuleResult> matchRuleByGroupId(String tenantId,Integer ruleGroupId, List<Object> facts) {
		log.info("<matchRuleByGroupId> ruleGroupId=" + ruleGroupId + " facts=" + JsonUtil.objToJson(facts));
		
		if(ruleGroupId == null || BlankUtil.isBlank(facts)) {
			return null;
		}
		
		RuleTypeVO ruleType = ruleDataCache.getRuleTypeByGroupId(ruleGroupId);
		FactsContainer factsContainer = ruleFactPrepareService.prepareFacts(facts, false, ruleType);
		
		RuleExecuteContext context = getRuleExecutorService(ruleType).processByGroupId(tenantId,ruleGroupId, ruleType.getName(), factsContainer);
	    return afterMatch(context, ruleType);
	}
	
	public List<RuleResult> matchRuleByGroupIds(String tenantId,List<Integer> ruleGroupIds, List<Object> facts) {
		log.info("<matchRuleByGroupIds> ruleGroupIds=" + JsonUtil.objToJson(ruleGroupIds) + " facts=" + JsonUtil.objToJson(facts));
		
		if(BlankUtil.isBlank(ruleGroupIds) || BlankUtil.isBlank(facts)) {
			return null;
		}
		//匹配都是同类的规则
		RuleTypeVO ruleType = ruleDataCache.getRuleTypeByGroupId(ruleGroupIds.get(0));		
		FactsContainer factsContainer = ruleFactPrepareService.prepareFacts(facts, false, ruleType);
		
		RuleExecuteContext context = getRuleExecutorService(ruleType).processByGroupIds(tenantId,ruleGroupIds, ruleType.getName(), factsContainer);
	    return afterMatch(context, ruleType);
	}
	
	public List<RuleResult> matchRuleByIds(String tenantId,List<Integer> ids, List<Object> facts) {
		log.info("<matchRuleByIds> ids=" + JsonUtil.objToJson(ids) + " facts=" + JsonUtil.objToJson(facts));
		
		if(BlankUtil.isBlank(ids) || BlankUtil.isBlank(facts)) {
			return null;
		}
		//匹配都是同类的规则
		RuleTypeVO ruleType = ruleDataCache.getRuleTypeByRuleId(ids.get(0));
		
		FactsContainer factsContainer = ruleFactPrepareService.prepareFacts(facts, false, ruleType);
		
		RuleExecuteContext context = getRuleExecutorService(ruleType).processByRuleIds(tenantId,ids, ruleType.getName(), factsContainer);
		//匹配指定组时, 不选优
		return afterMatch(context, ruleType);
	}
	
	/**
	 * 指定匹配某几条规则
	 * @param ids 规则id
	 * @param facts
	 * @return
	 */
	public String matchRuleById(String tenantId,Integer ruleId, String facts) {
		if(ruleId == null || BlankUtil.isBlank(facts)) {
			return null;
		}
		List srcFacts = (List)JsonUtil.jsonArrayToList(facts, Map.class);
		return JsonUtil.objToJsonIgnoreNull(matchRuleById(tenantId,ruleId, srcFacts));
	}
	
	public List<RuleResult> matchRuleById(String tenantId,Integer ruleId, List<Object> facts) {
		log.info("<matchRuleById> ruleId=" + ruleId + " facts=" + JsonUtil.objToJson(facts));
		if(BlankUtil.isBlank(facts)) {
			return null;
		}
		
		if(!existRule(ruleId)) {
			return null;
		}
		RuleTypeVO ruleType = ruleDataCache.getRuleTypeByRuleId(ruleId);
		
		FactsContainer factsContainer = ruleFactPrepareService.prepareFacts(facts, false, ruleType);
		
		RuleExecuteContext context = getRuleExecutorService(ruleType).processByRuleId(tenantId,ruleId, ruleType.getName(), factsContainer);
		//匹配指定组时, 不选优
		return afterMatch(context, ruleType);
	}
	
	/**
	 * 匹配某类型的规则
	 * @param type    规则内置类别
	 * @param facts    业务属性数据和值
	 */
	public String matchRule(String tenantId,String type, String facts) {
		if(BlankUtil.isBlank(facts)) {
			return null;
		}
		List srcFacts = (List)JsonUtil.jsonArrayToList(facts, Map.class);
		
		return JsonUtil.objToJsonIgnoreNull(matchRule(tenantId,type, srcFacts));
	}
	
	public List<RuleResult> matchRule(String tenantId,String typeName, List<Object> facts) {
		log.info("<matchRule> typeName=" + typeName + " facts=" + JsonUtil.objToJson(facts));
		
		long start = System.currentTimeMillis();
		if(BlankUtil.isBlank(facts)) {
			return null;
		}
		
		RuleTypeVO ruleType = BlankUtil.isBlank(typeName) 
				                 ? ruleDataCache.getDefaultRuleType() 
				                 : ruleDataCache.getRuleTypeByName(typeName);
				                 
		FactsContainer factsContainer = ruleFactPrepareService.prepareFacts(facts, false, ruleType);
		
		RuleExecuteContext context = getRuleExecutorService(ruleType).process(tenantId,ruleType.getName(), factsContainer);
		List<RuleResult> result = afterMatch(context, ruleType);
		
		if(log.isDebugEnabled()) {
			log.debug("------------use Rule time = " + (System.currentTimeMillis() - start ));
		}
		return result;
	}
	
	@Transactional
	public List<RuleResult> testRuleGroup(RuleGroupVO groupVO, String facts) {
		return testRuleGroup(groupVO, JsonUtil.jsonArrayToList(facts, Object.class));
	}
	
	@Transactional
	public List<RuleResult> testRuleGroup(RuleGroupVO groupVO, List<Object> facts) {
		log.info("<testRuleGroup> RuleGroupVO=" + groupVO.getId() + " facts=" + JsonUtil.objToJson(facts));
		long start = System.currentTimeMillis();
		if(BlankUtil.isBlank(facts)) {
			return null;
		}
		
		RuleTypeVO ruleType = ruleDataCache.getRuleTypeByName(groupVO.getRuleType());
		FactsContainer factsContainer = ruleFactPrepareService.prepareFacts(facts, true, ruleType);
		
		RuleExecuteContext context = getRuleExecutorService(ruleType).processWithGroupTest(groupVO, factsContainer);
		List<RuleResult> result = afterMatch(context, ruleType);
		
		if(log.isDebugEnabled()) {
			log.debug("------------use Rule time = " + (System.currentTimeMillis() - start ));
		}
		
		return result;
	}
	
	public RuleVO getRuleBasicInfo(Integer ruleId) {
		if(ruleId != null) {
			Rule rule = ruleDao.get(ruleId);
			if(rule != null) {
				return getMapper().map(rule, RuleVO.class);
			}
		}
		return null;
	}
	
	public RuleVO getRuleFullInfo(Integer ruleId) {
		if(ruleId != null) {
			Rule rule = ruleDao.get(ruleId);
			if(rule != null) {
				return getMapper().map(rule, RuleVO.class);
			}
		}
		return null;
	}
	
	public RuleVO getRuleBasicInfoInGroup(Integer groupId, Integer ruleId) {
		if(groupId != null && ruleId != null) {
			Rule rule = ruleDao.getRuleInGroup(groupId, ruleId);
			if(rule != null) {
				return getMapper().map(rule, RuleVO.class);
			}
		}
	    return null;
	}
	
	public boolean existRuleInGroup(Integer groupId, Integer ruleId) {
		if(groupId != null && ruleId != null) {
			return ruleDao.existRuleInGroup(groupId, ruleId);
		}
		return false;
	}
	
	public RuleVO getRuleFullInfoInGroup(Integer groupId, Integer ruleId) {
		if(groupId != null && ruleId != null) {
			Rule rule = ruleDao.getRuleInGroup(groupId, ruleId);
			if(rule != null) {
				return getMapper().map(rule, RuleVO.class);
			}
		}
	    return null;
	}
	
	public RuleWSResultVO invodeRuleExeAction(Integer ruleId, Long actionId, Map<String, Object> otherParams) {
		if(ruleId != null && actionId != null) {
			List<Long> actionIds = new ArrayList<Long>();
			actionIds.add(actionId);
			
			List<RuleActionVO> ruleActionVOs = ruleActionService.getActionVOsForInvoke(ruleId, actionIds);
			validateActionParams(ruleActionVOs);
			RuleActionVO actionVO = ruleActionVOs.get(0);
			if(otherParams == null) {
				otherParams = new HashMap<String, Object>();
			}
			
			RuleVO ruleVO = ruleDataCache.getRuleById(ruleId);
			RuleWSResultVO retVO = callAction(ruleVO, actionVO, otherParams);
			if(retVO == null) {
				retVO = new RuleWSResultVO();
				retVO.setFail();
			}
			return retVO;
			
		} else {
			RuleWSResultVO vo = new RuleWSResultVO();
			vo.setStatus(RuleWSResultVO.FAIL);
			vo.setMessage("请提供规则id或动作id");
			return vo;
		}
	}
	
	@Override
	public RuleWSResultVO invodeExeActions(Integer ruleId, Map<String, Object> otherParams) {
		return this.invodeExeActions(ruleId, otherParams, false);
	}
	
	@Override
	public RuleWSResultVO invodeExeActions(Integer ruleId, Map<String, Object> otherParams, boolean ignoreFail) {
		RuleWSResultVO retVo = new RuleWSResultVO();
		if(ruleId != null ) {
			//从缓存查
			RuleVO ruleVO = RuleExecutorDataHelper.getRuleVO(ruleId);
			if(ruleVO == null) {
				retVo.setFail();
				retVo.setCode("RULE_NOT_EXIST");
				retVo.setMessage("规则不存在");
				return retVo;
			}
			List<RuleActionVO> ruleActionVOs = ruleVO.findAllActions();
			List<Long> actionIds = new ArrayList<Long>();
			for(RuleActionVO action : ruleActionVOs) {
				actionIds.add(action.getId());
			}
			//需要查询所有参数
			ruleActionVOs = ruleActionService.getActionVOsForInvoke(actionIds);
			if(otherParams == null) {
				otherParams = new HashMap<String, Object>();
			}
			retVo.setOk();
			for(RuleActionVO actionVO : ruleActionVOs) {
				RuleWSResultVO tempVo = callAction(ruleVO, actionVO, otherParams);
				if(tempVo == null) {
					//为null，表示该action不是要service方式执行的方法。
					continue;
				}
				if(!ignoreFail && !tempVo.isOk()) {
					retVo.setFail();
					retVo.setMessage(tempVo.getMessage());
					break;
				}
			}
		} else {
			retVo.setFail();
			retVo.setCode("NULL_RULE_ID");
			retVo.setMessage("规则ID为空");
		}
		return retVo;
	}
	
	private RuleWSResultVO callAction(RuleVO ruleVO, RuleActionVO actionVO, Map<String, Object> otherParams) {
		Map<String, Object> anParams = actionVO.getAllParameterMapObjWithConfig();
		String serviceMethod = (String)anParams.get(SERVICE_METHOD_NAME);
		
		if( BlankUtil.isBlank(serviceMethod) ) {
			return null;
		}
		otherParams.put("remark", ruleVO.getRuleGroupName() + "-" + ruleVO.getName() + ":" + ruleVO.getBizOrderCode());
		otherParams.put("ruleGroupName", ruleVO.getRuleGroupName());
		otherParams.put("ruleName", ruleVO.getName());
		otherParams.put("ruleId", ruleVO.getId());
		otherParams.put("actionId", actionVO.getId());
		otherParams.put("funcId", actionVO.getActionFnId());
		otherParams.put("bizOrderCode", ruleVO.getBizOrderCode());
		anParams.put("otherParams", otherParams);
		log.info("<callAction> serviceMethod" + serviceMethod + " anParams=" + JsonUtil.objToJson(anParams));
		return ruleSystemWebServiceInvoker.invodeServiceWithResultVO(serviceMethod, anParams);
	}
	
	/**
	 * 验证action 执行型方法 参数
	 * @param ruleActionVOs
	 */
	private void validateActionParams(List<RuleActionVO> ruleActionVOs) {
		if(BlankUtil.isBlank(ruleActionVOs)) {
			throw new RuleExecuteRuntimeException("不存执行方法");
		}
		
		for(RuleActionVO actionVO : ruleActionVOs) {
			Map<String, Object> anParams = actionVO.getAllParameterMapObjWithConfig();
			if(BlankUtil.isBlank(anParams)) {
				throw new RuleExecuteRuntimeException("数据库没设置方法参数!");
			}
			//检查是否有service 方法
			if(!anParams.containsKey(SERVICE_METHOD_NAME)) {
				throw new RuleExecuteRuntimeException("数据库没设置方法!");
			}
		}
	}
	
	public String searchRule(RuleSearchCondition condition) {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	public String searchRuleFilterResult(String type, String facts) {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	/**
	 * 返回结果前处理
	 * @param context
	 * @param isFilter 是否过滤最优
	 * @return
	 */
	private List<RuleResult> afterMatch(RuleExecuteContext context, RuleTypeVO ruleType ) {
		if(context != null ) {
			if(BlankUtil.isBlank(context.getRuleResults())) {
				return null;
			}
			
			//若没必要,无须设置太多过滤器,影响性能
			FilterUtil.createRuleResultFilterChain(ruleType).doFilter(context);
			
			if(BlankUtil.isBlank(context.getRuleResults())) {
				return null;
			}
			return context.getRuleResults();
		}
		return null;
	}
	
	public IRuleExecutorService getRuleExecutorService(RuleTypeVO ruleType) {
    	return RuleExecutorFactory.chooseRuleExecutor(ruleType);
    }

	public boolean existRule(Integer ruleId) {
	    if(ruleId != null) {
	    	return ruleDao.exists(ruleId);
	    }
	    return false;
	}

}
