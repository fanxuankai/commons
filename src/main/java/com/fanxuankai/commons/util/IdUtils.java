package com.fanxuankai.commons.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author fanxuankai
 */
public class IdUtils {
    private static final Snowflake SNOW_FLAKE;

    static {
        long dataCenterId = getDataCenterId();
        SNOW_FLAKE = IdUtil.createSnowflake(getWorkerId(dataCenterId), dataCenterId);
    }

    public static long nextId() {
        return SNOW_FLAKE.nextId();
    }

    public static String nextIdStr() {
        return SNOW_FLAKE.nextIdStr();
    }

    /**
     * 获取 maxWorkerId
     */
    protected static long getWorkerId(long dataCenterId) {
        StringBuilder pid = new StringBuilder();
        pid.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isNotBlank(name)) {
            /*
             * GET jvmPid
             */
            pid.append(name.split(StringPool.AT)[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (pid.toString().hashCode() & 0xffff) % ((long) 31 + 1);
    }

    /**
     * 数据标识id部分
     */
    protected static long getDataCenterId() {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                    id = id % ((long) 31 + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
