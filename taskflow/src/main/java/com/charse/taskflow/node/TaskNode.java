package com.charse.taskflow.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * description:
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/8/24 14:44
 */
public class TaskNode implements Serializable {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = -1698408320604481298L;
    /**
     * 任务ID
     */
    @XStreamAsAttribute()
    @XStreamAlias("task-id")
    private String taskId;

    /**
     * 任务类名(保留)
     */
    @XStreamAsAttribute()
    @XStreamAlias("class-name")
    private String className;

    /**
     * 任务处理器
     */
    private InvokeNode invoke;

    /**
     * 任务路由集合
     */
    private ResultsNode results;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public InvokeNode getInvoke() {
        return invoke;
    }

    public void setInvoke(InvokeNode invoke) {
        this.invoke = invoke;
    }

    public ResultsNode getResults() {
        return results;
    }

    public void setResults(ResultsNode results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "TaskNode{" +
                "taskId='" + taskId + '\'' +
                ", className='" + className + '\'' +
                ", invoke=" + invoke +
                ", results=" + results +
                '}';
    }
}
