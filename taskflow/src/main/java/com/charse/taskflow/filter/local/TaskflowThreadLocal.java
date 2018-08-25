package com.charse.taskflow.filter.local;


/**
 * @Author: wangyj
 * @Date: 2018/4/10 22:30
 * @Description: taskflow 线程变量
 **/
public class TaskflowThreadLocal {

    /**
     * threadlocal
     */
    private static final ThreadLocal<TaskFlowVar> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * <p>功能描述: 获取taskflow线程变量</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/13 18:40 </p>
     *
     * @return taskflow 线程变量
     */
    public static TaskFlowVar getTaskFlowVar() {
        TaskFlowVar var = THREAD_LOCAL.get();
        if (var == null) {
            var = new TaskFlowVar();
            THREAD_LOCAL.set(var);
        }
        return var;
    }

    /**
     * <p>功能描述: 移除线程变量</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/13 18:41 </p>
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
