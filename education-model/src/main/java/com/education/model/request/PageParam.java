package com.education.model.request;

import org.apache.ibatis.session.RowBounds;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/12 19:39
 */
public class PageParam {

    private static final int NO_PAGE = 1;

    private Integer pageNumber;
    private Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
