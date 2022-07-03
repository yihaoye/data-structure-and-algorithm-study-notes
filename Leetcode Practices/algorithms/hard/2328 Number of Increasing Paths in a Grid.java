/**
You are given an m x n integer matrix grid, where you can move from a cell to any adjacent cell in all 4 directions.

Return the number of strictly increasing paths in the grid such that you can start from any cell and end at any cell. Since the answer may be very large, return it modulo 109 + 7.

Two paths are considered different if they do not have exactly the same sequence of visited cells.

 

Example 1:
https://assets.leetcode.com/uploads/2022/05/10/griddrawio-4.png

Input: grid = [[1,1],[3,4]]
Output: 8
Explanation: The strictly increasing paths are:
- Paths with length 1: [1], [1], [3], [4].
- Paths with length 2: [1 -> 3], [1 -> 4], [3 -> 4].
- Paths with length 3: [1 -> 3 -> 4].
The total number of paths is 4 + 3 + 1 = 8.

Example 2:

Input: grid = [[1],[2]]
Output: 3
Explanation: The strictly increasing paths are:
- Paths with length 1: [1], [2].
- Paths with length 2: [1 -> 2].
The total number of paths is 2 + 1 = 3.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 1000
1 <= m * n <= 10^5
1 <= grid[i][j] <= 10^5
 */



// Other's Solution:
class Solution {
    static long MOD = 1000000007L;
    static int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private long[][] memo; // memo[x][y] means number of increasing paths which start from grid[x][y]

    public int countPaths(int[][] grid) {
        // DFS+记忆化搜索 - https://leetcode.com/problems/number-of-increasing-paths-in-a-grid/discuss/2229819/C++Python-Top-Down-DP/1468257
        // 如果不想递归的话可以使用：拓扑排序+DP - https://leetcode.com/problems/number-of-increasing-paths-in-a-grid/discuss/2230147/Python-Topo-DP-Solution-and-DFS-Solution （有空可以试试用 Java 将其实现）
        long result = 0L;
        int m = grid.length, n = grid[0].length;
        memo = new long[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result = (result + dp(grid, i, j)) % MOD;
            }
        }         
        return (int) (result);
    }

    private long dp(int[][] grid, int x, int y) {
        long res = 1L;
        if (memo[x][y] != 0) return memo[x][y]; // 记忆化搜索

        for (int[] dir : DIRS) {
            int neiX = x + dir[0];
            int neiY = y + dir[1];
            if (neiX >= 0 && neiY >= 0 && neiX < grid.length && neiY < grid[0].length && grid[x][y] < grid[neiX][neiY]) { // 也可以 grid[x][y] > grid[neiX][neiY]，但是这样 memo[x][y] 的定义就不一样了
                res = (res + dp(grid, neiX, neiY)) % MOD; // 记忆化搜索，会一直递归到一个数值较大的 grid 元素而周围没有其他元素比它大，所以它只能是 1（前面 res 的初值 1L）并返回。不用担心是否会漏了计算，因为会计算所有比当前元素大的邻居元素的 res
            }
        }
        return memo[x][y] = res;
    }
}
