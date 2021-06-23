package com.fanxuankai.commons.domain;

/**
 * 分页参数
 *
 * @author fanxuankai
 */
public class PageRequest {
    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NUMBER = 1;
    /**
     * 默认每页数量
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * 页码
     */
    private int pageIndex;
    /**
     * 每页数量
     */
    private int pageSize;

    public PageRequest() {
    }

    public PageRequest(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
    }

    public static PageRequest defaultPage() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.pageIndex = DEFAULT_PAGE_NUMBER;
        pageRequest.pageSize = DEFAULT_PAGE_SIZE;
        return pageRequest;
    }
}
