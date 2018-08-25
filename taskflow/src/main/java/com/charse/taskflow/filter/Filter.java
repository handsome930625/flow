package com.charse.taskflow.filter;


import com.charse.taskflow.flow.TaskFlowContext;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 21:06
 * @Description: 过滤器接口
 * 用户自定义过滤器必须实现这个接口，
 * 验证成功调用 filterChain.doFilter 方法
 * 验证失败 return 或者 方法执行结束 就会返回
 **/
public interface Filter<P> {
    /**
     * 执行过滤
     * 验证成功调用 filterChain.doFilter 方法
     * 验证失败 return 或者 方法执行结束 就会返回
     *
     * @param params          用户参数
     * @param taskFlowContext 任务流上下文
     * @param filterChain     过滤链
     * @throws Exception 执行链时发现错误
     */
    void doFilter(P params, TaskFlowContext taskFlowContext, FilterChain filterChain) throws Exception;
}
