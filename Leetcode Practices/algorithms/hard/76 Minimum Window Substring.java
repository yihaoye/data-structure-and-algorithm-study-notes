/**
Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".

The testcases will be generated such that the answer is unique.

A substring is a contiguous sequence of characters within the string.

 

Example 1:

Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
Example 2:

Input: s = "a", t = "a"
Output: "a"
Explanation: The entire string s is the minimum window.
Example 3:

Input: s = "a", t = "aa"
Output: ""
Explanation: Both 'a's from t must be included in the window.
Since the largest window of s only has one 'a', return empty string.
 

Constraints:

m == s.length
n == t.length
1 <= m, n <= 10^5
s and t consist of uppercase and lowercase English letters.
 

Follow up: Could you find an algorithm that runs in O(m + n) time?
 */



// My Solution:
class Solution {
    public String minWindow(String s, String t) {
        // 双指针 + 哈希表 + 哨兵变量
        int m = s.length(), n = t.length(), res = Integer.MAX_VALUE, resL = -1, resR = -1;
        if (m < n) return "";
        Map<Character, Integer> tMap = new HashMap<>();
        
        for (int i=0; i<n; i++) {
            tMap.put(t.charAt(i), tMap.getOrDefault(t.charAt(i), 0) + 1);
        }
        int cnt = n, l = 0, r = -1;
        boolean noMove = true;
        while (noMove) {
            noMove = false;
            while (cnt == 0 && l <= r) {
                char lChar = s.charAt(l);
                if (tMap.containsKey(lChar)) {
                    int lCharCnt = tMap.get(lChar);
                    if (lCharCnt == 0) cnt++;
                    tMap.put(lChar, lCharCnt + 1);
                }
                l++;
                noMove = true;
                if (cnt == 0 && r-l+1 < res) {
                    res = r-l+1;
                    resL = l;
                    resR = r;
                }
            }
            while (cnt > 0 && ++r < m) {
                noMove = true;
                char rChar = s.charAt(r);
                if (tMap.containsKey(rChar)) {
                    int rCharCnt = tMap.get(rChar);
                    if (rCharCnt > 0) cnt--;
                    tMap.put(rChar, rCharCnt - 1);
                }
                if (cnt == 0 && r-l+1 < res) {
                    res = r-l+1;
                    resL = l;
                    resR = r;
                }
            }
        }
        if (res == Integer.MAX_VALUE) return "";
        return s.substring(resL, resR + 1);
    }
}
