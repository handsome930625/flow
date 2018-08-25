package com.charse.taskflow.builder.xml;

import com.charse.taskflow.builder.BaseBuilder;
import com.charse.taskflow.exception.DefinitionException;
import com.charse.taskflow.exception.ErrorHandlerException;
import com.charse.taskflow.filter.Filter;
import com.charse.taskflow.filter.FirstFilter;
import com.charse.taskflow.filter.LastFilter;
import com.charse.taskflow.flow.ITask;
import com.charse.taskflow.flow.ITaskFlow;
import com.charse.taskflow.flow.ITaskHandler;
import com.charse.taskflow.flow.impl.DefaultTask;
import com.charse.taskflow.flow.impl.DefaultTaskFlow;
import com.charse.taskflow.node.*;
import com.charse.taskflow.utils.ClassLoaderUtils;
import com.charse.taskflow.utils.SpringBeanHelper;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * description:
 *
 * @author 王亦杰（yijie.wang01@ucarinc.com）
 * @version 1.0
 * @date 2018/8/24 15:04
 */
public class XmlTaskFlowBuilder implements BaseBuilder {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlTaskFlowBuilder.class);

    private TaskFlowsNode taskFlowsNode;

    private XStream xmlConfigureParser;

    public XmlTaskFlowBuilder(String taskflowXmlPaths) {
        assert (StringUtils.isNotBlank(taskflowXmlPaths)) : "taskFlow xml config path is blank!!!";
        String[] xmlPaths = taskflowXmlPaths.split(",");
        try {
            xmlConfigureParser = new XStream();
            XStream.setupDefaultSecurity(xmlConfigureParser);
            xmlConfigureParser.allowTypes(new Class[]{TaskFlowsNode.class});
            xmlConfigureParser.autodetectAnnotations(true);
            xmlConfigureParser.processAnnotations(TaskFlowsNode.class);
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
            taskFlowsNode = parse(fileList);
        } catch (Exception e) {
            // 解析xml 规则失败，让程序停止
            throw new DefinitionException("taskflow xml definition error please check it!!!", e);
        }
    }


    /**
     * <p>功能描述: 将所有文件的任务流解析完整合到一起</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/11 20:29 </p>
     *
     * @param resources 所有 taskflow 文件
     * @return 所有的任务流
     */
    private TaskFlowsNode parse(List<File> resources) throws IOException, SAXException {
        if (CollectionUtils.isEmpty(resources)) {
            LOGGER.warn("taskflow files is empty,please check it");
            return null;
        }

        Map<String, TaskFlowNode> taskFlowNodeByTaskFlowIdMap = new LinkedHashMap<>();
        for (File resource : resources) {
            TaskFlowsNode taskFlowsNode = parseImpl(resource);
            for (TaskFlowNode taskFlowNode : taskFlowsNode.getTaskFlowList()) {
                if (taskFlowNodeByTaskFlowIdMap.containsKey(taskFlowNode.getId())) {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn("there has two same taskflow file:{}", resource);
                    }
                    throw new IllegalArgumentException("'taskflow.id - " + taskFlowNode.getId() + " repeat, " + resource);
                }
                taskFlowNodeByTaskFlowIdMap.put(taskFlowNode.getId(), taskFlowNode);
            }
        }
        // map 转 list
        TaskFlowsNode taskFlowsNode = new TaskFlowsNode();
        for (TaskFlowNode taskFlowNode : taskFlowNodeByTaskFlowIdMap.values()) {
            taskFlowsNode.addTaskFlow(taskFlowNode);
        }
        return taskFlowsNode;
    }

    /**
     * <p>功能描述: 将resource 解析成配置对象</p>
     * <p>创建人: wangyj </p>
     * <p>创建日期: 2018/4/11 20:27 </p>
     *
     * @param resource 需要解析的文件
     * @return 每个文件解析的结果
     */
    private TaskFlowsNode parseImpl(File resource) throws IOException, SAXException {
        return (TaskFlowsNode) xmlConfigureParser.fromXML(resource);
    }


    @Override
    public List<ITaskFlow> buildTaskFlows() throws Exception {
        List<ITaskFlow> taskFlowList = new ArrayList<>();
        for (TaskFlowNode taskFlowNode : taskFlowsNode.getTaskFlowList()) {
            Boolean useSpring = taskFlowNode.getUseSpring();
            Boolean validate = taskFlowNode.getValidate();
            DefaultTaskFlow defaultTaskFlow = new DefaultTaskFlow();
            initBase(defaultTaskFlow, taskFlowNode);
            initFilter(defaultTaskFlow, taskFlowNode.getFilters(), useSpring);
            initTask(defaultTaskFlow, taskFlowNode.getTaskList(), useSpring);
            validate(defaultTaskFlow, validate);
            taskFlowList.add(defaultTaskFlow);
        }
        return taskFlowList;
    }

    private void validate(DefaultTaskFlow defaultTaskFlow, Boolean validate) {
        /*
          开始校验
          1.校验 startTask 是否可达
          2.校验 next-stop 是否可达
         */
        if (validate) {
            ITask startTask = defaultTaskFlow.getStartTask();
            if (startTask == null) {
                throw new DefinitionException("There is no corresponding startTask,flow id is "
                        + defaultTaskFlow.getId());
            }

            Map<String, ITask> taskMap = defaultTaskFlow.getTaskMap();
            for (ITask task : taskMap.values()) {
                if (task instanceof DefaultTask) {
                    DefaultTask defaultTask = (DefaultTask) task;
                    if (defaultTask.getResultNodeList() != null) {
                        for (ResultNode resultNode : defaultTask.getResultNodeList()) {
                            if (taskMap.get(resultNode.getNextStop()) == null) {
                                throw new DefinitionException("There is no corresponding task for resultNode,flow id is "
                                        + defaultTaskFlow.getId() + " result next-stop is " + resultNode.getNextStop());
                            }
                        }
                    }
                }
            }
        }
    }

    private void initTask(DefaultTaskFlow defaultTaskFlow, List<TaskNode> taskList, Boolean useSpring) throws ClassNotFoundException, OperationNotSupportedException {
        Map<String, ITask> taskMap = new HashMap<>();
        for (TaskNode taskNode : taskList) {
            DefaultTask task = new DefaultTask();
            String taskId = taskNode.getTaskId();
            if (StringUtils.isBlank(taskId)) {
                throw new IllegalArgumentException("taskId must not be null!!!");
            }
            task.setTaskId(taskId);
            if (taskNode.getResults() != null) {
                task.setResultNodeList(taskNode.getResults().getResults());
            }
            InvokeNode invokeNode = taskNode.getInvoke();
            // 目标方法
            Object invokeObject;
            // 是否使用spring
            if (useSpring) {
                invokeObject = SpringBeanHelper.getBean(invokeNode.getBeanId(), Object.class);
            } else {
                invokeObject = ClassLoaderUtils.getObject(invokeNode.getClassName());
            }
            if (!(invokeObject instanceof ITaskHandler)) {
                throw new ErrorHandlerException(invokeNode.getClassName() + "don't implement ITaskHandler interface");
            }
            task.setInvokeObject(invokeObject);
            taskMap.put(taskId, task);
        }
        defaultTaskFlow.setTask(taskMap);
    }

    private void initFilter(DefaultTaskFlow defaultTaskFlow, FiltersNode filtersNode, Boolean useSpring) throws
            ClassNotFoundException, OperationNotSupportedException {
        // 组装过滤器
        LinkedList<Filter> filterList = new LinkedList<>();
        filterList.add(new FirstFilter());
        if (filtersNode != null && filtersNode.getFilterList() != null) {
            for (FilterNode filterNode : filtersNode.getFilterList()) {
                Filter filter;
                // 是否使用spring
                if (useSpring) {
                    filter = SpringBeanHelper.getBean(filterNode.getBeanId(), Filter.class);
                } else {
                    filter = ClassLoaderUtils.getObject(filterNode.getClassName());
                }
                filterList.add(filter);
            }
        }
        filterList.add(new LastFilter());
        defaultTaskFlow.setFilters(filterList);
    }


    private void initBase(DefaultTaskFlow defaultTaskFlow, TaskFlowNode taskFlowNode) {
        if (StringUtils.isBlank(taskFlowNode.getId()) || StringUtils.isBlank(taskFlowNode.getStartTask())) {
            throw new IllegalArgumentException("taskFlowId or startTask must not be null!!!");
        }
        defaultTaskFlow.setId(taskFlowNode.getId());
        defaultTaskFlow.setStartTaskId(taskFlowNode.getStartTask());
    }
}
