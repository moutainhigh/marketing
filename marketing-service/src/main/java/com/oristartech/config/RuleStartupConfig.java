package com.oristartech.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.rule.core.init.service.IRuleSystemInitService;

@Component
public class RuleStartupConfig implements CommandLineRunner{

	@Autowired
    private DefaultMQProducer defaultMQProducer;
	@Autowired
	IMarketingService marketingService;
	@Autowired 
	IRuleSystemInitService ruleSystemInitService;
	
	public void run(String... args) throws Exception {
		ruleSystemInitService.init();
//		for (int i = 0; i < 10000; i++) {
//	        Message msg = new Message("MKT_RULE", "transaction", "KEY",("aa"+i).getBytes());
//	        Thread.sleep(3000);
//	        SendResult sendResult = defaultMQProducer.send(msg);  
//    	}
	}

}
