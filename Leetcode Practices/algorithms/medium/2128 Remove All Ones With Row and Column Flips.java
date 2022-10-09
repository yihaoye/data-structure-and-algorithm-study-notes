/**
You are given an m x n binary matrix grid.

In one operation, you can choose any row or column and flip each value in that row or column (i.e., changing all 0's to 1's, and all 1's to 0's).

Return true if it is possible to remove all 1's from grid using any number of operations or false otherwise.

 

Example 1:
https://assets.leetcode.com/uploads/2022/01/03/image-20220103191300-1.png

Input: grid = [[0,1,0],[1,0,1],[0,1,0]]
Output: true
Explanation: One possible way to remove all 1's from grid is to:
- Flip the middle row
- Flip the middle column

Example 2:
https://assets.leetcode.com/uploads/2022/01/03/image-20220103181204-7.png

Input: grid = [[1,1,0],[0,0,0],[0,0,0]]
Output: false
Explanation: It is impossible to remove all 1's from grid.

Example 3:
https://assets.leetcode.com/uploads/2022/01/03/image-20220103181224-8.png

Input: grid = [[0]]
Output: true
Explanation: There are no 1's in grid.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 300
grid[i][j] is either 0 or 1.
 */



// My Solution:
class Solution {
    public boolean removeOnes(int[][] grid) {
        // BFS + memory (visited)
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> rQueue = new LinkedList<>(), cQueue = new LinkedList<>(); // flip by row or col
        for (int r=0; r<m; r++) {
            for (int c=0; c<n; c++) {
                if (grid[r][c] == 0) continue;
                if (grid[r][c] == 1 && visited[r][c] == true) return false;
                
                if (grid[r][c] == 1) rQueue.offer(new int[]{r, c});
                while (!rQueue.isEmpty() || !cQueue.isEmpty()) {
                    while (!rQueue.isEmpty()) {
                        int[] oneToFlip = rQueue.poll();
                        if (grid[oneToFlip[0]][oneToFlip[1]] == 0) continue;
                        if (visited[oneToFlip[0]][oneToFlip[1]] == true) return false;
                        visited[oneToFlip[0]][oneToFlip[1]] = true;
                        flipRow(oneToFlip, grid, cQueue);
                    }
                    while (!cQueue.isEmpty()) {
                        int[] oneToFlip = cQueue.poll();
                        if (grid[oneToFlip[0]][oneToFlip[1]] == 0) continue;
                        if (visited[oneToFlip[0]][oneToFlip[1]] == true) return false;
                        visited[oneToFlip[0]][oneToFlip[1]] = true;
                        flipCol(oneToFlip, grid, rQueue);
                    }
                }
            }
        }
        return true;
    }
    
    private void flipRow(int[] oneToFlip, int[][] grid, Queue<int[]> cQueue) {
        int r = oneToFlip[0];
        for (int c=0; c<grid[0].length; c++) {
            if (grid[r][c] == 0) cQueue.offer(new int[]{r, c});
            grid[r][c] = grid[r][c] == 1 ? 0 : 1;
        }
    }
    
    private void flipCol(int[] oneToFlip, int[][] grid, Queue<int[]> rQueue) {
        int c = oneToFlip[1];
        for (int r=0; r<grid.length; r++) {
            if (grid[r][c] == 0) rQueue.offer(new int[]{r, c});
            grid[r][c] = grid[r][c] == 1 ? 0 : 1;
        }
    }
}
