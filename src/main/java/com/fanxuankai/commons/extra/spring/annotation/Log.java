package com.fanxuankai.commons.extra.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动打印日志
 *
 * @author fanxuankai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 执行时间
     *
     * @return flag
     */
    boolean executeTime() default true;

    /**
     * 参数
     *
     * @return flag
     */
    boolean params() default true;

    /**
     * 返回值
     *
     * @return flag
     */
    boolean returnValue() default true;

    /**
     * 日志格式化
     *
     * @return flag
     */
    boolean prettyFormat() default false;

    /**
     * 设置 debug 级别, 默认是 info 级别
     *
     * @return flag
     */
    boolean debug() default false;
}
