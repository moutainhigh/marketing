package com.oristartech.marketing.controller;

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
import com.oristartech.marketing.service.IRuleExeStatisService;
import com.oristartech.rule.search.RuleExeStatisCondition;
import com.oristartech.rule.vos.core.vo.RuleExeTimeResultVO;

@RestController
@RequestMapping("/ruleStatis")
public class RuleExeStatisController {

	private Logger logger = LoggerFactory.getLogger(RuleExeStatisController.class);
	
	@Autowired
	IRuleExeStatisService ruleExeStatisService;
	
	@RequestMapping(value = "/addExeTime",method = RequestMethod.GET)
    public RestResponse<RuleExeTimeResultVO> addExeTime(@RequestParam(value = "ruleId", required = true) Integer ruleId,
    		@RequestParam(value = "amount", required = true) Integer amount) {
		
		RuleExeTimeResultVO results = ruleExeStatisService.addExeTime(ruleId, amount);
		return RestResponse.createSuccessResult(results);
    }
	
	@RequestMapping(value = "/addExeTimes",method = RequestMethod.GET)
    public RestResponse<RuleExeTimeResultVO> addExeTimes(@RequestParam(value = "ruleIds", required = true) String ruleIds,
    		@RequestParam(value = "amounts", required = true) String amounts) {
		
		RuleExeTimeResultVO results = ruleExeStatisService.addExeTime(ruleIds, amounts);
		return RestResponse.createSuccessResult(results);
    }
	
	@RequestMapping(value = "/sumExeTimes",method = RequestMethod.POST)
    public RestResponse<Long> sumExeTimes(@RequestBody RuleExeStatisCondition condition) {
		
		return RestResponse.createSuccessResult(ruleExeStatisService.sumExeTimes(condition));
    }
}
