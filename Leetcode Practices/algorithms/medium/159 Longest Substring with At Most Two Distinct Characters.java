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