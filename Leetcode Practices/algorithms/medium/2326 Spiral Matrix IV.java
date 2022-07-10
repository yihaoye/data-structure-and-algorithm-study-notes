/**
You are given two integers m and n, which represent the dimensions of a matrix.

You are also given the head of a linked list of integers.

Generate an m x n matrix that contains the integers in the linked list presented in spiral order (clockwise), starting from the top-left of the matrix. If there are remaining empty spaces, fill them with -1.

Return the generated matrix.

 

Example 1:
https://assets.leetcode.com/uploads/2022/05/09/ex1new.jpg

Input: m = 3, n = 5, head = [3,0,2,6,8,1,7,9,4,2,5,5,0]
Output: [[3,0,2,6,8],[5,0,-1,-1,1],[5,2,4,9,7]]
Explanation: The diagram above shows how the values are printed in the matrix.
Note that the remaining spaces in the matrix are filled with -1.

Example 2:
https://assets.leetcode.com/uploads/2022/05/11/ex2.jpg

Input: m = 1, n = 4, head = [0,1,2]
Output: [[0,1,2,-1]]
Explanation: The diagram above shows how the values are printed from left to right in the matrix.
The last space in the matrix is set to -1.
 

Constraints:

1 <= m, n <= 10^5
1 <= m * n <= 10^5
The number of nodes in the list is in the range [1, m * n].
0 <= Node.val <= 1000
 */



// My Solution:
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    int[][] DIRS = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};
        
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        // 模拟
        int[][] res = new int[m][n];
        for (int[] row : res) Arrays.fill(row, -1);
        int y = 0, x = 0, dir = 0;
        while (head != null) {
            res[y][x] = head.val;
            if (!valid(res, x + DIRS[dir][1], y + DIRS[dir][0])) dir = (dir + 1) % 4; // 如果现在的方向不 valid，下一次一定可以，因为 list 的节点数 <= m*n
            y += DIRS[dir][0];
            x += DIRS[dir][1];
            head = head.next;
        }
        
        return res;
    }
    
    private boolean valid(int[][] res, int nextX, int nextY) {
        if (0 <= nextX && nextX < res[0].length && 0 <= nextY && nextY < res.length && res[nextY][nextX] == -1) return true;
        return false;
    }
}
