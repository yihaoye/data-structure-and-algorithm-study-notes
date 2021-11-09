/*
Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

 

Example 1:
https://assets.leetcode.com/uploads/2020/11/26/max1grid.jpg
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4

Example 2:
https://assets.leetcode.com/uploads/2020/11/26/max2grid.jpg
Input: matrix = [["0","1"],["1","0"]]
Output: 1

Example 3:
Input: matrix = [["0"]]
Output: 0
 

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 300
matrix[i][j] is '0' or '1'.
*/



// Other's Solution (https://leetcode.com/problems/maximal-square/discuss/61802/Extremely-Simple-Java-Solution-:)/63328):
// 动态规划 O(n*m)
class Solution {
    public int maximalSquare(char[][] matrix) {
        if(matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];

        int maxEdge = 0;      
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(matrix[i - 1][j - 1] == '1'){
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]),dp[i - 1][j - 1]) + 1;
                    maxEdge = Math.max(maxEdge, dp[i][j]);
                }
            }
        }

        return maxEdge * maxEdge;  
    }
}
