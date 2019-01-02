package com.oristartech.marketing.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 默认的配置
 *
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
@Configuration
@PropertySource(value = {"classpath:/jdbc.properties"}, ignoreResourceNotFound = true)
public class DefaultProperties {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidProperties druidProperties() {
        return new DruidProperties();
    }

}
