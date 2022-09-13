/**
The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.

Given an integer n, return the number of distinct solutions to the n-queens puzzle.

 

Example 1:
https://assets.leetcode.com/uploads/2020/11/13/queens.jpg

Input: n = 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown.

Example 2:

Input: n = 1
Output: 1
 

Constraints:

1 <= n <= 9
 */



// My Solution:
class Solution {
    public int res;
    public int N;
    public int[][] DIRS = new int[][]{{-1,-1}, {1,1}, {1,0}, {0,1}, {-1,0}, {0,-1}, {1,-1}, {-1,1}};
    
    public int totalNQueens(int n) {
        N = n;
        boolean[][] puzzle = new boolean[n][n];
        for (int col=0; col<n; col++) {
            if (0 == N-1) {
                res++;
                continue;
            }
            puzzle[0][col] = true;
            backtracking(puzzle, 1);
            puzzle[0][col] = false;
        }
        return res;
    }
    
    public void backtracking(boolean[][] puzzle, int row) {
        for (int col=0; col<N; col++) {
            if (!isValid(puzzle, row, col)) continue;
            if (row == N-1) {
                res++;
                continue;
            }
            puzzle[row][col] = true;
            backtracking(puzzle, row + 1);
            puzzle[row][col] = false;
        }
    }
    
    public boolean isValid(boolean[][] puzzle, int row, int col) {
        // 8 directions check
        for (int[] dir : DIRS) {
            int[] pos = new int[]{row, col};
            while (0 <= pos[0] && pos[0] < N && 0 <= pos[1] && pos[1] < N) {
                if (puzzle[pos[0]][pos[1]] == true) return false;
                pos[0] += dir[0];
                pos[1] += dir[1];
            }
        }
        
        return true;
    }
}
