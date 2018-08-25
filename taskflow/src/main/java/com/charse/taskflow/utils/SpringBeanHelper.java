package com.charse.taskflow.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.naming.OperationNotSupportedException;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 21:32
 * @Description: 获取xml上注册的bean
 **/
public class SpringBeanHelper implements ApplicationContextAware {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBeanHelper.class);
    /**
     * spring 上下文
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * <p>功能描述: 如果有beanId 选择去spring ioc 容器中获取，如果没有选择自己加载</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/11 21:40 </p>
     *
     * @param beanId spring beanId
     * @param clazz  bean的类型
     * @return bean 的例子
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId, Class<T> clazz) throws ClassNotFoundException, OperationNotSupportedException {
        if (StringUtils.isBlank(beanId)) {
            throw new IllegalArgumentException("beanId must not be null");
        }
        if (applicationContext == null) {
            throw new OperationNotSupportedException("SpringBeanHelper is not in spring ioc,please add it to spring " +
                    "ioc; use xml config or annotation");
        }
        T bean = applicationContext.getBean(beanId, clazz);
        if (bean == null) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Class not found,beanId is :{}", beanId);
                throw new ClassNotFoundException("Class not found,beanId is " + ":" + beanId);
            }
        }
        return bean;
    }

}


