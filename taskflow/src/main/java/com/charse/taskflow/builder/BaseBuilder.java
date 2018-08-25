package com.charse.taskflow.builder;

import com.charse.taskflow.flow.ITaskFlow;

import java.util.List;

/**
 * description: 将配置模型转成java对象模型接口
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/8/24 14:38
 */
public interface BaseBuilder {
    /**
     * description: 创建taskflow
     *
     * @return 返回taskflow 集合
     * @author 王亦杰
     * @date 2018/8/24 15:01
     */
    List<ITaskFlow> buildTaskFlows() throws Exception;
}
