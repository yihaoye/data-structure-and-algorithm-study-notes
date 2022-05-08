/*
Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

Example 1:

Input:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
Output: [1,2,3,6,9,8,7,4,5]
Example 2:

Input:
[
  [1, 2, 3, 4],
  [5, 6, 7, 8],
  [9,10,11,12]
]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
*/



// My Solution:
class Solution {
    public int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // directions in order
    
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<Integer>();
        int[] pos = new int[]{0, 0};
        int[] direction = new int[]{0, 1}; // current direction
        if (matrix.length == 0) return res;
        int len = matrix.length * matrix[0].length;
        for (int i=0; i<len; i++) {
            res.add(moveNext(matrix, pos, direction));
        }
        
        return res;
    }
    
    public void setDirection(int[][] matrix, int[] pos, int[] direction) {
        if (pos[0]+direction[0] >= matrix.length || pos[0]+direction[0] < 0 || pos[1]+direction[1] >= matrix[0].length || pos[1]+direction[1] < 0 || matrix[pos[0]+direction[0]][pos[1]+direction[1]] == Integer.MIN_VALUE) {
            if (direction[0] == directions[3][0] && direction[1] == directions[3][1]) { // if current direction is {-1, 0}, set it to {0, 1}
                direction[0] = directions[0][0];
                direction[1] = directions[0][1];
                return;
            }
            for (int i=0; i<3; i++) { // get next direction from directions if current direction is not yet {-1, 0}
                if (direction[0] == directions[i][0] && direction[1] == directions[i][1]) {
                    direction[0] = directions[i+1][0];
                    direction[1] = directions[i+1][1];
                    return;
                }
            }
        }
    }
    
    // set pos
    public int moveNext(int[][] matrix, int[] pos, int[] direction) {
        int res = matrix[pos[0]][pos[1]];
        matrix[pos[0]][pos[1]] = Integer.MIN_VALUE;
        setDirection(matrix, pos, direction);
        pos[0] = pos[0]+direction[0];
        pos[1] = pos[1]+direction[1];
        return res;
    }
}



// My Solution 2:
class Solution {
    int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public List<Integer> spiralOrder(int[][] matrix) {        
        int lastDirIndex = 0, curX = 0, curY = 0, m = matrix.length, n = matrix[0].length;
        int[] curDir = dirs[lastDirIndex];
        List<Integer> res = new ArrayList<>();
        for (int i=0; i<m*n; i++) {
            if (checkIfEdge(curX, curY, matrix, curDir)) {
                lastDirIndex = ++lastDirIndex % 4;
            }
            res.add(matrix[curX][curY]);
            matrix[curX][curY] -= 201;
            curDir = dirs[lastDirIndex];
            curX += curDir[0]; curY += curDir[1]; // next X; next Y
        }

        return res;
    }
    
    public boolean checkIfEdge(int x, int y, int[][] matrix, int[] curDir) {
        x += curDir[0]; y += curDir[1]; // next X; next Y
        if (x >= matrix.length || y >= matrix[0].length || x <= -1 || y <= -1 || matrix[x][y] < -100) return true;

        return false;
     }
}
