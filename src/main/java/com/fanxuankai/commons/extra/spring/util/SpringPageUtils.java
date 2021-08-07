package com.fanxuankai.commons.extra.spring.util;

import com.fanxuankai.commons.domain.PageRequest;
import com.fanxuankai.commons.domain.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public static <T> PageResult<T> convert(Page<T> page) {
        return new PageResult<>(page.getContent(), PageRequest.of(page.getNumber() + 1,
                page.getSize()), (int) page.getTotalElements());
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
    public static <T, R> PageResult<R> convert(Page<T> page, Function<T, R> converter) {
        return new PageResult<>(page.getContent(),
                PageRequest.of(page.getNumber() + 1, page.getSize()), (int) page.getTotalElements())
                .map(converter);
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
    public static <T, R> PageResult<R> convert(Page<T> page, List<R> content) {
        return new PageResult<>(content,
                PageRequest.of(page.getNumber() + 1, page.getSize()), (int) page.getTotalElements());
    }

    /**
     * Page 转 Spring Pageable
     *
     * @param pageRequest /
     * @return /
     */
    public static Pageable convert(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(pageRequest.getPageIndex(), pageRequest.getPageSize());
    }
}
