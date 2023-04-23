/*
You are given an n x n 2D matrix representing an image.

Rotate the image by 90 degrees (clockwise).

Note:

You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

Example 1:

Given input matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

rotate the input matrix in-place such that it becomes:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
Example 2:

Given input matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
], 

rotate the input matrix in-place such that it becomes:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]
*/




// Other's Solution:
class Solution {
    public void rotate(int[][] matrix) {
        // https://leetcode.com/problems/rotate-image/solutions/18872/a-common-method-to-rotate-the-image/?orderBy=most_votes
        /*
        * clockwise rotate
        * first reverse up to down, then swap the symmetry 
        * 1 2 3     7 8 9     7 4 1
        * 4 5 6  => 4 5 6  => 8 5 2
        * 7 8 9     1 2 3     9 6 3
        *
        * 5  1  9  11      15 14 12 16          15 13 2  5
        * 2  4  8  10  =>  13 3  6  7     =>    14 3  4  1
        * 13 3  6  7       2  4  8  10          12 6  8  9
        * 15 14 12 16      5  1  9  11          16 7  10 11
        */
        int s = 0, e = matrix.length - 1;
        while (s < e) {
            int[] temp = matrix[s];
            matrix[s] = matrix[e];
            matrix[e] = temp;
            s++; e--;
        }

        for (int r = 0; r < matrix.length; r++) {
            for (int c = r+1; c < matrix[r].length; c++) { // c = r+1 因为对角线左边的已经被之前的操作换过了，对角线上的不用换
                int temp = matrix[r][c];
                matrix[r][c] = matrix[c][r];
                matrix[c][r] = temp;
            }
        }
    }
}



// My Solution:
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length, half = matrix.length/2, columnStart = 0;
        for (int row=0; row<half; row++) {
            for (int column=columnStart; column<n-1; column++) {
                rotateE(matrix, row, column);
            }
            n--;
            columnStart++;
        }
    }
    
    // rotate element {row, column} and its related 3 elements
    public void rotateE(int[][] matrix, int row, int column) {
        int n = matrix.length-1; 
        int tmp1 = matrix[n-column][row], tmp2 = matrix[row][column], tmp3 = matrix[column][n-row], tmp4 = matrix[n-row][n-column];
        matrix[row][column] = tmp1;
        matrix[column][n-row] = tmp2;
        matrix[n-row][n-column] = tmp3;
        matrix[n-column][row] = tmp4;
    }
}



// My Solution:
class Solution {
    public void rotate(int[][] matrix) {
        // 分治 + 缓存变量 + 模拟
        int n = matrix.length;
        for (int i=0; i<n/2; i++) // n 如果为奇数，中间的不需要操作，n/2 仍正确
            rotate(matrix, i);
    }

    public void rotate(int[][] matrix, int layer) {
        int n = matrix.length, m = matrix.length - layer;
        for (int r=layer, c=layer; c<m-1; c++) {
            int tmp = matrix[r][c];
            matrix[r][c] = matrix[n-1-c][r];
            matrix[n-1-c][r] = matrix[n-1-r][n-1-c];
            matrix[n-1-r][n-1-c] = matrix[c][n-1-r];
            matrix[c][n-1-r] = tmp;
        }
    }
}
