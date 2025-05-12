/*
Given an n x n 2D matrix , write a function to rotate the image by 90 degrees (clockwise). 

Input: 

const matrix = [ 
    [1, 2, 3], 
    [4, 5, 6], 
    [7, 8, 9] 
]; 
 
Output: 
[ 
  [7, 4, 1], 
  [8, 5, 2], 
  [9, 6, 3] 
] 


0,0 -> 0,n-1
0,1 -> 1,n-1
0,2 -> 2,n-1

1,0 -> 0,n-2 (n-1 - row)
1,1 -> 1,n-2
1,2 -> 2,n-2

element (r, c) -> (c, n-1-r)

[ 
  [0, 0, 0], 
  [0, 0, 0], 
  [0, 0, 0] 
] 
*/

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] matrix = new int[][]{
            {1, 2, 3}, 
            {4, 5, 6}, 
            {7, 8, 9}
        };
        s.process(matrix);
        assert 
    }

    public int[][] process(int[][] matrix) { // Time: O(n^2), Space: (n^2)
        int n = matrix.length;
        int[][] res = new int[n][n];
        for (int r=0; r<n; r++) {
            for (int c=0; c<n; c++) {
                res[c][n-1-r] = matrix[r][c];
            }
        }
        for (int[] row : res) {
            for (int e : row) {
                System.out.print(e + ", ");
            }
            System.out.println("");
        }
        return res;
    }
}