/*
In a row of dominoes, A[i] and B[i] represent the top and bottom halves of the i-th domino.  (A domino is a tile with two numbers from 1 to 6 - one on each half of the tile.)

We may rotate the i-th domino, so that A[i] and B[i] swap values.

Return the minimum number of rotations so that all the values in A are the same, or all the values in B are the same.

If it cannot be done, return -1.

 

Example 1:
![](https://assets.leetcode.com/uploads/2019/03/08/domino.png)

Input: A = [2,1,2,4,2,2], B = [5,2,6,2,3,2]
Output: 2
Explanation: 
The first figure represents the dominoes as given by A and B: before we do any rotations.
If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as indicated by the second figure.
Example 2:

Input: A = [3,5,1,2,3], B = [3,6,3,3,4]
Output: -1
Explanation: 
In this case, it is not possible to rotate the dominoes to make one row of values equal.
 

Note:

1 <= A[i], B[i] <= 6
2 <= A.length == B.length <= 20000
*/



// My Solution:
class Solution {
    public int minDominoRotations(int[] A, int[] B) {
        int len = A.length;
        int res = len/2+1; // swap times never more than half, so set len/2+1 as default, if res finally still len/2+1 means cannot be done.
        int[][] map = new int[6][3]; // first index represent value 1 - 6, second index represent A & B, third index means duplicate
        for (int i=0; i<len; i++) { // loop through A,B index
            map[A[i]-1][0]++; // A[i]-1 because map array start from 0 end at 5
            map[B[i]-1][1]++;
            if (A[i] == B[i]) map[A[i]-1][2]++;
        }
        
        for (int i=0; i<6; i++) { // loop through number value 1 - 6 (but since map array start from 0, so loop 0 - 5)
            if (map[i][0] + map[i][1] - map[i][2] >= len) {
                int temp = len - (map[i][0] >= map[i][1] ? map[i][0] : map[i][1]);
                if (temp < res) res = temp;
            }
        }
        
        return res < (len/2+1) ? res : -1;
    }
}