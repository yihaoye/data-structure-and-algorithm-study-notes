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
        // 从左上角到右下角的过程中，需要移动 m + n - 2 次，其中有 m - 1 次向下移动，n - 1 次向右移动。
        // 更简单的比方是假如有且必须严格选 m + n - 2 张票、它们均表示移动一格，其中有严格且必选 m - 1 张写向下移动一格，严格且必选 n - 1 张写向右移动一格，而假设第一步向下方向移动时 - 意味着从 m - 1 张票选一张但它们都是一样的所以没有先后顺序的不同，即最后为一个无关排列的组合问题，
        // 因此路径的总数，就等于从 m + n - 2 次移动中选择 m − 1 次向下移动的方案数（或从 m + n - 2 次移动中选择 n − 1 次向右移动的方案数，计算结果都是一样的），
        // 所以整个组合 ans 的计算可以使用组合公式：C(m+n-2, m-1) 或 C(m+n-2, n-1)，而它们都是相等的。
        // 即组合数：ans = ((m + n - 2)! / (n - 1)!) / (m - 1)!
        // 但是直接算阶乘会在数字太大的时候数据溢出，所以需要一些变动，如下面代码所示：但是一边乘一边除 ans 会不会在过程中得到小数导致数不准确呢？答案是不会，因为从 1 开始的遍历经过到一个 x 的倍数时，从 n 开始的遍历也一定经过了一个 x 的倍数，所以总是除得开的。
        // Time: O(min(m, n)), Space: O(1)
        long ans = 1;
        for (int x = n, y = 1; y < m; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }
}
