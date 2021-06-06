package com.fanxuankai.commons.extra.spring.util;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author fanxuankai
 */
@Configuration
@EnableRedisAutoIdGenerator
@ConditionalOnClass(RedisTemplate.class)
public class RedisAutoIdGeneratorAutoConfiguration {
}