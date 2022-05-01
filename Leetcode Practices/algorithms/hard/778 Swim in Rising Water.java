/**
You are given an n x n integer matrix grid where each value grid[i][j] represents the elevation at that point (i, j).

The rain starts to fall. At time t, the depth of the water everywhere is t. You can swim from a square to another 4-directionally adjacent square if and only if the elevation of both squares individually are at most t. You can swim infinite distances in zero time. Of course, you must stay within the boundaries of the grid during your swim.

Return the least time until you can reach the bottom right square (n - 1, n - 1) if you start at the top left square (0, 0).

 

Example 1:
https://assets.leetcode.com/uploads/2021/06/29/swim1-grid.jpg

Input: grid = [[0,2],[1,3]]
Output: 3
Explanation:
At time 0, you are in grid location (0, 0).
You cannot go anywhere else because 4-directionally adjacent neighbors have a higher elevation than t = 0.
You cannot reach point (1, 1) until time 3.
When the depth of water is 3, we can swim anywhere inside the grid.

Example 2:
https://assets.leetcode.com/uploads/2021/06/29/swim2-grid-1.jpg

Input: grid = [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
Output: 16
Explanation: The final route is shown.
We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
 

Constraints:

n == grid.length
n == grid[i].length
1 <= n <= 50
0 <= grid[i][j] < n2
Each value grid[i][j] is unique.

 */



// Other's Solution:
class Solution {
    private int N;

    public static final int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int swimInWater(int[][] grid) {
        // https://leetcode-cn.com/problems/swim-in-rising-water/solution/shui-wei-shang-sheng-de-yong-chi-zhong-y-862o/
        // Time: O(N^2*logN), Space: O(N^2)
        this.N = grid.length;

        int left = 0, right = N * N - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (grid[0][0] <= mid && bfs(grid, mid)) {
                right = mid; // mid 可以，尝试 mid 小一点是不是也可以呢 [left, mid]
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    // 使用广度优先遍历得到从 (x, y) 开始向四个方向的所有小于等于 t 且与 (x, y) 连通的结点
    private boolean bfs(int[][] grid, int t) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        boolean[][] visited = new boolean[N][N];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0];
            int y = point[1];
            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];
                if (isValid(newX, newY) && !visited[newX][newY] && grid[newX][newY] <= t) { // grid[newX][newY] <= t 意味着 t 时间可以覆盖或刚好等高该点
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                    if (newX == N - 1 && newY == N - 1) return true;
                }
            }
        }
        return false;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}
