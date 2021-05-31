package com.fanxuankai.commons.util;

import java.util.Optional;

/**
 * Optional 工具类
 *
 * @author fanxuankai
 * @see Optional
 */
public class OptionalUtils {
    /**
     * 判断参数是否为空, 区别于 Optional, 不仅仅是判断引用是否为空, 比如是集合, 会校验是否集合 isEmpty
     *
     * @param value the value
     * @param <T>   泛型
     * @return an {@code Optional} 如果参数为空返回 Optional.empty()
     */
    public static <T> Optional<T> of(T value) {
        if (ParamUtils.isEmpty(value)) {
            return Optional.empty();
        }
        return Optional.of(value);
    }
}