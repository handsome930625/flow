package com.charse.taskflow.flow;

import java.util.Map;

/**
 * description: 这边使用了泛型，用户自定义入参和返回值
 * 定义一个taskflow的基础方法
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/8/24 14:43
 */
public interface ITaskFlow {

    /**
     * <p>功能描述: 任务流id</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/11 20:47 </p>
     *
     * @return 任务流id
     */
    String getId();

    /**
     * <p>功能描述: 执行每一个task步骤</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/10 22:38 </p>
     *
     * @param params          用户自定义参数
     * @param taskFlowContext taskflow 上下文
     * @throws Exception 执行步骤会出现的异常
     */
    void executeTask(Object params, TaskFlowContext taskFlowContext) throws Exception;

    /**
     * <p>功能描述: 获取开始任务 </p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/11 22:11 </p>
     *
     * @return 获取开始任务
     */
    ITask getStartTask();

    /**
     * <p>功能描述: 设置开始任务id </p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/11 22:11 </p>
     *
     * @param startTaskId 开始任务id
     */
    void setStartTaskId(String startTaskId);

    /**
     * <p>功能描述: 根据任务ID获取任务</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/11 22:12 </p>
     *
     * @param taskId 任务id
     * @return 根据任务ID获取任务
     */
    ITask getTask(String taskId);

    /**
     * <p>功能描述: 设置任务</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/11 22:17 </p>
     *
     * @param taskMap xml 中 task 配置
     */
    void setTask(Map<String, ITask> taskMap);
}
