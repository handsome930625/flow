package com.charse.test.local;

import com.charse.taskflow.filter.Filter;
import com.charse.taskflow.filter.FilterChain;
import com.charse.taskflow.flow.TaskFlowContext;

import java.util.Random;

/**
 * description:模拟验证
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/8/25 15:05
 */
public class FirstFilter implements Filter<Object> {

    @Override
    public void doFilter(Object params, TaskFlowContext taskFlowContext, FilterChain filterChain) throws Exception {
        Random random = new Random();
        int i = random.nextInt(100);
        // 模拟验证
        if (i > 20) {
            taskFlowContext.addStepReturnValue("firstFilter", 1);
            filterChain.doFilter(params, taskFlowContext);
        }
    }
}
