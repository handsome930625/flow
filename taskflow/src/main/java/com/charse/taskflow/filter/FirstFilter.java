package com.charse.taskflow.filter;

import com.charse.taskflow.filter.local.TaskFlowVar;
import com.charse.taskflow.filter.local.TaskflowThreadLocal;
import com.charse.taskflow.flow.ITaskFlow;
import com.charse.taskflow.flow.TaskFlowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 21:06
 * @Description: 头过滤器
 **/
public class FirstFilter implements Filter<Object> {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstFilter.class);

    @Override
    public void doFilter(Object params, TaskFlowContext taskFlowContext, FilterChain filterChain) throws Exception {
        // 获取线程变量 记录访问时间，taskflow过程中可能存在的参数
        ITaskFlow taskFlow = taskFlowContext.getTaskFlow();
        String taskFlowId = taskFlow.getId();
        TaskFlowVar taskFlowVar = TaskflowThreadLocal.getTaskFlowVar();
        taskFlowVar.beforeExecute(taskFlowId);
        try {
            filterChain.doFilter(params, taskFlowContext);
            taskFlowVar.afterExecute();
            if (LOGGER.isInfoEnabled()) {
                // 打印日志
                LOGGER.info(taskFlowVar.log());
            }
        } catch (Exception e) {
            LOGGER.error("taskFlowId:{} uuid:{}出现错误{}", taskFlowId, taskFlowVar.getUuid(), e.getMessage(), e);
            throw e;
        }
    }
}