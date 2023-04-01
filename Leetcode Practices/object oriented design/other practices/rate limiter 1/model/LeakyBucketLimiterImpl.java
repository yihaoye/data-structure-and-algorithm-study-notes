package com.example.ratelimiter.model;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import com.example.ratelimiter.exception.RejectException;
import com.example.ratelimiter.util.TimestampHolder;

/**
漏桶算法面对限流，就更加的柔性，不存在直接的粗暴拒绝。

原理很简单，可以认为就是注水漏水的过程。往漏桶中以任意速率流入水，以固定的速率流出水。当水超过桶的容量时，会被溢出，也就是被丢弃。因为桶容量是不变的，保证了整体的速率。
    * 流入的水滴，可以看作是访问系统的请求，这个流入速率是不确定的。
    * 桶的容量一般表示系统所能处理的请求数。
    * 如果桶的容量满了，就达到限流的阀值，就会丢弃水滴（拒绝请求）
    * 流出的水滴，是恒定过滤的，对应服务按照固定的速率处理请求。
 */
public class LeakyBucketLimiterImpl implements RateLimiter {

    private static final int DEFAULT_RATE_LIMIT_PER_SECOND = Integer.MAX_VALUE;

    private static final long NANOSECOND = 1000 * 1000 * 1000;

    private BlockingQueue<Thread> bucket;

    public LeakyBucketLimiterImpl() {
        this(DEFAULT_RATE_LIMIT_PER_SECOND);
    }

    public LeakyBucketLimiterImpl(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException();
        }

        bucket = new LinkedBlockingQueue<>(limit);
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        TimestampHolder holder = new TimestampHolder(System.nanoTime());
        long interval = NANOSECOND / limit;
        threadPool.submit(() -> {
            while (true) {
                long cur = System.nanoTime();
                if (cur - holder.getTimestamp() >= interval) {
                    Thread thread = bucket.poll();
                    Optional.ofNullable(thread).ifPresent(LockSupport::unpark);
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
        if (bucket.remainingCapacity() == 0) {
            throw new RejectException();
        }

        Thread thread = Thread.currentThread();
        bucket.add(thread);
        LockSupport.park();
    }

    // for test
    // public static void main(String[] args) throws InterruptedException {
    //     RateLimiter rateLimiter = new LeakyBucketLimiterImpl(1);

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

    //     long start = System.currentTimeMillis();
    //     ExecutorService threadPool = Executors.newCachedThreadPool();
    //     for (int i = 0; i < 10; i++) {
    //         threadPool.submit(runnable);
    //     }
    //     threadPool.awaitTermination(100, TimeUnit.SECONDS);
    //     long end = System.currentTimeMillis();
    //     System.out.println("over time: " + (end - start) / 1000);
    // }
}
