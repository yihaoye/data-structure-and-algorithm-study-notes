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



// Other's Solution:
class Solution {
    public int maxProfit(int[] prices) {
        /*
            动态规划 状态转移方程（状态机？）https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/solution/mai-mai-gu-piao-de-zui-jia-shi-ji-iii-by-wrnt/
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        int n = prices.length;
        int buy1 = -prices[0], sell1 = 0;
        int buy2 = -prices[0], sell2 = 0;
        // https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/solution/dong-tai-gui-hua-de-jian-hua-ban-by-hero-2mwr/
        // buy1: 在该天第一次买入股票可获得的最大收益 
        // sell1: 在该天第一次卖出股票可获得的最大收益
        // buy2: 在该天第二次买入股票可获得的最大收益
        // sell2: 在该天第二次卖出股票可获得的最大收益
        // 分别对四个变量进行相应的更新, 最后 sell2 就是最大
        // 收益值（sell2 >= sell1）
        // 若以上不好理解，可参考下面，由 sell2 -> buy2 -> sell1 -> buy1 的满足最优逻辑反推导，有点类似递归那样自上而下
        for (int i = 1; i < n; ++i) {
            buy1 = Math.max(buy1, -prices[i]); // 假设当 buy1 最终答案的第一个买入点被遍历到时，其必然是至今（i）的最小价位
            sell1 = Math.max(sell1, buy1 + prices[i]); // 假设当 sell1 最终答案的第一个卖出点被遍历到时，其必然是与之前最小的买入价相加（与负数相加），sell1 总是至今最大第一次交易收益（因为一直用 max 函数保存最大差值，假设 [0..i] 中两点 -j+k 为该段的该种组合的最大差值，假定遍历到该最大差值的卖出点 k 时，其买入点必为当时的 buy1 即当时的最小第一次买入价位，但遍历到 i 时的最大值时使用的 buy1' 未必是当前 i 的 buy1 这个当前最小值，因为 buy1 可能在 (k, i] 段中更新了且卖出点与其差未必大于之前那个最大差值）
            buy2 = Math.max(buy2, sell1 - prices[i]); // 假设当 buy2 最终答案的第二个买入点被遍历到时，其必然是被之前的最大的第一次交易收益减，buy2 总是至今的第二次买入股票可获得的最大收益（逻辑如上，一直用 max 函数保存最大差值，假设 [0..i] 中的三个点 -j+k-l 为该段的该种组合的最大值，假定遍历到 l 时，-j+k 必须为 [0..l] 的最大第一次交易收益，而 sell1 总是满足此要求 - 此时的 sell1 就是 [0..l] 的最大第一次交易收益）（同理，当最终答案的最优的 -j+k-l 出现时，会将其值存在 buy2 当中由 max 函数保护其传递到最后，为最后卖出点准备）
            sell2 = Math.max(sell2, buy2 + prices[i]); // 假设当 sell2 最终答案的最后卖出点被遍历到时，其必然是与之前的最大的第二次买入股票可获得的最大收益相加，因为在此时（最后卖出点被遍历到时）buy2 一定是当时（最后卖出点被遍历到时）的第二次买入股票可获得的最大收益
        }
        return sell2;
    }
}
