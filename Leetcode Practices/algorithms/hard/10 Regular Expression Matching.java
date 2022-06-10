/*
Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*' where: 

'.' Matches any single character.​​​​
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

 

Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
Example 3:

Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
Example 4:

Input: s = "aab", p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
Example 5:

Input: s = "mississippi", p = "mis*is*p*."
Output: false
 

Constraints:

0 <= s.length <= 20
0 <= p.length <= 30
s contains only lowercase English letters.
p contains only lowercase English letters, '.', and '*'.
It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.
*/





// Other's Solution (DP - 时间复杂度 O(N*M)，空间复杂度 O(N*M)):
class Solution {
    public boolean isMatch(String s, String p) {
        // corner case
        if (s == null || p == null) return false;

        boolean[][] dp = new boolean[s.length()+1][p.length()+1];

        // initialization: 
		// dp[0][0] = true, since empty string matches empty pattern
		dp[0][0] = true;
        // https://youtu.be/ym1zpF7sInc?t=101
        for (int j = 2; j <= p.length(); j++) {
            if (p.charAt(j-1) == '*') { // 注意是 p.charAt(j-1)，而不是 p.charAt(j)，因为 dp 数组的 j 的 0 为 null 其实为字符串第 -1 位
                dp[0][j] = dp[0][j-2];
            }
        }

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.') { // 这里的 s.charAt(i) 和 p.charAt(j) 正好对应 dp[i+1][x] 和 dp[x][j+1]，原因如上 - dp 数组的 i 和 j 的 0 为 null 其实为字符串第 -1 位
                    dp[i+1][j+1] = dp[i][j];
                } else if (p.charAt(j) == '*') {
                    if (p.charAt(j-1) == s.charAt(i) || p.charAt(j-1) == '.') {
                        dp[i+1][j+1] = dp[i+1][j-1] || dp[i][j+1];
                    } else {
                        dp[i+1][j+1] = dp[i+1][j-1];
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}
/*
dp[i][j] means whether [0...j] of p regex match [0...i] of s.

1, If p.charAt(j) == s.charAt(i) : dp[i][j] = dp[i-1][j-1];
2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
3, If p.charAt(j) == '*': 
   here are two sub conditions:
                    If dp[i][j-2] == true :
                            dp[i][j] = dp[i][j-2]  // in this case, a* only counts as empty (e.g. Example4's "c*" : Input: s = "aab", p = "c*a*b")
                    Or (逻辑或) If p.charAt(j-1) == s.charAt(i) or p.charAt(j-1) == '.' :
                            dp[i][j] = dp[i-1][j]   // in this case, a* counts as single - multiple a. (https://youtu.be/ym1zpF7sInc?t=302)
                            // Leetcode 最高票题解还检查并 or dp[i][j-1]，实际上不需要，因为只要 dp[i][j-1] == true 则 dp[i-1][j] 必为 true，但是反之未必 (参考 https://youtu.be/ym1zpF7sInc?t=419 的 dp[s.length()-1][p.length()-2] 与 dp[s.length()-2][p.length()-1])
*/
