package com.oristartech.marketing.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.oristartech.marketing.core.config.properties.DruidProperties;


/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    /**
     * 单数据源连接池配置
     */
    @Bean
    public DruidDataSource dataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        System.out.println("single--------------"+dataSource.getUrl());
        return dataSource;
    }
}

