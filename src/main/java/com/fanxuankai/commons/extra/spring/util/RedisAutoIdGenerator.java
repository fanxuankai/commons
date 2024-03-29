package com.fanxuankai.commons.extra.spring.util;

import com.fanxuankai.commons.util.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 自动 ID 生成器, 基于 Redis incr
 *
 * @author fanxuankai
 */
@Component
public class RedisAutoIdGenerator implements InitializingBean {

    public static final String DEFAULT_DATE_PATTERN = "yyyyMMdd";
    public static final int DEFAULT_LENGTH = 3;
    private static RedisTemplate<String, Object> rt;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * title+日期+增量数字, 基于 hash
     *
     * @param key         key
     * @param title       标题
     * @param datePattern 日期格式
     * @param length      长度
     * @param delta       delta
     * @return FXK20210109000
     */
    public static String incrementWithDate(String key, String title, String datePattern, int length, long delta) {
        String hashKey = title + DateUtils.toText(new Date(), datePattern);
        return increment(key, hashKey, length, delta);
    }

    /**
     * (non-Javadoc)
     *
     * @see RedisAutoIdGenerator#incrementWithDate(String, String, String, int, long)
     */
    public static String incrementWithDate(String key, String title, String datePattern, int length) {
        return incrementWithDate(key, title, datePattern, length, 1);
    }

    /**
     * (non-Javadoc)
     *
     * @see RedisAutoIdGenerator#incrementWithDate(String, String, String, int, long)
     */
    public static String incrementWithDate(String key, String title, String datePattern) {
        return incrementWithDate(key, title, datePattern, DEFAULT_LENGTH, 1);
    }

    /**
     * (non-Javadoc)
     *
     * @see RedisAutoIdGenerator#incrementWithDate(String, String, String, int, long)
     */
    public static String incrementWithDate(String key, String title) {
        return incrementWithDate(key, title, DEFAULT_DATE_PATTERN, DEFAULT_LENGTH, 1);
    }

    /**
     * hashKey+增量数字, 基于 hash
     *
     * @param key     key
     * @param hashKey hashKey
     * @param length  长度
     * @param delta   delta
     * @return FXK000
     */
    public static String increment(String key, String hashKey, int length, long delta) {
        long increment = rt.opsForHash().increment(key, hashKey, delta);
        return hashKey + String.format("%0" + length + "d", increment);
    }

    /**
     * (non-Javadoc)
     *
     * @see RedisAutoIdGenerator#increment(String, String, int, long)
     */
    public static String increment(String key, String hashKey, int length) {
        return increment(key, hashKey, length, 1);
    }

    /**
     * (non-Javadoc)
     *
     * @see RedisAutoIdGenerator#increment(String, String, int, long)
     */
    public static String increment(String key, String hashKey) {
        return increment(key, hashKey, DEFAULT_LENGTH, 1);
    }

    @Override
    public void afterPropertiesSet() {
        RedisAutoIdGenerator.rt = redisTemplate;
    }

}
