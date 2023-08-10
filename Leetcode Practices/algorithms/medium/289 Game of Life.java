/**
According to Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1) or dead (represented by a 0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):

Any live cell with fewer than two live neighbors dies as if caused by under-population.
Any live cell with two or three live neighbors lives on to the next generation.
Any live cell with more than three live neighbors dies, as if by over-population.
Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.

 

Example 1:
https://assets.leetcode.com/uploads/2020/12/26/grid1.jpg

Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]

Example 2:
https://assets.leetcode.com/uploads/2020/12/26/grid2.jpg

Input: board = [[1,1],[1,0]]
Output: [[1,1],[1,1]]
 

Constraints:

m == board.length
n == board[i].length
1 <= m, n <= 25
board[i][j] is 0 or 1.
 

Follow up:

Could you solve it in-place? Remember that the board needs to be updated simultaneously: You cannot update some cells first and then use their updated values to update other cells.
In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches upon the border of the array (i.e., live cells reach the border). How would you address these problems?
 */



// Other's Solution: Time: O(mn), Space: O(1)
class Solution {
    public void gameOfLife(int[][] board) {
        // https://leetcode.cn/problems/game-of-life/solution/sheng-ming-you-xi-by-leetcode-solution/
        int[] neighbors = {0, 1, -1};

        int rows = board.length;
        int cols = board[0].length;

        // 遍历面板每一个格子里的细胞
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // 对于每一个细胞统计其八个相邻位置里的活细胞数量
                int liveNeighbors = 0;

                // 查看相邻 8 个的细胞是否是活细胞
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if ((neighbors[i] == 0 && neighbors[j] == 0)) continue;
                        // 相邻位置的坐标
                        int r = (row + neighbors[i]);
                        int c = (col + neighbors[j]);
                        if (r >= rows || r < 0 || c >= cols || c < 0) continue;
                        if (Math.abs(board[r][c]) == 1) liveNeighbors++;
                    }
                }

                // 规则 1 或规则 3 
                if ((board[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    board[row][col] = -1; // -1 代表这个细胞过去是活的现在死了
                }
                // 规则 4
                if (board[row][col] == 0 && liveNeighbors == 3) {
                    board[row][col] = 2; // 2 代表这个细胞过去是死的现在活了
                }
            }
        }

        // 遍历 board 得到一次更新后的状态
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] > 0) board[row][col] = 1;
                else board[row][col] = 0;
            }
        }
    }
}



// My Solution:
class Solution {
    public void gameOfLife(int[][] board) {
        // 把要翻转的 1 设置为 3，要翻转的 0 设置为 2，即一旦要翻转则 += 2，然后检查时也检查 int[i][j]-2
        // 最后再遍历一次，若为 3 则改为 0，若为 2 则改为 1
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[0].length; j++) {
                mark(board, i, j);
            }
        }
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[0].length; j++) {
                set(board, i, j);
            }
        }
    }
    
    public void mark(int[][] board, int i, int j) {
        int cntLive = 0;
        if (i > 0 && j > 0) cntLive += (board[i-1][j-1] == 1) || (board[i-1][j-1] == 3) ? 1 : 0;
        if (i > 0) cntLive += (board[i-1][j] == 1) || (board[i-1][j] == 3) ? 1 : 0;
        if (i > 0 && j < board[0].length-1) cntLive += (board[i-1][j+1] == 1) || (board[i-1][j+1] == 3) ? 1 : 0;
        if (j > 0) cntLive += (board[i][j-1] == 1) || (board[i][j-1] == 3) ? 1 : 0;
        if (j < board[0].length-1) cntLive += (board[i][j+1] == 1) || (board[i][j+1] == 3) ? 1 : 0;
        if (i < board.length-1 && j > 0) cntLive += (board[i+1][j-1] == 1) || (board[i+1][j-1] == 3) ? 1 : 0;
        if (i < board.length-1) cntLive += (board[i+1][j] == 1) || (board[i+1][j] == 3) ? 1 : 0;
        if (i < board.length-1 && j < board[0].length-1) cntLive += (board[i+1][j+1] == 1) || (board[i+1][j+1] == 3) ? 1 : 0;
        
        if (cntLive < 2 && board[i][j] == 1) board[i][j] += 2;
        if ((cntLive == 2 || cntLive == 3) && board[i][j] == 1) return;
        if (cntLive > 3 && board[i][j] == 1) board[i][j] += 2;
        if (cntLive == 3 && board[i][j] == 0) board[i][j] += 2;
    }
    
    public void set(int[][] board, int i, int j) {
        if (board[i][j] == 3) board[i][j] = 0;
        if (board[i][j] == 2) board[i][j] = 1;
    }
}
