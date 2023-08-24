/*
You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

An island is considered to be the same as another if they have the same shape, or have the same shape after rotation (90, 180, or 270 degrees only) or reflection (left/right direction or up/down direction).

Return the number of distinct islands.

 

Example 1:


Input: grid = [[1,1,0,0,0],[1,0,0,0,0],[0,0,0,0,1],[0,0,0,1,1]]
Output: 1
Explanation: The two islands are considered the same because if we make a 180 degrees clockwise rotation on the first island, then two islands will have the same shapes.
Example 2:


Input: grid = [[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]]
Output: 1
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 50
grid[i][j] is either 0 or 1.
*/



// Other's Solution:
class Solution {
    private final int[][] dirs = new int[][]{{0, 1},{0, -1},{1, 0},{-1, 0}};
    
    public int numDistinctIslands2(int[][] grid) {
        // 几何 - https://leetcode.com/problems/number-of-distinct-islands-ii/solutions/1728104/most-straightforward-solution-no-rotation-or-reflection-calculation-java/
        Set<Map<Integer, Integer>> allDistinctIslands = new HashSet<>();
        int rows = grid.length; int cols = grid[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != 1) continue;
                List<int[]> island = dfs(grid, r, c); // get island indexs and remove them from grid to prevent duplicate visit
                Map<Integer, Integer> allDistanceCountMap = new HashMap<>();
                for (int i = 0; i < island.size(); i++) {
                    for (int j = i + 1; j < island.size(); j++) {
                        int dist = (int) Math.pow(island.get(i)[0] - island.get(j)[0], 2) + (int) Math.pow(island.get(i)[1] - island.get(j)[1], 2);
                        allDistanceCountMap.put(dist, allDistanceCountMap.getOrDefault(dist, 0) + 1);
                    }
                }
                allDistinctIslands.add(allDistanceCountMap);
            }
        }
        return allDistinctIslands.size();
    }
    
    private List<int[]> dfs(int[][] grid, int r, int c) {
        List<int[]> res = new ArrayList<>();
        res.add(new int[]{r, c});
        grid[r][c] = 0;
        for (int[] dir : dirs) {
            int rNext = r + dir[0]; int cNext = c + dir[1];
            if (rNext < 0 || rNext >= grid.length || cNext < 0 || cNext >= grid[0].length || grid[rNext][cNext] == 0) {
                continue;
            }
            res.addAll(dfs(grid, rNext, cNext));
        }
        return res;
    }
}
