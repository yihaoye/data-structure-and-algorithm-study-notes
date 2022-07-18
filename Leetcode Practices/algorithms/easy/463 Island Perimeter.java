/**
You are given row x col grid representing a map where grid[i][j] = 1 represents land and grid[i][j] = 0 represents water.

Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water, and there is exactly one island (i.e., one or more connected land cells).

The island doesn't have "lakes", meaning the water inside isn't connected to the water around the island. One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. Determine the perimeter of the island.

 

Example 1:
https://assets.leetcode.com/uploads/2018/10/12/island.png

Input: grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
Output: 16
Explanation: The perimeter is the 16 yellow stripes in the image above.

Example 2:

Input: grid = [[1]]
Output: 4
Example 3:

Input: grid = [[1,0]]
Output: 4
 

Constraints:

row == grid.length
col == grid[i].length
1 <= row, col <= 100
grid[i][j] is 0 or 1.
There is exactly one island in grid.
 */



// My Solution:
class Solution {
    public int islandPerimeter(int[][] grid) {
        // 几何，一次遍历，岛屿的周长就是岛屿方格相邻非岛屿方格（0 或越界）相邻的数量总和
        // Time: O(N*M), Space: O(1)
        int res = 0;
        for (int y=0; y<grid.length; y++)
            for (int x=0; x<grid[0].length; x++)
                if (grid[y][x] != 0) res += notIslandNeiCnt(grid, x, y);
        return res;
    }
    
    private int notIslandNeiCnt(int[][] grid, int x, int y) { // not island neighbours count
        int res = 0;
        if (x == 0 || grid[y][x-1] == 0) res++;
        if (x == grid[0].length-1 || grid[y][x+1] == 0) res++;
        if (y == 0 || grid[y-1][x] == 0) res++;
        if (y == grid.length-1 || grid[y+1][x] == 0) res++;
        return res;
    }
}
