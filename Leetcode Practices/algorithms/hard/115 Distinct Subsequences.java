/*
Given two strings s and t, return the number of distinct 
subsequences
 of s which equals t.

The test cases are generated so that the answer fits on a 32-bit signed integer.

 

Example 1:

Input: s = "rabbbit", t = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from s.
rabbbit
rabbbit
rabbbit
Example 2:

Input: s = "babgbag", t = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from s.
babgbag
babgbag
babgbag
babgbag
babgbag
 

Constraints:

1 <= s.length, t.length <= 1000
s and t consist of English letters.

*/



// My Solution:
class Solution {
    public int numDistinct(String s, String t) {
        // DP + HashMap
        // Time: O(N*M^2), Space: O(N*M)
        int n = s.length(), m = t.length();
        if (n < m) return 0;

        Map<Character, List<Integer>> cMap = new HashMap<>(); // <char, <index in t...>>
        for (int i=0; i<m; i++) cMap.computeIfAbsent(t.charAt(i), k -> new ArrayList<>()).add(i);

        int[][] dp = new int[m][m]; // dp[x][y] means how many combination can match to t[0..x] which end with y --> y >= x && t[x] == t[y] --> dp[x][y] = dp[x-1][0] + ... + dp[x-1][y-1]
        for (int i=0; i<n; i++) {
            char sChar = s.charAt(i);
            for (int j=Math.min(i, m-1); j>=0; j--) { // 反向，避免循环时影响到后面的计算
                char tChar = t.charAt(j);
                if (sChar != tChar) continue;

                // sChar == tChar
                if (j == 0) dp[j][j]++;
                for (int tCharIndex : cMap.get(tChar)) {
                    if (tCharIndex > j) break;
                    if (tCharIndex > 0) dp[tCharIndex][j] += dp[tCharIndex-1][tCharIndex-1];
                }
            }
        }

        return dp[m-1][m-1];
    }
}
