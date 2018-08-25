package com.charse.taskflow.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 19:16
 * @Description: 任务处理器解析
 **/
public class InvokeNode implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 2602311055548127334L;

    /**
     * 任务执行类名
     */
    @XStreamAsAttribute()
    @XStreamAlias("class-name")
    private String className;

    /**
     * 任务beanId (spring ioc)
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
        return "InvokeNode{" +
                "className='" + className + '\'' +
                ", beanId='" + beanId + '\'' +
                '}';
    }
}