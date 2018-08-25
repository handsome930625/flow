package com.charse.taskflow.flow.impl;

import com.charse.taskflow.filter.Filter;
import com.charse.taskflow.filter.FilterChain;
import com.charse.taskflow.flow.ITask;
import com.charse.taskflow.flow.ITaskFlow;
import com.charse.taskflow.flow.TaskFlowContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 20:35
 * @Description: 默认任务流实现
 **/
public class DefaultTaskFlow implements ITaskFlow {
    /**
     * flow id
     */
    private String id;
    /**
     * 过滤器集合
     */
    private List<Filter> filters;
    /**
     * 第一个任务的id
     */
    private String startTaskId;
    /**
     * flow 中 任务的集合
     */
    private Map<String, ITask> taskMap = new HashMap<>();

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void executeTask(Object params, TaskFlowContext taskFlowContext) throws Exception {
        FilterChain filterChain = new FilterChain(filters);
        filterChain.doFilter(params, taskFlowContext);
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public ITask getStartTask() {
        return taskMap.get(startTaskId);
    }

    @Override
    public ITask getTask(String taskId) {
        return taskMap.get(taskId);
    }

    @Override
    public void setStartTaskId(String startTaskId) {
        this.startTaskId = startTaskId;
    }

    public Map<String, ITask> getTaskMap() {
        return taskMap;
    }

    @Override
    public void setTask(Map<String, ITask> taskMap) {
        this.taskMap = taskMap;
    }
}