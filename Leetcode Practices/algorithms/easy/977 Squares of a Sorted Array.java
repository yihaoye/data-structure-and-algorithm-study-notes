/*
Given an array of integers A sorted in non-decreasing order, return an array of the squares of each number, also in sorted non-decreasing order.

 

Example 1:

Input: [-4,-1,0,3,10]
Output: [0,1,9,16,100]
Example 2:

Input: [-7,-3,2,3,11]
Output: [4,9,9,49,121]
 

Note:

1 <= A.length <= 10000
-10000 <= A[i] <= 10000
A is sorted in non-decreasing order.
*/



// My Solution:
class Solution {
    public int[] sortedSquares(int[] A) {
        // Two Pointer solution
        int midIndex = 0;
        int len = A.length;
        for (int i=0; i<len; i++) {
            if (A[i] <= 0) midIndex = i;
            A[i] *= A[i];
        }
        
        int[] res = new int[len];
        for (int i=0, j=midIndex, k=midIndex+1; i<len; i++) {
            if (j < 0) {
                res[i] = A[k];
                k++;
            } else if (k == len) {
                res[i] = A[j];
                j--;
            } else {
                if (A[j] <= A[k]) {
                    res[i] = A[j];
                    j--;
                } else {
                    res[i] = A[k];
                    k++;
                }
            }
        }
        
        return res;
    }
}