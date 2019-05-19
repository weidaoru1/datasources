package com.datasources.common.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class SystemConfigSupport {
    private static Properties systemConfigProperties = new Properties();
    @Bean
    public void process(){
        try {
            systemConfigProperties = PropertiesLoaderUtils.loadAllProperties("systemConfig.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getSystemConfig (String key){
        String res = "";
        try {
            res = new String(systemConfigProperties.getProperty(key).getBytes("iso-8859-1"), "gbk");
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
