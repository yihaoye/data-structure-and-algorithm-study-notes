/**
Given an m x n picture consisting of black 'B' and white 'W' pixels, return the number of black lonely pixels.

A black lonely pixel is a character 'B' that located at a specific position where the same row and same column don't have any other black pixels.

 

Example 1:
https://assets.leetcode.com/uploads/2020/11/11/pixel1.jpg

Input: picture = [["W","W","B"],["W","B","W"],["B","W","W"]]
Output: 3
Explanation: All the three 'B's are black lonely pixels.

Example 2:
https://assets.leetcode.com/uploads/2020/11/11/pixel2.jpg

Input: picture = [["B","B","B"],["B","B","W"],["B","B","B"]]
Output: 0
 

Constraints:

m == picture.length
n == picture[i].length
1 <= m, n <= 500
picture[i][j] is 'W' or 'B'.
 */



// My Solution:
class Solution {
    public int findLonelyPixel(char[][] picture) {
        // DP
        int m = picture.length, n = picture[0].length;
        int[][][] dp = new int[m][n][2];
        for (int r=0; r<m; r++) {
            for (int c=0; c<n; c++) {
                dp[r][c][0] = (c > 0 ? dp[r][c-1][0] : 0) + (picture[r][c] == 'B' ? 1 : 0);
                dp[r][c][1] = (r > 0 ? dp[r-1][c][1] : 0) + (picture[r][c] == 'B' ? 1 : 0);
            }
        }
        
        int res = 0;
        for (int r=0; r<m; r++) {
            for (int c=0; c<n; c++) {
                if (dp[r][n-1][0] == 1 && dp[m-1][c][1] == 1 && picture[r][c] == 'B') res++;
            }
        }
        
        return res;
    }
}
