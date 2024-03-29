/**
You are given an integer array prices representing the daily price history of a stock, where prices[i] is the stock price on the ith day.

A smooth descent period of a stock consists of one or more contiguous days such that the price on each day is lower than the price on the preceding day by exactly 1. The first day of the period is exempted from this rule.

Return the number of smooth descent periods.

 

Example 1:

Input: prices = [3,2,1,4]
Output: 7
Explanation: There are 7 smooth descent periods:
[3], [2], [1], [4], [3,2], [2,1], and [3,2,1]
Note that a period with one day is a smooth descent period by the definition.
Example 2:

Input: prices = [8,6,7,7]
Output: 4
Explanation: There are 4 smooth descent periods: [8], [6], [7], and [7]
Note that [8,6] is not a smooth descent period as 8 - 6 ≠ 1.
Example 3:

Input: prices = [1]
Output: 1
Explanation: There is 1 smooth descent period: [1]
 

Constraints:

1 <= prices.length <= 10^5
1 <= prices[i] <= 10^5
 */



// My Solution:
class Solution {
    public long getDescentPeriods(int[] prices) {
        // DP
        // Time: O(N), Space: O(1)
        long pre = 1L;
        long res = 0L;
        res += pre;
        for (int i=1; i<prices.length; i++) {
          long cur = (prices[i-1] - prices[i] == 1L) ? pre + 1L : 1L;
          res += cur;
          pre = cur;
        }
        return res;
    }
}
// 以上解法由以下解法简化而来
class Solution {
    public long getDescentPeriods(int[] prices) {
        // Time: O(N), Space: O(N)
        long[] dp = new long[prices.length];
        dp[0] = 1L;
        long res = 0L;
        res += dp[0];
        for (int i=1; i<prices.length; i++) {
            dp[i] = (prices[i-1] - prices[i] == 1L) ? dp[i-1] + 1L : 1L;
            res += dp[i];
        }
        return res;
    }
}
// 以上解法由下面的思路简化而来
// dp[i] -> 以 ith element 为结尾的最长的平滑下降区间 3,2,1 -> 3: dp[i] = (prices[i-1] - prices[i] == 1) ? dp[i-1]+1 : 1;
// hasmap<3, count> <1, count> ... key max 平滑下降区间
// 最长的平滑下降区间若为 len 的组合数 -> len: (1) + len-1: (len-(len-1)+1) + len-2: (len-(len-2)+1) + ... + len-i: (i+1) + 1: (len) -> 比如最长区间为 3 时的组合数 = 3: (1) + 2: (2) + 1: (3) = 1 + 2 + 3 = 6
// [3,2,1,4,3, 1] -> [1,2,3,1,2,1] (dp[0] = 1)
// map.put(dp[i-1], ++) -> map<3,1><2,1><1,1>
