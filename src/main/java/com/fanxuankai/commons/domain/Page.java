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
    private final int pageIndex;
    /**
     * 每页数量
     */
    private final int pageSize;

    public Page(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Page() {
        this(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
    }

    public static Page of(int page, int size) {
        return new Page(page, size);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }
}
