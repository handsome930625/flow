package com.charse.matchservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.charse.matchservice.node.ParamNode;
import com.charse.matchservice.node.ReturnNode;
import com.charse.matchservice.node.ServiceNode;
import com.charse.matchservice.node.ValidateNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author 王亦杰（yijie.wang01@ucarinc.com）
 * @version 1.0
 * @date 2018/9/10 22:01
 */
public class Test {
    private MatchServiceManager.Pair pair = MatchServiceManager.getMatchServicePair("matchservices.xml");
    private Map<String, ServiceNode> serviceNodeMap = pair.getServiceNodeMap();
    private Map<String, Object> validateUtilsMap = pair.getValidateUtilsMap();
    private ExpressionParser parser = new SpelExpressionParser();

    public Object testDoService(String url, Map<String, Object> paramMap) {
        ServiceNode serviceNode = serviceNodeMap.get(url);
        if (serviceNode == null) {
            return null;
        }
        String q = JSONObject.toJSONString(paramMap);
        // 1. 获取参数
        List<ParamNode> paramNodes = serviceNode.getParamNodes();
        Object[] args = new Object[paramNodes.size()];
        // 1. 验证对象
        // 创建一个EvaluationContext对象，作为SpEL解析变量的上下文
        EvaluationContext paramCtx = new StandardEvaluationContext();
        paramCtx.setVariable("json",JSON.class);
        ((StandardEvaluationContext) paramCtx).setVariables(validateUtilsMap);
        for (int i = 0; i < paramNodes.size(); i++) {
            ParamNode paramNode = paramNodes.get(i);
            Object o;
            if (StringUtils.isBlank(paramNode.getName())) {
                o = JSONObject.parseObject(q, paramNode.getClazz());
            } else {
                o = paramMap.get(paramNode.getName());
                if (o instanceof JSONObject) {
                    o = JSONObject.toJavaObject((JSON) o, paramNode.getClazz());
                } else if (o instanceof JSONArray) {
                    o = JSONArray.toJavaObject((JSON) o, paramNode.getClazz());
                }
            }
            // 设置两个变量
            paramCtx.setVariable("param", o);
            for (ValidateNode validateNode : paramNode.getValidateNodes()) {
                if (!parser.parseExpression(validateNode.getExpression()).getValue(paramCtx, Boolean.class)) {
                    throw new RuntimeException(validateNode.getMessage());
                }
            }
            args[i] = o;
        }
        System.out.println("------------------开始rpc----------------------------");
        System.out.println("------------------params------------------------------" + args);
        Object res = getRes();
        System.out.println("------------------结束rpc----------------------------");
        // 2.判断是否调用成功
        ReturnNode returnNode = serviceNode.getReturnNode();
        EvaluationContext ctx = new StandardEvaluationContext();
        // 设置两个变量
        ctx.setVariable("result", res);
        if (parser.parseExpression(returnNode.getJudgeMethod()).getValue(ctx, Boolean.class)) {
            return parser.parseExpression(returnNode.getSuccessMethod()).getValue(ctx);
        } else {
            return parser.parseExpression(returnNode.getFailedMethod()).getValue(ctx);
        }
    }

    public Object getRes() {
        return new Res<>(1, "错误", "123");
    }


    public static void main(String[] args) {
        String q = "{\"bankName\":\"上海浦东发展银行\",\"bankNumber\":\"310100000052\",\"cityName\":\"厦门\",\"depositBranchBank\":\"上海浦东发展银行北京中关村支行\",\"depositName\":\"余待永\",\"depositNumber\":\"6217920660574166\",\"phoneNumber\":\"18659232227\"}";
        JSONObject jsonObject = JSONObject.parseObject(q);
        Map<String, Object> paramMap = new HashMap<>(jsonObject);
        Test test = new Test();
        Object o = test.testDoService("/action/4s/object", paramMap);
        System.out.println(o);
    }
}
