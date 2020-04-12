/*
A string is called a happy prefix if is a non-empty prefix which is also a suffix (excluding itself).

Given a string s. Return the longest happy prefix of s .

Return an empty string if no such prefix exists.

 

Example 1:

Input: s = "level"
Output: "l"
Explanation: s contains 4 prefix excluding itself ("l", "le", "lev", "leve"), and suffix ("l", "el", "vel", "evel"). The largest prefix which is also suffix is given by "l".
Example 2:

Input: s = "ababab"
Output: "abab"
Explanation: "abab" is the largest prefix which is also suffix. They can overlap in the original string.
Example 3:

Input: s = "leetcodeleet"
Output: "leet"
Example 4:

Input: s = "a"
Output: ""
 

Constraints:

1 <= s.length <= 10^5
s contains only lowercase English letters.
*/



// My Solution (KMP):
class Solution {
    public String longestPrefix(String s) {
        int len = s.length();
        int[] pmt = PMT(s);
        StringBuilder strB = new StringBuilder();
        for (int i=0; i<pmt[len]; i++) {
            strB.append(s.charAt(i));
        }
        
        return strB.toString();
    }
    
    public static int[] PMT(String pat) {
        int pmt[] = new int[pat.length()+1];
        pmt[0] = 0;
        pmt[1] = 0;
        for (int i=1, j=0; i < pat.length(); i++) {
            while (j > 0 && pat.charAt(i) != pat.charAt(j)) j = pmt[j];
            if (pat.charAt(i) == pat.charAt(j)) j++;
            pmt[i+1] = j;
        }
        return pmt;
    }
}