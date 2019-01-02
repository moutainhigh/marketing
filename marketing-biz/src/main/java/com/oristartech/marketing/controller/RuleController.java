package com.oristartech.marketing.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oristartech.marketing.components.constant.RestResponse;
import com.oristartech.marketing.service.IRuleService;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.vos.core.vo.RuleVO;
import com.oristartech.rule.vos.result.RuleResult;

@RestController
@RequestMapping("/rule")
public class RuleController {
	
	private Logger logger = LoggerFactory.getLogger(RuleController.class);

	@Autowired
	IRuleService ruleService;
	
	@RequestMapping(value = "/matchRule",method = RequestMethod.GET)
    public RestResponse<String> matchrule(@RequestParam(value = "tenantId", required = true) String tenantId,@RequestParam(value = "typeName", required = false) String typeName,@RequestParam(value = "facts") String facts) {
		
		String result = ruleService.matchRule(tenantId,typeName, facts);
		return RestResponse.createSuccessResult(result);
    }
	
//	@RequestMapping(value = "/matchRuleByGroupIds",method = RequestMethod.GET)
//    public RestResponse<List<RuleResult>> matchRuleByGroupIds(@RequestParam(value = "tenantId", required = true) String tenantId,
//    		List<Integer> ruleGroupIds,List<Object> facts) {
//		
//		List<RuleResult> results = ruleService.matchRuleByGroupIds(tenantId, ruleGroupIds, facts);
//		return RestResponse.createSuccessResult(results);
//    }
	
	@RequestMapping(value = "/getRuleFullInfo",method = RequestMethod.GET)
    public RestResponse<RuleVO> getRuleFullInfo(@RequestParam(value = "ruleId", required = true) Integer ruleId) {
		
		RuleVO results = ruleService.getRuleFullInfo(ruleId);
		return RestResponse.createSuccessResult(results);
    }
	
	@RequestMapping(value = "/getRuleBasicInfo",method = RequestMethod.GET)
    public RestResponse<String> getRuleBasicInfo(@RequestParam(value = "ruleId", required = true) Integer ruleId) {
		
		RuleVO results = ruleService.getRuleBasicInfo(ruleId);
		return RestResponse.createSuccessResult("s"+ruleId);
    }
}
