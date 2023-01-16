/**
You are given a positive integer n, indicating that we initially have an n x n 0-indexed integer matrix mat filled with zeroes.

You are also given a 2D integer array query. For each query[i] = [row1i, col1i, row2i, col2i], you should do the following operation:

Add 1 to every element in the submatrix with the top left corner (row1i, col1i) and the bottom right corner (row2i, col2i). That is, add 1 to mat[x][y] for for all row1i <= x <= row2i and col1i <= y <= col2i.
Return the matrix mat after performing every query.

 

Example 1:
https://assets.leetcode.com/uploads/2022/11/24/p2example11.png

Input: n = 3, queries = [[1,1,2,2],[0,0,1,1]]
Output: [[1,1,0],[1,2,1],[0,1,1]]
Explanation: The diagram above shows the initial matrix, the matrix after the first query, and the matrix after the second query.
- In the first query, we add 1 to every element in the submatrix with the top left corner (1, 1) and bottom right corner (2, 2).
- In the second query, we add 1 to every element in the submatrix with the top left corner (0, 0) and bottom right corner (1, 1).

Example 2:
https://assets.leetcode.com/uploads/2022/11/24/p2example22.png

Input: n = 2, queries = [[0,0,1,1]]
Output: [[1,1],[1,1]]
Explanation: The diagram above shows the initial matrix and the matrix after the first query.
- In the first query we add 1 to every element in the matrix.
 

Constraints:

1 <= n <= 500
1 <= queries.length <= 10^4
0 <= row1i <= row2i < n
0 <= col1i <= col2i < n
 */



// My Solution:
class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        // 二维差分数组
        int[][] diff = new int[n+1][n+1]; // n+1 for easy implement
        for (int[] query : queries) {
            int x1 = query[0]; int y1 = query[1]; int x2 = query[2]; int y2 = query[3];
            diff[x1][y1] += 1;
            diff[x1][y2+1] -= 1;
            diff[x2+1][y1] -= 1;
            diff[x2+1][y2+1] += 1;
        }
        
        int[][] mat = new int[n][n];
        for (int r=0; r<n; r++) {
            for (int c=0; c<n; c++) {
                mat[r][c] += c > 0 ? mat[r][c-1] : 0;
                mat[r][c] += diff[r][c];
            }
        }
        for (int c=0; c<n; c++) {
            for (int r=0; r<n; r++) {
                mat[r][c] += r > 0 ? mat[r-1][c] : 0;
            }
        }
        return mat;
    }
}
// 可以进一步优化为如下：
class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        // 二维差分数组
        int[][] res = new int[n][n];
        for (int[] query : queries) {
            int x1 = query[0]; int y1 = query[1]; int x2 = query[2]; int y2 = query[3];
            res[x1][y1] += 1;
            if (y2 < n-1) res[x1][y2+1] -= 1;
            if (x2 < n-1) res[x2+1][y1] -= 1;
            if (x2 < n-1 && y2 < n-1) res[x2+1][y2+1] += 1;
        }
        
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                res[i][j] += i > 0 ? res[i-1][j] : 0;
                res[i][j] += j > 0 ? res[i][j-1] : 0;
                res[i][j] -= i > 0 && j > 0 ? res[i-1][j-1] : 0;
            }
        }
        return res;
    }
}
