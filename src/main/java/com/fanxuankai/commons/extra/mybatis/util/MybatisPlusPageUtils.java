package com.fanxuankai.commons.extra.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanxuankai.commons.domain.PageRequest;
import com.fanxuankai.commons.domain.PageResult;

import java.util.List;
import java.util.function.Function;

/**
 * Mybatis Plus 分页工具类
 *
 * @author fanxuankai
 */
public class MybatisPlusPageUtils {
    private MybatisPlusPageUtils() {
    }

    /**
     * Mybatis Plus IPage 转 PageResult
     *
     * @param page /
     * @param <T>  /
     * @return /
     */
    public static <T> PageResult<T> convert(IPage<T> page) {
        return new PageResult<>(page.getRecords(), PageRequest.of((int) page.getCurrent(), (int) page.getSize()),
                page.getTotal());
    }

    /**
     * Mybatis Plus IPage 转 PageResult
     *
     * @param page      /
     * @param converter 数据类型转换函数
     * @param <T>       转换前类型
     * @param <R>       转换后类型
     * @return /
     */
    public static <T, R> PageResult<R> convert(IPage<T> page, Function<T, R> converter) {
        return convert(page).map(converter);
    }

    /**
     * Mybatis Plus IPage 转 PageResult
     *
     * @param page    /
     * @param content /
     * @param <T>     转换前类型
     * @param <R>     转换后类型
     * @return /
     */
    public static <T, R> PageResult<R> convert(IPage<T> page, List<R> content) {
        return new PageResult<>(content, PageRequest.of((int) page.getCurrent(), (int) page.getSize()),
                page.getTotal());
    }

    /**
     * Page 转 Mybatis Plus Page
     *
     * @param pageRequest /
     * @param <T>         /
     * @return /
     */
    public static <T> Page<T> convert(PageRequest pageRequest) {
        return new Page<>(pageRequest.getPageIndex(), pageRequest.getPageSize());
    }

    /**
     * Page 转空的 PageResult
     *
     * @param pageRequest /
     * @param <T>         /
     * @return /
     */
    public static <T> PageResult<T> convertEmpty(PageRequest pageRequest) {
        return convert(convert(pageRequest));
    }
}
