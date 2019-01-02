package com.oristartech.marketing.components.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.oristartech.marketing.components.config.properties.DubboProperties;
/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
@Configuration
@ConditionalOnClass({AnnotationBean.class,ApplicationConfig.class,ProtocolConfig.class,RegistryConfig.class,ProviderConfig.class})
public class DubboAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(AnnotationBean.class)//容器中如果没有这个类,那么自动配置这个类
    public static AnnotationBean annotationBean(@Value("${spring.dubbo.scan.basePackages}")String packageName) {
        System.out.println("-------------------------"+packageName+"-----------------------------");
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(packageName);
        return annotationBean;
    }

    @Bean
    @ConditionalOnMissingBean(ApplicationConfig.class)//容器中如果没有这个类,那么自动配置这个类
    public ApplicationConfig applicationConfig( DubboProperties prop) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        System.out.println("----"+prop.getApplication().getName());
        applicationConfig.setName(prop.getApplication().getName());
        return applicationConfig;
    }

    @Bean
    @ConditionalOnMissingBean(ProtocolConfig.class)//容器中如果没有这个类,那么自动配置这个类
    public ProtocolConfig protocolConfig( DubboProperties prop) {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(prop.getProtocol().getName());
        System.out.println(prop.getProtocol().getName()+"======");
        protocolConfig.setPort(prop.getProtocol().getPort());
        return protocolConfig;
    }

    @Bean
    @ConditionalOnMissingBean(RegistryConfig.class)//容器中如果没有这个类,那么自动配置这个类
    public RegistryConfig registryConfig( DubboProperties prop) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(prop.getRegistry().getAddress());
        return registryConfig;
    }
    @Bean
    public ProviderConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ProtocolConfig protocolConfig,DubboProperties prop) {
        System.out.println("providerConfig 配置=====");
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(prop.getTimeout());
        providerConfig.setRetries(prop.getRetries());
        providerConfig.setDelay(prop.getDelay());
        providerConfig.setApplication(applicationConfig);
        providerConfig.setRegistry(registryConfig);
        providerConfig.setProtocol(protocolConfig);
        return providerConfig;
    }
}