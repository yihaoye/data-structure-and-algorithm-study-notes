/*
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
Example:

Input: [1,2,3,0,2]
Output: 3 
Explanation: transactions = [buy, sell, cooldown, buy, sell]
*/



// Other's Solution (DP by State Machine thinking):
/*
![](https://assets.leetcode.com/users/npvinhphat/image_1560663201.png)
There are three states, according to the action that you can take.

Hence, from there, you can now the profit at a state at time i as:

s0[i] = max(s0[i - 1], s2[i - 1]); // Stay at s0, or rest from s2
s1[i] = max(s1[i - 1], s0[i - 1] - prices[i]); // Stay at s1, or buy from s0
s2[i] = s1[i - 1] + prices[i]; // Only one way from s1
Then, you just find the maximum of s0[n] and s2[n], since they will be the maximum profit we need (No one can buy stock and left with more profit that sell right :) )

Define base case:

s0[0] = 0; // At the start, you don't have any stock if you just rest
s1[0] = -prices[0]; // After buy, you should have -prices[0] profit. Be positive!
s2[0] = Integer.MIN_VALUE; // Lower base case
*/
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length <= 1) return 0;
		int[] s0 = new int[prices.length];
		int[] s1 = new int[prices.length];
		int[] s2 = new int[prices.length];
		s1[0] = -prices[0];
		s0[0] = 0;
		s2[0] = Integer.MIN_VALUE;
		for (int i = 1; i < prices.length; i++) {
			s0[i] = Math.max(s0[i-1], s2[i-1]);
			s1[i] = Math.max(s1[i-1], s0[i-1] - prices[i]);
			s2[i] = s1[i-1] + prices[i];
		}
		return Math.max(s0[prices.length-1], s2[prices.length-1]);
    }
}
// 以上每个状态 Sx[i] 意味着第 i 步若为 Sx 状态时的最佳值（本题为最大值），因为通过前面的第 i-1 步的状态们的最佳值为计算基础，
// 因此意味着大量其他可能的组合会被忽略（这是合理的，因为这些组合得到的第 i-1 步的状态们的值既然无法胜过第 i-1 步的状态们的最佳值，所以基于它们的往后的计算结果也一定不是最佳值）。
// 以上逻辑往前推算第 i-n 步也同理。



// My Solution 1 (DFS): (Time Limit Exceeded)
class Solution {
    public int maxProfit(int[] prices) {
        int[] res = new int[1];
        dfs(prices, res, 0, 0, new int[]{0,0});
        dfs(prices, res, 0, 0, new int[]{0,1});
        
        return res[0];
    }
    
    // dfs
    public void dfs(int[] prices, int[] res, int index, int profit, int[] action) { // action[0] last action index; action[1] last action: 0 for do nothing or cool down, 1 for buy, -1 for sell.
        int len = prices.length;
        if (index == len) {
            res[0] = Math.max(res[0], profit);
            return;
        }
        for (int i=index; i<=len-1; i++) {
            dfs(prices, res, i+1, profit, action);
            if (!(action[0] == i-1 && action[1] == -1)) {
                if (action[1] == 1) {
                    int curProfit = prices[i] - prices[action[0]];
                    dfs(prices, res, i+1, profit+curProfit, new int[]{i,-1});
                } else {
                    dfs(prices, res, i+1, profit, new int[]{i,1});
                }
            }
        }
    }
}