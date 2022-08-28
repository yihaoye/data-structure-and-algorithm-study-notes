/**
A matrix diagonal is a diagonal line of cells starting from some cell in either the topmost row or leftmost column and going in the bottom-right direction until reaching the matrix's end. For example, the matrix diagonal starting from mat[2][0], where mat is a 6 x 3 matrix, includes cells mat[2][0], mat[3][1], and mat[4][2].

Given an m x n matrix mat of integers, sort each matrix diagonal in ascending order and return the resulting matrix.

 

Example 1:
https://assets.leetcode.com/uploads/2020/01/21/1482_example_1_2.png

Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]
Example 2:

Input: mat = [[11,25,66,1,69,7],[23,55,17,45,15,52],[75,31,36,44,58,8],[22,27,33,25,68,4],[84,28,14,11,5,50]]
Output: [[5,17,4,1,52,7],[11,11,25,45,8,69],[14,23,25,44,58,15],[22,27,31,36,50,66],[84,28,75,33,55,68]]
 

Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 100
1 <= mat[i][j] <= 100
 */



// My Solution:
class Solution {
    public int[][] diagonalSort(int[][] mat) {
        // HashMap + PriorityQueue + 状态压缩
        int m = mat.length, n = mat[0].length;
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>(); // key 为 mat[i][j] 的最左上坐标的状态压缩
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                int key = findKey(i, j);
                PriorityQueue<Integer> pq = map.getOrDefault(key, new PriorityQueue<>());
                pq.offer(mat[i][j]);
                map.put(key, pq);
            }
        }
        
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                int key = findKey(i, j);
                PriorityQueue<Integer> pq = map.get(key);
                int update = pq.poll();
                mat[i][j] = update;
            }
        }
        
        return mat;
    }
    
    public int findKey(int i, int j) { // 其实也是 find mat[i][j] 的最左上坐标的状态压缩
        int maxSub = Math.max(i, j);
        // 1 <= m, n <= 100 -- 2^7 == 128
        return (i-maxSub) << 7 | (j-maxSub);
    }
}
