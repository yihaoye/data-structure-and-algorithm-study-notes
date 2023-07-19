/*
A binary string is monotone increasing if it consists of some number of 0's (possibly none), followed by some number of 1's (also possibly none).

You are given a binary string s. You can flip s[i] changing it from 0 to 1 or from 1 to 0.

Return the minimum number of flips to make s monotone increasing.

 

Example 1:

Input: s = "00110"
Output: 1
Explanation: We flip the last digit to get 00111.
Example 2:

Input: s = "010110"
Output: 2
Explanation: We flip to get 011111, or alternatively 000111.
Example 3:

Input: s = "00011000"
Output: 2
Explanation: We flip to get 00000000.
 

Constraints:

1 <= s.length <= 10^5
s[i] is either '0' or '1'.
*/



// My Solution:
class Solution {
    public int minFlipsMonoIncr(String s) {
        // DP
        // Time: O(N)
        int n = s.length();
        int[] dp0 = new int[n]; // dp0[i] means how many min flip with s[0..i] to make monotone and end with 0
        int[] dp1 = new int[n]; // dp1[i] means how many min flip with s[0..i] to make monotone and end with 1
        if (s.charAt(0) == '1') dp0[0] = 1;
        else dp1[0] = 1;

        for (int i=1; i<n; i++) { // dp[0] must be 0 
            if (s.charAt(i) == '1') {
                dp0[i] = dp0[i-1] + 1; // [0..i] all 0 can only remove all exist 1, no other options
                dp1[i] = Math.min(dp0[i-1], dp1[i-1]);
            } else {
                dp0[i] = dp0[i-1]; // [0..i] all 0 can only remove all exist 1, no other options
                dp1[i] = Math.min(dp0[i-1], dp1[i-1]) + 1;
            }
        }

        return Math.min(dp0[n-1], dp1[n-1]);
    }
}