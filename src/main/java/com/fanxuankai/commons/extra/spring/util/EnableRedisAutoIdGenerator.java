package com.fanxuankai.commons.extra.spring.util;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 RedisAutoIdGenerator, 即注入 RedisAutoIdGenerator 到容器中
 *
 * @author fanxuankai
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RedisAutoIdGenerator.class)
public @interface EnableRedisAutoIdGenerator {
}
