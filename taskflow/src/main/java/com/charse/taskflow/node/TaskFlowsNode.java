package com.charse.taskflow.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 19:16
 * @Description: 任务流集合解析
 **/
@XStreamAlias("taskflows")
public class TaskFlowsNode implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1165830038351860306L;

    /**
     * 任务流集合
     */
    @XStreamImplicit(itemFieldName = "taskflow")
    private List<TaskFlowNode> taskFlowList = new ArrayList<>();

    public void addTaskFlow(TaskFlowNode taskFlow) {
        taskFlowList.add(taskFlow);
    }

    public List<TaskFlowNode> getTaskFlowList() {
        return taskFlowList;
    }

    public void setTaskFlowList(List<TaskFlowNode> taskFlowList) {
        this.taskFlowList = taskFlowList;
    }

    @Override
    public String toString() {
        return "TaskFlowsNode{" +
                "taskFlowList=" + taskFlowList +
                '}';
    }
}