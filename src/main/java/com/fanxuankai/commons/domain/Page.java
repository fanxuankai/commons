package com.fanxuankai.commons.domain;

/**
 * 分页参数
 *
 * @author fanxuankai
 */
public class Page {
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
    private final int pageNumber;
    /**
     * 每页数量
     */
    private final int pageSize;

    public Page(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Page() {
        this(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
    }

    public static Page of(int page, int size) {
        return new Page(page, size);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
}
