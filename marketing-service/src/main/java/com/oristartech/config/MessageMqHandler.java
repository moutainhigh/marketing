package com.oristartech.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.components.listener.RocketmqConsumer;

@Component
public class MessageMqHandler implements RocketmqConsumer {

	private static Logger logger = LoggerFactory.getLogger(MessageMqHandler.class);

	@Override
	public void excute(String jsonData, String topic) {
		System.out.println(jsonData);
	}

}
