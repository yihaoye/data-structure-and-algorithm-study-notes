/*
Given two strings s and goal, return true if you can swap two letters in s so the result is equal to goal, otherwise, return false.

Swapping letters is defined as taking two indices i and j (0-indexed) such that i != j and swapping the characters at s[i] and s[j].

For example, swapping at indices 0 and 2 in "abcd" results in "cbad".
 

Example 1:

Input: s = "ab", goal = "ba"
Output: true
Explanation: You can swap s[0] = 'a' and s[1] = 'b' to get "ba", which is equal to goal.
Example 2:

Input: s = "ab", goal = "ab"
Output: false
Explanation: The only letters you can swap are s[0] = 'a' and s[1] = 'b', which results in "ba" != goal.
Example 3:

Input: s = "aa", goal = "aa"
Output: true
Explanation: You can swap s[0] = 'a' and s[1] = 'a' to get "aa", which is equal to goal.
Example 4:

Input: s = "aaaaaaabc", goal = "aaaaaaacb"
Output: true
 

Constraints:

1 <= s.length, goal.length <= 2 * 104
s and goal consist of lowercase letters.
*/



// My Solution:
class Solution {
    public boolean buddyStrings(String s, String goal) {
        /*
            思路 - 使用一个 2*26 二维数组统计每个字符（必须一致）、和一个 count 记录不同位置的个数（若大于 2 或等于 1 则直接返回 false，若等于 0 则需至少一个字符统计多于 1）
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        if (s.length() != goal.length()) return false;
        char[] sArr = s.toCharArray();
        char[] gArr = goal.toCharArray();
        int count = 0;
        int[][] chars = new int[2][26];
        for (int i=0; i<sArr.length; i++) {
            if (sArr[i] != gArr[i]) count++;
            chars[0][sArr[i]-'a']++;
            chars[1][gArr[i]-'a']++;
        }
        if (count > 2 || count == 1) {
            return false;
        } else if (count == 2) {
            for (int i=0; i<26; i++) {
                if (chars[0][i] != chars[1][i]) return false;
            }
            return true;
        }
        else { // count == 0
            for (int i=0; i<26; i++) {
                if (chars[0][i] >= 2) return true;
            }
            return false;
        }
    }
}
