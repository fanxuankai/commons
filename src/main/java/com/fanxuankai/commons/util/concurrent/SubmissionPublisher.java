package com.fanxuankai.commons.util.concurrent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import static com.fanxuankai.commons.util.concurrent.Flow.DEFAULT_BUFFER_SIZE;

/**
 * @author fanxuankai
 */
public class SubmissionPublisher<T> implements Flow.Publisher<T>, AutoCloseable {
    private final Map<Flow.Subscriber<? super T>, BufferedSubscription<T>> subscriptionCache = new LinkedHashMap<>();
    private final Executor executor;
    private final int maxBufferCapacity;

    public SubmissionPublisher() {
        this(ThreadPoolService.getInstance(), DEFAULT_BUFFER_SIZE);
    }

    public SubmissionPublisher(Executor executor, int maxBufferCapacity) {
        this.executor = executor;
        this.maxBufferCapacity = maxBufferCapacity;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        if (!subscriptionCache.containsKey(subscriber)) {
            BufferedSubscription<T> subscription = new BufferedSubscription<>(subscriber, executor, maxBufferCapacity);
            subscriptionCache.put(subscriber, subscription);
        }
    }

    public void submit(T item) {
        subscriptionCache.forEach((subscriber, subscription) -> subscription.submit(item));
    }

    @Override
    public void close() {
        this.subscriptionCache.forEach((subscriber, subscription) -> subscription.stop());
        this.subscriptionCache.clear();
    }

    private static class BufferedSubscription<T> implements Flow.Subscription {
        private final Executor executor;
        private final BlockingQueue<T> items;
        private final BlockingQueue<Long> requests;
        private final Flow.Subscriber<? super T> subscriber;
        private volatile boolean stop;

        BufferedSubscription(Flow.Subscriber<? super T> subscriber, Executor executor, int maxBufferCapacity) {
            this.subscriber = subscriber;
            this.executor = executor;
            items = new ArrayBlockingQueue<>(maxBufferCapacity);
            requests = new ArrayBlockingQueue<>(maxBufferCapacity);
            consume();
        }

        void submit(T item) {
            try {
                items.put(item);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        void stop() {
            this.stop = true;
        }

        @Override
        public void request(long n) {
            try {
                requests.put(n);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void cancel() {
            this.items.clear();
            this.requests.clear();
            stop();
        }

        private void consume() {
            executor.execute(() -> {
                subscriber.onSubscribe(this);
                while (!stop) {
                    try {
                        long n = requests.take();
                        for (long i = 0; i < n; i++) {
                            T item = items.take();
                            try {
                                subscriber.onNext(item);
                            } catch (Throwable throwable) {
                                subscriber.onError(throwable);
                                stop();
                                break;
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                subscriber.onComplete();
            });
        }
    }
}
