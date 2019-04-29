/*
There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.

Given the ball's start position, the destination and the maze, determine whether the ball could stop at the destination.

The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the borders of the maze are all walls. The start and destination coordinates are represented by row and column indexes.

 

Example 1:

Input 1: a maze represented by a 2D array

0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0

Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (4, 4)

Output: true

Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.

Example 2:

Input 1: a maze represented by a 2D array

0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0

Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (3, 2)

Output: false

Explanation: There is no way for the ball to stop at the destination.

 

Note:

There is only one ball and one destination in the maze.
Both the ball and the destination exist on an empty space, and they will not be at the same position initially.
The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
*/



// My Solution:
class Solution {
    public int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // forward 1 setp to: up, left, down, right
    public Set<String> stopPosSet = new HashSet<>();
    
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        // dfs backtrack - should be no duplicate pos?
        if (isNotDuplicatePos(start)) {
            for (int[] direction : directions) {
                int[] stopPos = moveOnce(maze, start, direction);
                if (stopPos[0] == start[0] && stopPos[1] == start[1]) continue; // can be removed, but this can reduce stack push
                if (stopPos[0] == destination[0] && stopPos[1] == destination[1]) return true;
                if (hasPath(maze, stopPos, destination)) return true;
            }
        }
        return false;
    }
    
    public boolean isNotDuplicatePos(int[] stopPos) {
        if (stopPosSet.contains(stopPos[0]+","+stopPos[1])) return false;
        stopPosSet.add(stopPos[0]+","+stopPos[1]);
        return true;
    }
    
    public int[] moveOnce(int[][] maze, int[] start, int[] direction) {
        // 这里不能直接 int[] stopPos = start; 因为这样是赋予指针。
        int[] stopPos = new int[2];
        stopPos[0] = start[0];
        stopPos[1] = start[1];
        while (true) {
            int tempIndex0 = stopPos[0] + direction[0];
            int tempIndex1 = stopPos[1] + direction[1];
            if (tempIndex0 < 0 || tempIndex1 < 0 || tempIndex0 >= maze.length || tempIndex1 >= maze[0].length || maze[tempIndex0][tempIndex1] == 1) break;
            stopPos[0] = tempIndex0;
            stopPos[1] = tempIndex1;
        }
        
        return stopPos;
    }
}