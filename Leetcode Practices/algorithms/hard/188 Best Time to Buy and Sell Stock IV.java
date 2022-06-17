/**
You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.

Find the maximum profit you can achieve. You may complete at most k transactions.

Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

 

Example 1:

Input: k = 2, prices = [2,4,1]
Output: 2
Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
Example 2:

Input: k = 2, prices = [3,2,6,5,0,3]
Output: 7
Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 

Constraints:

0 <= k <= 100
0 <= prices.length <= 1000
0 <= prices[i] <= 1000

 */



// My Solution:
class Solution {
    public int maxProfit(int k, int[] prices) {
        // DP - 状态机可以有 2*k+1 个状态（完成 1..k 次交易、完成 0..k-1 次交易并又买入一股、没有完成一次交易也没有买入一股）
        // sold[i] 即完成 i 次交易且手上无股票的 max profit, 一次也没有交易也没有股票必然 max profit 为 0 也即 sold[0]
        // buy[i] 即完成 i 次交易且手上持有一个股票的 max profit, buy[k] 其实在本题无需 care，但是即使被计算了也没关系不影响结果
        // Time: O(N*k), Space: O(k)
        int[] sold = new int[k+1], buy = new int[k+1];
        Arrays.fill(sold, Integer.MIN_VALUE); // 其实也可以为 -100000，因为根据题目条件这是最低可能值
        Arrays.fill(buy, Integer.MIN_VALUE);
        int res = 0;
        for (int i=0; i<prices.length; i++) {
            for (int j=Math.min((i+1)/2, k); j>=0; j--) { // j=Math.min((i+1)/2, k) 因为 i 较少时可能根本达不到 k
                if (j == 0) {
                    sold[j] = 0;
                    buy[j] = Math.max(buy[j], -prices[i]);
                    continue;
                }
                
                int soldThisTime = buy[j-1] + prices[i]; // sold at prices[i] with j-1 transaction max profit
                if (soldThisTime > sold[j]) sold[j] = soldThisTime;
                else buy[j] = Math.max(buy[j], sold[j] - prices[i]);
                
                res = Math.max(res, sold[j]);
            }
        }
        
        return res;
    }
}
