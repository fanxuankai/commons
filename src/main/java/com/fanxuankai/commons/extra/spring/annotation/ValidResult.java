package com.fanxuankai.commons.extra.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HTTP 响应结果校验注解
 *
 * @author fanxuankai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidResult {
    /**
     * 打印结果日志
     *
     * @return /
     */
    boolean log() default false;

    /**
     * 失败时的行为
     *
     * @return /
     */
    BehaviorOnFail behaviorOnFail() default BehaviorOnFail.FAIL;

    enum BehaviorOnFail {
        // 忽略
        IGNORE,
        // 失败
        FAIL
    }
}