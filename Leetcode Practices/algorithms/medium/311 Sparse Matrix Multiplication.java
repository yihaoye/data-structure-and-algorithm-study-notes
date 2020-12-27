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



// My Solution (pure for loop):
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
