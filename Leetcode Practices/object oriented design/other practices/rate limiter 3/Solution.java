import java.io.*;
import java.util.*;

/*
    	            漏桶算法	        令牌桶算法
处理突发流量的能力	   较弱	               较强
对延迟的影响	      可能增加延迟	       可能导致延迟峰值
参数配置	         漏斗的流出速率        令牌的发放速率、桶的大小
适用场景	         系统的处理能力恒定	   系统的处理能力可变
实现难度	         较低	             较高

解释：
* 
当突发流量到来时，大量的水滴会迅速进入漏斗，导致漏斗中的水位快速上升。如果漏斗中的水位超过了溢出阀的高度，那么多余的水滴就会溢出，代表系统无法处理所有的突发请求，部分请求会被丢弃。随着时间的推移，漏斗中的水位会逐渐下降，直至恢复到正常水平。
令牌的发放速率可以根据系统的处理能力进行调整，从而适应突发流量的变化。令牌桶中的令牌数量反映了系统的剩余处理能力。即使在繁忙状态下，令牌桶中也可能存在剩余的令牌，可以用于处理突发流量。
*
在漏桶算法中，漏斗的流出速率是固定的，代表了系统的处理能力。因此，只要漏斗的流出速率保持不变，那么系统的处理能力就也是稳定的。
在令牌桶算法中，令牌的发放速率可以根据系统的实际情况进行调整。例如，当系统负载较低时，可以降低令牌的发放速率；当系统负载较高时，可以提高令牌的发放速率。因此，令牌桶算法的处理能力是可变的。

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
