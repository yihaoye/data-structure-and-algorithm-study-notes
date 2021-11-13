/*
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

Now consider if some obstacles are added to the grids. How many unique paths would there be?


![](https://assets.leetcode.com/uploads/2018/10/22/robot_maze.png)
An obstacle and empty space is marked as 1 and 0 respectively in the grid.

Note: m and n will be at most 100.

Example 1:

Input:
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
Output: 2
Explanation:
There is one obstacle in the middle of the 3x3 grid above.
There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right
*/



// My Solution 1:
// 思路 - DP，本题与题目 62 类似，遍历横纵坐标，dp[i, j] 代表着到达坐标 [i, j] 的路径可能性数量，因为机器人只能向右或向下移动，所以 dp[i, j] = dp[i-1, j] + dp[i][j-1]，在内循环时遇到障碍（即值为 1 的情况时），直接跳过本次 j 即可，而 dp[i][j] 维持原始值 0 即正解（注意，这里不会使后面的 dp[i+k][j] 为 0）
// 时间复杂度 O(N*M)，空间复杂度 O(N*M)
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length; // only difference with question 62
        int[][] dp = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (obstacleGrid[i][j] == 1) continue; // only difference with question 62
                if (i == 0 && j == 0) dp[i][j] = 1;
                else if (i == 0) dp[i][j] = dp[i][j-1];
                else if (j == 0) dp[i][j] = dp[i-1][j];
                else dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
}
