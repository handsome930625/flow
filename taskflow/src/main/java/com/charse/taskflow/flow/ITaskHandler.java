package com.charse.taskflow.flow;

/**
 * description: 这边使用了泛型，用户自定义入参和返回值
 * 定义一个taskflow的每个task的处理类要实现的接口
 * 泛型P代表请求参数
 *
 * @author 王亦杰（yijie.wang01@ucarinc.com）
 * @version 1.0
 * @date 2018/8/24 14:43
 */
public interface ITaskHandler<P> {

    /**
     * 执行任务处理逻辑
     *
     * @param params          用户参数
     * @param taskFlowContext 任务流上下文
     * @return 方法返回值 决定走哪个分支
     * @throws Exception 可能会抛出的异常
     */
    String handle(P params, TaskFlowContext taskFlowContext) throws Exception;


}