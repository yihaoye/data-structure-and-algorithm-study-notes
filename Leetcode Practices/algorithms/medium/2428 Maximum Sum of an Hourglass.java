/**
You are given an m x n integer matrix grid.

We define an hourglass as a part of the matrix with the following form:
https://assets.leetcode.com/uploads/2022/08/21/img.jpg

Return the maximum sum of the elements of an hourglass.

Note that an hourglass cannot be rotated and must be entirely contained within the matrix.

 

Example 1:
https://assets.leetcode.com/uploads/2022/08/21/1.jpg

Input: grid = [[6,2,1,3],[4,2,1,5],[9,2,8,7],[4,1,2,9]]
Output: 30
Explanation: The cells shown above represent the hourglass with the maximum sum: 6 + 2 + 1 + 2 + 9 + 2 + 8 = 30.

Example 2:
https://assets.leetcode.com/uploads/2022/08/21/2.jpg

Input: grid = [[1,2,3],[4,5,6],[7,8,9]]
Output: 35
Explanation: There is only one hourglass in the matrix, with the sum: 1 + 2 + 3 + 5 + 7 + 8 + 9 = 35.
 

Constraints:

m == grid.length
n == grid[i].length
3 <= m, n <= 150
0 <= grid[i][j] <= 10^6
 */



// My Solution:
class Solution {
    public int maxSum(int[][] grid) {
        // 前缀和
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int r=0; r<m; r++) {
            for (int c=0; c<n; c++) {
                dp[r][c] = (c > 0 ? dp[r][c-1] : 0) + grid[r][c];
            }
        }
        int res = 0;
        for (int r=1; r<m-1; r++) {
            for (int c=1; c<n-1; c++) {
                res = Math.max(res, grid[r][c] + dp[r-1][c+1] - dp[r-1][c-1] + dp[r+1][c+1] - dp[r+1][c-1] + grid[r-1][c-1] + grid[r+1][c-1]);
            }
        }
        return res;
    }
}
