/*
You are given an n x n binary matrix grid where 1 represents land and 0 represents water.

An island is a 4-directionally connected group of 1's not connected to any other 1's. There are exactly two islands in grid.

You may change 0's to 1's to connect the two islands to form one island.

Return the smallest number of 0's you must flip to connect the two islands.

 

Example 1:

Input: grid = [[0,1],[1,0]]
Output: 1
Example 2:

Input: grid = [[0,1,0],[0,0,0],[0,0,1]]
Output: 2
Example 3:

Input: grid = [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
Output: 1
 

Constraints:

n == grid.length == grid[i].length
2 <= n <= 100
grid[i][j] is either 0 or 1.
There are exactly two islands in grid.
*/



// My Solution:
class Solution {
    public int[][] dirs = new int[][]{{1,0}, {-1,0}, {0,1}, {0, -1}};
    public int n, m;

    public int shortestBridge(int[][] grid) {
        // BFS - 找到第一个为 1 的点然后 BFS 遍历岛找到其边缘的 0，同时把之前的 1 改为 -1，然后从这些 0 开始出发 BFS 直到碰到第一个 1（也可以用设置 -1 来去重），迭代的次数即为结果
        n = grid.length; m = grid[0].length;
        int[] firstLand = findLand(grid);
        Queue<int[]> edges = findEdges(grid, firstLand[0], firstLand[1]);

        int res = 0;
        while (!edges.isEmpty()) {
            res++;
            int size = edges.size();
            while (size-- > 0) {
                int[] prev = edges.poll();
                for (int[] dir : dirs) {
                    int nxtR = prev[0] + dir[0];
                    int nxtC = prev[1] + dir[1];
                    if (nxtR < 0 || nxtR >= n || nxtC < 0 || nxtC >= m || grid[nxtR][nxtC] == -1) continue;
                    if (grid[nxtR][nxtC] == 0) edges.offer(new int[]{nxtR, nxtC});
                    if (grid[nxtR][nxtC] == 1) return res;
                    grid[nxtR][nxtC] = -1;
                }
            }
        }
        return -1;
    }

    public int[] findLand(int[][] grid) {
        for (int r=0; r<n; r++)
            for (int c=0; c<m; c++)
                if (grid[r][c] == 1) return new int[]{r, c};
                
        return new int[]{-1, -1};
    }

    public Queue<int[]> findEdges(int[][] grid, int r, int c) {
        Queue<int[]> res = new LinkedList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        grid[r][c] = -1;
        while (!queue.isEmpty()) {
            int[] prev = queue.poll();
            for (int[] dir : dirs) {
                int nxtR = prev[0] + dir[0];
                int nxtC = prev[1] + dir[1];
                if (nxtR < 0 || nxtR >= n || nxtC < 0 || nxtC >= m || grid[nxtR][nxtC] == -1) continue;
                if (grid[nxtR][nxtC] == 0) res.offer(new int[]{nxtR, nxtC});
                if (grid[nxtR][nxtC] == 1) queue.offer(new int[]{nxtR, nxtC});
                grid[nxtR][nxtC] = -1;
            }
        }
        return res;
    }
}
