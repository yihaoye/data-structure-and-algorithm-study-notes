/*
Given two sparse matrices A and B, return the result of AB.

You may assume that A's column number is equal to B's row number.

Example:

Input:

A = [
  [ 1, 0, 0],
  [-1, 0, 3]
]

B = [
  [ 7, 0, 0 ],
  [ 0, 0, 0 ],
  [ 0, 0, 1 ]
]

Output:

     |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
                  | 0 0 1 |
 

Constraints:

1 <= A.length, B.length <= 100
1 <= A[i].length, B[i].length <= 100
-100 <= A[i][j], B[i][j] <= 100
*/



// My Solution (单纯的 for loop 计算模拟矩阵相乘逻辑):
class Solution {
    public int[][] multiply(int[][] A, int[][] B) {
        int aH = A.length, aW = A[0].length, bW = B[0].length;
        int[][] res = new int[aH][bW];
        
        for (int ai=0; ai<aH; ai++) {
            for (int aj=0; aj<aW; aj++) {
                for (int bj=0; bj<bW; bj++) {
                    res[ai][bj] += A[ai][aj]*B[aj][bj];
                }
            }
        }
        
        return res;
    }
}



// Other's Solution (基本与上面解法一样，只是优化了不必要的与 0 元素相乘):
class Solution {
    public int[][] multiply(int[][] A, int[][] B) {
        int aH = A.length, aW = A[0].length, bW = B[0].length;
        int[][] res = new int[aH][bW];
        
        for (int ai=0; ai<aH; ai++) {
            for (int aj=0; aj<aW; aj++) {
                if (A[ai][aj] != 0) { // 优化
                    for (int bj=0; bj<bW; bj++) {
                        if (B[aj][bj] != 0) res[ai][bj] += A[ai][aj]*B[aj][bj]; // 优化
                    }
                }
            }
        }
        
        return res;
    }
}

