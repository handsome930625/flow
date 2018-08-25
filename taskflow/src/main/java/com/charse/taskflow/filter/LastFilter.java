package com.charse.taskflow.filter;

import com.charse.taskflow.flow.ITask;
import com.charse.taskflow.flow.ITaskFlow;
import com.charse.taskflow.flow.TaskFlowContext;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 21:57
 * @Description: 尾过滤器 工作过滤器
 **/
public class LastFilter implements Filter<Object> {

    @Override
    public void doFilter(Object params, TaskFlowContext taskFlowContext, FilterChain filterChain) throws Exception {
        ITaskFlow taskFlow = taskFlowContext.getTaskFlow();
        taskFlowContext.writeNextTaskParameter(params);
        ITask currentTask = taskFlow.getStartTask();
        while (currentTask != null) {
            Object resultValue = currentTask.invokeMethod(taskFlowContext.getParam(), taskFlowContext);
            currentTask = currentTask.nextTask(resultValue, taskFlowContext.getParam(), taskFlowContext);
        }
        taskFlowContext.writeNextTaskParameter(null);
    }
}
