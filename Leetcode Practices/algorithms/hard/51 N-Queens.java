/*
The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

![](https://assets.leetcode.com/uploads/2018/10/12/8-queens.png)

Given an integer n, return all distinct solutions to the n-queens puzzle.

Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space respectively.

Example:

Input: 4
Output: [
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above.
*/



// My Solution:
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<List<String>>();
        int[][] chessboard = new int[n][n];
        
        // backtracking with recrusive
        backtracking(res, chessboard, 0);
        
        return res;
    }
    
    public void backtracking(List<List<String>> res, int[][] chessboard, int x) {
        int n = chessboard.length;
        for (int y=0; y<n; y++) {
            if (isValid(chessboard, x, y)) {
                chessboard[x][y] = 1;
                if (x == n-1) {
                    res.add(cbArrToStrList(chessboard));
                } else {
                    backtracking(res, chessboard, x+1);
                }
                chessboard[x][y] = 0;
            }
        }
    }
    
    // check if new queen location is valid with previous queens
    public boolean isValid(int[][] chessboard, int x, int y) {
        int n = chessboard.length;
        for (int i=0; i<x; i++) {
            if (chessboard[i][y] == 1) return false;
            if (y+x-i < n && chessboard[i][y+x-i] == 1) return false;
            if (y-x+i >= 0 && chessboard[i][y-x+i] == 1) return false;
        }
        return true;
    }
    
    public List<String> cbArrToStrList(int[][] chessboard) {
        List<String> strList = new ArrayList<String>();
        for (int[] row : chessboard) {
            String rowStr = "";
            for (int e : row) {
                if (e == 0) rowStr += ".";
                else rowStr += "Q";
            }
            strList.add(rowStr);
        }
        return strList;
    }
}