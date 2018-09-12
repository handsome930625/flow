package com.charse.matchservice.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author 王亦杰
 *  * @version 1.0
 * @date 2018/9/10 19:55
 */
@XStreamAlias("param")
public class ParamNode {
    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    @XStreamAlias("class-name")
    private String classType;

    private Class<?> clazz;

    @XStreamImplicit(itemFieldName = "validate")
    private List<ValidateNode> validateNodes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public List<ValidateNode> getValidateNodes() {
        return validateNodes;
    }

    public void setValidateNodes(List<ValidateNode> validateNodes) {
        this.validateNodes = validateNodes;
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
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"classType\":\"")
                .append(classType).append('\"');
        sb.append(",\"clazz\":")
                .append(clazz);
        sb.append(",\"validateNodes\":")
                .append(validateNodes);
        sb.append('}');
        return sb.toString();
    }
}
