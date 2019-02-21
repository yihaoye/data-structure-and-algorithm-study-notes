// Question:
/*
Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

Example 1:

Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
Example 2:

Input: "cbbd"
Output: "bb"
*/


// Other's Solution:
public class Solution {
    private int lo, maxLen;
    
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2)
            return s;
        
        for (int i = 0; i < len-1; i++) {
             extendPalindrome(s, i, i);  //assume odd length, try to extend Palindrome as possible
             extendPalindrome(s, i, i+1); //assume even length.
        }
        return s.substring(lo, lo + maxLen);
    }
    
    private void extendPalindrome(String s, int j, int k) {
        while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
            j--;
            k++;
        }
        if (maxLen < k - j - 1) {
            lo = j + 1;
            maxLen = k - j - 1;
        }
    }
}


// My Solution:
class Solution {
    public String longestPalindrome(String s) {
        if (isPalindrome(s)) {
            return s;
        } else {
            String s1 = longestPalindrome(s.substring(0, s.length()-1));
            String s2 = longestPalindrome(s.substring(1, s.length()));
            return (s1.length() >= s2.length()) ? s1 : s2;
        }
    }
    
    public Boolean isPalindrome(String str) {
        if (str.length() <= 1) {
        } else if (str.charAt(0) == str.charAt(str.length()-1)) {
            if (str.length() == 2) {
                return true;
            }
            str = str.substring(1, str.length()-1);
            return isPalindrome(str);
        } else {
            return false;
        }
        return true;
    }
}