package com.fanxuankai.commons.core.util.concurrent;

/**
 * @author fanxuankai
 */
public class Flow {

    static final int DEFAULT_BUFFER_SIZE = 256;

    public static int defaultBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }

    public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {

    }

    @FunctionalInterface
    public interface Publisher<T> {
        /**
         * 订阅
         *
         * @param subscriber 订阅者
         */
        void subscribe(Subscriber<? super T> subscriber);
    }

    public interface Subscription {
        /**
         * 请求
         *
         * @param n 次数
         */
        void request(long n);

        /**
         * 取消
         */
        void cancel();
    }

    public interface Subscriber<T> {

        /**
         * on subscribe
         *
         * @param subscription Subscription
         */
        void onSubscribe(Subscription subscription);

        /**
         * on next
         *
         * @param item the item
         */
        void onNext(T item);

        /**
         * on error
         *
         * @param throwable the throwable
         */
        void onError(Throwable throwable);

        /**
         * on complete
         */
        void onComplete();
    }
}
