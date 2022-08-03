/**
Tic-tac-toe is played by two players A and B on a 3 x 3 grid. The rules of Tic-Tac-Toe are:

Players take turns placing characters into empty squares ' '.
The first player A always places 'X' characters, while the second player B always places 'O' characters.
'X' and 'O' characters are always placed into empty squares, never on filled ones.
The game ends when there are three of the same (non-empty) character filling any row, column, or diagonal.
The game also ends if all squares are non-empty.
No more moves can be played if the game is over.
Given a 2D integer array moves where moves[i] = [rowi, coli] indicates that the ith move will be played on grid[rowi][coli]. return the winner of the game if it exists (A or B). In case the game ends in a draw return "Draw". If there are still movements to play return "Pending".

You can assume that moves is valid (i.e., it follows the rules of Tic-Tac-Toe), the grid is initially empty, and A will play first.

 

Example 1:
https://assets.leetcode.com/uploads/2021/09/22/xo1-grid.jpg

Input: moves = [[0,0],[2,0],[1,1],[2,1],[2,2]]
Output: "A"
Explanation: A wins, they always play first.

Example 2:
https://assets.leetcode.com/uploads/2021/09/22/xo2-grid.jpg

Input: moves = [[0,0],[1,1],[0,1],[0,2],[1,0],[2,0]]
Output: "B"
Explanation: B wins.

Example 3:
https://assets.leetcode.com/uploads/2021/09/22/xo3-grid.jpg

Input: moves = [[0,0],[1,1],[2,0],[1,0],[1,2],[2,1],[0,1],[0,2],[2,2]]
Output: "Draw"
Explanation: The game ends in a draw since there are no moves to make.
 

Constraints:

1 <= moves.length <= 9
moves[i].length == 2
0 <= rowi, coli <= 2
There are no repeated elements on moves.
moves follow the rules of tic tac toe.
 */



// Other's Solution:
class Solution {
    public String tictactoe(int[][] moves) {
        // https://leetcode.com/problems/find-winner-on-a-tic-tac-toe-game/discuss/490101/Java-100-with-comments-and-Interview-tip/610276
        int n = 3;
        int[] rows = new int[n];
        int[] cols = new int[n];
        int diag1 = 0;
        int diag2 = 0;
        int currPlayer = 1;   // 1 is 'A', -1 is 'B'

        for (int[] currMove : moves) {
            rows[currMove[0]] += currPlayer;
            cols[currMove[1]] += currPlayer;
            diag1 = currMove[0] == currMove[1] ? diag1 + currPlayer : diag1;
            diag2 = currMove[0] + currMove[1] == n - 1 ? diag2 + currPlayer : diag2; 

            if (Math.abs(rows[currMove[0]]) == n || Math.abs(cols[currMove[1]]) == n || Math.abs(diag1) == n || Math.abs(diag2) == n) {
                return currPlayer == 1 ? "A" : "B";
            }
            currPlayer *= -1; // moves 数组里，A、B 选手是交替进行的
        }

        return moves.length < n*n ? "Pending" : "Draw";
    }
}
