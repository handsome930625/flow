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
public class ThirdTask implements ITaskHandler<Integer> {
    @Override
    public String handle(Integer params, TaskFlowContext taskFlowContext) throws Exception {
        System.out.println("ThirdTask 收到上一个task传递过来的参数：" + params);
        taskFlowContext.addStepReturnValue("thirdTask", params);
        return null;
    }
}
