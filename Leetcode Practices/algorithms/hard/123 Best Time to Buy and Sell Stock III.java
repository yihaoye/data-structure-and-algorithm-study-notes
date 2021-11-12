/*
You are given an array prices where prices[i] is the price of a given stock on the ith day.

Find the maximum profit you can achieve. You may complete at most two transactions.

Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

 

Example 1:

Input: prices = [3,3,5,0,0,3,1,4]
Output: 6
Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
Example 2:

Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging multiple transactions at the same time. You must sell before buying again.
Example 3:

Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.
Example 4:

Input: prices = [1]
Output: 0
 

Constraints:

1 <= prices.length <= 105
0 <= prices[i] <= 105

*/



// My Solution (DP):
class Solution {
    public int maxProfit(int[] prices) {
        /*
            a: 假设必须交易两次才能得到最大收益，实际上把 prices 以 i 分割成两段数组 [0..i]、[i..prices.length]，每个数组内最多交易一次（即以该数组作为 Q121 的参数），然后把这两个数组最大收益相加即是 i 分割情况下的总最大收益，i 需要尝试从 0 到 prices.length，遍历完可得交易两次的最大收益（当 i 为 0 时，第一次交易实际没有进行，只进行了第二次交易，意味着仅通过单次交易实现最大收益，可视为一种特殊的两次交易）。实际中为提升性能，采用两个 dp 数组，dp1[i] 代表 prices[0..i] 的最大收益，dp2[i] 代表 prices[i..prices.length] 的最大收益（计算 dp2 时只要从右至左模仿 dp1 即可，计算出整个数组的时间复杂度均为 O(N)），当给定分割点 i，两次交易最大收益为 dp1[i] + dp2[i]，因此只要遍历一次 i，得出最大的 dp1[i] + dp2[i] 即可。
            b: 时间复杂度 O(N)，空间复杂度 O(N)
            c: 60 min
        */
        int min1 = prices[0];
        int res1 = 0;
        int[] dp1 = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min1) {
                min1 = prices[i];
            } else if (prices[i] - min1 > res1) {
                res1 = prices[i] - min1;
            }
            dp1[i] = res1;
        }
        
        int max2 = prices[prices.length-1];
        int res2 = 0;
        int[] dp2 = new int[prices.length];
        for (int j = prices.length - 1; j >= 0; j--) {
            if (prices[j] > max2) {
                max2 = prices[j];
            } else if (max2 - prices[j] > res2) {
                res2 = max2 - prices[j];
            }
            dp2[j] = res2;
        }
        
        int res = 0;
        for (int k = 0; k < prices.length; k++) {
            res = Math.max(res, dp1[k] + dp2[k]);
        }
        
        return res;
    }
}
