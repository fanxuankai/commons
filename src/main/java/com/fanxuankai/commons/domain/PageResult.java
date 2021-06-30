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
    private int pageNum;
    /**
     * 每页的数量
     */
    private int pageSize;
    /**
     * 当前页的数量
     */
    private int size;
    /**
     * 总数
     */
    private int total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 分页数据
     */
    private List<T> list;

    public PageResult() {
    }

    public PageResult(List<T> list, PageRequest pageRequest, int total) {
        this.list = list;
        this.pageNum = pageRequest.getPageIndex();
        this.pageSize = pageRequest.getPageSize();
        this.size = list.size();
        this.total = total;
        int pageSize = pageRequest.getPageSize();
        this.pages = pageSize == 0 ? 1 : (int) Math.ceil((double) total / (double) pageSize);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 空的
     *
     * @param <T> 数据类型泛型
     * @return /
     */
    public static <T> PageResult<T> empty() {
        return empty(PageRequest.defaultPage());
    }

    /**
     * 空的
     *
     * @param pageRequest /
     * @param <T>         数据类型泛型
     * @return /
     */
    public static <T> PageResult<T> empty(PageRequest pageRequest) {
        return new PageResult<>(Collections.emptyList(), pageRequest, 0);
    }

    /**
     * 映射
     *
     * @param converter 映射函数
     * @param <U>       目标类型
     * @return /
     */
    public <U> PageResult<U> map(Function<? super T, ? extends U> converter) {
        return new PageResult<>(list.stream().map(converter).collect(Collectors.toList()),
                new PageRequest(pageNum, pageSize), total);
    }
}
