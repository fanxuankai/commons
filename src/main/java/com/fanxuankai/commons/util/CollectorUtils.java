package com.fanxuankai.commons.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * toMap，解决以下常见问题：
     * 1、key 重复时，Duplicate key 的问题
     * 2、value 为 null 时 NullPointException 的问题
     *
     * @param <T>         the type of the input elements
     * @param <K>         the output type of the key mapping function
     * @param <U>         the output type of the value mapping function
     * @param keyMapper   a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return a {@code Collector} which collects elements into a {@code Map}
     * whose keys and values are the result of applying mapping functions to
     * the input elements
     * @see Collectors#toMap(java.util.function.Function, java.util.function.Function)
     */
    public static <T, K, U> Map<K, U> toMap(Stream<T> stream,
                                            Function<? super T, ? extends K> keyMapper,
                                            Function<? super T, ? extends U> valueMapper) {
        return stream.collect(HashMap::new,
                (m, v) -> m.put(keyMapper.apply(v), valueMapper.apply(v)),
                HashMap::putAll);
    }

    /**
     * 同 toMap
     *
     * @param <T>         the type of the input elements
     * @param <K>         the output type of the key mapping function
     * @param <U>         the output type of the value mapping function
     * @param keyMapper   a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return a {@code Collector} which collects elements into a {@code Map}
     * whose keys and values are the result of applying mapping functions to
     * the input elements
     * @see CollectorUtils#toMap(java.util.stream.Stream, java.util.function.Function, java.util.function.Function)
     * @see Collectors#toMap(java.util.function.Function, java.util.function.Function)
     */
    public static <T, K, U> ConcurrentMap<K, U> toConcurrentMap(Stream<T> stream,
                                                                Function<? super T, ? extends K> keyMapper,
                                                                Function<? super T, ? extends U> valueMapper) {
        return stream.collect(ConcurrentHashMap::new,
                (m, v) -> m.put(keyMapper.apply(v), valueMapper.apply(v)),
                ConcurrentHashMap::putAll);
    }
}
