/**
Given an m x n matrix matrix and an integer k, return the max sum of a rectangle in the matrix such that its sum is no larger than k.

It is guaranteed that there will be a rectangle with a sum no larger than k.

 

Example 1:


Input: matrix = [[1,0,1],[0,-2,3]], k = 2
Output: 2
Explanation: Because the sum of the blue rectangle [[0, 1], [-2, 3]] is 2, and 2 is the max number no larger than k (k = 2).
Example 2:

Input: matrix = [[2,2,-1]], k = 3
Output: 3
 

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 100
-100 <= matrix[i][j] <= 100
-10^5 <= k <= 10^5
 

Follow up: What if the number of rows is much larger than the number of columns?
 */



// My Solution:
class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        // 二维前缀和 - dp[r][c] means sum of rectangle from [0,0] to [r,c]
        int n = matrix.length, m = matrix[0].length, res = Integer.MIN_VALUE;
        int[][] dp = new int[n][m];
        for (int r=0; r<n; r++) {
            for (int c=0; c<m; c++) {
                dp[r][c] = (r > 0 ? dp[r-1][c] : 0) + (c > 0 ? dp[r][c-1] : 0) - (r > 0 && c > 0 ? dp[r-1][c-1] : 0) + matrix[r][c];
                
                for (int i=-1; i<=r; i++) {
                    for (int j=-1; j<=c; j++) {
                        if (i == r || j == c) continue;
                        int sub = dp[r][c] - (i >= 0 ? dp[i][c] : 0) - (j >= 0 ? dp[r][j] : 0) + (i >= 0 && j >= 0 ? dp[i][j] : 0);
                        if (sub <= k) res = Math.max(res, sub);
                    }
                }
            }
        }
        
        return res;
    }
}
