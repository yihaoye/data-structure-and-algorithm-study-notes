/**
You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water and 1's represent land. Initially, all the cells of grid are water cells (i.e., all the cells are 0's).

We may perform an add land operation which turns the water at position into a land. You are given an array positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.

Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

 

Example 1:
https://assets.leetcode.com/uploads/2021/03/10/tmp-grid.jpg

Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
Output: [1,1,2,3]
Explanation:
Initially, the 2d grid is filled with water.
- Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. We have 1 island.
- Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land. We still have 1 island.
- Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land. We have 2 islands.
- Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land. We have 3 islands.
Example 2:

Input: m = 1, n = 1, positions = [[0,0]]
Output: [1]
 

Constraints:

1 <= m, n, positions.length <= 10^4
1 <= m * n <= 10^4
positions[i].length == 2
0 <= ri < m
0 <= ci < n
 

Follow up: Could you solve it in time complexity O(k log(mn)), where k == positions.length?
 */



// My Solution:
class Solution {
    private int[] parents_;
    private int[] ranks_;
    private Set<Integer> combined = new HashSet<>();
    private Set<Integer> lands = new HashSet<>();
    private int[][] DIRS = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
    
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        // 状态压缩 + 并查集 + 哈希集
        List<Integer> res = new ArrayList<>();
        ufs(m*n);
        for (int[] position : positions) {
            int newland = compress(position[0], position[1], m, n);
            if (lands.contains(newland)) { // filter out previous duplicate position
                res.add(combined.size());
                continue;
            }
            lands.add(newland);
            combined.add(newland); // 后面的 for loop 可能没有一个 adjcent 在 lands 里
            for (int[] DIR : DIRS) {
                if (isValid(position[0] + DIR[0], position[1] + DIR[1], m, n)) {
                    int adjcent = compress(position[0] + DIR[0], position[1] + DIR[1], m, n);
                    if (lands.contains(adjcent)) union(newland, adjcent);
                }
            }

            res.add(combined.size());
        }
        return res;
    }
    
    private int compress(int row, int col, int m, int n) {
        return row * n + col;
    }
    
    private boolean isValid(int row, int col, int m, int n) {
        if (row < 0 || row >= m) return false;
        if (col < 0 || col >= n) return false;
        return true;
    }
    
    /* union find set */
    private void ufs(int k) {
        parents_ = new int[k];
        ranks_ = new int[k];
        for (int i=0; i<k; i++) {
            parents_[i] = i;
            ranks_[i] = 1;
        }
    }
    
    private boolean union(int u, int v) {
        int pu = find(u);
        int pv = find(v);
        if (pu == pv) return false;
        
        if (ranks_[pu] < ranks_[pv]) {
            parents_[pu] = pv;
            // 在 union 过程里对 combined 进行更新，优化性能
            combined.add(pv);
            combined.remove(pu);
        } else {
            parents_[pv] = pu;
            if (ranks_[pu] == ranks_[pv]) ranks_[pv]++;
            // 在 union 过程里对 combined 进行更新，优化性能
            combined.add(pu);
            combined.remove(pv);
        }
        return true;
    }
    
    private int find(int u) {
        while (parents_[u] != u) {
            parents_[u] = parents_[parents_[u]];
            u = parents_[u];
        }
        return u;
    }
}
