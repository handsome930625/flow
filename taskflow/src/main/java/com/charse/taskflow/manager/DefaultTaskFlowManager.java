package com.charse.taskflow.manager;

import com.charse.taskflow.builder.BaseBuilder;
import com.charse.taskflow.builder.xml.XmlTaskFlowBuilder;
import com.charse.taskflow.exception.TaskFlowNotFoundException;
import com.charse.taskflow.flow.ITaskFlow;
import com.charse.taskflow.flow.TaskFlowContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangyj
 * @Date: 2018/4/10 22:30
 * @Description: 默认管理器的实现
 **/
public class DefaultTaskFlowManager implements ITaskFlowManager {

    /**
     * 资源文件
     */
    private String resources;
    /**
     * taskflow map
     */
    private Map<String, ITaskFlow> taskFlowMap;

    public DefaultTaskFlowManager(String resource) throws Exception {
        this.resources = resource;
        initManager();
    }

    @Override
    public void initManager() throws Exception {
        BaseBuilder configure = new XmlTaskFlowBuilder(this.resources);
        List<ITaskFlow> taskFlowList = configure.buildTaskFlows();
        Map<String, ITaskFlow> taskFlowMap = new HashMap<>();
        for (ITaskFlow taskFlow : taskFlowList) {
            taskFlowMap.put(taskFlow.getId(), taskFlow);
        }
        this.taskFlowMap = taskFlowMap;
    }

    @Override
    public Map<String, Object> executeFlow(String taskFlowId, Object params) throws Exception {
        ITaskFlow taskFlow = taskFlowMap.get(taskFlowId);
        if (taskFlow == null) {
            throw new TaskFlowNotFoundException("'taskFlowId - " + taskFlowId + "'不存在");
        }
        TaskFlowContext taskFlowContext = new TaskFlowContext(taskFlow);
        taskFlow.executeTask(params, taskFlowContext);
        return taskFlowContext.getReturnValues();
    }

    @Override
    public void destroyManager() {

    }
}
