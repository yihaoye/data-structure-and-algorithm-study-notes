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



// My Solution (并查集):
class Solution {    
    public int numIslands(char[][] grid) {
        int rs = grid.length;
        int cs = grid[0].length;
        UnionFindSet uf = new UnionFindSet(rs * cs);
        for (int r = 0; r < rs; r++) {
            for (int c = 0; c < cs; c++) {
                if (grid[r][c] == '0') continue;

                // grid[r][c] == '1'
                if (r - 1 >= 0 && grid[r-1][c] == '1') uf.union(r * cs + c, (r-1) * cs + c);
                if (r + 1 < rs && grid[r+1][c] == '1') uf.union(r * cs + c, (r+1) * cs + c);
                if (c - 1 >= 0 && grid[r][c-1] == '1') uf.union(r * cs + c, r * cs + c - 1);
                if (c + 1 < cs && grid[r][c+1] == '1') uf.union(r * cs + c, r * cs + c + 1);
            }
        }

        Set<Integer> res = new HashSet<>();
        for (int r = 0; r < rs; r++) {
            for (int c = 0; c < cs; c++) {
                if (grid[r][c] == '0') continue;
                res.add(uf.find(r * cs + c));
            }
        }

        return res.size();
    }

    // 并查集部分
    class UnionFindSet {
        private int[] parents_;
        private int[] ranks_;
    
        public UnionFindSet(int n) {
            parents_ = new int[n + 1];
            ranks_ = new int[n + 1];
            for (int i = 0; i < parents_.length; ++i) {
                parents_[i] = i;
                ranks_[i] = 1;
            }
        }
    
        public boolean union(int u, int v) {
            int pu = find(u);
            int pv = find(v);
            if (pu == pv) return false;
        
            if (ranks_[pv] > ranks_[pu])
                parents_[pu] = pv;
            else if (ranks_[pu] > ranks_[pv])
                parents_[pv] = pu;
            else {
                parents_[pv] = pu;
                ranks_[pu] += 1;
            }
        
            return true;
        }

        public int find(int u) { // 递归写法：find 调用会将该节点所属树/路径的全部节点都 path compression
            if (u != parents_[u]) parents_[u] = find(parents_[u]);
            return parents_[u];
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