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