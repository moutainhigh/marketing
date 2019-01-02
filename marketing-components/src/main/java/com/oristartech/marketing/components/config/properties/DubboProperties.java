package com.oristartech.marketing.components.config.properties;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
//@ConfigurationProperties(prefix = DubboProperties.DUBBO_PREFIX)
public class DubboProperties {
//    public static final String DUBBO_PREFIX = "spring.dubbo";

    private String scan;

    private ApplicationConfig application;

    private ProtocolConfig protocol;

    private RegistryConfig registry;

    private  int timeout=3000;
    private  int  retries=3;
    private  int delay=-1;
    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public ProtocolConfig getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolConfig protocol) {
        this.protocol = protocol;
    }

    public RegistryConfig getRegistry() {
        return registry;
    }

    public void setRegistry(RegistryConfig registry) {
        this.registry = registry;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
