/*
Given an integer n, return true if it is a power of two. Otherwise, return false.

An integer n is a power of two, if there exists an integer x such that n == 2x.

 

Example 1:

Input: n = 1
Output: true
Explanation: 20 = 1
Example 2:

Input: n = 16
Output: true
Explanation: 24 = 16
Example 3:

Input: n = 3
Output: false
Example 4:

Input: n = 4
Output: true
Example 5:

Input: n = 5
Output: false
 

Constraints:

-231 <= n <= 231 - 1
*/



// My Solution:
class Solution {
    public boolean isPowerOfTwo(int n) {
        if (n == 1 || n == 2) return true;
        if (n == 0 || n%2 != 0) return false;
        return isPowerOfTwo(n/2);
    }
}



// Other's Solution:
// Power of 2 means only one bit of n is '1', so use the trick n&(n-1)==0 to judge whether that is the case
// https://leetcode.com/problems/power-of-two/discuss/63974/Using-nand(n-1)-trick/571472
class Solution {
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) return false;
        return (n & (n-1)) == 0;
    }
}
