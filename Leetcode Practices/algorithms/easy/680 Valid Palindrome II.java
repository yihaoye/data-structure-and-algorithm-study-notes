/*
Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.

Example 1:
Input: "aba"
Output: True
Example 2:
Input: "abca"
Output: True
Explanation: You could delete the character 'c'.
Note:
The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
*/



// My Solution (O(N)):
class Solution {
    public boolean validPalindrome(String s) {
        // two pointers
        int count = 0;
        for (int i=0, j=s.length()-1; i<=j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                if (count > 0) return false;
                else count++;
                
                if (i+1 != j && s.charAt(i+1) == s.charAt(j) && s.charAt(i) == s.charAt(j-1)) { // edge case handler
                    if (s.charAt(i+2) == s.charAt(j-1)) j++;
                    else if (s.charAt(i+1) == s.charAt(j-2)) i--;
                }
                else if (s.charAt(i+1) == s.charAt(j)) j++;
                else if (s.charAt(i) == s.charAt(j-1)) i--;
                else return false;
            }
        }
        return true;
    }
}
