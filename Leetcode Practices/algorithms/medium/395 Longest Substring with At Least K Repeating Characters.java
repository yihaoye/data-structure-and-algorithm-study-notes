/*
Given a string s and an integer k, return the length of the longest substring of s such that the frequency of each character in this substring is greater than or equal to k.

 

Example 1:

Input: s = "aaabb", k = 3
Output: 3
Explanation: The longest substring is "aaa", as 'a' is repeated 3 times.
Example 2:

Input: s = "ababbc", k = 2
Output: 5
Explanation: The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 

Constraints:

1 <= s.length <= 104
s consists of only lowercase English letters.
1 <= k <= 105

*/





// Other's Solution:
class Solution {
    public int longestSubstring(String s, int k) {
        /*
            双指针 - https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters/solution/zhi-shao-you-kge-zhong-fu-zi-fu-de-zui-c-o6ww/
            时间复杂度 O(N*26)，空间复杂度 O(26)
        */
        int res = 0, n = s.length();
        for (int t=1; t<=26; t++) {
            int l = 0, r = 0, tot = 0, less = 0;
            int[] cnt = new int[26];
            while (r < n) {
                cnt[s.charAt(r)-'a']++;
                if (cnt[s.charAt(r)-'a'] == 1) {
                    tot++;
                    less++;
                }
                if (cnt[s.charAt(r)-'a'] == k) less--;

                while (tot > t) {
                    cnt[s.charAt(l)-'a']--;
                    if (cnt[s.charAt(l)-'a'] == k-1) less++;
                    if (cnt[s.charAt(l)-'a'] == 0) {
                        tot--;
                        less--;
                    }
                    l++;
                }
                if (less == 0) res = Math.max(res, r-l+1);
                r++;
            }
        }
        return res;
    }
}



// Other's Solution:
class Solution {
    public int longestSubstring(String s, int k) {
        /*
            递归 - https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters/solution/jie-ben-ti-bang-zhu-da-jia-li-jie-di-gui-obla/
            时间复杂度 O(N)，空间复杂度 O(1)
        */
        if (s.length() < k) return 0;
        HashMap<Character, Integer> counter = new HashMap();
        for (int i=0; i<s.length(); i++) {
            counter.put(s.charAt(i), counter.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (char c : counter.keySet()) {
            if (counter.get(c) < k) {
                int res = 0;
                for (String t : s.split(String.valueOf(c))) {
                    res = Math.max(res, longestSubstring(t, k));
                }
                return res;
            }
        }
        return s.length();
    }
}
