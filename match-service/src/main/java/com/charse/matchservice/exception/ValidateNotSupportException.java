package com.charse.matchservice.exception;

/**
 * description:
 *
 * @author 王亦杰（yijie.wang01@ucarinc.com）
 * @version 1.0
 * @date 2018/9/10 21:24
 */
public class ValidateNotSupportException extends RuntimeException {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6082826494392712501L;

    public ValidateNotSupportException(String message) {
        super(message);
    }

    public ValidateNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }
}
