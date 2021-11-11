/*
Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.

You have the following three operations permitted on a word:

Insert a character
Delete a character
Replace a character
 

Example 1:

Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
Example 2:

Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')
 

Constraints:

0 <= word1.length, word2.length <= 500
word1 and word2 consist of lowercase English letters.
*/



// Other's Solution:
class Solution {
    public int minDistance(String word1, String word2) {
        /*
            答案思路 - DP，dp[i][j] 代表 word1 到 i 位置转换成 word2 到 j 位置需要最少步数。所以，当 word1[i] == word2[j]，dp[i][j] = dp[i-1][j-1]；当 word1[i] != word2[j]，dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1。其中，dp[i-1][j-1] 表示替换操作，dp[i-1][j] 表示删除操作，dp[i][j-1] 表示插入操作。
            时间复杂度 O(N*M)，空间复杂度 O(N*M)
            参考（https://leetcode-cn.com/problems/edit-distance/solution/zi-di-xiang-shang-he-zi-ding-xiang-xia-by-powcai-3/）
        */
        int rowLen = word1.length() + 1, colLen = word2.length() + 1;
        int[][] dp = new int[rowLen][colLen]; // 两个维度长度皆为两个字符串的长度+1，因为考虑到字符串可以为空的场景，因此需要多一位存储空字符串（word1 或和 word2）的情况
        
        for (int i=0; i<rowLen; i++) dp[i][0] = i;
        for (int j=0; j<colLen; j++) dp[0][j] = j;
        for (int i=1; i<rowLen; i++) {
            for (int j=1; j<colLen; j++) {
                // word1 和 word2 当前字符对比应该为 i-1 和 j-1，因为它们的首字符索引为 0，而 dp 数组索引 0 代表空字符串而索引 1 才代表首字符，所以对 word 索引字符时应比 dp 数组低一位才能对应
                if (word1.charAt(i-1) == word2.charAt(j-1)) dp[i][j] = dp[i-1][j-1];
                else dp[i][j] = Math.min(Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1]) + 1;
            }
        }
        
        return dp[rowLen-1][colLen-1];
    }
}
