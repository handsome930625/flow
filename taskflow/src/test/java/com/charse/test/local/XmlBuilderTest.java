package com.charse.test.local;

import com.charse.taskflow.manager.DefaultTaskFlowManager;

import java.util.Map;

/**
 * description:
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/8/25 10:27
 */
public class XmlBuilderTest {

    public static void main(String[] args) throws Exception {
        DefaultTaskFlowManager manager = new DefaultTaskFlowManager("reserve.xml");
        Map<String, Object> productReserve = manager.executeFlow("productReserve", 1);
        System.out.println(productReserve);
    }
}
