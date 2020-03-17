/*
Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word (last word means the last appearing word if we loop from left to right) in the string.

If the last word does not exist, return 0.

Note: A word is defined as a maximal substring consisting of non-space characters only.

Example:

Input: "Hello World"
Output: 5
*/



// My Solution:
class Solution {
    public int lengthOfLastWord(String s) {
        char[] cArray = s.trim().toCharArray();
        int res = 0;
        for (int i=cArray.length-1; i>=0; i--) {
            if (cArray[i] != ' ') res++;
            else break;
        }
        
        return res;
    }
}