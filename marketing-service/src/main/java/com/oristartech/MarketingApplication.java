package com.oristartech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MarketingApplication {
	
    public static void main( String[] args )    {
    	SpringApplication.run(MarketingApplication.class, args);
    }
}
