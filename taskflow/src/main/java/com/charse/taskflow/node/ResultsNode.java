package com.charse.taskflow.node;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: wangyj
 * @Date: 2018/4/11 19:16
 * @Description: 任务处理结果路由集合解析
 **/
public class ResultsNode implements Serializable {

    /**
     * 序列id
     */
    private static final long serialVersionUID = -4990095181304557234L;

    /**
     * 路由集合
     */
    @XStreamImplicit(itemFieldName="result")
    private List<ResultNode> results = new ArrayList<>();

    public void addResult(ResultNode result) {
        results.add(result);
    }

    public List<ResultNode> getResults() {
        return results;
    }

    public void setResults(List<ResultNode> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResultsNode{" +
                "results=" + results +
                '}';
    }
}