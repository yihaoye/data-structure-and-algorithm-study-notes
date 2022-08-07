/**
Given an integer n, your task is to count how many strings of length n can be formed under the following rules:

Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
Each vowel 'a' may only be followed by an 'e'.
Each vowel 'e' may only be followed by an 'a' or an 'i'.
Each vowel 'i' may not be followed by another 'i'.
Each vowel 'o' may only be followed by an 'i' or a 'u'.
Each vowel 'u' may only be followed by an 'a'.
Since the answer may be too large, return it modulo 10^9 + 7.

 

Example 1:

Input: n = 1
Output: 5
Explanation: All possible strings are: "a", "e", "i" , "o" and "u".
Example 2:

Input: n = 2
Output: 10
Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".
Example 3: 

Input: n = 5
Output: 68
 

Constraints:

1 <= n <= 2 * 10^4
 */



// Other's Solution:
class Solution {
    int a = 0, e = 1, i = 2, o = 3, u = 4, MOD = (int) (1e9 + 7);
    
    public int countVowelPermutation(int n) {
        // 状态机/DP - https://leetcode.com/problems/count-vowels-permutation/discuss/1315113/C%2B%2BJavaPython-Top-down-DP-Bottom-up-DP-Picture-explain-Clean-and-Concise
        // Time: O(N), Space: O(1)
        long[] dp = new long[5], prevDP = new long[5];
        Arrays.fill(prevDP, 1L);
        while (n-- > 1) {
            dp[a] = (prevDP[e] + prevDP[i] + prevDP[u]) % MOD;
            dp[e] = (prevDP[a] + prevDP[i]) % MOD;
            dp[i] = (prevDP[e] + prevDP[o]) % MOD;
            dp[o] = prevDP[i]; // no need MOD
            dp[u] = (prevDP[i] + prevDP[o]) % MOD;
            prevDP = dp; dp = new long[5];
        }
        return (int) ((prevDP[a] + prevDP[e] + prevDP[i] + prevDP[o] + prevDP[u]) % MOD);
    }
}
