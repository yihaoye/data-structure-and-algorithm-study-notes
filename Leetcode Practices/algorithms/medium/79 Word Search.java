/**
Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

 

Example 1:
https://assets.leetcode.com/uploads/2020/11/04/word2.jpg

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true

Example 2:
https://assets.leetcode.com/uploads/2020/11/04/word-1.jpg

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
Output: true

Example 3:
https://assets.leetcode.com/uploads/2020/10/15/word3.jpg

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
Output: false
 

Constraints:

m == board.length
n = board[i].length
1 <= m, n <= 6
1 <= word.length <= 15
board and word consists of only lowercase and uppercase English letters.
 

Follow up: Could you use search pruning to make your solution faster with a larger board?
 */



// Other's Solution:
class Solution {
    public boolean exist(char[][] board, String word) {
        // 回溯法
        for (int i=0; i<board.length; i++)
            for (int j=0; j<board[0].length; j++)
                if (backtrack(board, i, j, word, 0)) return true;
        return false;
    }
    
    private boolean backtrack(char[][] board, int i, int j, String word, int wIndex) {
        if (board[i][j] != word.charAt(wIndex)) return false;
        if (wIndex == word.length()-1) return true;
        board[i][j] = '#';
        if (i < board.length-1 && backtrack(board, i+1, j, word, wIndex+1)) return true;
        if (i > 0 && backtrack(board, i-1, j, word, wIndex+1)) return true;
        if (j < board[0].length-1 && backtrack(board, i, j+1, word, wIndex+1)) return true;
        if (j > 0 && backtrack(board, i, j-1, word, wIndex+1)) return true;
        board[i][j] = word.charAt(wIndex);
        return false;
    }
}
