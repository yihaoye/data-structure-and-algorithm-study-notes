/**
Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:

'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

 

Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input: s = "aa", p = "*"
Output: true
Explanation: '*' matches any sequence.
Example 3:

Input: s = "cb", p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 

Constraints:

0 <= s.length, p.length <= 2000
s contains only lowercase English letters.
p contains only lowercase English letters, '?' or '*'.
 */



// My Solution:
class Solution {
    public boolean isMatch(String s, String p) {
        // 2D DP
        // boolean[][] dp - dp[i][j] == true means s[0-i] match p[0-j]
        int sLen = s.length(), pLen = p.length();
        boolean[] onlyStar = new boolean[pLen]; // onlyStar[i] means p[0..i] only contains star *
        for (int j=0; j<pLen; j++) {
            if (p.charAt(j) != '*') break;
            onlyStar[j] = true;
        }
        
        // edge cases
        if (sLen == 0 && pLen == 0) return true;
        if (sLen == 0) return onlyStar[pLen-1];
        if (pLen == 0) return false; // 唯一可能 true 的 sLen == 0 已经在前面处理了
        
        boolean[][] dp = new boolean[sLen][pLen];
        boolean[][] prefixOrRow = new boolean[sLen][pLen]; // 前缀或，dp[0..i][j] 只要有一个为 true，则 prefixOrRow[i][j] 就为 true
        for (int i=0; i<sLen; i++) {
            for (int j=0; j<pLen; j++) {
                if (p.charAt(j) == '*') {
                    if (j == 0) dp[i][j] = true;
                    else dp[i][j] = prefixOrRow[i][j] || prefixOrRow[i][j-1]; // core logic
                } else if (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i)) {
                    if (i > 0 && j > 0) dp[i][j] = dp[i-1][j-1];
                    if (i == 0 && j == 0) dp[i][j] = true;
                    if (i == 0 && j > 0 && onlyStar[j-1]) dp[i][j] = dp[i][j-1]; // 必须 p[0..j-1] 均是 *。同时 i > 0 && j == 0 不可能因为只能是 * 已经被上面的逻辑覆盖，这里不可能
                }
                prefixOrRow[i][j] = dp[i][j] || (i > 0 ? prefixOrRow[i-1][j] : false);
            }
        }
        
        return dp[sLen-1][pLen-1];
    }
}



// Other's Solution:
class Solution {
    public boolean isMatch(String str, String pattern) {
        // https://leetcode.com/problems/wildcard-matching/discuss/17810/Linear-runtime-and-constant-space-solution
        int s = 0, p = 0, match = 0, starIdx = -1;            
        while (s < str.length()) {
            if (p < pattern.length() && (pattern.charAt(p) == '?' || str.charAt(s) == pattern.charAt(p))) { // advancing both pointers
                s++; p++;
            } else if (p < pattern.length() && pattern.charAt(p) == '*') { // * found, only advancing pattern pointer
                starIdx = p++;
                match = s;
            } else if (starIdx != -1) { // last pattern pointer was *, advancing string pointer
                p = starIdx + 1;
                s = ++match;
            } else {
                return false; // current pattern pointer is not star, last patter pointer was not *, characters do not match
            }
        }

        while (p < pattern.length() && pattern.charAt(p) == '*') p++; // check for remaining characters in pattern

        return p == pattern.length();
    }
}
