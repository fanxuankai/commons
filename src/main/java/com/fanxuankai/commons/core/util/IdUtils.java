package com.fanxuankai.commons.core.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author fanxuankai
 */
public class IdUtils {
    private static final Snowflake SNOW_FLAKE = IdUtil.createSnowflake(0, 0);

    public static long nextId() {
        return SNOW_FLAKE.nextId();
    }
}
