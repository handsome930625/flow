package com.charse.taskflow.exception;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 20:05
 * @Description: 解析异常
 **/
public class ErrorHandlerException extends RuntimeException {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6082826494392712501L;

    /**
     * 错误处理类
     */
    public ErrorHandlerException(String message) {
        super(message);
    }
}
