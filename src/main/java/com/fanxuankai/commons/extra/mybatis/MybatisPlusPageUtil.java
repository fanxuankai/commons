package com.fanxuankai.commons.extra.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;

import java.util.List;
import java.util.function.Function;

/**
 * Mybatis Plus 分页工具类
 *
 * @author fanxuankai
 */
public class MybatisPlusPageUtil {
    private MybatisPlusPageUtil() {
    }

    /**
     * Mybatis Plus IPage 转 PageResult
     *
     * @param page /
     * @param <T>  /
     * @return /
     */
    public static <T> PageResult<T> convert(IPage<T> page) {
        return new PageResult<>(page.getRecords(), Page.of((int) page.getCurrent(), (int) page.getSize()),
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
        return new PageResult<>(page.getRecords(), Page.of((int) page.getCurrent(),
                (int) page.getSize()),
                page.getTotal()).map(converter);
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
        return new PageResult<>(content, Page.of((int) page.getCurrent(), (int) page.getSize()),
                page.getTotal());
    }

    /**
     * Page 转 Mybatis Plus IPage
     *
     * @param page /
     * @param <T>  /
     * @return /
     */
    public static <T> IPage<T> convert(Page page) {
        return new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page.getPageNumber(),
                page.getPageSize());
    }
}
