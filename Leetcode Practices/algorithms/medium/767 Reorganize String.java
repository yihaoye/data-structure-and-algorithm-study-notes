/*
Given a string S, check if the letters can be rearranged so that two characters that are adjacent to each other are not the same.

If possible, output any possible result.  If not possible, return the empty string.

Example 1:

Input: S = "aab"
Output: "aba"
Example 2:

Input: S = "aaab"
Output: ""
Note:

S will consist of lowercase letters and have length in range [1, 500].
*/



// My Solution (greedy + 暴力算法):
class Solution {
    public String reorganizeString(String S) {
        int[] letters = new int[26];
        char[] cArr = S.toCharArray();
        for (char c : cArr) letters[c-'a']++;
        
        int preCharIndex = -1;
        StringBuilder strB = new StringBuilder();
        for (int i=0; i<cArr.length; i++) {
            int nextCharIndex = findNextCharIndex(preCharIndex, letters);
            if (letters[nextCharIndex] < 0) return "";
            strB.append((char)(nextCharIndex + 'a'));
            preCharIndex = nextCharIndex;
        }
        
        return strB.toString();
    }
    
    public int findNextCharIndex(int preCharIndex, int[] letters) {
        int nextCharIndex = -1;
        for (int i=0; i<letters.length; i++) {
            if (i == preCharIndex) continue;
            if (nextCharIndex == -1 || letters[i] >= letters[nextCharIndex]) nextCharIndex = i;
        }
        letters[nextCharIndex]--;
        return nextCharIndex;
    }
}
