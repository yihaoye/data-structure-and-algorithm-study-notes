/*
You are given a string s and an integer k. You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most k times.

Return the length of the longest substring containing the same letter you can get after performing the above operations.

 

Example 1:

Input: s = "ABAB", k = 2
Output: 4
Explanation: Replace the two 'A's with two 'B's or vice versa.
Example 2:

Input: s = "AABABBA", k = 1
Output: 4
Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters, which is 4.
There may exists other ways to achive this answer too.
 

Constraints:

1 <= s.length <= 10^5
s consists of only uppercase English letters.
0 <= k <= s.length
*/



// My Solution:
class Solution {
    public int characterReplacement(String s, int k) {
        // two pointers + hashmap + greedy
        // Time: O(N)
        int[] map = new int[26];
        int l = 0, r = 0, res = 0;
        map[s.charAt(r) - 'A']++;
        while (l < s.length() - 1) {
            boolean success = check(map, k, r - l + 1);
            if (success) res = Math.max(res, r - l + 1);
            if (success && r < s.length() - 1) map[s.charAt(++r) - 'A']++;
            else map[s.charAt(l++) - 'A']--; // must be l < r，最后一个 l 虽然会 miss 掉，但是因为最多只能是 1 所以可以忽略
        }

        return res;
    }

    public boolean check(int[] map, int k, int len) {
        for (int a : map) {
            if (a + k >= len) return true;
        }
        return false;
    }
}
