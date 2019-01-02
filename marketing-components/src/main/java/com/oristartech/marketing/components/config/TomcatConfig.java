package com.oristartech.marketing.components.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 *
 */
@Configuration
public class TomcatConfig {

//    @Value("${spring.server.MaxFileSize}")
//    private String MaxFileSize;
//    @Value("${spring.server.MaxRequestSize}")
//    private String MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("50MB");// KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }

}
