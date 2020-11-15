package com.education.common.model;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/15 14:24
 */
public class PageInfo<T> {

    private long total;
    private List<T> dataList;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
