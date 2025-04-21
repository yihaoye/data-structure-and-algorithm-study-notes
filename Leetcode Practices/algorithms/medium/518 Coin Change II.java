/*
You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.

Return the number of combinations that make up that amount. If that amount of money cannot be made up by any combination of the coins, return 0.

You may assume that you have an infinite number of each kind of coin.

The answer is guaranteed to fit into a signed 32-bit integer.

 

Example 1:

Input: amount = 5, coins = [1,2,5]
Output: 4
Explanation: there are four ways to make up the amount:
5=5
5=2+2+1
5=2+1+1+1
5=1+1+1+1+1
Example 2:

Input: amount = 3, coins = [2]
Output: 0
Explanation: the amount of 3 cannot be made up just with coins of 2.
Example 3:

Input: amount = 10, coins = [10]
Output: 1
 

Constraints:

1 <= coins.length <= 300
1 <= coins[i] <= 5000
All the values of coins are unique.
0 <= amount <= 5000
*/



// My Solution (fixed by GPT):
class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1; // 有 1 种方法凑出 0 元：什么也不选

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin]; // 把当前硬币加入组合
            }
        }
        return dp[amount];
    }
}
/*
Why must loop coins outside and loop amounts (dp) inside but not vice versa

本题的关键是「组合数」而不是「排列数」
用一个具体例子来说明：

金额=5，硬币=[1,2,5]
跟踪dp数组的填充过程：

金额i=1:
    遍历硬币1: dp[1] += dp[0] = 0 + 1 = 1 (使用一个1元硬币)
    遍历硬币2: dp[1] += dp[-1] (不合法，跳过)
    遍历硬币5: dp[1] += dp[-4] (不合法，跳过)
    结果: dp[1] = 1

金额i=2:
    遍历硬币1: dp[2] += dp[1] = 0 + 1 = 1 (使用一个1元硬币)
    遍历硬币2: dp[2] += dp[0] = 1 + 1 = 2 (使用一个2元硬币)
    遍历硬币5: dp[2] += dp[-3] (不合法，跳过)
    结果: dp[2] = 2

金额i=3开始错误:
    遍历硬币1: dp[3] += dp[2] = 0 + 2 = 2 (使用一个1元硬币 + 之前凑2元的方法)
    遍历硬币2: dp[3] += dp[1] = 2 + 1 = 3 (使用一个2元硬币 + 之前凑1元的方法)
    遍历硬币5: dp[3] += dp[-2] (不合法，跳过)
    结果: dp[3] = 3

分析dp[3]=3的组成：
得到了3种方式，但实际上组合数应该是2种：{1,1,1}, {1,2}
为什么多计算了1种？因为{1,2}和{2,1}被当作不同的排列计算了

问题的根本原因：
当先考虑金额，后考虑硬币时，在每个金额i处，都在重新考虑所有硬币
这意味着在不同顺序中可能多次使用同一种硬币
例如在计算dp[3]时，既考虑了"先用硬币1再用硬币2"的情况，也考虑了"先用硬币2再用硬币1"的情况

而外循环为硬币时，是按照硬币种类的固定顺序来构建解决方案，这确保了相同的组合只被计算一次，不管其元素的顺序如何。
这就是为什么外循环为金额时计算的是排列数（考虑顺序），而题目要求的组合数（不考虑顺序）必须用外循环为硬币的方式来解决。

而正确的解法（外层循环硬币）确保了每种硬币被考虑的顺序是固定的

这样，对于每个硬币面值，按顺序考虑它可以如何构成各种金额。由于外层循环固定了考虑硬币的顺序，所以避免了重复计算组合。
简单来说：
外层循环硬币：计算的是组合数（不考虑顺序）
外层循环金额：计算的是排列数（考虑顺序）

题目要求组合数，所以必须把硬币循环放在外层。
*/
