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
First we define a spot as where next character in the string is different from previous one. 
j keeps track of the last spot. And j specifically points to the first char in the last spot. 
For example if "ab" is the last spot, then j points to 'a'.

if code gets to second if, it means s[k] != s[k-1]. we find a new spot.

j>-1 means if last spot exists, s[k]!=s[j] means first char in the last spot is different from the second char in the new spot. 
So second if means if s[k] is the 3rd char. 
and we need to update the substring starting point in this case such that new substring still only has two unique char. 
So we update the starting point i to the second char in the last spot, which is j+1. 
This makes sure all 1st chars are removed and at the same time keep 2nd char as many as possible.
finally, we update j pointing to the new spot.

As you can see the key idea here is that we can update substring starting point by only keeping track of the last spot where character changes.
*/
