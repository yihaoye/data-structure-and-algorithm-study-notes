import java.io.*;
import java.util.*;

/*
    	            漏桶算法	        令牌桶算法
处理突发流量的能力	   较弱	               较强
对延迟的影响	      可能增加延迟	       可能导致延迟峰值
参数配置	         漏斗的流出速率        令牌的发放速率、桶的大小
适用场景	         系统的处理能力恒定	   系统的处理能力可变
实现难度	         稍低	             稍高

令牌桶算法其实是对漏桶算法的一种改进，主要在于令牌桶算法在限制平均调用速率的同时还允许一定程度的突发调用。

解释：
漏桶不如令牌桶应对突发流量的主要原因是因为定义上漏桶的水流出是匀速、固定的（代表了下游系统的处理能力，因此，只要漏斗的流出速率保持不变，那么系统的整体处理能力就也是稳定的）。
而令牌桶没这个定义可以瞬时添加大量令牌，如果漏桶可以瞬时流出大量的水则实际上它从定义上就变成了令牌桶法。

在对比滑动窗口限流时，这 2 个桶算法有一个优势是在使用存储时，滑动窗口要记录每个进桶请求的时间戳，而桶只需要记录一个或零个时间戳，这样可以减少存储的开销。
*/

public class Solution {
    public static void main(String[] args) {
        //...
    }
}


public class LeakyBucketRateLimiter {
    private final double capacity;
    private final double leakRate;
    private double water;
    private long lastTS;

    public LeakyBucketRateLimiter(double capacity, double leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.water = 0.0;
        this.lastTS = fetchTimestamp();
    }

    public synchronized boolean tryAcquire(double num) {
        leak();
        if (water + num > capacity) return false;
        water += num;
        return true;
    }

    private void leak() {
        long currTS = fetchTimestamp();
        double decr = (currTS - lastTS) * leakRate;
        water = Math.max(0, water - decr);
        lastTS = currTS;
    }

    private long fetchTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}


public class TokenBucketRateLimiter {
    private final double capacity;
    private final double refillRate;
    private double tokens;
    private long lastTS;

    public TokenBucketRateLimiter(double capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate; // 或 int[] refillRate = new int[]{1, 3}; 比如每 3 秒 1 credit 或每 9 秒 3 credit（可以自行选择是否计算并除以最大公约数以适应分数速率变更）-> 1/3 credit per sec，初始为 0 credit 比如 [0, 3]。这里简化使用 double 但是要小心除不尽的精度情况
        this.tokens = 0.0;
        this.lastTS = fetchTimestamp();
    }

    public synchronized boolean tryAcquire(double num) {
        refill();
        if (tokens < num) return false;
        tokens -= num;
        return true;
    }

    private void refill() {
        long currTS = fetchTimestamp();
        double incr = (currTS - lastTS) * refillRate;
        tokens = Math.min(capacity, tokens + incr);
        lastTS = currTS;
    }

    private long fetchTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
