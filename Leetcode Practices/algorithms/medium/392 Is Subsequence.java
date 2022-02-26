/*
Given a string s and a string t, check if s is subsequence of t.

You may assume that there is only lower case English letters in both s and t. t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).

Example 1:
s = "abc", t = "ahbgdc"

Return true.

Example 2:
s = "axc", t = "ahbgdc"

Return false.

Follow up:
If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence. In this scenario, how would you change your code?
*/



// Other's Solution: (Greedy - 贪心算法)
class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return true;
        int idx = 0;
        for (int i = 0; i < t.length(); i++) {
            if (s.charAt(idx) == t.charAt(i)) idx++;
            if (idx == s.length()) return true;
        }
        return false;
    }
}



// My Solution:
class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return true;
        int findIndex = t.indexOf(s.charAt(0));
        if (findIndex != -1) return isSubsequence(s.substring(1), t.substring(findIndex+1));
        return false;
    }
}



// My Solution:
class Solution {
    public boolean isSubsequence(String s, String t) {
        /*
            思路 - 记录 s match 的位置，只能递增，当全部 match 后返回 true 否则 false
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        if (s.length() == 0) return true;
        int match = 0;
        for (int i=0; i<t.length(); i++) {
            if (s.charAt(match) == t.charAt(i)) match++;
            if (match == s.length()) return true;
        }
        
        return false;
    }
}