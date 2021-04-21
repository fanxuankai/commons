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
     * 分页数据
     */
    private final List<T> content;
    /**
     * 分页参数
     */
    private final Page page;
    /**
     * 总数
     */
    private final long total;
    /**
     * 总页数
     */
    private final int totalPages;

    public PageResult(List<T> content, Page page, long total) {
        this.content = content;
        this.page = page;
        this.total = total;
        int pageSize = page.getPageSize();
        this.totalPages = pageSize == 0 ? 1 : (int) Math.ceil((double) total / (double) pageSize);
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

    public Page getPage() {
        return page;
    }

    /**
     * 总页数
     *
     * @return /
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 总数量
     *
     * @return /
     */
    public long getTotal() {
        return total;
    }

    /**
     * 当前页数据
     *
     * @return /
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * 映射
     *
     * @param converter 映射函数
     * @param <U>       目标类型
     * @return /
     */
    public <U> PageResult<U> map(Function<? super T, ? extends U> converter) {
        return new PageResult<>(content.stream().map(converter).collect(Collectors.toList()), page, total);
    }
}
