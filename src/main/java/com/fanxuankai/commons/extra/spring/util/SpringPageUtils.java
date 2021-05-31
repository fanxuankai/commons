package com.fanxuankai.commons.extra.spring.util;

import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;

import java.util.List;
import java.util.function.Function;

/**
 * Spring 分页工具类
 *
 * @author fanxuankai
 */
public class SpringPageUtils {
    private SpringPageUtils() {
    }

    /**
     * Spring Page 转 PageResult
     *
     * @param page /
     * @param <T>  /
     * @return /
     */
    public static <T> PageResult<T> convert(org.springframework.data.domain.Page<T> page) {
        return new PageResult<>(page.getContent(), com.fanxuankai.commons.domain.Page.of(page.getNumber() + 1,
                page.getSize()), page.getTotalElements());
    }

    /**
     * Spring Page 转 PageResult
     *
     * @param page      /
     * @param converter 数据类型转换函数
     * @param <T>       转换前类型
     * @param <R>       转换后类型
     * @return /
     */
    public static <T, R> PageResult<R> convert(org.springframework.data.domain.Page<T> page, Function<T, R> converter) {
        return new PageResult<>(page.getContent(), com.fanxuankai.commons.domain.Page.of(page.getNumber() + 1,
                page.getSize()),
                page.getTotalElements()).map(converter);
    }

    /**
     * Spring Page 转 PageResult
     *
     * @param page    /
     * @param content /
     * @param <T>     转换前类型
     * @param <R>     转换后类型
     * @return /
     */
    public static <T, R> PageResult<R> convert(org.springframework.data.domain.Page<T> page, List<R> content) {
        return new PageResult<>(content, com.fanxuankai.commons.domain.Page.of(page.getNumber() + 1, page.getSize()),
                page.getTotalElements());
    }

    /**
     * Page 转 Spring Pageable
     *
     * @param page /
     * @return /
     */
    public static org.springframework.data.domain.Pageable convert(Page page) {
        return org.springframework.data.domain.PageRequest.of(page.getPageIndex(), page.getPageSize());
    }
}
