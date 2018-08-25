package com.charse.taskflow.node;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangyj
 * @Date: 2018/4/11 19:16
 * @Description: 过滤器集合解析
 **/
public class FiltersNode implements Serializable {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6904899168458958399L;

    /**
     * 过滤器集合
     */
    @XStreamImplicit(itemFieldName="filter")
    private List<FilterNode> filterList = new ArrayList<>();


    public void addFilter(FilterNode filter) {
        filterList.add(filter);
    }

    public List<FilterNode> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<FilterNode> filterList) {
        this.filterList = filterList;
    }

    @Override
    public String toString() {
        return "FiltersNode{" +
                "filterList=" + filterList +
                '}';
    }
}