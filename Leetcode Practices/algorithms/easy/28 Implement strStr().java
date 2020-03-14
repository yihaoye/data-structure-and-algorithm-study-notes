/*
Implement strStr().

Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

Example 1:

Input: haystack = "hello", needle = "ll"
Output: 2
Example 2:

Input: haystack = "aaaaa", needle = "bba"
Output: -1
Clarification:

What should we return when needle is an empty string? This is a great question to ask during an interview.

For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
*/



// My Solution:
class Solution {
    public int strStr(String haystack, String needle) {
        char[] haystackChars = haystack.toCharArray();
        char[] needleChars = needle.toCharArray();
        int hLen = haystackChars.length, nLen = needleChars.length;
        if (nLen == 0) return 0;
        int res = -1;
        for (int i=0; hLen-i >= nLen; i++) {
            if (check(i, haystackChars, needleChars)) {
                res = i;
                break;
            }
        }
        
        return res;
    }
    
    public boolean check(int index, char[] haystackChars, char[] needleChars) {
        for (int i=0; i<needleChars.length; i++) {
            if (haystackChars[i+index] != needleChars[i]) return false;
        }
        
        return true;
    }
}