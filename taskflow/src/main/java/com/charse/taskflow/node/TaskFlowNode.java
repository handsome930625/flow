package com.charse.taskflow.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: wangyj
 * @Date: 2018/4/11 19:16
 * @Description: 任务流解析
 **/
@XStreamAlias("taskflow")
public class TaskFlowNode implements Serializable {
    /**
     * 序列id
     */
    private static final long serialVersionUID = 6962765418746113739L;
    /**
     * 任务流ID
     */
    @XStreamAsAttribute()
    private String id;

    /**
     * 启动任务ID
     */
    @XStreamAsAttribute()
    @XStreamAlias("start-task")
    private String startTask;
    /**
     * 是否校验 任务id可达
     */
    @XStreamAsAttribute()
    private Boolean validate;
    /**
     * 是否使用spring ioc容器
     */
    @XStreamAsAttribute()
    @XStreamAlias("use-spring")
    private Boolean useSpring;
    /**
     * 过滤器集合
     */
    private FiltersNode filters = new FiltersNode();

    /**
     * 任务集合
     */
    @XStreamImplicit(itemFieldName="task")
    private List<TaskNode> taskList = new ArrayList<>();

    public void addTask(TaskNode task) {
        taskList.add(task);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTask() {
        return startTask;
    }

    public void setStartTask(String startTask) {
        this.startTask = startTask;
    }

    public FiltersNode getFilters() {
        return filters;
    }

    public void setFilters(FiltersNode filters) {
        this.filters = filters;
    }

    public List<TaskNode> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskNode> taskList) {
        this.taskList = taskList;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public Boolean getUseSpring() {
        return useSpring;
    }

    public void setUseSpring(Boolean useSpring) {
        this.useSpring = useSpring;
    }

    @Override
    public String toString() {
        return "TaskFlowNode{" +
                "id='" + id + '\'' +
                ", startTask='" + startTask + '\'' +
                ", validate=" + validate +
                ", useSpring=" + useSpring +
                ", filters=" + filters +
                ", taskList=" + taskList +
                '}';
    }
}