package com.fanxuankai.commons.core.util.concurrent;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author fanxuankai
 * @deprecated {@link ThreadUtil}
 */
public class Threads {
    public static void sleep(long timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
