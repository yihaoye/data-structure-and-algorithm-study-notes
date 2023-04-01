package com.example.ratelimiter.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import com.example.ratelimiter.exception.RejectException;
import com.example.ratelimiter.util.TimestampHolder;

public class RateCounterImpl implements RateLimiter {

    private static final int DEFAULT_RATE_LIMIT_PER_SECOND = Integer.MAX_VALUE;

    private int limit;

    private AtomicInteger counter;

    public RateCounterImpl() {
        this(DEFAULT_RATE_LIMIT_PER_SECOND);
    }

    public RateCounterImpl(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit less than zero");
        }
        this.limit = limit;
        counter = new AtomicInteger();
        TimestampHolder holder = new TimestampHolder();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            while (true) {
                long cur = System.currentTimeMillis();
                if (cur - holder.getTimestamp() >= 1000) {
                    holder.setTimestamp(cur);
                    counter.set(0);
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void acquire() {
        if (counter.incrementAndGet() > limit) {
            throw new RejectException();
        }
    }

    // for test
    // public static void main(String[] args) {
    //     RateLimiter rateLimiter = new RateCounterImpl(10);

    //     int num = 100;
    //     while (num > 0) {
    //         try {
    //             rateLimiter.acquire();
    //         } catch (Exception e) {
    //             continue;
    //         }

    //         num--;
    //         System.out.println("sec: " + System.currentTimeMillis() / 1000L + ", mil: " + System.currentTimeMillis() + " got a token");
    //     }
    // }
}
