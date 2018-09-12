package com.charse.matchservice.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * description:
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/9/10 19:55
 */
@XStreamAlias("return")
public class ReturnNode {

    @XStreamAsAttribute
    @XStreamAlias("return-class-name")
    private String returnClassType;

    @XStreamAsAttribute
    @XStreamAlias("judge-method")
    private String judgeMethod;

    @XStreamAsAttribute
    @XStreamAlias("success-method")
    private String successMethod;

    @XStreamAsAttribute
    @XStreamAlias("failed-method")
    private String failedMethod;

    private Class<?> clazz;

    public String getReturnClassType() {
        return returnClassType;
    }

    public void setReturnClassType(String returnClassType) {
        this.returnClassType = returnClassType;
    }

    public String getJudgeMethod() {
        return judgeMethod;
    }

    public void setJudgeMethod(String judgeMethod) {
        this.judgeMethod = judgeMethod;
    }

    public String getSuccessMethod() {
        return successMethod;
    }

    public void setSuccessMethod(String successMethod) {
        this.successMethod = successMethod;
    }

    public String getFailedMethod() {
        return failedMethod;
    }

    public void setFailedMethod(String failedMethod) {
        this.failedMethod = failedMethod;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"returnClassType\":\"")
                .append(returnClassType).append('\"');
        sb.append(",\"judgeMethod\":\"")
                .append(judgeMethod).append('\"');
        sb.append(",\"successMethod\":\"")
                .append(successMethod).append('\"');
        sb.append(",\"failedMethod\":\"")
                .append(failedMethod).append('\"');
        sb.append(",\"clazz\":")
                .append(clazz);
        sb.append('}');
        return sb.toString();
    }
}
