package com.fanxuankai.commons.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Collectors 工具类
 *
 * @author fanxuankai
 */
public class CollectorUtils {
    /**
     * 根据对象的某个字段去重，配合 <code>java.util.stream.Stream#filter(java.util.function.Predicate)</code> 使用
     *
     * @param keyExtractor 提取某个字段
     * @param <T>          泛型
     * @return Predicate
     */
    public static <T> Predicate<T> distinct(Function<? super T, Object> keyExtractor) {
        Map<Object, Object> map = new ConcurrentHashMap<>(16);
        return t -> map.putIfAbsent(keyExtractor.apply(t), 1) == null;
    }

    /**
     * 自动合并，避免 merge 异常
     *
     * @see Collectors#toMap(java.util.function.Function, java.util.function.Function, java.util.function.BinaryOperator, java.util.function.Supplier)
     */
    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> keyMapper,
                                                             Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, (u, u2) -> u2, HashMap::new);
    }
}
