/**
A square matrix is said to be an X-Matrix if both of the following conditions hold:

All the elements in the diagonals of the matrix are non-zero.
All other elements are 0.
Given a 2D integer array grid of size n x n representing a square matrix, return true if grid is an X-Matrix. Otherwise, return false.

 

Example 1:
https://assets.leetcode.com/uploads/2022/05/03/ex1.jpg

Input: grid = [[2,0,0,1],[0,3,1,0],[0,5,2,0],[4,0,0,2]]
Output: true
Explanation: Refer to the diagram above. 
An X-Matrix should have the green elements (diagonals) be non-zero and the red elements be 0.
Thus, grid is an X-Matrix.

Example 2:
https://assets.leetcode.com/uploads/2022/05/03/ex2.jpg

Input: grid = [[5,7,0],[0,3,1],[0,5,0]]
Output: false
Explanation: Refer to the diagram above.
An X-Matrix should have the green elements (diagonals) be non-zero and the red elements be 0.
Thus, grid is not an X-Matrix.
 

Constraints:

n == grid.length == grid[i].length
3 <= n <= 100
0 <= grid[i][j] <= 105
 */



// My Solution:
class Solution {
    public boolean checkXMatrix(int[][] grid) {
        // 模拟+双指针，可根据第几行计算出该行应该不为 0 的位置（有且只有两个或一个）
        int n = grid.length;
        int l = 0, r = n-1;
        for (int[] row : grid) {
            if (row[l] == 0 || row[r] == 0) return false;
            for (int i=0; i<n; i++) {
                if (i == l || i == r) continue;
                if (row[i] != 0) return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
