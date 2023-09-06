import java.io.*;
import java.util.*;
import java.time.*;
import java.time.temporal.*;

public class Solution {
    public static int reqLimit = 8;
    public static int timeLimit = 5; // sec
    public static int creditLimit = 10;
    public static Map<Integer, Queue<Instant>> customerRecords = new HashMap<>(); // 漏桶法
    public static Map<Integer, Float> creditRecords = new HashMap<>(); // <id, creditLimit by default> 令牌桶法

   public static void main(String[] args) {
        // test with same customer id
        for (int i=0; i<10; i++) {
            System.out.println(rateLimit(1));
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // ... 
        }
        System.out.println(rateLimit(1));
   }

   // Perform rate limiting logic for provided customer ID. Return true if the request is allowed, and false if it is not.
   // Each customer allow x request in y sec
   
   // Credit for customer - accumulate when not exceed last time, max = 10
   public static boolean rateLimit(int customerId) {
       Queue<Instant> queue = customerRecords.getOrDefault(customerId, new LinkedList<>());
       Instant now = Instant.now();
       Instant limit = now.minus(timeLimit, ChronoUnit.SECONDS);
       while(!queue.isEmpty() && queue.peek().isBefore(limit)) {
           queue.poll();
       }
       creditRecords.put(customerId, Math.min(creditLimit, creditRecords.getOrDefault(customerId, 0) + (float) (now.getEpochSecond() - queue.peekLast().getEpochSecond()) * reqLimit / timeLimit)); // 如果除不尽可以改用数组表示 [分子，分母]
       if (queue.size() >= reqLimit && creditRecords.get(customerId) < 1) return false;
       if (queue.size() >= reqLimit) creditRecords.put(customerId, creditRecords.get(customerId) - 1);
       queue.offer(now);
       customerRecords.put(customerId, queue);
       return true;
   }
}
