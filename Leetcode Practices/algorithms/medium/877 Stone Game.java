/**
Alice and Bob play a game with piles of stones. There are an even number of piles arranged in a row, and each pile has a positive integer number of stones piles[i].

The objective of the game is to end with the most stones. The total number of stones across all the piles is odd, so there are no ties.

Alice and Bob take turns, with Alice starting first. Each turn, a player takes the entire pile of stones either from the beginning or from the end of the row. This continues until there are no more piles left, at which point the person with the most stones wins.

Assuming Alice and Bob play optimally, return true if Alice wins the game, or false if Bob wins.

 

Example 1:

Input: piles = [5,3,4,5]
Output: true
Explanation: 
Alice starts first, and can only take the first 5 or the last 5.
Say she takes the first 5, so that the row becomes [3, 4, 5].
If Bob takes 3, then the board is [4, 5], and Alice takes 5 to win with 10 points.
If Bob takes the last 5, then the board is [3, 4], and Alice takes 4 to win with 9 points.
This demonstrated that taking the first 5 was a winning move for Alice, so we return true.
Example 2:

Input: piles = [3,7,2,3]
Output: true
 

Constraints:

2 <= piles.length <= 500
piles.length is even.
1 <= piles[i] <= 500
sum(piles[i]) is odd.
 */



// My Solution:
class Solution {
    public boolean stoneGame(int[] piles) {
        // 贪心+DP - https://leetcode.com/problems/stone-game/discuss/154610/DP-or-Just-return-true
        // - https://leetcode.cn/problems/stone-game/solution/shi-zi-you-xi-by-leetcode-solution/
        /*
            由于每次只能从行的开始或结束处取走整堆石子，因此可以保证剩下的石子堆一定是连续的。

            如果只剩下一堆石子，则当前玩家只能取走这堆石子。如果剩下多堆石子，则当前玩家可以选择从行的开始或结束处取走整堆石子，然后轮到另一个玩家在剩下的石子堆中取走石子。这是一个递归的过程，因此可以使用递归进行求解，递归过程中维护一个总数，表示 Alice 和 Bob 的石子数量之差，当游戏结束时，如果总数大于 0，则 Alice 赢得比赛，否则 Bob 赢得比赛。

            如果有 n 堆石子，则递归的时间复杂度为 O(2^n)，无法通过所有的测试用例。递归的时间复杂度高的原因是存在大量重复计算。由于存在重复子问题，因此可以使用动态规划降低时间复杂度。

            定义二维数组 dp，其行数和列数都等于石子的堆数，dp[i][j] 表示当剩下的石子堆为下标 i 到下标 j 时，即在下标范围 [i,j] 中，当前玩家与另一个玩家的石子数量之差的最大值，注意当前玩家不一定是先手 Alice。

            只有当 i≤j 时，剩下的石子堆才有意义，因此当 i>j 时，dp[i][j]=0。

            当 i=j 时，只剩下一堆石子，当前玩家只能取走这堆石子，因此对于所有 0≤i<nums.length，都有 dp[i][i]=piles[i]。

            当 i<j 时，当前玩家可以选择取走 piles[i] 或 piles[j]，然后轮到另一个玩家在剩下的石子堆中取走石子。在两种方案中，当前玩家会选择最优的方案，使得自己的石子数量最大化。因此可以得到如下状态转移方程：
            dp[i][j] = max(piles[i]−dp[i+1][j], piles[j]−dp[i][j−1])

            最后判断 dp[0][piles.length−1] 的值，如果大于 0，则 Alice 的石子数量大于 Bob 的石子数量，因此 Alice 赢得比赛，否则 Bob 赢得比赛。
        */
        int n = piles.length;
        int[][] dp = new int[n][n];
        for (int i=0; i<n; i++) dp[i][i] = piles[i];
        for (int i=n-2; i>=0; i--) {
            for (int j=i+1; j<n; j++) {
                dp[i][j] = Math.max(piles[i] - dp[i+1][j], piles[j] - dp[i][j-1]);
            }
        }

        return dp[0][n-1] > 0;
    }
}
