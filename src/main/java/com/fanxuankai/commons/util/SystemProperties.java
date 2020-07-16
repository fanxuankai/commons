package com.fanxuankai.commons.util;

import java.util.Optional;

/**
 * @author fanxuankai
 */
public class SystemProperties {

    public static Optional<Integer> getInteger(String key) {
        Integer value = null;
        String valueStr = System.getProperty(key);
        if (valueStr != null) {
            value = Integer.parseInt(valueStr);
        }
        return Optional.ofNullable(value);
    }

    public static Optional<Long> getLong(String key) {
        Long value = null;
        String valueStr = System.getProperty(key);
        if (valueStr != null) {
            value = Long.parseLong(valueStr);
        }
        return Optional.ofNullable(value);
    }

}
