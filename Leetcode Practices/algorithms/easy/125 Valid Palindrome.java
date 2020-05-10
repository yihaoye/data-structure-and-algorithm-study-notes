/*
Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.

Note: For the purpose of this problem, we define empty string as valid palindrome.

Example 1:

Input: "A man, a plan, a canal: Panama"
Output: true
Example 2:

Input: "race a car"
Output: false
*/



// My Solution:
class Solution {
    public boolean isPalindrome(String s) {
        char[] cArray = s.toCharArray();
        if (cArray.length <= 1) return true;
        int i = 0, j = cArray.length-1;
        while (true) {
            while(i < cArray.length-1 && !isValid(cArray[i])) i++;
            while(j >= 0 && !isValid(cArray[j])) j--;
            if (i >= j) break;
            if (Character.toLowerCase(cArray[i]) != Character.toLowerCase(cArray[j])) return false;
            i++;
            j--;
        }
        return true;
    }
    
    public boolean isValid(char c) {
        if (c-'a'>=0 && c-'a'<=25) return true;
        if (c-'A'>=0 && c-'A'<=25) return true;
        if (c-'0'>=0 && c-'0'<=9) return true;
        return false;
    }
}