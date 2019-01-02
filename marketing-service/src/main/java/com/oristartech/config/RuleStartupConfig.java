package com.oristartech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.rule.core.init.service.IRuleSystemInitService;

@Component
public class RuleStartupConfig implements CommandLineRunner{

	@Autowired
	IMarketingService marketingService;
	@Autowired
	IRuleSystemInitService ruleSystemInitService;
	
	public void run(String... args) throws Exception {
		ruleSystemInitService.init();
	}

}
