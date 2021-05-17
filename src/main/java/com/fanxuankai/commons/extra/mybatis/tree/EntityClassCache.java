package com.fanxuankai.commons.extra.mybatis.tree;

import com.fanxuankai.commons.extra.spring.util.GenericTypeUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fanxuankai
 */
class EntityClassCache {
    private static final Map<Class<?>, Class<?>> CACHE = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> Class<T> entityClass(Class<?> clazz) {
        return (Class<T>) CACHE.computeIfAbsent(clazz, c -> GenericTypeUtils.getGenericType(clazz, TreeDao.class, "T"));
    }
}