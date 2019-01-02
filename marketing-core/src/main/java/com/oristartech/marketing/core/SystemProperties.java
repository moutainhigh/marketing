package com.oristartech.marketing.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取系统配置文件内容
 */
public class SystemProperties {
    private static final Logger LOG = LoggerFactory.getLogger(SystemProperties.class);

    private static final String PROPERTIES_FILE = "/rule-system-config/system.properties";

    private static SystemProperties INSTANCE;

    private Properties prop = null;

    private SystemProperties() throws IOException {
        File properties = getPropertiesFile();
        FileInputStream stream = new FileInputStream(properties);

        prop = new Properties();
        prop.load(stream);

        stream.close();

        LOG.debug("System properties loaded from: " + properties.getPath());

    }

    public static SystemProperties getInstance()  throws IOException{
        if (INSTANCE == null)
            INSTANCE = new SystemProperties();
        return INSTANCE;
    }

    public static File getPropertiesFile() {
        URL url = SystemProperties.class.getResource(PROPERTIES_FILE);
        return new File(url.getFile());
    }

    public String get(String key) {
        String value = prop.getProperty(key);
        return value;
    }
    
    public String get(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
}

