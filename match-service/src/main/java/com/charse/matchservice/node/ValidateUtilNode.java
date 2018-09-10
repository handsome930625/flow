package com.charse.matchservice.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author 王亦杰（yijie.wang01@ucarinc.com）
 * @version 1.0
 * @date 2018/9/10 23:54
 */
@XStreamAlias("validate-util")
public class ValidateUtilNode {

    @XStreamImplicit(itemFieldName = "util")
    private List<UtilNode> utilNodes = new ArrayList<>();

    public List<UtilNode> getUtilNodes() {
        return utilNodes;
    }

    public void setUtilNodes(List<UtilNode> utilNodes) {
        this.utilNodes = utilNodes;
    }
}
