package com.oristartech.marketing.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHelper implements ApplicationContextAware {

	private static ApplicationContext context;
	
	
	public static ApplicationContext getContext() {
    	return context;
    }

	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		ApplicationContextHelper.context = appContext;
	}
}
