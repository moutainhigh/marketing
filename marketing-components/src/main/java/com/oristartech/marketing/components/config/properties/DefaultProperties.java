package com.oristartech.marketing.components.config.properties;

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
@PropertySource(value = {"classpath:/redis.properties",
        "classpath:/dubbo.properties",
        "classpath:/httpclient.properties",
        "classpath:/oss.properties",
        "classpath:/rocketmq.properties"}, ignoreResourceNotFound = true)
public class DefaultProperties {

    @Bean
    @ConfigurationProperties(prefix = "spring.dubbo")
    public DubboProperties dubboProperties() {
        return new DubboProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.cache")
    public RedisProperties redisProperties() {
        return new RedisProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "http")
    public HttpClientProperties httpClientProperties() {
        return new HttpClientProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "oss")
    public OSSProperties ossProperties() {
        return new OSSProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "rocketmq.producer")
    public RocketmqProducerProperties rocketmqProducerProperties() {
        return new RocketmqProducerProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "rocketmq.consumer")
    public RocketmqConsumerproperties rocketmqConsumerproperties() {
        return new RocketmqConsumerproperties();
    }

}
