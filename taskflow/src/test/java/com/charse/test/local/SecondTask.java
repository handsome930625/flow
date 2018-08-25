package com.charse.test.local;

import com.charse.taskflow.flow.ITaskHandler;
import com.charse.taskflow.flow.TaskFlowContext;

/**
 * description:
 *
 * @author 王亦杰（yijie.wang01@ucarinc.com）
 * @version 1.0
 * @date 2018/8/25 13:46
 */
public class SecondTask implements ITaskHandler<String> {

    @Override
    public String handle(String params, TaskFlowContext taskFlowContext) throws Exception {
        System.out.println("SecondTask 收到上一个task传递过来的参数：" + params);
        taskFlowContext.addStepReturnValue("secondTask", params);
        taskFlowContext.writeNextTaskParameter(2);
        return "Jump3";
    }
}
