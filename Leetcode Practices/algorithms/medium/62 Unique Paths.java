/*
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

How many possible unique paths are there?

![](https://assets.leetcode.com/uploads/2018/10/22/robot_maze.png)
Above is a 7 x 3 grid. How many possible unique paths are there?

Note: m and n will be at most 100.

Example 1:

Input: m = 3, n = 2
Output: 3
Explanation:
From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Right -> Down
2. Right -> Down -> Right
3. Down -> Right -> Right
Example 2:

Input: m = 7, n = 3
Output: 28
*/



// My Solution 1 (2D Dynamic Programming):
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] mem = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (i == 0 && j == 0) mem[i][j] = 1;
                else if (i == 0) mem[i][j] = mem[i][j-1];
                else if (j == 0) mem[i][j] = mem[i-1][j];
                else mem[i][j] = mem[i-1][j] + mem[i][j-1];
            }
        }
        return mem[m-1][n-1];
    }
}



// Other's Solution:
class Solution {
    public int uniquePaths(int m, int n) {
        // 组合数学 - https://leetcode.cn/problems/unique-paths/solution/bu-tong-lu-jing-by-leetcode-solution-hzjf/
        // 从左上角到右下角的过程中，需要移动 m + n - 2 次，其中有 m - 1 次向下移动，n - 1 次向右移动。因此路径的总数，就等于从 m + n - 2 次移动中选择 m − 1 次向下移动的方案数，即组合数：
        // ans = ((m + n - 2)! / (n - 1)!) / (m - 1)!
        // 但是直接算阶乘会在数字太大的时候数据溢出，所以需要一些变动
        // Time: O(min(m, n)), Space: O(1)
        long ans = 1;
        for (int x = n, y = 1; y < m; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }
}
