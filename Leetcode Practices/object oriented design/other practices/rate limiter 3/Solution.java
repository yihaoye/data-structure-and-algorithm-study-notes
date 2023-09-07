import java.io.*;
import java.util.*;
import java.time.*;
import java.time.temporal.*;

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
