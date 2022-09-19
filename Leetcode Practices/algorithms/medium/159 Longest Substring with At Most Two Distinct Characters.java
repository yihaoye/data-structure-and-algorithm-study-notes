/*
Given a string s , find the length of the longest substring t  that contains at most 2 distinct characters.

Example 1:

Input: "eceba"
Output: 3
Explanation: t is "ece" which its length is 3.
Example 2:

Input: "ccaabbb"
Output: 5
Explanation: t is "aabbb" which its length is 5.
*/



// My Solution (using flag):
class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        char[] dc = new char[2]; // distinctChars
        int[] dcc = new int[2]; // distinctCharsCounts
        int[] dcc2 = new int[2]; // distinctChars continuous Counts
        char[] arr = s.toCharArray();
        int res = 0;
        
        for (char c : arr) {
            if (dcc[0] == 0) {
                dc[0] = c;
                dcc[0] = 1;
                dcc2[0] = 1;
            }
            else if (dcc[1] == 0) {
                if (dc[0] == c) {
                    dcc[0]++;
                    dcc2[0] = dcc[0];
                } else {
                    dc[1] = c;
                    dcc[1] = 1;
                    dcc2[1] = 1;
                }
            }
            else if (dc[0] != c && dc[1] != c) {
                if (dcc[0] + dcc[1] > res) res = dcc[0] + dcc[1];
                dc[0] = dc[1];
                dc[1] = c;
                dcc[0] = dcc2[1];
                dcc2[0] = dcc2[1];
                dcc[1] = 1;
                dcc2[1] = 1;
            } else if (dc[0] == c) {
                dcc[0]++;
                swapChars(dc, dcc, dcc2);
            } else if (dc[1] == c) {
                dcc[1]++;
                dcc2[1]++;
            }
        }
        if (dcc[0] + dcc[1] > res) res = dcc[0] + dcc[1];
        
        return res;
    }
    
    public void swapChars(char[] dc, int[] dcc, int[] dcc2) {
        char tempC = dc[0];
        dc[0] = dc[1];
        dc[1] = tempC;
        
        int tempCt = dcc[0];
        dcc[0] = dcc[1];
        dcc[1] = tempCt;
        
        dcc2[0] = 0;
        dcc2[1] = 1;
    }
}



// Other's Solution:
class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int i = 0, j = -1;
        int maxLen = 0;
        for (int k = 1; k < s.length(); k++) {
            if (s.charAt(k) == s.charAt(k-1)) continue;
            if (j > -1 && s.charAt(k) != s.charAt(j)) {
                maxLen = maxLen > (k-i) ? maxLen : (k-i);
                i = j + 1;
            }
            j = k - 1;
        }
        return maxLen > (s.length() - i) ? maxLen : s.length() - i;
    }
}
/*
https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/discuss/49687/Clean-11-lines-AC-answer-O(1)-space-O(n)-time./117115

i and j keeps track of the current substring's 2 key spot. And i specifically points to the first index of the substring. j specifically points to the index before first index of last continuous char of the current substring. 
For example if "abaabbb" is the substring, then i points to index 0, then j points to index 3.

if code gets to second if, it means s[k] != s[k-1]. we find a new spot. 

j>-1 means if second char exist, (j > -1 && s[k] != s[j]) means if means if s[k] is the 3rd char. 
and we need to update the substring starting point in this case such that new substring still only has two unique char. 

So we update the starting point i to the first index of last continuous char of the previous substring, which is j+1. 
This makes sure all 1st chars are removed and at the same time keep 2nd char as many as possible.
finally, we update j pointing to the new spot (index before first index of last continuous char of the new substring).

As you can see the key idea here is that we can update substring starting point by only keeping track of the last spot where character changes.
*/



// My Solution 2:
class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        // Two pointer + HashMap
        int left = 0, right = 0, res = 1;
        Map<Character, Integer> count = new HashMap<>();
        count.put(s.charAt(left), 1);
        while (true) {
            if (count.size() > 2) {
                int lCnt = count.get(s.charAt(left));
                if (lCnt == 1) count.remove(s.charAt(left));
                else count.put(s.charAt(left), lCnt - 1);
                left++;
            } else {
                res = Math.max(res, right - left + 1);
                if (++right < s.length()) count.put(s.charAt(right), count.getOrDefault(s.charAt(right), 0) + 1);
                else break;
            }
        }
        return res;
    }
}
