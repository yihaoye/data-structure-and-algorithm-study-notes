package com.example.ratelimiter.model;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import com.example.ratelimiter.util.TimestampHolder;
import com.example.ratelimiter.exception.RejectException;

public class TokenBucketLimiterImpl implements RateLimiter {

    private static final int DEFAULT_RATE_LIMIT_PER_SECOND = Integer.MAX_VALUE;

    private static final long NANOSECOND = 1000 * 1000 * 1000;

    private static final Object TOKEN = new Object();

    private Queue<Object> tokenBucket;

    public TokenBucketLimiterImpl() {
        this(DEFAULT_RATE_LIMIT_PER_SECOND);
    }

    public TokenBucketLimiterImpl(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException();
        }

        tokenBucket = new LinkedBlockingQueue<>(limit);
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        TimestampHolder holder = new TimestampHolder(System.nanoTime());
        long interval = NANOSECOND / limit;
        threadPool.submit(() -> {
            while (true) {
                long cur = System.nanoTime();
                if (cur - holder.getTimestamp() >= interval) {
                    tokenBucket.offer(TOKEN);
                    holder.setTimestamp(cur);
                }

                try {
                    TimeUnit.NANOSECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void acquire() {
        Object token = tokenBucket.poll();
        if (Objects.isNull(token)) {
            throw new RejectException();
        }
    }

    /* for test */
    // public static void main(String[] args) throws InterruptedException {
    //     RateLimiter rateLimiter = new TokenBucketLimiterImpl(10);

    //     Runnable runnable = () -> {
    //         int num = 100;
    //         while (num > 0) {
    //             try {
    //                 rateLimiter.acquire();
    //             } catch (Exception e) {
    //                 continue;
    //             }

    //             num--;
    //             System.out.println("Thread: " + Thread.currentThread().getName() + ", sec: " + System.currentTimeMillis() / 1000L + ", mil: " + System.currentTimeMillis() + " got a token");
    //         }
    //     };

    //     ExecutorService threadPool = Executors.newCachedThreadPool();
    //     for (int i = 0; i < 10; i++) {
    //         threadPool.submit(runnable);
    //     }
    //     threadPool.awaitTermination(100, TimeUnit.SECONDS);
    // }
}
