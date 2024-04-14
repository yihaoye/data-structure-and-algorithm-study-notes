import java.io.*;
import java.util.*;
import java.time.*;
import java.time.temporal.*;

/*
    	            漏桶算法	        令牌桶算法
处理突发流量的能力	   较弱	               较强
对延迟的影响	      可能增加延迟	       可能导致延迟峰值
参数配置	         漏斗的流出速率        令牌的发放速率、桶的大小
适用场景	         系统的处理能力恒定	    系统的处理能力可变

解释：
* 
当突发流量到来时，大量的水滴会迅速进入漏斗，导致漏斗中的水位快速上升。如果漏斗中的水位超过了溢出阀的高度，那么多余的水滴就会溢出，代表系统无法处理所有的突发请求，部分请求会被丢弃。随着时间的推移，漏斗中的水位会逐渐下降，直至恢复到正常水平。
令牌的发放速率可以根据系统的处理能力进行调整，从而适应突发流量的变化。令牌桶中的令牌数量反映了系统的剩余处理能力。即使在繁忙状态下，令牌桶中也可能存在剩余的令牌，可以用于处理突发流量。
*
在漏桶算法中，漏斗的流出速率是固定的，代表了系统的处理能力。因此，只要漏斗的流出速率保持不变，那么系统的处理能力就也是稳定的。
在令牌桶算法中，令牌的发放速率可以根据系统的实际情况进行调整。例如，当系统负载较低时，可以降低令牌的发放速率；当系统负载较高时，可以提高令牌的发放速率。因此，令牌桶算法的处理能力是可变的。


下面的代码实现了两种限流算法综合使用：
其中漏桶规定了 5 秒内最多 8 个请求
令牌桶额外灵活规定了每 3 秒 1 个 credit，最多 2 个 credit，在用户超过漏桶限制的情况下可以使用 credit
*/

public class Solution {
    // 漏桶法
    public static int reqLimit = 8;
    public static int timeLimit = 5; // sec
    public static Map<Integer, Queue<Instant>> userRecords = new HashMap<>();

    // 令牌桶法
    public static int creditLimit = 2;
    public static int[] creditRate = new int[]{1, 3}; // 比如每 3 秒 1 credit 或每 9 秒 3 credit（可以自行选择是否计算并除以最大公约数）-> 1/3 credit per sec，简化可以使用 double 但是要小心除不尽的情况
    public static Map<Integer, Instant> userLastReq = new HashMap<>(); // <uid, user_last_request_time>
    public static Map<Integer, int[]> creditRecords = new HashMap<>(); // <uid, credit_num> 初始为 0 credit 比如 [0, 3]，简化可以使用 double 但是要小心除不尽的情况

    public static void main(String[] args) {
        // test with same user id
        int uid = 1000;
        for (int i=0; i<11; i++) {
            System.out.println(rateLimit(uid));
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // ... 
        }
        System.out.println(rateLimit(uid));
    }

    // Perform rate limiting logic for provided user ID. Return true if the request is allowed, and false if it is not.
    // Each user allow x request in y sec
   
    // Credit for user - accumulate when not exceed last time, max = 2
    public static boolean rateLimit(int userId) {
        // 更新漏桶部分
        userRecords.putIfAbsent(userId, new LinkedList<>());
        Queue<Instant> queue = userRecords.get(userId);
        Instant now = Instant.now();
        Instant limit = now.minus(timeLimit, ChronoUnit.SECONDS);
        while(!queue.isEmpty() && queue.peek().isBefore(limit)) {
            queue.poll();
        }

        // 更新令牌桶部分
        userLastReq.putIfAbsent(userId, now);
        creditRecords.putIfAbsent(userId, new int[]{0, creditRate[1]});
        int gapSeconds = now.getEpochSecond() - userLastReq.get(userId).getEpochSecond();
        creditRecords.get(userId)[0] = Math.min(creditLimit * creditRate[1], creditRecords.get(userId)[0] + gapSeconds * creditRate[0]);

        // 处理请求
        if (queue.size() >= reqLimit && creditRecords.get(userId)[0] < creditRecords.get(userId)[1]) {
            return false;
        } else if (queue.size() >= reqLimit) {
            creditRecords.get(userId)[0] -= creditRecords.get(userId)[1];
            userLastReq.put(userId, now);
        } else {
            queue.offer(now);
        }
        return true;
    }
}
