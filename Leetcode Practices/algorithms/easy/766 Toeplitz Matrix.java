/*
A matrix is Toeplitz if every diagonal from top-left to bottom-right has the same element.

Now given an M x N matrix, return True if and only if the matrix is Toeplitz.
 

Example 1:

Input:
matrix = [
  [1,2,3,4],
  [5,1,2,3],
  [9,5,1,2]
]
Output: True
Explanation:
In the above grid, the diagonals are:
"[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]".
In each diagonal all elements are the same, so the answer is True.
Example 2:

Input:
matrix = [
  [1,2],
  [2,2]
]
Output: False
Explanation:
The diagonal "[1, 2]" has different elements.

Note:

matrix will be a 2D array of integers.
matrix will have a number of rows and columns in range [1, 20].
matrix[i][j] will be integers in range [0, 99].

Follow up:

What if the matrix is stored on disk, and the memory is limited such that you can only load at most one row of the matrix into the memory at once?
What if the matrix is so large that you can only load up a partial row into the memory at once?
*/



// My Solution:
class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {
        int lenColumn = matrix.length;
        int lenRow = matrix[0].length;
        
        ArrayList<ArrayList<Integer>> diagonals = new ArrayList<ArrayList<Integer>>();
        int lenDiagonals = lenColumn + lenRow - 1;
        for (int i=0; i < lenDiagonals; i++) {
            diagonals.add(new ArrayList<Integer>());
        }
        for (int c=0; c < lenColumn; c++) {
            for (int r=0; r < lenRow; r++) {
                diagonals.get(lenColumn-c+r-1).add(matrix[c][r]);
            }
        }
        
        for (ArrayList<Integer> diagonal : diagonals) {
            if (!isToeplitzDiagonal(diagonal)) return false;
        }
        
        return true;
    }
    
    public boolean isToeplitzDiagonal(ArrayList<Integer> diagonal) {
        for (int digit : diagonal) {
            if (diagonal.get(0) != digit) return false;
        }
        return true;
    }
}