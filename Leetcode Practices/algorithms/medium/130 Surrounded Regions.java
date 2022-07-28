/**
Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'.

A region is captured by flipping all 'O's into 'X's in that surrounded region.

 

Example 1:
https://assets.leetcode.com/uploads/2021/02/19/xogrid.jpg

Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
Explanation: Notice that an 'O' should not be flipped if:
- It is on the border, or
- It is adjacent to an 'O' that should not be flipped.
The bottom 'O' is on the border, so it is not flipped.
The other three 'O' form a surrounded region, so they are flipped.

Example 2:

Input: board = [["X"]]
Output: [["X"]]
 

Constraints:

m == board.length
n == board[i].length
1 <= m, n <= 200
board[i][j] is 'X' or 'O'.
 */



// My Solution:
class Solution {
    public int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    public void solve(char[][] board) {
        // 从四边往里进行检查，以四边上的 O 格子为起始进行 BFS 找寻并存入 HashSet（并采用状态压缩存 x y）中
        // 重新遍历一次整个矩阵，若 O 格子不在 HashSet 中则填为 X
        // Time: O(N*M), Space: O(K)
        int n = board[0].length, m = board.length;
        Queue<int[]> queue = new LinkedList<>();
        for (int i=0; i<m; i++) {
            if (board[i][0] == 'O') queue.offer(new int[]{i, 0});
            if (board[i][n-1] == 'O') queue.offer(new int[]{i, n-1});
        }
        for (int j=0; j<n; j++) {
            if (board[0][j] == 'O') queue.offer(new int[]{0, j});
            if (board[m-1][j] == 'O') queue.offer(new int[]{m-1, j});
        }
        Set<Integer> notFlipped = new HashSet<>();
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            if (notFlipped.contains(pos[0] << 8 | pos[1])) continue;
            for (int[] dir : dirs) {
                int newX = pos[1] + dir[1], newY = pos[0] + dir[0];
                if (newX >= 0 && newX < n && newY >= 0 && newY < m && board[newY][newX] == 'O') queue.offer(new int[]{newY, newX});
            }
            notFlipped.add(pos[0] << 8 | pos[1]);
        }
        
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (board[i][j] == 'O' && !notFlipped.contains(i << 8 | j)) board[i][j] = 'X';
            }
        }
    }
}
