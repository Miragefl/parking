package com.viscum.device.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * package: com.mobilefly.facecapture.tools
 * author : CaineZhu
 * mail   : CaineZhu@me.com
 * date   : 2018/1/13 17:09
 * desc   : SpringTool
 */


@Component
public class SpringEnvironmentUtil implements EnvironmentAware {

    private static Environment env = null;

    public static Environment getEnvironment() {
        return env;
    }

    public static Object getProperty(String property) {
        return env.getProperty(property);
    }

    public static String getPropertyOrDefault(String property, String defaultValue) {
        return env.getProperty(property, defaultValue);
    }

    public static <T> T getProperty(String val, Class<T> className) {
        return env.getProperty(val,className);
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringEnvironmentUtil.env = environment;
    }
}
