/**
You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.

Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

You may assume that you have an infinite number of each kind of coin.

 

Example 1:

Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1
Example 2:

Input: coins = [2], amount = 3
Output: -1
Example 3:

Input: coins = [1], amount = 0
Output: 0
 

Constraints:

1 <= coins.length <= 12
1 <= coins[i] <= 2^31 - 1
0 <= amount <= 10^4
 */



// My Solution 2:
class Solution {
    int[] dp = new int[10001];

    public int coinChange(int[] coins, int amount) {
        // memory search
        if (amount < 0) return -1;
        if (amount == 0 || dp[amount] != 0) return dp[amount]; // dp[0] should be 0 which is default value too
        int res = -1;
        for (int coin : coins) {
            int tmp = coinChange(coins, amount - coin);
            if (tmp != -1) res = Math.min(res == -1 ? Integer.MAX_VALUE : res, tmp + 1);
        }
        dp[amount] = res;
        return res;
    }
}



// My Solution 1:
class Solution {
    public int coinChange(int[] coins, int amount) {
        // 动态规划，使用 dp 数组存储每个 amount 的最小成功组合
        // Time: O(N), Space: O(N), N == amount, coins.length <= 12 so can be considered as const
        int[] dp = new int[amount+1];
        for (int i=1; i<=amount; i++) { // dp[0] should be 0, see example 3
            dp[i] = -1;
        }
        
        for (int i=0; i<=amount; i++) {
            for (int coin : coins) {
                if (i - coin < 0 || dp[i-coin] == -1) continue;
                
                if (dp[i] == -1) dp[i] = dp[i-coin] + 1;
                else dp[i] = Math.min(dp[i], dp[i-coin] + 1);
            }
        }
        
        return dp[amount];
    }
}
