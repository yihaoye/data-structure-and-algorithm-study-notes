/**
Design a hit counter which counts the number of hits received in the past 5 minutes (i.e., the past 300 seconds).

Your system should accept a timestamp parameter (in seconds granularity), and you may assume that calls are being made to the system in chronological order (i.e., timestamp is monotonically increasing). Several hits may arrive roughly at the same time.

Implement the HitCounter class:

HitCounter() Initializes the object of the hit counter system.
void hit(int timestamp) Records a hit that happened at timestamp (in seconds). Several hits may happen at the same timestamp.
int getHits(int timestamp) Returns the number of hits in the past 5 minutes from timestamp (i.e., the past 300 seconds).
 

Example 1:

Input
["HitCounter", "hit", "hit", "hit", "getHits", "hit", "getHits", "getHits"]
[[], [1], [2], [3], [4], [300], [300], [301]]
Output
[null, null, null, null, 3, null, 4, 3]

Explanation
HitCounter hitCounter = new HitCounter();
hitCounter.hit(1);       // hit at timestamp 1.
hitCounter.hit(2);       // hit at timestamp 2.
hitCounter.hit(3);       // hit at timestamp 3.
hitCounter.getHits(4);   // get hits at timestamp 4, return 3.
hitCounter.hit(300);     // hit at timestamp 300.
hitCounter.getHits(300); // get hits at timestamp 300, return 4.
hitCounter.getHits(301); // get hits at timestamp 301, return 3.
 

Constraints:

1 <= timestamp <= 2 * 109
All the calls are being made to the system in chronological order (i.e., timestamp is monotonically increasing).
At most 300 calls will be made to hit and getHits.
 

Follow up: What if the number of hits per second could be huge? Does your design scale?
 */



// My Solution:
class HitCounter {
    TreeMap<Integer, Integer> tMap;
    int gap = 300;

    public HitCounter() {
        // TreeMap（提供二分搜索）+ PrefixSum（因为 hit 严格按时间戳递增，所以每次获得一个新的 hit，只需取最后一个的 Value 进行相加）
        // TreeMap<timestamp, totalOfHitsBeforeEqualsTimestamp>
        // Time: O(logN), Space: O(N)
        tMap = new TreeMap<>();
        tMap.put(0, 0); // 初始值方便简化后面的代码逻辑
    }
    
    public void hit(int timestamp) {
            tMap.put(timestamp, tMap.lastEntry().getValue() + 1); // 可以省去判断 tMap 是否已有 timestamp，因为 hit 时间戳严格递增，如果 timestamp 存在也必是最后一个，而 tMap.lastEntry() 时间复杂度仍为 O(1)，所以直接使用即可
    }
    
    public int getHits(int timestamp) {
        Integer start = tMap.floorKey(timestamp - this.gap);
        Integer end = tMap.floorKey(timestamp);
        if (start == null && end == null) return 0;
        if (start == null && end != null) return tMap.get(end);
        return tMap.get(end) - tMap.get(start); // start != null && end != null
        // 不可能存在 start != null && end == null，因为 timestamp > timestamp - this.gap
    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */
