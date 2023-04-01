/*
Design a logger system that receive stream of messages along with its timestamps, each message should be printed if and only if it is not printed in the last 10 seconds.

Given a message and a timestamp (in seconds granularity), return true if the message should be printed in the given timestamp, otherwise returns false.

It is possible that several messages arrive roughly at the same time.

Example:

Logger logger = new Logger();

// logging string "foo" at timestamp 1
logger.shouldPrintMessage(1, "foo"); returns true; 

// logging string "bar" at timestamp 2
logger.shouldPrintMessage(2,"bar"); returns true;

// logging string "foo" at timestamp 3
logger.shouldPrintMessage(3,"foo"); returns false;

// logging string "bar" at timestamp 8
logger.shouldPrintMessage(8,"bar"); returns false;

// logging string "foo" at timestamp 10
logger.shouldPrintMessage(10,"foo"); returns false;

// logging string "foo" at timestamp 11
logger.shouldPrintMessage(11,"foo"); returns true;
*/





// My Solution:
class Logger {
    HashMap<String, Integer> map;
    
    /** Initialize your data structure here. */
    public Logger() {
        map = new HashMap<String, Integer>();
    }
    
    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {
        if (!map.containsKey(message) || map.get(message) <= timestamp-10) {
            map.put(message, timestamp);
            return true;
        }
        return false;
    }
}
/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */



 // My Solution:
 class Logger {
    /*
        双端队列+哈希集
        时间复杂度 O(1)，空间复杂度 O(M) M - 同一时间戳最大 message 量
    */
    private Deque<Set<String>> deque; // max length 10
    private Set<String> curSet; // used as reference
    private int lastTimestamp;
    
    public Logger() {
        deque = new LinkedList<>();
        lastTimestamp = 0;
    }
    
    public boolean shouldPrintMessage(int timestamp, String message) {
        int gap = timestamp - lastTimestamp, loop = timestamp - lastTimestamp;
        lastTimestamp = timestamp;
        if (gap > 10) loop = 11;
        while (--loop > 0) deque.offer(new HashSet<>());
        while (deque.size() > 10) deque.poll();
        if (deque.size() == 10 && gap != 0) deque.poll(); // 当 gap == 0，且队列长度 为 10 时，仍保留原队列
        boolean res = true;
        for (Set<String> set : deque) {
            if (set.contains(message)) res = false;
        }
        
        if (res) {
            if (!deque.isEmpty() && gap == 0) {
                curSet = deque.peekLast(); // timestamp passed in non-decreasing order，所以只可能是与上一次时间戳相同或大于而不会早于上一个时间戳，因此不用向前寻找对应的时间戳的哈希集，要不就取上一次的哈希集要不就 append 新的哈希集
                curSet.add(message);
            } else {
                curSet = new HashSet<>();
                curSet.add(message);
                deque.offer(curSet);
            }
        } else {
            if (gap != 0) deque.offer(new HashSet<>());
        }
        return res;
    }
}



// Other's Solution: (https://leetcode.com/problems/logger-rate-limiter/discuss/349733/Simple-Java-solution-using-Queue-and-Set-for-slow-learners-like-myself)
class Logger {
    
    private final Queue<Log> queue;
    private final Set<String> messages;
    
    /** Initialize your data structure here. */
    public Logger() {
        this.messages = new HashSet<>();
        //the queue is sorted as we are guaranteed to be called shouldPrintMessage by increasing time value
        //the idea is to toss any log pass the window of 10 when a new log is requested
        //e.g. if the head of queue is pointing to log at 1 sec, and we are requested at 11, the 1 sec log would be tossed
        //as it is no longer relevant
        this.queue = new LinkedList<>();
    }
    
    public boolean shouldPrintMessage(int timestamp, String message) {
        while (!queue.isEmpty() && (timestamp - queue.peek().timestamp >= 10)) {
            //toss away all irrelvant logs
            //also remove the message from seen message
            messages.remove(queue.poll().message);
        }
        if (messages.contains(message)) return false;
        queue.offer(new Log(timestamp, message));
        messages.add(message);
        return true;
    }
    
    private static final class Log {
        private final int timestamp;
        private final String message;
        
        public Log(int t, String s) {
            this.timestamp = t;
            this.message = s;
        }
    }
}
