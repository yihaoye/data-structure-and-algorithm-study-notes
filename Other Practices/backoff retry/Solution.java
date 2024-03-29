// 经典策略模式
import java.util.concurrent.*;
import java.util.*;

public class BackoffRetry throws InterruptedException {
    private static final int MAX_RETRIES = 5;
    private static final int INITIAL_INTERVAL = 1000; // in milliseconds

    public static void main(String[] args) {
        BackoffStrategy linearBackoff = new LinearBackoff();
        BackoffStrategy exponentialBackoff = new ExponentialBackoff();
        BackoffStrategy fibonacciBackoff = new FibonacciBackoff(); // new FibonacciBackoff(MAX_RETRIES);

        BackoffRetryManager retryManager = new BackoffRetryManager(fibonacciBackoff); // Change the strategy here

        boolean success = false;
        int retries = 0;

        while (!success && retries < MAX_RETRIES) {
]           // Simulate some operation that might fail
            if (operation()) {
                success = true;
                System.out.println("Operation succeeded!");
            } else {
                System.out.println("Operation failed. Retrying...");
                retryManager.waitBeforeRetry(retries);
            }

            retries++;
        }
    }

    private static boolean operation() {
        // Simulate the operation
        return Math.random() < 0.7; // 70% chance of success
    }
}

interface BackoffStrategy {
    long getWaitTime(int retryAttempt);
}

class LinearBackoff implements BackoffStrategy {
    private static final int MULTIPLIER = 1000; // in milliseconds

    @Override
    public long getWaitTime(int retryAttempt) {
        return retryAttempt * MULTIPLIER;
    }
}

class ExponentialBackoff implements BackoffStrategy {
    private static final int BASE = 2;

    @Override
    public long getWaitTime(int retryAttempt) {
        return (long) Math.pow(BASE, retryAttempt);
    }
}

class FibonacciBackoff implements BackoffStrategy {
    private int MAX_RETRIES = 64; // Adjust default value as needed
    private long[] fibonacciCache;

    public FibonacciBackoff() {
        this(MAX_RETRIES);
    }

    public FibonacciBackoff(int maxRetries) {
        this.MAX_RETRIES = maxRetries;
        fibonacciCache = new long[MAX_RETRIES];
        fibonacciCache[0] = 0;
        fibonacciCache[1] = 1;
        for (int i = 2; i < MAX_RETRIES; i++) fibonacciCache[i] = fibonacciCache[i - 1] + fibonacciCache[i - 2];
    }

    @Override
    public long getWaitTime(int retryAttempt) {
        if (retryAttempt >= MAX_RETRIES) return Long.MAX_VALUE; // Indicate no more retries
        return fibonacciCache[retryAttempt];
    }
}

class BackoffRetryManager {
    private BackoffStrategy backoffStrategy;

    public BackoffRetryManager(BackoffStrategy strategy) {
        this.backoffStrategy = strategy;
    }

    public void waitBeforeRetry(int retryAttempt) throws InterruptedException {
        long waitTime = backoffStrategy.getWaitTime(retryAttempt);
        Thread.sleep(waitTime);
    }
}
