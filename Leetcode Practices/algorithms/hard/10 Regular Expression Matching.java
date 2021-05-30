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





// Other's Solution (DP):
class Solution {
    public boolean isMatch(String s, String p) {
        // corner case
        if (s == null || p == null) return false;

        boolean[][] dp = new boolean[s.length()+1][p.length()+1];

        // initialization: 
		// 1. dp[0][0] = true, since empty string matches empty pattern
		dp[0][0] = true;

		// 2. dp[i][0] = false (which is default value of the boolean array) since empty pattern cannot match non-empty string
		// 3. dp[0][j]: what pattern matches empty string ""? It should be #*#*#*#*..., or (#*)* if allow me to represent regex using regex :P, 
		// and for this case we need to check manually: 
        // as we can see, the length of pattern should be even && the character at the even position should be *, 
		// thus for odd length, dp[0][j] = false which is default. So we can just skip the odd position, i.e. j starts from 2, the interval of j is also 2. 
		// and notice that the length of repeat sub-pattern #* is only 2, we can just make use of dp[0][j-2] rather than scanning j length each time 
		// for checking if it matches #*#*#*#*.
        for (int j = 2; j <= p.length(); j += 2) {
            if (p.charAt(j-1) == '*' && dp[0][j-2]) {
                dp[0][j] = true;
            }
        }

        for (int i = 0 ; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == '.') {
                    dp[i+1][j+1] = dp[i][j];
                }
                if (p.charAt(j) == s.charAt(i)) {
                    dp[i+1][j+1] = dp[i][j];
                }
                if (p.charAt(j) == '*') {
                    if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                        dp[i+1][j+1] = dp[i+1][j-1];
                    } else {
                        dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}
/*
dp[i][j] means whether [0...j] of p regex match [0...i] of s.

1, If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
3, If p.charAt(j) == '*': 
   here are two sub conditions:
               3.1   if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]  // in this case, a* only counts as empty (e.g. Example4's "c*" : Input: s = "aab", p = "c*a*b")
               3.2   if p.charAt(j-1) == s.charAt(i) or p.charAt(j-1) == '.':
                                dp[i][j] = dp[i-1][j]   // in this case, a* counts as multiple a (if p[j] == * && p[j-1] ~= s[i] && dp[i-1][j] == true, it means p[j-1..j]:s[i-1..i] ~= a*:aa i.e. s[i-1] == s[i]?. https://youtu.be/l3hda49XcDE?t=764)
                                        or dp[i][j-1]   // in this case, a* counts as single a
                                        or dp[i][j-2]   // in this case, a* counts as empty (丢弃 p[j-1] 和 p[j]，按 3.1 来)
*/
