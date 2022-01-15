/*
Given a string s consisting of only the characters 'a' and 'b', return true if every 'a' appears before every 'b' in the string. Otherwise, return false.

 

Example 1:

Input: s = "aaabbb"
Output: true
Explanation:
The 'a's are at indices 0, 1, and 2, while the 'b's are at indices 3, 4, and 5.
Hence, every 'a' appears before every 'b' and we return true.
Example 2:

Input: s = "abab"
Output: false
Explanation:
There is an 'a' at index 2 and a 'b' at index 1.
Hence, not every 'a' appears before every 'b' and we return false.
Example 3:

Input: s = "bbb"
Output: true
Explanation:
There are no 'a's, hence, every 'a' appears before every 'b' and we return true.
 

Constraints:

1 <= s.length <= 100
s[i] is either 'a' or 'b'.
*/



// My Solution:
class Solution {
    public boolean checkString(String s) {
        int firstIndexOfA = -1, lastIndexOfA = -1;
        int firstIndexOfB = -1, lastIndexOfB = -1;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == 'a' && firstIndexOfA == -1) firstIndexOfA = i;
            if (s.charAt(i) == 'a' && firstIndexOfA != -1) lastIndexOfA = i;
            if (s.charAt(i) == 'b' && firstIndexOfB == -1) firstIndexOfB = i;
            if (s.charAt(i) == 'b' && firstIndexOfB != -1) lastIndexOfB = i;
        }
        
        if (firstIndexOfB > lastIndexOfA || firstIndexOfB == -1 || firstIndexOfA == -1) return true;
        return false;
    }
}
