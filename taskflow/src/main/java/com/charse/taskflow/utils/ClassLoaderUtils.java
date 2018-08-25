package com.charse.taskflow.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description:
 *
 * @author 王亦杰（yijie.wang01@ucarinc.com）
 * @version 1.0
 * @date 2018/8/25 9:12
 */
public class ClassLoaderUtils {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtils.class);
    /**
     * 本地上下文
     */
    private static Map<String, Object> localIoc = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getObject(String className) throws ClassNotFoundException {
        if (StringUtils.isBlank(className)) {
            throw new IllegalArgumentException("className must not be null");
        }
        try {
            if (localIoc.containsKey(className)) {
                return (T) localIoc.get(className);
            }
            T t = (T) Class.forName(className).newInstance();
            // 并发的时候可能会出现多例，但是之后都会被gc掉，所以可以忽略
            localIoc.put(className, t);
            return t;
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Class not found,className is :{}", className);
                throw new ClassNotFoundException("Class not found,className is " + ":" + className);
            }
        }
        return null;
    }
}
