package com.charse.matchservice.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * description:
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/9/10 20:03
 */
@XStreamAlias("validate")
public class ValidateNode {

    @XStreamAsAttribute
    private String expression;

    @XStreamAsAttribute
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
