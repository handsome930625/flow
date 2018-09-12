package com.charse.matchservice.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/9/10 19:52
 */
@XStreamAlias("matchservices")
public class MatchServicesNode {
    @XStreamAlias("validate-utils")
    private ValidateUtilNode validateUtilNodes;

    @XStreamImplicit(itemFieldName = "service")
    private List<ServiceNode> serviceNodes = new ArrayList<ServiceNode>();

    public List<ServiceNode> getServiceNodes() {
        return serviceNodes;
    }

    public void setServiceNodes(List<ServiceNode> serviceNodes) {
        this.serviceNodes = serviceNodes;
    }

    public ValidateUtilNode getValidateUtilNodes() {
        return validateUtilNodes;
    }

    public void setValidateUtilNodes(ValidateUtilNode validateUtilNodes) {
        this.validateUtilNodes = validateUtilNodes;
    }
}
