//Question:
/*
3. Longest Substring Without Repeating Characters:

Given a string, find the length of the longest substring without repeating characters.

Examples:

Given "abcabcbb", the answer is "abc", which the length is 3.

Given "bbbbb", the answer is "b", with the length of 1.

Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
*/




//Offitial Answer:
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }
};


//Offitial Answer2:
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}



// My Solution:
class Solution {
    public int lengthOfLongestSubstring(String s) {
        // two pointer + hashmap
        // Time: O(N), Space: O(1) - count of unique chars is constant
        if (s.length() == 0) return 0;
        Map<Character, Integer> map = new HashMap<>();
        int l = 0, r = 0, res = 1;
        map.put(s.charAt(l), l);
        while (++r < s.length()) {
            if (l == r) continue;

            if (map.containsKey(s.charAt(r)) && map.get(s.charAt(r)) >= l) { // move left pointer to the one whch is after next index of previous duplicate char's index
                l = map.get(s.charAt(r)) + 1;
            } else {
                res = Math.max(res, r - l + 1);
            }
            map.put(s.charAt(r), r);
        }

        return res;
    }
}
