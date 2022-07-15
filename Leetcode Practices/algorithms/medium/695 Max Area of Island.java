/**
You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

The area of an island is the number of cells with a value 1 in the island.

Return the maximum area of an island in grid. If there is no island, return 0.

 

Example 1:
https://assets.leetcode.com/uploads/2021/05/01/maxarea1-grid.jpg

Input: grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
Output: 6
Explanation: The answer is not 11, because the island must be connected 4-directionally.

Example 2:

Input: grid = [[0,0,0,0,0,0,0,0]]
Output: 0
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 50
grid[i][j] is either 0 or 1.
 */



// My Solution:
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        // BFS+记忆化搜索
        // Time: O(N*M), Space: O(N*M)
        int n = grid.length, m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        int res = 0;
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                if (visited[i][j]) continue;
                
                int curIslandArea = 0;
                Queue<int[]> queue = new LinkedList<>();
                visited[i][j] = true;
                queue.offer(new int[]{i, j});
                while (!queue.isEmpty()) {
                    int[] curPos = queue.poll();
                    if (grid[curPos[0]][curPos[1]] != 1) continue; // 若 curPos 不为 1 则不进行下面的 BFS 扩散
                    
                    curIslandArea += 1;
                    // 若 curPos 为 1 则四方扩散寻找，因为可能有 # 这类形状，所以每个方向都不能省略
                    if (0 < curPos[0] && !visited[curPos[0]-1][curPos[1]]) {
                        visited[curPos[0]-1][curPos[1]] = true;
                        queue.offer(new int[]{curPos[0]-1, curPos[1]});
                    }
                    if (0 < curPos[1] && !visited[curPos[0]][curPos[1]-1]) {
                        visited[curPos[0]][curPos[1]-1] = true;
                        queue.offer(new int[]{curPos[0], curPos[1]-1});
                    }
                    if (curPos[0] < n-1 && !visited[curPos[0]+1][curPos[1]]) {
                        visited[curPos[0]+1][curPos[1]] = true;
                        queue.offer(new int[]{curPos[0]+1, curPos[1]});
                    }
                    if (curPos[1] < m-1 && !visited[curPos[0]][curPos[1]+1]) {
                        visited[curPos[0]][curPos[1]+1] = true;
                        queue.offer(new int[]{curPos[0], curPos[1]+1});
                    }
                }
                
                res = Math.max(res, curIslandArea);
            }
        }
        return res;
    }
}
