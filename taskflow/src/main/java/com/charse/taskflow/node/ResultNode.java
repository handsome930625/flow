package com.charse.taskflow.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 19:16
 * @Description: 任务处理结果路由解析
 **/
public class ResultNode implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6569167310143979310L;

    /**
     * 路由名称，支持EL
     */
    @XStreamAsAttribute()
    private String road;

    /**
     * 下一个任务
     */
    @XStreamAsAttribute()
    @XStreamAlias("next-stop")
    private String nextStop;

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getNextStop() {
        return nextStop;
    }

    public void setNextStop(String nextStop) {
        this.nextStop = nextStop;
    }

    @Override
    public String toString() {
        return "ResultNode{" +
                "road='" + road + '\'' +
                ", nextStop='" + nextStop + '\'' +
                '}';
    }
}