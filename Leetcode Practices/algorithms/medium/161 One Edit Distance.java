/**
Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.

A string s is said to be one distance apart from a string t if you can:

Insert exactly one character into s to get t.
Delete exactly one character from s to get t.
Replace exactly one character of s with a different character to get t.
 

Example 1:

Input: s = "ab", t = "acb"
Output: true
Explanation: We can insert 'c' into s to get t.
Example 2:

Input: s = "", t = ""
Output: false
Explanation: We cannot get t from s by only one step.
 

Constraints:

0 <= s.length, t.length <= 10^4
s and t consist of lowercase letters, uppercase letters, and digits.
 */



// My Solution:
class Solution {
    public boolean isOneEditDistance(String s, String t) {
        // HashSet
        // Time: O(N), Space: O(1)
        int sLen = s.length(), tLen = t.length();
        if (Math.abs(sLen - tLen) > 1) return false;
        if (sLen != tLen) return sLen < tLen ? handleDiffLen(s, t) : handleDiffLen(t, s);
        return handleSameLen(s, t);
    }
    
    public boolean handleDiffLen(String shorter, String longer) {
        Set<Character> set = new HashSet<>();
        int findDif = 0;
        for (int i=0, j=0; i<shorter.length() && j<longer.length(); i++, j++) {
            set.add(shorter.charAt(i));
            while (!set.contains(longer.charAt(j))) {
                if (findDif++ > 0) return false;
                j++;
            }
            set.remove(longer.charAt(j));
        }
        
        return true;
    }
    
    public boolean handleSameLen(String strA, String strB) {
        Set<Character> set = new HashSet<>();
        int findDif = 0;
        for (int i=0, j=0; i<strA.length() && j<strB.length(); i++, j++) {
            set.add(strA.charAt(i));
            set.remove(strB.charAt(j));
            if (set.size() > 0) {
                if (findDif++ > 0) return false;
                set.remove(strA.charAt(i));
            }
        }
        
        return findDif == 1;
    }
}
