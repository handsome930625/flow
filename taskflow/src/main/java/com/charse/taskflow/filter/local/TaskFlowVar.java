package com.charse.taskflow.filter.local;

import java.util.UUID;

/**
 * description: 用来记录taskflow执行情况
 *
 * @author wangyj on 2018/4/13
 */
public class TaskFlowVar {
    /**
     * 开始时间
     */
    private long startTime;
    /**
     * 耗时
     */
    private long totalTime;
    /**
     * taskId
     */
    private String taskId;
    /**
     * 唯一id
     */
    private String uuid;
    /**
     * 自定义数据变量
     */
    private Object object;

    /**
     * 任务执行前方法
     */
    public void beforeExecute(String taskId) {
        this.startTime = System.currentTimeMillis();
        uuid = UUID.randomUUID().toString();
        this.taskId = taskId;
    }

    /**
     * 执行之后方法
     */
    public void afterExecute() {
        long endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
    }

    /**
     * <p>功能描述: 日志</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/14 17:59 </p>
     */
    public String log() {
        return "taskflow-id:" + taskId + "唯一性id:" + uuid + "耗时:" + totalTime + "ms";
    }

    public String getUuid() {
        return uuid;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
