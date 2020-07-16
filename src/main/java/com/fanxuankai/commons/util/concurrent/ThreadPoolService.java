package com.fanxuankai.commons.util.concurrent;

import com.fanxuankai.commons.util.SystemProperties;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程池, 枚举方式实现单例模式
 *
 * @author fanxuankai
 */
public class ThreadPoolService {

    private static final String PROPERTY_PREFIX = "com.fanxuankai.commons";
    private static final String PROPERTY_CPS = PROPERTY_PREFIX + ".corePoolSize";
    private static final String PROPERTY_KAT = PROPERTY_PREFIX + ".keepAliveTime";

    private ThreadPoolService() {
    }

    public static ThreadPoolExecutor getInstance() {
        return Singleton.INSTANCE.threadPoolExecutor;
    }

    private enum Singleton {
        // 实例
        INSTANCE;

        private final ThreadPoolExecutor threadPoolExecutor;

        Singleton() {
            threadPoolExecutor = threadPoolExecutor();
        }

        private ThreadPoolExecutor threadPoolExecutor() {
            int corePoolSize = SystemProperties.getInteger(PROPERTY_CPS)
                    .orElse(Runtime.getRuntime().availableProcessors() * 2);
            return new ThreadPoolExecutor(corePoolSize, corePoolSize * 2,
                    SystemProperties.getLong(PROPERTY_KAT).orElse(60L), TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(), new ThreadFactory() {
                private final AtomicLong count = new AtomicLong(0L);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("ZeusThreadPool-worker-" + count.getAndIncrement());
                    return thread;
                }
            });
        }

    }

}
