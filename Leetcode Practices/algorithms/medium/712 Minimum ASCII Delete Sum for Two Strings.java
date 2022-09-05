/**
Given two strings s1 and s2, return the lowest ASCII sum of deleted characters to make two strings equal.

 

Example 1:

Input: s1 = "sea", s2 = "eat"
Output: 231
Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
Deleting "t" from "eat" adds 116 to the sum.
At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.
Example 2:

Input: s1 = "delete", s2 = "leet"
Output: 403
Explanation: Deleting "dee" from "delete" to turn the string into "let",
adds 100[d] + 101[e] + 101[e] to the sum.
Deleting "e" from "leet" adds 101[e] to the sum.
At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.
 

Constraints:

1 <= s1.length, s2.length <= 1000
s1 and s2 consist of lowercase English letters.
 */



// Other's Solution:
class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        // DP - https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/discuss/136346/C%2B%2B-DP
        /*
            二维数组dp[i][j]代表 s1前i个字符 和 s2前j个字符 实现相同（s1==s2） 所需要删除的ASCII value；
            目标：dp[s1的长度][s2的长度]

            推导状态转移公式：
            d[i][j]可能由以下三种方式达到：
            1、dp[i-1][j] + s1[i]：由于从dp[i-1][j]到dp[i][j]是多考虑了s1的一个字符，但是s2字符数没变，所以要想相同，必须删除s1[i],考虑value的话就是加上s1[i]；
            2、dp[i][j-1] + s2[j]：类似地，这个是多考虑s2的一个字符，所以要删除s2[j]，考虑value的话就是加上s2[j]
            3、dp[i-1][j-1] + a，这里是考虑两个string都加了一个字符，当s1[i] =s2[j]时，a=0；当str1[i] ！= str2[j]时，两个都要删除，a=s1[i] +s2[j]

            以上三种情况每次比较出最小的，即为d[i][j]。
        */
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n+1][m+1];
        
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=m; j++) {
                dp[i][j] = (i != 0 || j != 0) ? Integer.MAX_VALUE : 0;
                
                if (i > 0) dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + s1.charAt(i-1));
                if (j > 0) dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + s2.charAt(j-1));
                if (i > 0 && j > 0) {
                    if (s1.charAt(i-1) == s2.charAt(j-1)) {
                        dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1] + 0);
                    } else {
                        dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1] + s1.charAt(i-1) + s2.charAt(j-1));
                    }
                }
            }
        }
        
        return dp[n][m];
    }
}
