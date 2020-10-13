package com.fanxuankai.commons.util.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author fanxuankai
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
