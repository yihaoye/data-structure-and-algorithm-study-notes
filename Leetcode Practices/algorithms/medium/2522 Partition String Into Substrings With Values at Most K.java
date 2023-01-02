/**
You are given a string s consisting of digits from 1 to 9 and an integer k.

A partition of a string s is called good if:

Each digit of s is part of exactly one substring.
The value of each substring is less than or equal to k.
Return the minimum number of substrings in a good partition of s. If no good partition of s exists, return -1.

Note that:

The value of a string is its result when interpreted as an integer. For example, the value of "123" is 123 and the value of "1" is 1.
A substring is a contiguous sequence of characters within a string.
 

Example 1:

Input: s = "165462", k = 60
Output: 4
Explanation: We can partition the string into substrings "16", "54", "6", and "2". Each substring has a value less than or equal to k = 60.
It can be shown that we cannot partition the string into less than 4 substrings.
Example 2:

Input: s = "238182", k = 5
Output: -1
Explanation: There is no good partition for this string.
 

Constraints:

1 <= s.length <= 10^5
s[i] is a digit from '1' to '9'.
1 <= k <= 10^9
 */



// My Solution:
class Solution {
    public int minimumPartition(String s, int k) {
        // DP - dp[i] means min substring in range s[0..i], dp[i+1] = min(dp[i], dp[i-1], ... dp[i-j]) + 1, && s[i-j+1..i+1] <= k
        int n = s.length(), kCnt = countK(k);
        int curNum = 0, curNumMod = (int) 1e8, kCntMod = (int) Math.pow(10, kCnt);
        int[] dp = new int[n];
        for (int i=0; i<n; i++) {
            int curDigit = s.charAt(i) - '0';
            if (curDigit > k) return -1;

            curNum %= curNumMod;
            curNum = curNum * 10 + curDigit;
            dp[i] = i + 1; // 最多也就是每个字符都拆分
            for (int j=1; j<kCnt && i>=j; j++) {
                dp[i] = Math.min(dp[i-j] + 1, dp[i]);
            }
            int curMod = curNum % kCntMod;
            if (i >= kCnt && curMod <= k) dp[i] = Math.min(dp[i-kCnt] + 1, dp[i]);
            if (i < kCnt && curMod <= k) dp[i] = Math.min(1, dp[i]);
        }
        
        return dp[n-1];
    }
    
    private int countK(int k) { // how many digit k has
        int res = 0;
        while (k > 0) {
            res++;
            k /= 10;
        }
        return res;
    }
}
