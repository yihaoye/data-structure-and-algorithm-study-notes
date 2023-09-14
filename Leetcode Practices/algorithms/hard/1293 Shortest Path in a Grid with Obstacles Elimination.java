/*
You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle). You can move up, down, left, or right from and to an empty cell in one step.

Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m - 1, n - 1) given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.

 

Example 1:


Input: grid = [[0,0,0],[1,1,0],[0,0,0],[0,1,1],[0,0,0]], k = 1
Output: 6
Explanation: 
The shortest path without eliminating any obstacle is 10.
The shortest path with one obstacle elimination at position (3,2) is 6. Such path is (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
Example 2:


Input: grid = [[0,1,1],[1,1,1],[1,0,0]], k = 1
Output: -1
Explanation: We need to eliminate at least two obstacles to find such a walk.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 40
1 <= k <= m * n
grid[i][j] is either 0 or 1.
grid[0][0] == grid[m - 1][n - 1] == 0
*/



// My Solution:
class Solution {
    public int[][] DIRS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int shortestPath(int[][] grid, int k) {
        // BFS + 记忆化搜索 + 剪枝
        int m = grid.length, n = grid[0].length;
        int[][][] dp = new int[m][n][k+1]; // shortest path with take k obst to arrive [r, c]
        int res = Integer.MAX_VALUE;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 0, 0}); // [row, col, obst, path]
        while (!queue.isEmpty()) {
            int[] last = queue.poll();
            if (last[0] == m-1 && last[1] == n-1) res = Math.min(res, last[3]);
            List<int[]> nexts = nexts(last, grid);
            for (int[] next : nexts) {
                int r = next[0]; int c = next[1]; int obst = next[2]; int path = next[3];
                if (r == 0 && c == 0 || obst > k) continue;
                if (dp[r][c][obst] == 0) dp[r][c][obst] = Integer.MAX_VALUE;
                if (dp[r][c][obst] > path) {
                    dp[r][c][obst] = path;
                    queue.offer(next);
                }
            }
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }

    public List<int[]> nexts(int[] last, int[][] grid) {
        List<int[]> res = new ArrayList<>();
        for (int[] DIR : DIRS) {
            int nxtR = last[0] + DIR[0]; int nxtC = last[1] + DIR[1];
            if (nxtR < 0 || nxtC < 0 || nxtR >= grid.length || nxtC >= grid[0].length) continue;
            int[] next = new int[]{nxtR, nxtC, last[2] + grid[nxtR][nxtC], last[3] + 1};
            res.add(next);
        }
        return res;
    }
}
