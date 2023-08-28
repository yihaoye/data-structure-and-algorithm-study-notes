/*
Let's play the minesweeper game (Wikipedia, online game)!

You are given an m x n char matrix board representing the game board where:

'M' represents an unrevealed mine,
'E' represents an unrevealed empty square,
'B' represents a revealed blank square that has no adjacent mines (i.e., above, below, left, right, and all 4 diagonals),
digit ('1' to '8') represents how many mines are adjacent to this revealed square, and
'X' represents a revealed mine.
You are also given an integer array click where click = [clickr, clickc] represents the next click position among all the unrevealed squares ('M' or 'E').

Return the board after revealing this position according to the following rules:

If a mine 'M' is revealed, then the game is over. You should change it to 'X'.
If an empty square 'E' with no adjacent mines is revealed, then change it to a revealed blank 'B' and all of its adjacent unrevealed squares should be revealed recursively.
If an empty square 'E' with at least one adjacent mine is revealed, then change it to a digit ('1' to '8') representing the number of adjacent mines.
Return the board when no more squares will be revealed.
 

Example 1:
https://assets.leetcode.com/uploads/2023/08/09/untitled.jpeg

Input: board = [["E","E","E","E","E"],["E","E","M","E","E"],["E","E","E","E","E"],["E","E","E","E","E"]], click = [3,0]
Output: [["B","1","E","1","B"],["B","1","M","1","B"],["B","1","1","1","B"],["B","B","B","B","B"]]

Example 2:
https://assets.leetcode.com/uploads/2023/08/09/untitled-2.jpeg

Input: board = [["B","1","E","1","B"],["B","1","M","1","B"],["B","1","1","1","B"],["B","B","B","B","B"]], click = [1,2]
Output: [["B","1","E","1","B"],["B","1","X","1","B"],["B","1","1","1","B"],["B","B","B","B","B"]]
 

Constraints:

m == board.length
n == board[i].length
1 <= m, n <= 50
board[i][j] is either 'M', 'E', 'B', or a digit from '1' to '8'.
click.length == 2
0 <= clickr < m
0 <= clickc < n
board[clickr][clickc] is either 'M' or 'E'.
*/



// My Solution:
class Solution {
    public int[][] neis = new int[][]{{1,0}, {-1,0}, {0,1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    public int n, m;

    public char[][] updateBoard(char[][] board, int[] click) {
        // DFS
        n = board.length; m = board[0].length;
        int r = click[0]; int c = click[1];
        if (board[r][c] == 'M') {
            board[r][c] = 'X';
        } else {
            int adjMines = checkNeis(board, click);
            if (adjMines == 0) board[r][c] = 'B';
            else board[r][c] = (char) (adjMines + '0');
        }
        if (board[r][c] != 'B') return board;

        for (int[] nei : neis) {
            int nxtR = r + nei[0];
            int nxtC = c + nei[1];
            if (nxtR < 0 || nxtR >= n || nxtC < 0 || nxtC >= m || board[nxtR][nxtC] != 'E') continue;
            updateBoard(board, new int[]{nxtR, nxtC});
        }

        return board;
    }

    public int checkNeis(char[][] board, int[] click) {
        int res = 0;
        for (int[] nei : neis) {
            int r = click[0]; int c = click[1];
            int nxtR = r + nei[0];
            int nxtC = c + nei[1];
            if (nxtR < 0 || nxtR >= n || nxtC < 0 || nxtC >= m) continue;
            if (board[nxtR][nxtC] == 'M') res++;
        }
        return res;
    }
}
