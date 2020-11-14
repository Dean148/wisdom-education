package com.education.model.request;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/12 19:39
 */
public class PageParam {

    private int pageNumber;
    private int pageSize;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
