/*
Given an integer n, return the number of trailing zeroes in n!.

Note that n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1.

 

Example 1:

Input: n = 3
Output: 0
Explanation: 3! = 6, no trailing zero.
Example 2:

Input: n = 5
Output: 1
Explanation: 5! = 120, one trailing zero.
Example 3:

Input: n = 0
Output: 0
 

Constraints:

0 <= n <= 10^4
 

Follow up: Could you write a solution that works in logarithmic time complexity?
*/



// Other's Solution:
class Solution {
    public int trailingZeroes(int n) {
        // 数学，只有 maxCount(x5)*2、x0 会产生 0，这些只需要用取余+取模可知
        // https://leetcode.com/problems/factorial-trailing-zeroes/discuss/52506/O(log_5(N))-solution-java
        int r = 0;
        while (n > 0) {
            n /= 5;
            r += n;
        }
        return r;
    }
}
