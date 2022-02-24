/*
You are given an n x n grid where you have placed some 1 x 1 x 1 cubes. Each value v = grid[i][j] represents a tower of v cubes placed on top of cell (i, j).

After placing these cubes, you have decided to glue any directly adjacent cubes to each other, forming several irregular 3D shapes.

Return the total surface area of the resulting shapes.

Note: The bottom face of each shape counts toward its surface area.

 

Example 1:
https://assets.leetcode.com/uploads/2021/01/08/tmp-grid2.jpg

Input: grid = [[1,2],[3,4]]
Output: 34


Example 2:
https://assets.leetcode.com/uploads/2021/01/08/tmp-grid4.jpg

Input: grid = [[1,1,1],[1,0,1],[1,1,1]]
Output: 32


Example 3:
https://assets.leetcode.com/uploads/2021/01/08/tmp-grid5.jpg

Input: grid = [[2,2,2],[2,1,2],[2,2,2]]
Output: 46
 

Constraints:

n == grid.length == grid[i].length
1 <= n <= 50
0 <= grid[i][j] <= 50

*/





// Other's Solution:
class Solution {
    public int surfaceArea(int[][] grid) {
        /*
            数学 - https://leetcode-cn.com/problems/surface-area-of-3d-shapes/solution/shi-li-you-tu-you-zhen-xiang-jiang-jie-yi-kan-jiu-/
            时间复杂度 O(n^2)，空间复杂度 O(1)
        */
        int n = grid.length, res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int level = grid[i][j];
                if (level > 0) {
                    res += level*4 + 2; // 一个柱体中：2个底面 + 所有的正方体都贡献了4个侧表面积 
                    res -= i > 0 ? Math.min(level, grid[i-1][j])*2 : 0; // 减掉 i 与 i-1 相贴的两份表面积
                    res -= j > 0 ? Math.min(level, grid[i][j-1])*2 : 0; // 减掉 j 与 j-1 相贴的两份表面积
                }  
            }
        }
        return res;
    }
}