package com.fanxuankai.commons.core.util.concurrent;

import cn.hutool.system.SystemUtil;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程池, 枚举方式实现单例模式
 *
 * @author fanxuankai
 */
public enum ThreadPool {
    /**
     * 单例
     */
    INSTANCE;

    private static final String PROPERTY_PREFIX = "com.fanxuankai.commons";
    public static final String PROPERTY_CPS = PROPERTY_PREFIX + ".corePoolSize";
    public static final String PROPERTY_KAT = PROPERTY_PREFIX + ".keepAliveTime";
    private ExecutorService executor;

    ThreadPool() {
        init();
    }

    private void init() {
        int corePoolSize = (int) SystemUtil.getInt(PROPERTY_CPS, Runtime.getRuntime().availableProcessors() * 2);
        executor = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2, SystemUtil.getLong(PROPERTY_KAT,
                60L),
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            private final AtomicLong count = new AtomicLong(0L);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("ThreadPool-worker-" + count.getAndIncrement());
                return thread;
            }
        });
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void execute(Runnable command) {
        executor.execute(command);
    }

    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return executor.submit(task, result);
    }

    public static void main(String[] args) {
        ThreadPool.INSTANCE.execute(() -> System.out.println(1));
    }
}
