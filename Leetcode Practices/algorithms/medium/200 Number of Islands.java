/*
Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:

Input:
11110
11010
11000
00000

Output: 1
Example 2:

Input:
11000
11000
00100
00011

Output: 3
*/



// Other's Solution:
class Solution {
    public int numIslands(char[][] grid) {
        // 并查集 - https://leetcode.cn/problems/number-of-islands/solution/dao-yu-shu-liang-by-leetcode/
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;
        UnionFind uf = new UnionFind(grid);
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    grid[r][c] = '0';
                    if (r - 1 >= 0 && grid[r-1][c] == '1') {
                        uf.union(r * nc + c, (r-1) * nc + c);
                    }
                    if (r + 1 < nr && grid[r+1][c] == '1') {
                        uf.union(r * nc + c, (r+1) * nc + c);
                    }
                    if (c - 1 >= 0 && grid[r][c-1] == '1') {
                        uf.union(r * nc + c, r * nc + c - 1);
                    }
                    if (c + 1 < nc && grid[r][c+1] == '1') {
                        uf.union(r * nc + c, r * nc + c + 1);
                    }
                }
            }
        }

        return uf.getCount();
    }

    class UnionFind {
        int count;
        int[] parent;
        int[] rank;

        public UnionFind(char[][] grid) {
            count = 0;
            int m = grid.length;
            int n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (grid[i][j] == '1') {
                        parent[i * n + j] = i * n + j;
                        ++count;
                    }
                    rank[i * n + j] = 0;
                }
            }
        }

        public int find(int i) {
            if (parent[i] != i) parent[i] = find(parent[i]);
            return parent[i];
        }

        public void union(int x, int y) {
            int rootx = find(x);
            int rooty = find(y);
            if (rootx != rooty) {
                if (rank[rootx] > rank[rooty]) {
                    parent[rooty] = rootx;
                } else if (rank[rootx] < rank[rooty]) {
                    parent[rootx] = rooty;
                } else {
                    parent[rooty] = rootx;
                    rank[rootx] += 1;
                }
                --count;
            }
        }

        public int getCount() {
            return count;
        }
    }
}



// My Solution:
class Solution {
    public int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // forward 1 setp to: up, left, down, right
    
    public int numIslands(char[][] grid) {
        int num = 0;
        for (int i=0; i < grid.length; i++) {
            for (int j=0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    backtrack(grid, new int[]{i, j});
                    num++;
                }
            }
        }
        return num;
    }

    public void backtrack(char[][] grid, int[] start) {
        for (int[] direction : directions) {
            int[] stopPos = moveOnce(grid, start, direction);
            if (stopPos[0] < 0 || stopPos[1] < 0 || stopPos[0] >= grid.length || stopPos[1] >= grid[0].length) continue;
            if (grid[stopPos[0]][stopPos[1]] == '1') {
                grid[stopPos[0]][stopPos[1]] = '2';
                backtrack(grid, stopPos);
            }
        }
        return;
    }
    
    public int[] moveOnce(char[][] grid, int[] start, int[] direction) {
        int[] stopPos = new int[2];
        stopPos[0] = start[0] + direction[0];
        stopPos[1] = start[1] + direction[1];
        return stopPos;
    }
}