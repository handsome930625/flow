package com.charse.taskflow.flow.impl;

import com.charse.taskflow.exception.ErrorHandlerException;
import com.charse.taskflow.flow.ITask;
import com.charse.taskflow.flow.ITaskHandler;
import com.charse.taskflow.flow.TaskFlowContext;
import com.charse.taskflow.node.ResultNode;
import com.charse.taskflow.utils.SpringExpressionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 20:35
 * @Description: 默认任务实现
 **/
public class DefaultTask implements ITask {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 结果解析类
     */
    private List<ResultNode> resultNodeList;

    /**
     * 调用类
     */
    private Object invokeObject;

    @SuppressWarnings("unchecked")
    @Override
    public Object invokeMethod(Object params, TaskFlowContext taskFlowContext) throws Exception {
        if (!(invokeObject instanceof ITaskHandler)) {
            throw new ErrorHandlerException(invokeObject.getClass().getName() + "don't implement ITaskHandler interface");
        }
        ITaskHandler taskHandler = (ITaskHandler) invokeObject;
        return taskHandler.handle(params, taskFlowContext);
    }

    @Override
    public ITask nextTask(Object resultValue, Object params, TaskFlowContext taskFlowContext) {
        boolean isResultValueEmpty = resultValue == null || StringUtils.isBlank(resultValue.toString());
        if (isResultValueEmpty && CollectionUtils.isEmpty(resultNodeList)) {
            return null;
        } else if (isResultValueEmpty) {
            throw new IllegalArgumentException("任务处理的返回值不能为空,taskFlowId:" + taskFlowContext.getTaskFlow().getId() + ", taskId:" + taskId);
        }
        if (CollectionUtils.isEmpty(resultNodeList)) {
            return null;
        }
        String nextTaskId = null;
        resultValue = resultValue.toString().trim();
        for (ResultNode resultNode : resultNodeList) {
            String road = resultNode.getRoad().trim();
            if (road.startsWith("#")) {
                SpringExpressionUtils el = SpringExpressionUtils.getInstance();
                Map<String, Object> contextParamMap = new HashMap<>();
                contextParamMap.put("R", resultValue);
                contextParamMap.put("P", params);
                contextParamMap.put("C", taskFlowContext);
                String roadValue = el.parseSpel(road, contextParamMap, String.class);
                if (resultValue.equals(roadValue)) {
                    nextTaskId = resultNode.getNextStop();
                    break;
                }
            } else if (resultValue.equals(road)) {
                nextTaskId = resultNode.getNextStop();
                break;
            }
        }
        if (nextTaskId == null) {
            throw new IllegalArgumentException(resultValue + "未能匹配到下游任务，taskFlowId:" + taskFlowContext.getTaskFlow().getId() + ", taskId:" + taskId);
        }
        return taskFlowContext.getTaskFlow().getTask(nextTaskId);
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setResultNodeList(List<ResultNode> resultNodeList) {
        this.resultNodeList = resultNodeList;
    }

    public void setInvokeObject(Object invokeObject) {
        this.invokeObject = invokeObject;
    }

    public List<ResultNode> getResultNodeList() {
        return resultNodeList;
    }
}