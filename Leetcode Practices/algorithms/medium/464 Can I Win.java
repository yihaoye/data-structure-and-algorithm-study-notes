/**
In the "100 game" two players take turns adding, to a running total, any integer from 1 to 10. The player who first causes the running total to reach or exceed 100 wins.

What if we change the game so that players cannot re-use integers?

For example, two players might take turns drawing from a common pool of numbers from 1 to 15 without replacement until they reach a total >= 100.

Given two integers maxChoosableInteger and desiredTotal, return true if the first player to move can force a win, otherwise, return false. Assume both players play optimally.

 

Example 1:

Input: maxChoosableInteger = 10, desiredTotal = 11
Output: false
Explanation:
No matter which integer the first player choose, the first player will lose.
The first player can choose an integer from 1 up to 10.
If the first player choose 1, the second player can only choose integers from 2 up to 10.
The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
Same with other integers chosen by the first player, the second player will always win.
Example 2:

Input: maxChoosableInteger = 10, desiredTotal = 0
Output: true
Example 3:

Input: maxChoosableInteger = 10, desiredTotal = 1
Output: true
 

Constraints:

1 <= maxChoosableInteger <= 20
0 <= desiredTotal <= 300
 */



// Other's Solution:
class Solution {
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        /*
            博弈论+记忆化递归+状态压缩 - 如果当前先手选了 i 之后还不能马上赢，但是下一步后手选数字的时候选输了（也就是 dfs(cur | state, desiredTotal - i, dp) 返回了 False），说明先手能赢，dp[state] = True，并返回 True。“假设两位玩家游戏时都表现最佳”，其实意味着每个人都会从剩余能选择的数字里选一个最后能让自己赢的数，递归里面的 for 循环就是在做这一件事，如果这个数不能让我赢，我就选下一个数看看能不能赢，只有当我选择任意的数都不能赢的时候，才会不甘心地返回一个 False
            https://leetcode-cn.com/problems/can-i-win/solution/464-wo-neng-ying-ma-dai-bei-wang-lu-de-d-qu1t/
            https://leetcode-cn.com/problems/can-i-win/solution/python3-bo-yi-lun-ji-yi-hua-di-gui-zhuan-kf99/
            Time: O(2^N), Space: O(N)
        */
        if (maxChoosableInteger >= desiredTotal) return true;
        if ((1 + maxChoosableInteger) * maxChoosableInteger / 2 < desiredTotal) return false;

        return dfs(0, desiredTotal, new Boolean[1 << maxChoosableInteger], maxChoosableInteger);
    }

    private boolean dfs(int state, int desiredTotal, Boolean[] dp, int maxChoosableInteger){
        if (dp[state] != null) return dp[state];

        for (int i = 1; i <= maxChoosableInteger; i++) {
            int cur = 1 << (i - 1);
            if ((cur & state) != 0) continue;

            if (i >= desiredTotal || !dfs(cur|state, desiredTotal - i, dp, maxChoosableInteger)) {
                return dp[state] = true;
            }
        }
        return dp[state] = false;
    }
}
