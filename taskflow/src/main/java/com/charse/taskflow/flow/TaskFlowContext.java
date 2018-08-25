package com.charse.taskflow.flow;

import java.util.HashMap;
import java.util.Map;

/**
 * description: taskflow 上下文信息传递
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/8/24 14:43
 */
public class TaskFlowContext {
    /**
     * 执行的taskflow
     */
    private ITaskFlow taskFlow;
    /**
     * 下个任务请求参数
     */
    private Object param;
    /**
     * 任务结果集
     */
    private Map<String, Object> returnValues = new HashMap<>();

    public TaskFlowContext(ITaskFlow taskFlow) {
        this.taskFlow = taskFlow;
    }

    public Map<String, Object> getReturnValues() {
        return returnValues;
    }


    /**
     * 每一步设置返回值
     *
     * @param fieldName 返回值所对应的对象的字段名
     * @param value     属性值
     */
    public void addStepReturnValue(String fieldName, Object value) {
        returnValues.put(fieldName, value);
    }

    /**
     * description: 包内访问权限，获取整个工作流
     *
     * @return 整个工作流
     * @author 王亦杰
     * @date 2018/8/25 13:24
     */
    public ITaskFlow getTaskFlow() {
        return taskFlow;
    }

    public void writeNextTaskParameter(Object param) {
        this.param = param;
    }

    public Object getParam() {
        return param;
    }
}
