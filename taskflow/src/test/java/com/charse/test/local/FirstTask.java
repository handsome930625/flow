package com.charse.test.local;

import com.charse.taskflow.flow.ITaskHandler;
import com.charse.taskflow.flow.TaskFlowContext;

import java.util.Random;

/**
 * description:FirstTask
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/8/25 13:46
 */
public class FirstTask implements ITaskHandler<Integer> {

    @Override
    public String handle(Integer params, TaskFlowContext taskFlowContext) throws Exception {
        System.out.println("first task working......");
        System.out.println("working result is [firstTask work]");
        taskFlowContext.addStepReturnValue("firstTask", "[firstTask work]");
        Random random = new Random();
        int i = random.nextInt(100);
        // 模拟业务选择
        if (i > 50) {
            // 调用该方法可以给下个task的params对象传递参数，类型必须是可以转换的否则会cast error
            // 如果不调用这个方法默认是传递的是本方法的 params 对象
            taskFlowContext.writeNextTaskParameter("[firstTask work]");
            // 这边返回的字符串代表配置文件中 result 节点的 road 属性，如果匹配则跳转至对应的task
            return "Jump2";
        }
        taskFlowContext.writeNextTaskParameter(3);
        return "3";
    }
}
