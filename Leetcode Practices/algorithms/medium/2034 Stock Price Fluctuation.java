/**
You are given a stream of records about a particular stock. Each record contains a timestamp and the corresponding price of the stock at that timestamp.

Unfortunately due to the volatile nature of the stock market, the records do not come in order. Even worse, some records may be incorrect. Another record with the same timestamp may appear later in the stream correcting the price of the previous wrong record.

Design an algorithm that:

Updates the price of the stock at a particular timestamp, correcting the price from any previous records at the timestamp.
Finds the latest price of the stock based on the current records. The latest price is the price at the latest timestamp recorded.
Finds the maximum price the stock has been based on the current records.
Finds the minimum price the stock has been based on the current records.
Implement the StockPrice class:

StockPrice() Initializes the object with no price records.
void update(int timestamp, int price) Updates the price of the stock at the given timestamp.
int current() Returns the latest price of the stock.
int maximum() Returns the maximum price of the stock.
int minimum() Returns the minimum price of the stock.
 

Example 1:

Input
["StockPrice", "update", "update", "current", "maximum", "update", "maximum", "update", "minimum"]
[[], [1, 10], [2, 5], [], [], [1, 3], [], [4, 2], []]
Output
[null, null, null, 5, 10, null, 5, null, 2]

Explanation
StockPrice stockPrice = new StockPrice();
stockPrice.update(1, 10); // Timestamps are [1] with corresponding prices [10].
stockPrice.update(2, 5);  // Timestamps are [1,2] with corresponding prices [10,5].
stockPrice.current();     // return 5, the latest timestamp is 2 with the price being 5.
stockPrice.maximum();     // return 10, the maximum price is 10 at timestamp 1.
stockPrice.update(1, 3);  // The previous timestamp 1 had the wrong price, so it is updated to 3.
                          // Timestamps are [1,2] with corresponding prices [3,5].
stockPrice.maximum();     // return 5, the maximum price is 5 after the correction.
stockPrice.update(4, 2);  // Timestamps are [1,2,4] with corresponding prices [3,5,2].
stockPrice.minimum();     // return 2, the minimum price is 2 at timestamp 4.
 

Constraints:

1 <= timestamp, price <= 109
At most 105 calls will be made in total to update, current, maximum, and minimum.
current, maximum, and minimum will be called only after update has been called at least once.
 */



// My Solution:
class StockPrice {
    TreeMap<Integer, Record> tMap;
    TreeSet<Record> tSet;

    public StockPrice() {
        // TreeMap<timestamp, Record> + TreeSet<Record>(sorted by Record.price) + Record{timestamp, price}
        // Time: O(logN), Space: O(N)
        tMap = new TreeMap<>();
        tSet = new TreeSet<>((r1, r2) -> r1.price == r2.price ? r1.timestamp - r2.timestamp : r1.price - r2.price); // 为什么需要特殊处理 r1.price == r2.price 的情况：https://leetcode.com/problems/stock-price-fluctuation/discuss/1544678/Java-solution-failing-test-case-11/1351441
    }
    
    public void update(int timestamp, int price) {
        Record record = tMap.getOrDefault(timestamp, new Record(timestamp, price));
        if (tMap.containsKey(timestamp)) {
            tSet.remove(record);
            record.price = price;
        }
        tMap.put(timestamp, record);
        tSet.add(record);
    }
    
    public int current() {
        return tMap.lastEntry().getValue().price;
    }
    
    public int maximum() {
        return tSet.last().price;
    }
    
    public int minimum() {
        return tSet.first().price;
    }
    
    class Record {
        public int timestamp;
        public int price;
        
        public Record(int timestamp, int price) {
            this.timestamp = timestamp;
            this.price = price;
        }
    }
}
/**
 * Your StockPrice object will be instantiated and called as such:
 * StockPrice obj = new StockPrice();
 * obj.update(timestamp,price);
 * int param_2 = obj.current();
 * int param_3 = obj.maximum();
 * int param_4 = obj.minimum();
 */



// My Solution:
class StockPrice {
    TreeMap<Integer, Record> tMap;
    TreeMap<Integer, Set<Record>> pMap;

    public StockPrice() {
        // TreeMap<timestamp, Record> + TreeMap<price, Set<Record>> + Record{timestamp, price}
        // Time: O(logN), Space: O(N)
        tMap = new TreeMap<>();
        pMap = new TreeMap<>();
    }

    public void update(int timestamp, int price) {
        Record record = tMap.getOrDefault(timestamp, new Record(timestamp, price));
        if (tMap.containsKey(timestamp)) {
            pMap.get(record.price).remove(record);
            if (pMap.get(record.price).isEmpty()) pMap.remove(record.price);
            record.price = price;
        }
        tMap.put(timestamp, record);
        Set<Record> rSet = pMap.getOrDefault(record.price, new HashSet<>());
        rSet.add(record);
        pMap.put(record.price, rSet);
    }

    public int current() {
        return tMap.lastEntry().getValue().price;
    }

    public int maximum() {
        return pMap.lastKey();
    }

    public int minimum() {
        return pMap.firstKey();
    }

    class Record {
        public int timestamp;
        public int price;

        public Record(int timestamp, int price) {
            this.timestamp = timestamp;
            this.price = price;
        }
    }
}
