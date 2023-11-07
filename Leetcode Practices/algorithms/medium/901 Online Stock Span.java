/*
Design an algorithm that collects daily price quotes for some stock and returns the span of that stock's price for the current day.

The span of the stock's price in one day is the maximum number of consecutive days (starting from that day and going backward) for which the stock price was less than or equal to the price of that day.

For example, if the prices of the stock in the last four days is [7,2,1,2] and the price of the stock today is 2, then the span of today is 4 because starting from today, the price of the stock was less than or equal 2 for 4 consecutive days.
Also, if the prices of the stock in the last four days is [7,34,1,2] and the price of the stock today is 8, then the span of today is 3 because starting from today, the price of the stock was less than or equal 8 for 3 consecutive days.
Implement the StockSpanner class:

StockSpanner() Initializes the object of the class.
int next(int price) Returns the span of the stock's price given that today's price is price.
 

Example 1:

Input
["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
[[], [100], [80], [60], [70], [60], [75], [85]]
Output
[null, 1, 1, 1, 2, 1, 4, 6]

Explanation
StockSpanner stockSpanner = new StockSpanner();
stockSpanner.next(100); // return 1
stockSpanner.next(80);  // return 1
stockSpanner.next(60);  // return 1
stockSpanner.next(70);  // return 2
stockSpanner.next(60);  // return 1
stockSpanner.next(75);  // return 4, because the last 4 prices (including today's price of 75) were less than or equal to today's price.
stockSpanner.next(85);  // return 6
 

Constraints:

1 <= price <= 10^5
At most 104 calls will be made to next.

*/



// 最优解是采用单调栈，next() 会是 O(1)，这里主要是为了练习分块法及其模版
// Other's Solution:
class StockSpanner {
    static int N = 10010, len = 100, idx = 0;
    static int[] nums = new int[N], region = new int[N / len + 10];

    public StockSpanner() {
        // 分块法 - https://mp.weixin.qq.com/s/XemUTlGVSZP-jq_dfTC6pw
        for (int loc = 0; loc <= getLoc(idx); loc++) region[loc] = 0;
        idx = 0;
    }

    int getLoc(int idx) {
        return (idx - 1) / len + 1;
    }

    int query(int price) {
        int ans = 0, loc = getLoc(idx), left = (loc - 1) * len + 1, right = idx;
        while (loc >= 1 && region[loc] <= price) {
            ans += right - left + 1;
            loc--; right = left - 1; left = (loc - 1) * len + 1;
        }
        for (int i = right; loc >= 1 && i >= left && nums[i] <= price; i--) ans++;
        return ans;
    }

    public int next(int price) {
        nums[++idx] = price;
        int loc = getLoc(idx);
        region[loc] = Math.max(region[loc], price); // update
        return query(price);
    }
}
/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */
