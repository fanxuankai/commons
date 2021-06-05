package com.fanxuankai.commons.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author fanxuankai
 */
public class StreamUtils {
    public static <T> Predicate<T> distinct(Function<? super T, Object> keyExtractor) {
        Map<Object, Object> map = new ConcurrentHashMap<>(16);
        return t -> map.putIfAbsent(keyExtractor.apply(t), 1) == null;
    }
}
