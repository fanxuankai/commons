package com.fanxuankai.commons.extra.mybatis.annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 条件封装行为加载器
 *
 * @author fanxuankai
 */
public class WrapBehaviorLoader {
    private static final Map<Query.Type, WrapBehavior> WRAP_BEHAVIOR_MAP;

    static {
        WRAP_BEHAVIOR_MAP = new HashMap<>();
        ServiceLoader<WrapBehavior> loader = ServiceLoader.load(WrapBehavior.class);
        loader.forEach(behavior -> WRAP_BEHAVIOR_MAP.put(behavior.getType(), behavior));
    }

    public static WrapBehavior get(Query.Type type) {
        return WRAP_BEHAVIOR_MAP.get(type);
    }
}
