/*
You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

An island is considered to be the same as another if and only if one island can be translated (and not rotated or reflected) to equal the other.

Return the number of distinct islands.

 

Example 1:
https://assets.leetcode.com/uploads/2021/05/01/distinctisland1-1-grid.jpg

Input: grid = [[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]]
Output: 1

Example 2:
https://assets.leetcode.com/uploads/2021/05/01/distinctisland1-2-grid.jpg

Input: grid = [[1,1,0,1,1],[1,0,0,0,0],[0,0,0,0,1],[1,1,0,1,1]]
Output: 3
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 50
grid[i][j] is either 0 or 1.
*/



// My Solution:
class Solution {
    public int[][] DIRS = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
    
    public int numDistinctIslands(int[][] grid) {
        // BFS + 哈希
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Set<String> distincts = new HashSet<>();
        for (int r=0; r<m; r++) {
            for (int c=0; c<n; c++) {
                if (grid[r][c] == 0 || visited[r][c]) continue;
                
                // BFS
                StringBuilder distinct = new StringBuilder(); // 哈希用字符表示，包括 0,1,2,3,x 五种字符组成
                Queue<int[]> queue = new LinkedList<>();
                visited[r][c] = true;
                queue.offer(new int[]{r, c});
                while (!queue.isEmpty()) {
                    int[] pos = queue.poll();
                    for (int i=0; i<4; i++) {
                        int nxtR = pos[0] + DIRS[i][0];
                        int nxtC = pos[1] + DIRS[i][1];
                        if (isValid(nxtR, nxtC, m, n) && grid[nxtR][nxtC] == 1 && !visited[nxtR][nxtC]) {
                            visited[nxtR][nxtC] = true;
                            distinct.append(i);
                            queue.offer(new int[]{nxtR, nxtC});
                        } else {
                            distinct.append('x');
                        }
                    }
                }
                distincts.add(distinct.toString());
            }
        }
        return distincts.size();
    }
    
    public boolean isValid(int row, int col, int m, int n) {
        if (row < 0 || row >= m) return false;
        if (col < 0 || col >= n) return false;
        return true;
    }
}
