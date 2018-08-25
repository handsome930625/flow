package com.charse.taskflow.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 19:16
 * @Description: 过滤器解析
 **/
public class FilterNode implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 3683634682417487302L;

    /**
     * 过滤器类名
     */
    @XStreamAsAttribute()
    @XStreamAlias("class-name")
    private String className;

    /**
     * 过滤器beanId (spring ioc)
     */
    @XStreamAsAttribute()
    @XStreamAlias("bean-id")
    private String beanId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    @Override
    public String toString() {
        return "FilterNode{" +
                "className='" + className + '\'' +
                ", beanId='" + beanId + '\'' +
                '}';
    }
}