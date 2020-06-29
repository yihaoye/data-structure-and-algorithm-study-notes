/*
In a given grid, each cell can have one of three values:

the value 0 representing an empty cell;
the value 1 representing a fresh orange;
the value 2 representing a rotten orange.
Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible, return -1 instead.

 

Example 1:
![](https://assets.leetcode.com/uploads/2019/02/16/oranges.png)


Input: [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
Example 2:

Input: [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
Example 3:

Input: [[0,2]]
Output: 0
Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
 

Note:

1 <= grid.length <= 10
1 <= grid[0].length <= 10
grid[i][j] is only 0, 1, or 2.
*/



// Other's Solution:
class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> q = new LinkedList<>();
    	int target = 0, cnt = 0, res = 0;
    	for (int i=0; i<grid.length; i++) {
    		for (int j=0; j<grid[0].length; j++) {
    			if (grid[i][j] != 0) {
                    target++;
                }
                if (grid[i][j] == 2) {
    				q.offer(new int[] {i,j});
    				cnt++;
    			}
    		}
    	}
        if (target == 0) return 0;
    	int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    	while (!q.isEmpty()) {
    		int size = q.size();
    		if (cnt == target) return res; // q 仍未为空，但因为最后一轮烂橘子皆为边缘橘子无法再腐化新的橘子，所以可以忽略后续动作（因为本来就不符合执行条件）。
    		for (int i=0; i<size; i++) { // 不能去掉这一步，因为这是用作 mock 本轮存在的 N 个烂橘子同时腐化周边的并发动作（也因此 res 应在此 for 之后才自加一）。
    			int[] cur = q.poll();
    			for (int[] dir : dirs) {
    				int ni = cur[0] + dir[0];
    				int nj = cur[1] + dir[1];
    				if (ni >=0 && ni < grid.length && nj >=0 && nj < grid[0].length && grid[ni][nj] == 1) {
    					cnt++;
    					q.offer(new int[] {ni, nj});
    					grid[ni][nj] = 2;
    				}
    			}
    		}
    		res++;
    	}
    	return -1;
    }
}