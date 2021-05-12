package com.fanxuankai.commons.util;

import java.util.List;

/**
 * 对象转换器接口
 *
 * @param <T> 实体类
 * @param <D> DTO
 * @param <V> VO
 * @author fanxuankai
 */
public interface Converter<T, D, V> {
    /**
     * Dto 转 Entity
     *
     * @param d /
     * @return /
     */
    T toEntity(D d);

    /**
     * Entity 转 Vo
     *
     * @param t /
     * @return /
     */
    V toVo(T t);

    /**
     * Dto 集合转 Entity 集合
     *
     * @param dList /
     * @return /
     */
    List<T> toEntity(List<D> dList);

    /**
     * Entity 集合转 Vo 集合
     *
     * @param tList /
     * @return /
     */
    List<V> toVo(List<T> tList);
}