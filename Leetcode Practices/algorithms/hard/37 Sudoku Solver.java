/**
Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
The '.' character indicates empty cells.

 

Example 1:
https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png

Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
Explanation: The input board is shown above and the only valid solution is shown below:
https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Sudoku-by-L2G-20050714_solution.svg/250px-Sudoku-by-L2G-20050714_solution.svg.png

 

Constraints:

board.length == 9
board[i].length == 9
board[i][j] is a digit or '.'.
It is guaranteed that the input board has only one solution.
 */



// My Solution:
class Solution {
    Map<Integer, Set<Integer>> rows;
    Map<Integer, Set<Integer>> cols;
    Map<Integer, Set<Integer>> boxs;
    int filled = 0;
    
    public void solveSudoku(char[][] board) {
        // 哈希表 + 哈希集 + 回溯法
        rows = new HashMap<>();
        cols = new HashMap<>();
        boxs = new HashMap<>();
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                rows.putIfAbsent(i, new HashSet<>());
                cols.putIfAbsent(j, new HashSet<>());
                boxs.putIfAbsent((i/3)*10+(j/3), new HashSet<>());
                if (board[i][j] != '.') {
                    filled++;
                    int digit = board[i][j] - '0';
                    rows.get(i).add(digit);
                    cols.get(j).add(digit);
                    boxs.get((i/3)*10+(j/3)).add(digit);
                }
            }
        }
        
        backtracking(board, 0, 0);
        return;
    }
    
    public boolean backtracking(char[][] board, int row, int col) {
        for (int i=row; i<9; i++) {
            for (int j=col; j<9; j++) {
                if (board[i][j] == '.') {
                    for (int val=1; val<=9; val++) {
                        if (rows.get(i).contains(val) || cols.get(j).contains(val) || boxs.get((i/3)*10+(j/3)).contains(val)) continue;
                        
                        rows.get(i).add(val);
                        cols.get(j).add(val);
                        boxs.get((i/3)*10+(j/3)).add(val);
                        board[i][j] = (char) (val + '0');
                        if (++filled == 81) return true;
                        if (backtracking(board, row, col)) return true;
                        --filled;
                        board[i][j] = '.';
                        rows.get(i).remove(val);
                        cols.get(j).remove(val);
                        boxs.get((i/3)*10+(j/3)).remove(val);
                    }
                    if (board[i][j] == '.') return false; // 没找到合适的填充值
                }
            }
        }
        return true;
    }
}
