/*
An array is monotonic if it is either monotone increasing or monotone decreasing.

An array A is monotone increasing if for all i <= j, A[i] <= A[j].  An array A is monotone decreasing if for all i <= j, A[i] >= A[j].

Return true if and only if the given array A is monotonic.

 

Example 1:

Input: [1,2,2,3]
Output: true
Example 2:

Input: [6,5,4,4]
Output: true
Example 3:

Input: [1,3,2]
Output: false
Example 4:

Input: [1,2,4,5]
Output: true
Example 5:

Input: [1,1,1]
Output: true
 

Note:

1 <= A.length <= 50000
-100000 <= A[i] <= 100000
*/



// My Solution:
class Solution {
    public boolean isMonotonic(int[] A) {
        int monotonic = 0;
        for (int i=1; i<A.length; i++) {
            if (monotonic == 0 && A[i-1] == A[i]) continue;
            if (monotonic == 0) {
                if (A[i-1] < A[i]) monotonic = 1;
                if (A[i-1] > A[i]) monotonic = -1;
                continue;
            }
            if (monotonic == 1 && A[i-1] > A[i]) return false;
            if (monotonic == -1 && A[i-1] < A[i]) return false;
        }
        
        return true;
    }
}



// Other's Solution:
class Solution {
    public boolean isMonotonic(int[] A) {
        boolean inc = true, dec = true;
        for (int i = 1; i < A.length; ++i) {
            inc &= A[i - 1] <= A[i];
            dec &= A[i - 1] >= A[i];
        }
        return inc || dec;
    }
}
