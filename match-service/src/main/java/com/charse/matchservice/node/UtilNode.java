package com.charse.matchservice.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * description:
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/9/11 0:24
 */
@XStreamAlias("util")
public class UtilNode {
    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    @XStreamAlias("class-name")
    private String classType;

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

}
