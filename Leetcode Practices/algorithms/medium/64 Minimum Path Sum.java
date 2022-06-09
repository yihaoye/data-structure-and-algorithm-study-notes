/**
Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

 

Example 1:
https://assets.leetcode.com/uploads/2020/11/05/minpath.jpg

Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
Example 2:

Input: grid = [[1,2,3],[4,5,6]]
Output: 12
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 200
0 <= grid[i][j] <= 100

 */



// My Solution:
class Solution {    
    public int minPathSum(int[][] grid) {
        // DP + greedy
        // Time: O(M*N), Space: O(M*N)
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n]; // dp[i][j] means minPathSum from [0,0] to [i,j]
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                dp[i][j] = lastMinSum(grid, dp, i, j);
            }
        }
        
        return dp[m-1][n-1];
    }
    
    private int lastMinSum(int[][] grid, int[][] dp, int i, int j) {
        int min = Integer.MAX_VALUE;
        if (i == 0 && j == 0) min = grid[i][j];
        // can only move either down or right at any point in time
        if (i > 0) min = Math.min(min, dp[i-1][j] + grid[i][j]);
        if (j > 0) min = Math.min(min, dp[i][j-1] + grid[i][j]);
        return min;
    }
}
