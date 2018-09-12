package com.charse.matchservice;

import com.charse.matchservice.exception.DefinitionException;
import com.charse.matchservice.node.*;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 透传服务管理器
 * 1.加载xml ，转javabean
 * 2.根据aid找到对应的透传服务、参数类型、参数校验
 * 3.根据返回值选择
 *
 * @author 王亦杰
 * @version 1.0
 * @date 2018/9/10 19:22
 */
public class MatchServiceManager {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchServiceManager.class);
    /**
     * xml 解析器
     */
    private XStream xmlConfigureParser;

    private static Pair pair = null;

    public static Pair getMatchServicePair(String xmlFilePath) {
        if (pair == null) {
            synchronized (MatchServiceManager.class) {
                MatchServiceManager matchServiceManager = new MatchServiceManager();
                matchServiceManager.init(xmlFilePath);
                return pair;
            }
        }
        return pair;
    }

    private MatchServiceManager() {

    }

    private void init(String xmlFilePath) {
        assert (StringUtils.isNotBlank(xmlFilePath)) : "matchservices xml config path is blank!!!";
        String[] xmlPaths = xmlFilePath.split(",");
        try {
            xmlConfigureParser = new XStream();
            XStream.setupDefaultSecurity(xmlConfigureParser);
            xmlConfigureParser.allowTypes(new Class[]{MatchServicesNode.class});
            xmlConfigureParser.autodetectAnnotations(true);
            xmlConfigureParser.processAnnotations(MatchServicesNode.class);
            List<File> fileList = new ArrayList<>();
            for (String xmlPath : xmlPaths) {
                ClassLoader classLoader = getClass().getClassLoader();
                URL url = classLoader.getResource(xmlPath);
                if (url == null) {
                    throw new FileNotFoundException("file not fount : " + xmlPath);
                }
                File file = new File(url.getFile());
                fileList.add(file);
            }
            pair = parse(fileList);
        } catch (Exception e) {
            // 解析xml 规则失败，让程序停止
            throw new DefinitionException("matchservices xml definition error please check it!!!", e);
        }
    }

    private Pair parse(List<File> resources) throws ClassNotFoundException {
        if (CollectionUtils.isEmpty(resources)) {
            LOGGER.warn("taskflow files is empty,please check it");
            return new Pair(new LinkedHashMap<>(0), new LinkedHashMap<>(0));
        }

        Map<String, ServiceNode> serviceNodeByUrlMap = new LinkedHashMap<>();
        Map<String, Object> validateUtilsMap = new LinkedHashMap<>();
        for (File resource : resources) {
            MatchServicesNode matchServicesNode = parseImpl(resource);
            if (matchServicesNode.getValidateUtilNodes() != null
                    && CollectionUtils.isNotEmpty(matchServicesNode
                    .getValidateUtilNodes().getUtilNodes())) {
                int i = 1;
                for (UtilNode utilNode : matchServicesNode.getValidateUtilNodes().getUtilNodes()) {
                    if (StringUtils.isAnyBlank(utilNode.getName(), utilNode.getClassType())) {
                        throw new IllegalArgumentException("第" + i + "个 utilNode 标签name或者class-name不能为空, " +
                                "resource" +
                                " " +
                                resource.getPath());
                    }
                    i++;
                    try {
                        Class<?> clazz = getClass(utilNode.getClassType());
                        validateUtilsMap.put(utilNode.getName(), clazz);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalArgumentException("第" + i + "个 utilNode 标签, " + utilNode
                                .getClassType() + "not found resource " + resource.getPath());
                    }
                }
            }
            // 1.校验MatchServicesNode
            if (matchServicesNode.getServiceNodes() == null || matchServicesNode.getServiceNodes().size() == 0) {
                throw new IllegalArgumentException("resource " + resource.getPath() + " ServiceNode 数量为空");
            }
            // 2.校验ServiceNode
            for (ServiceNode serviceNode : matchServicesNode.getServiceNodes()) {
                if (serviceNodeByUrlMap.containsKey(serviceNode.getUrl())) {
                    throw new IllegalArgumentException("serviceNode.url - " + serviceNode.getUrl() + " repeat, resource "
                            + resource.getPath());
                }
                // 3.校验ReturnNode
                ReturnNode returnNode = serviceNode.getReturnNode();
                if (returnNode == null) {
                    throw new IllegalArgumentException("serviceNode.url - " + serviceNode.getUrl() + " return标签为空, resource" +
                            resource.getPath());
                }
                if (StringUtils.isNotBlank(returnNode.getReturnClassType())) {
                    try {
                        Class<?> clazz = getClass(returnNode.getReturnClassType());
                        returnNode.setClazz(clazz);
                    } catch (ClassNotFoundException e) {
                        throw new ClassNotFoundException("'serviceNode.url - " + serviceNode.getUrl()
                                + " return class type not found , resource " + resource.getPath());
                    }
                }
                // 4.校验ParamNode
                List<ParamNode> paramNodes = serviceNode.getParamNodes();
                if (CollectionUtils.isNotEmpty(paramNodes)) {
                    for (ParamNode paramNode : paramNodes) {
                        try {
                            Class<?> clazz = getClass(paramNode.getClassType());
                            paramNode.setClazz(clazz);
                        } catch (ClassNotFoundException e) {
                            throw new ClassNotFoundException("'serviceNode.url - " + serviceNode.getUrl()
                                    + " paramNode class type not found , resource " + resource.getPath());
                        }
                        // 5.校验ValidateNode
                        List<ValidateNode> validateNodes = paramNode.getValidateNodes();
                        if (CollectionUtils.isNotEmpty(validateNodes)) {
                            int i = 1;
                            for (ValidateNode validateNode : validateNodes) {
                                if (StringUtils.isAnyBlank(validateNode.getExpression(), validateNode.getMessage())) {
                                    throw new IllegalArgumentException("serviceNode.url - " + serviceNode.getUrl() +
                                            "第" + i + "个 validateNode 标签校验表达式或错误信息不能为空, resource " +
                                            resource.getPath());
                                }
                                i++;
                            }
                        }
                    }
                }
                serviceNodeByUrlMap.put(serviceNode.getUrl(), serviceNode);
            }
        }
        return new Pair(serviceNodeByUrlMap, validateUtilsMap);
    }

    private MatchServicesNode parseImpl(File resource) {
        return (MatchServicesNode) xmlConfigureParser.fromXML(resource);
    }

    private Class getClass(String className) throws ClassNotFoundException {
        if ("String".equals(className)) {
            return String.class;
        } else if ("int".equals(className) || "Integer".equals(className)) {
            return Integer.class;
        } else if ("long".equals(className) || "Long".equals(className)) {
            return Long.class;
        } else if ("boolean".equals(className) || "Boolean".equals(className)) {
            return Boolean.class;
        } else if ("short".equals(className) || "Short".equals(className)) {
            return Short.class;
        } else if ("double".equals(className) || "Double".equals(className)) {
            return Double.class;
        } else if ("BigDecimal".equals(className)) {
            return BigDecimal.class;
        }
        return Class.forName(className);
    }

    public class Pair {
        Map<String, ServiceNode> serviceNodeMap;

        Map<String, Object> validateUtilsMap;

        Pair(Map<String, ServiceNode> serviceNodeMap, Map<String, Object> validateUtilsMap) {
            this.serviceNodeMap = serviceNodeMap;
            this.validateUtilsMap = validateUtilsMap;
        }

        public Map<String, ServiceNode> getServiceNodeMap() {
            return serviceNodeMap;
        }

        public Map<String, Object> getValidateUtilsMap() {
            return validateUtilsMap;
        }
    }
}
