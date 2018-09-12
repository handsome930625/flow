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
 * @version 1.0
 * @date 2018/9/10 19:53
 */
@XStreamAlias("service")
public class ServiceNode {

    @XStreamAsAttribute
    private String url;

    @XStreamAlias("return")
    private ReturnNode returnNode;

    @XStreamImplicit(itemFieldName = "param")
    private List<ParamNode> paramNodes = new ArrayList<ParamNode>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ReturnNode getReturnNode() {
        return returnNode;
    }

    public void setReturnNode(ReturnNode returnNode) {
        this.returnNode = returnNode;
    }

    public List<ParamNode> getParamNodes() {
        return paramNodes;
    }

    public void setParamNodes(List<ParamNode> paramNodes) {
        this.paramNodes = paramNodes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"url\":\"")
                .append(url).append('\"');
        sb.append(",\"returnNode\":")
                .append(returnNode);
        sb.append(",\"paramNodes\":")
                .append(paramNodes);
        sb.append('}');
        return sb.toString();
    }
}
