package com.charse.taskflow.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * description:spring 表达式
 *
 * @author wangyj on 2018/4/14
 */
public class SpringExpressionUtils {

    /**
     * 解析器
     */
    private ExpressionParser expressionParser;

    /**
     * 实例
     */
    private static SpringExpressionUtils instance = new SpringExpressionUtils();

    public static SpringExpressionUtils getInstance() {
        return instance;
    }

    /**
     * 初始化解析器和方法参数获取器
     */
    private SpringExpressionUtils() {
        // 创建解析器
        expressionParser = new SpelExpressionParser();
    }

    /**
     * 解析SpEL表达式
     *
     * @param expression      SpEL表达式
     * @param contextParamMap 参数
     * @param clazz           反射得到的值
     * @return 解析后SpEL表达式对应的值
     */
    public  <T> T parseSpel(String expression, Map<String, Object> contextParamMap, Class<T> clazz) {
        // 构造上下文
        EvaluationContext context = new StandardEvaluationContext();
        for (String paramName : contextParamMap.keySet()) {
            context.setVariable(paramName, contextParamMap.get(paramName));
        }
        return expressionParser.parseExpression(expression).getValue(context, clazz);
    }
}
