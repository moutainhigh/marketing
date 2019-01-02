package com.oristartech.marketing.components.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oristartech.marketing.components.util.OSSUploadTools;

/**
 * @Auther: hexu
 * @Date: 2018/9/10 17:49
 * @Description:
 */
@Configuration
public class OSSConfig {

    @Bean
    public OSSUploadTools myService() {
        return new OSSUploadTools();
    }

}
