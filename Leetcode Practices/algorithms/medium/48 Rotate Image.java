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