package com.fanxuankai.commons.domain;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果
 *
 * @author fanxuankai
 */
public class PageResult<T> {
    /**
     * 当前页
     */
    private final Integer pageNum;
    /**
     * 每页的数量
     */
    private final Integer pageSize;
    /**
     * 当前页的数量
     */
    private final Integer size;
    /**
     * 总数
     */
    private final long total;
    /**
     * 总页数
     */
    private final int pages;
    /**
     * 分页数据
     */
    private final List<T> list;

    public PageResult(List<T> list, Page page, long total) {
        this.list = list;
        this.pageNum = page.getPageIndex();
        this.pageSize = page.getPageSize();
        this.size = list.size();
        this.total = total;
        int pageSize = page.getPageSize();
        this.pages = pageSize == 0 ? 1 : (int) Math.ceil((double) total / (double) pageSize);
    }

    /**
     * 空的
     *
     * @param <T> 数据类型泛型
     * @return /
     */
    public static <T> PageResult<T> empty() {
        return empty(new Page());
    }

    /**
     * 空的
     *
     * @param page /
     * @param <T>  数据类型泛型
     * @return /
     */
    public static <T> PageResult<T> empty(Page page) {
        return new PageResult<>(Collections.emptyList(), page, 0);
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public int getPages() {
        return pages;
    }

    public List<T> getList() {
        return list;
    }

    /**
     * 映射
     *
     * @param converter 映射函数
     * @param <U>       目标类型
     * @return /
     */
    public <U> PageResult<U> map(Function<? super T, ? extends U> converter) {
        return new PageResult<>(list.stream().map(converter).collect(Collectors.toList()), new Page(pageNum,
                pageSize), total);
    }
}
