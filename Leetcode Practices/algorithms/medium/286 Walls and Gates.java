/*
You are given an m x n grid rooms initialized with these three possible values.

-1 A wall or an obstacle.
0 A gate.
INF Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.

 

Example 1:
https://assets.leetcode.com/uploads/2021/01/03/grid.jpg

Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
Example 2:

Input: rooms = [[-1]]
Output: [[-1]]
 

Constraints:

m == rooms.length
n == rooms[i].length
1 <= m, n <= 250
rooms[i][j] is -1, 0, or 231 - 1.
*/





class Solution {
    public void wallsAndGates(int[][] rooms) {
        /*
            BFS - 先遍历二维数组，把所有门的坐标存放在队列中，然后每次从队列中取出坐标，计算其4个方位的valid坐标（索引有效且其数组值为INF），然后以取出坐标的数组值+1更新新得坐标的数组值，然后将其存入队列中，当队列为空时则结束。更新值必为最近的门而不是另一个门，因为BFS遍历时有类似并行的属性。
            时间复杂度 O(N^2)，空间复杂度 O(N^2)
        */
        Queue<int[]> q = new LinkedList<>();
        for (int i=0; i<rooms.length; i++) {
            for (int j=0; j<rooms[0].length; j++) {
                if (rooms[i][j] == 0) q.add(new int[]{i, j});
            }
        }
        while (!q.isEmpty()) {
            int[] index = q.poll();
            if (isValid(new int[]{index[0]+1, index[1]}, rooms)) {
                rooms[index[0]+1][index[1]] = rooms[index[0]][index[1]] + 1;
                q.add(new int[]{index[0]+1, index[1]});
            }
            if (isValid(new int[]{index[0]-1, index[1]}, rooms)) {
                rooms[index[0]-1][index[1]] = rooms[index[0]][index[1]] + 1;
                q.add(new int[]{index[0]-1, index[1]});
            }
            if (isValid(new int[]{index[0], index[1]+1}, rooms)) {
                rooms[index[0]][index[1]+1] = rooms[index[0]][index[1]] + 1;
                q.add(new int[]{index[0], index[1]+1});
            }
            if (isValid(new int[]{index[0], index[1]-1}, rooms)) {
                rooms[index[0]][index[1]-1] = rooms[index[0]][index[1]] + 1;
                q.add(new int[]{index[0], index[1]-1});
            }
        }   
        return;
    }
                                            
    private boolean isValid(int[] index, int[][] rooms) {
        if (index[0] < 0 || index[0] >= rooms.length || index[1] < 0 || index[1] >= rooms[0].length) return false;
        if (rooms[index[0]][index[1]] != 2147483647) return false;
        return true;
    }
}
